package com.hyjf.cs.trade.service.impl;

import java.util.List;

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

    private static final Logger logger = LoggerFactory.getLogger(BankTenderCancelExceptionServiceImpl.class);

    @Autowired
    private BankTenderCancelClient bankTenderCancelClient;
    @Autowired
    private AmUserClient amUserClient;

    @Value("${hyjf.bank.instcode}")
    private String BANK_INSTCODE;

    @Value("${hyjf.bank.bankcode}")
    private String BANK_BANKCODE;

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
                BorrowTenderTmpVO info = tmpList.get(i);
                BankOpenAccountVO bankAccount = this.getBankOpenAccount(info.getUserId());
                UserVO user =this.amUserClient.findUserById(info.getUserId());

                JSONObject para = new JSONObject();
                para.put("borrowTenderTmp",info);
                para.put("username",user.getUsername());

                if (Validator.isNotNull(bankAccount)){
                    BankCallBean callBean = this.bidCancel(info.getUserId(), bankAccount.getAccount(),
                            info.getBorrowNid(), info.getNid(), info.getAccount().toString(),user.getUsername());
                    if (Validator.isNotNull(callBean)){
                        String retCode = StringUtils.isNotBlank(callBean.getRetCode()) ? callBean.getRetCode() : "";
                        if (retCode.equals(BankCallConstant.RESPCODE_SUCCESS) || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST1)
                                || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST2) || retCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_RIGHT)){
                            //投资撤销历史数据处理
                            this.bankTenderCancelClient.updateBidCancelRecord(para);
                        }else{
                            logger.info("投资撤销接口返回错误!原订单号:" + info.getNid() + ",返回码:" + retCode);
                            this.bankTenderCancelClient.updateTenderCancelExceptionData(info);
                        }
                    }else{
                        logger.info("投资撤销接口异常");
                        this.bankTenderCancelClient.updateTenderCancelExceptionData(info);
                    }
                }else {
                    this.bankTenderCancelClient.updateBidCancelRecord(para);

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
        String bankCode = BANK_BANKCODE;
        String instCode = BANK_INSTCODE;
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
