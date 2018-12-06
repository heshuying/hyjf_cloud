package com.hyjf.cs.trade.service.aems.authstatus.impl;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.cs.trade.service.aems.authstatus.AemsAuthStatusQueryService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version AemsAuthStatusQueryServiceImpl, v0.1 2018/12/6 10:23
 * @Author: Zha Daojian
 */

@Service
public class AemsAuthStatusQueryServiceImpl extends BaseTradeServiceImpl implements AemsAuthStatusQueryService {


    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmUserClient amUserClient;


    /**
     *
     * 根据电子账户号查询帐号
     * @param accountId
     * @return
     */
    @Override
    public BankOpenAccountVO selectBankOpenAccountByAccountId(String accountId) {
        BankOpenAccountVO bankOpenAccountVO = amUserClient.selectBankOpenAccountByAccountId(accountId);
        if (bankOpenAccountVO != null) {
            return bankOpenAccountVO;
        }
        return null;
    }

    /**
     *
     * 缴费授权  还款授权
     * @return
     */
    @Override
    public BankCallBean getTermsAuthQuery(int userId, String channel) {
        BankOpenAccountVO bankOpenAccount = getBankOpenAccount(userId);
        // 调用查询投资人签约状态查询
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        selectbean.setTxCode(BankCallConstant.TXCODE_TERMS_AUTH_QUERY);
        selectbean.setInstCode(systemConfig.getBankInstcode());// 机构代码
        selectbean.setBankCode(systemConfig.getBankBankcode());// 银行代码
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(channel);
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));// 电子账号
        selectbean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);
        return retBean;
    }

    /***
     * 获取用户在银行的开户信息
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        BankOpenAccountVO bankOpenAccountVO = amTradeClient.getBankOpenAccount(userId);
        if (bankOpenAccountVO != null) {
            return bankOpenAccountVO;
        }
        return null;
    }
}
