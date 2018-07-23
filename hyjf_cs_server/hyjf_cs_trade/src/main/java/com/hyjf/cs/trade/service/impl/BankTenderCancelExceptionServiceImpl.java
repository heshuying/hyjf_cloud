package com.hyjf.cs.trade.service.impl;

import java.util.List;

import com.hyjf.am.resquest.trade.TenderCancelRequest;
import com.hyjf.cs.trade.config.SystemConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.BankTenderCancelClient;
import com.hyjf.cs.trade.service.BankTenderCancelExceptionService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

/**
 * 投资撤销异常Service实现类
 * @author jijun
 * @since 20180625
 */
@Service
public class BankTenderCancelExceptionServiceImpl extends BaseServiceImpl implements BankTenderCancelExceptionService {

    @Autowired
    private BankTenderCancelClient bankTenderCancelClient;
    @Autowired
    private AmUserClient amUserClient;


    @Autowired
    private SystemConfig systemConfig;

    @Override
    public void handle() {
        int updateCount = this.executeTenderCancel();
        if(updateCount>0){
           this.handle();
        }
    }

    /**
     * 执行投资撤销
     */
    private int executeTenderCancel() {
        List<BorrowTenderTmpVO> tmpList = bankTenderCancelClient.getBorrowTenderTmpsForTenderCancel();
        int result = 0;
        if (CollectionUtils.isNotEmpty(tmpList)){
            result = tmpList.size();
            for (int i = 0; i < tmpList.size(); i++) {
                boolean delFlag = false;
                BorrowTenderTmpVO info = tmpList.get(i);
                UserVO user =this.amUserClient.findUserById(info.getUserId());
                TenderCancelRequest request = new TenderCancelRequest();
                request.setBorrowTenderTmpVO(info);
                request.setUserName(user.getUsername());

                try {
                    BankOpenAccountVO bankAccount = this.getBankOpenAccount(info.getUserId());
                    if (bankAccount==null){
                        delFlag = true;
                        throw new RuntimeException("该用户尚未在江西银行开户");
                    }

                    BankCallBean callBean = this.bidCancel(info.getUserId(), bankAccount.getAccount(),
                            info.getBorrowNid(), info.getNid(), info.getAccount().toString(),user.getUsername());

                    if (Validator.isNotNull(callBean)) {
                        String retCode = StringUtils.isNotBlank(callBean.getRetCode()) ? callBean.getRetCode() : "";
                        //投资正常撤销或投资订单不存在则删除冗余数据
                        if (retCode.equals(BankCallConstant.RESPCODE_SUCCESS) || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST1)
                                || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST2) || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_RIGHT)){
                            boolean ret = bankTenderCancelClient.updateBidCancelRecord(request);
                            if (!ret){
                                logger.info("投资撤销历史数据处理失败!");
                            }
                        }else{
                            throw new RuntimeException("投资撤销接口返回错误!原订单号:" + info.getNid() + ",返回码:" + retCode);
                        }
                    }else{
                        throw new RuntimeException("投资撤销接口异常!");
                    }

                }catch (Exception e){
                    if (delFlag) {
                        boolean ret=bankTenderCancelClient.updateBidCancelRecord(request);
                        if (!ret){
                            logger.info("投资撤销历史数据处理失败!");
                        }
                    }else{
                       boolean ret= bankTenderCancelClient.updateTenderCancelExceptionData(info);
                        if (!ret){
                            logger.info("处理撤销异常数据失败!");
                        }
                    }

                }
            }
        }
        return result;
    }



    /**
     * 银行投资撤销
     * @param userId
     */
    private BankCallBean bidCancel(Integer userId, String accountId, String productId, String orgOrderId, String txAmount,String username) {
        // 标的投资撤销
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
     * @param userId
     * @return
     */
    private BankOpenAccountVO getBankOpenAccount(Integer userId) {
        BankOpenAccountVO bankAccount = this.amUserClient.selectBankAccountById(userId);
        return bankAccount;
    }
}
