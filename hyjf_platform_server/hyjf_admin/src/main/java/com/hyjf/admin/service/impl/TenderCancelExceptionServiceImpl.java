/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.TenderCancelExceptionService;
import com.hyjf.am.resquest.admin.TenderCancelExceptionRequest;
import com.hyjf.am.vo.admin.FreezeHistoryVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: TenderCancelExceptionServiceImpl, v0.1 2018/7/11 10:00
 */
@Service
public class TenderCancelExceptionServiceImpl extends BaseAdminServiceImpl implements TenderCancelExceptionService {

    @Value("${hyjf.bank.bankcode}")
    private String BANK_BANKCODE;

    @Value("${hyjf.bank.instcode}")
    private String BANK_INSTCODE;

    /**
     * 根据筛选条件查询银行出借撤销异常的数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getTenderCancelExceptionCount(TenderCancelExceptionRequest request) {
        return amTradeClient.getTenderCancelExceptionCount(request);
    }

    /**
     * 根据筛选条件查询银行出借撤销异常list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<BorrowTenderTmpVO> searchTenderCancelExceptionList(TenderCancelExceptionRequest request) {
        return amTradeClient.searchTenderCancelExceptionList(request);
    }

    /**
     * 根据borrowNid查询borrow
     * @auth sunpeikai
     * @param borrowNid 项目编号
     * @return
     */
    @Override
    public BorrowAndInfoVO getBorrowByBorrowNid(String borrowNid) {
        return amTradeClient.searchBorrowByBorrowNid(borrowNid);
    }

    /**
     * 出借撤销异常处理
     * @auth sunpeikai
     * @param request 主要包含orderId
     * @return
     */
    @Override
    public JSONObject handleTenderCancelException(TenderCancelExceptionRequest request,Integer loginUserId) {
        AdminSystemVO adminSystemVO = amConfigClient.getUserInfoById(loginUserId);
        JSONObject jsonObject = new JSONObject();
        boolean tenderTempFlag = amTradeClient.searchBorrowTenderByOrderId(request.getOrderId()).size()>0;
        if (tenderTempFlag) {
            jsonObject.put("status","error");
            jsonObject.put("result","订单号在系统已经存在，只能撤销只在银行存在的订单！");
            return jsonObject;
        }
        String orgOrderId = request.getOrderId();
        BorrowTenderTmpVO tenderTmp = amTradeClient.searchBorrowTenderTmpByOrderId(orgOrderId);
        if (Validator.isNull(tenderTmp)) {
            jsonObject.put("status","error");
            jsonObject.put("result","出借撤销失败，出借可能已经撤销！");
            return jsonObject;
        }
        int userId = tenderTmp.getUserId();
        String productId = tenderTmp.getBorrowNid();
        BigDecimal txAmount = tenderTmp.getAccount();
        // 出借人的账户信息
        BankOpenAccountVO bankAccount = amUserClient.searchBankOpenAccount(userId);
        if (Validator.isNull(bankAccount)) {
            jsonObject.put("status","error");
            jsonObject.put("result","出借人的账户信息不存在");
            return jsonObject;
        }
        String accountId = bankAccount.getAccount();
        int status = 0;//撤销成功状态 0:正常撤销 1:异常记录处理
        boolean bidCancelFlag = false;
        BankCallBean bean = this.bidCancel(userId, accountId, productId, orgOrderId, txAmount.toString(),adminSystemVO);
        logger.info("bean:[{}]", JSON.toJSONString(bean));
        String retCode = "";
        String retMsg = "出借撤销失败，请联系客服！";
        if (Validator.isNotNull(bean)) {
            retCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
            if (retCode.equals(BankCallConstant.RESPCODE_SUCCESS)) {
                bidCancelFlag = true;
            } else if (retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST1) || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST2)) {
                status = 1;
                bidCancelFlag = true;
            } else if (retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_RIGHT)) {
                status = 1;
                bidCancelFlag = true;
            }
        }
        logger.info("bidCancelFlag:::::::::[{}]",bidCancelFlag);
        if (bidCancelFlag) {
            try {
                boolean tenderCancelFlag = this.updateBidCancelRecord(tenderTmp,adminSystemVO);
                logger.info("tenderCancelFlag:::::::::[{}]",tenderCancelFlag);
                if (tenderCancelFlag) {
                    jsonObject.put("status","success");
                    if (status == 1) {
                        jsonObject.put("result","出借异常记录处理成功!");
                    }else{
                        jsonObject.put("result","出借撤销成功!");
                    }
                    return jsonObject;
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        if(StringUtils.isNotBlank(retCode)){
            retMsg = amConfigClient.getBankRetMsg(retCode);
        }
        jsonObject.put("status","error");
        jsonObject.put("result",retMsg);
        return jsonObject;
    }

    /**
     * 银行出借撤销
     * @param userId
     * @param accountId
     * @param productId
     * @param orgOrderId
     * @param txAmount
     * @return
     */
    public BankCallBean bidCancel(Integer userId, String accountId, String productId, String orgOrderId, String txAmount,AdminSystemVO adminSystemVO) {
        // 标的出借撤销
        BankCallBean bean = new BankCallBean();
        String orderId = GetOrderIdUtils.getOrderId2(userId);
        bean.setVersion(BankCallConstant.VERSION_10); // 版本号(必须)
        bean.setTxCode(BankCallMethodConstant.TXCODE_BID_CANCEL); // 交易代码
        bean.setInstCode(BANK_INSTCODE);
        bean.setBankCode(BANK_BANKCODE);
        bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6)); // 交易流水号
        bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
        bean.setAccountId(accountId);// 电子账号
        bean.setOrderId(orderId); // 订单号(必须)
        bean.setTxAmount(CustomUtil.formatAmount(txAmount));// 交易金额
        bean.setProductId(productId);// 标的号
        bean.setOrgOrderId(orgOrderId);// 原标的订单号
        bean.setLogOrderId(orderId);// 订单号
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单日期
        bean.setLogUserId(String.valueOf(userId));// 用户Id
        bean.setLogUserName(adminSystemVO.getUsername()); // 用户名
        bean.setLogRemark("投标申请撤销"); // 备注
        // 调用汇付接口
        BankCallBean result = BankCallUtils.callApiBg(bean);
        return result;
    }

    public boolean updateBidCancelRecord(BorrowTenderTmpVO tenderTmp,AdminSystemVO adminSystemVO) throws Exception {

        boolean tenderTmpFlag = amTradeClient.deleteBorrowTenderTmpById(tenderTmp.getId()) > 0;
        if (!tenderTmpFlag) {
            throw new Exception("删除出借日志表失败，出借订单号：" + tenderTmp.getNid());
        }
        FreezeHistoryVO freezeHistory = new FreezeHistoryVO();
        freezeHistory.setTrxId(tenderTmp.getNid());
        freezeHistory.setNotes("银行出借撤销");
        freezeHistory.setFreezeUser(adminSystemVO.getUsername());
        freezeHistory.setFreezeTime(GetDate.getNowTime10());
        boolean freezeHisLog = amTradeClient.insertFreezeHistory(freezeHistory) > 0;
        if (!freezeHisLog) {
            throw new Exception("插入出借删除日志表失败，出借订单号：" + tenderTmp.getNid());
        }
        return true;
    }
}
