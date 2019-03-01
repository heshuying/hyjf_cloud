package com.hyjf.cs.trade.service.batch.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.TenderCancelRequest;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.batch.BankTenderCancelExceptionService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出借撤销异常Service实现类
 *
 * @author jijun
 * @since 20180625
 */
@Service
public class BankTenderCancelExceptionServiceImpl extends BaseServiceImpl implements BankTenderCancelExceptionService {

    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;


    @Autowired
    private SystemConfig systemConfig;

    /**
     * 执行出借异常处理
     */
    @Override
    public void handle() {
        this.executeTenderCancel();
    }

    /**
     * 执行出借撤销
     */
    private void executeTenderCancel() {
        List<BorrowTenderTmpVO> tmpList = amTradeClient.getBorrowTenderTmpsForTenderCancel();
        if (CollectionUtils.isNotEmpty(tmpList)) {
            for (int i = 0; i < tmpList.size(); i++) {
                BorrowTenderTmpVO info = tmpList.get(i);
                UserVO user = this.amUserClient.findUserById(info.getUserId());
                if (user == null){
                    logger.info("executeTenderCancel user为空");
                    continue;
                }
                TenderCancelRequest request = new TenderCancelRequest();
                request.setBorrowTenderTmpVO(info);
                request.setUserName(user == null ? "" : user.getUsername());
                BankOpenAccountVO bankAccount = this.getBankOpenAccount(info.getUserId());
                if (bankAccount == null) {
                    logger.info("该用户尚未在江西银行开户，userId=" + info.getUserId());
                    boolean ret = amTradeClient.updateBidCancelRecord(request);
                    if (!ret) {
                        logger.info("出借撤销历史数据处理失败!" + JSONObject.toJSONString(request.getBorrowTenderTmpVO()));
                    }
                    continue;
                }

                BankCallBean callBean = this.bidCancel(info.getUserId(), bankAccount.getAccount(),
                        info.getBorrowNid(), info.getNid(), info.getAccount().toString(), user.getUsername());

                if (Validator.isNotNull(callBean)) {
                    String retCode = StringUtils.isNotBlank(callBean.getRetCode()) ? callBean.getRetCode() : "";
                    //出借正常撤销或出借订单不存在则删除冗余数据
                    if (retCode.equals(BankCallConstant.RESPCODE_SUCCESS) || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST1)
                            || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST2) || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_RIGHT)) {
                        boolean ret = amTradeClient.updateBidCancelRecord(request);
                        if (!ret) {
                            logger.info("出借撤销历史数据处理失败!");
                        }
                    } else {
                        logger.info("出借撤销接口返回错误!原订单号:" + info.getNid() + ",返回码:" + retCode);
                        boolean ret = amTradeClient.updateTenderCancelExceptionData(info);
                        if (!ret) {
                            logger.info("处理撤销异常数据失败!");
                        }
                        continue;
                    }
                } else {
                    logger.info("出借撤销接口异常!");
                    boolean ret = amTradeClient.updateTenderCancelExceptionData(info);
                    if (!ret) {
                        logger.info("处理撤销异常数据失败!");
                    }
                }


            }
        }
    }


    /**
     * 银行出借撤销
     *
     * @param userId
     */
    private BankCallBean bidCancel(Integer userId, String accountId, String productId, String orgOrderId, String txAmount, String username) {
        // 标的出借撤销
        BankCallBean bean = new BankCallBean();
        String orderId = GetOrderIdUtils.getOrderId2(userId);
        String bankCode = systemConfig.getBankBankcode();
        String instCode = systemConfig.getBankInstcode();
        bean.setVersion(BankCallConstant.VERSION_10); // 版本号(必须)
        bean.setTxCode(BankCallMethodConstant.TXCODE_BID_CANCEL); // 交易代码
        bean.setInstCode(instCode);
        bean.setBankCode(bankCode);
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
        bean.setLogUserName(username); // 用户名
        bean.setLogRemark("投标申请撤销"); // 备注
        // 调用汇付接口
        BankCallBean result = BankCallUtils.callApiBg(bean);
        return result;

    }

    /**
     * 获取用户在银行的开户信息
     *
     * @param userId
     * @return
     */
    private BankOpenAccountVO getBankOpenAccount(Integer userId) {
        BankOpenAccountVO bankAccount = this.amUserClient.selectBankAccountById(userId);
        return bankAccount;
    }
}
