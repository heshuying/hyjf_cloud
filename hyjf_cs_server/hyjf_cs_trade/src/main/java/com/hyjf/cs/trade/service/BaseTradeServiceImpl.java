package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.RechargeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseTradeServiceImpl extends BaseServiceImpl implements BaseTradeService {
    Logger logger = LoggerFactory.getLogger(BaseTradeServiceImpl.class);

    @Autowired
    RechargeClient rechargeClient;

    /**
     * 获取用户在银行的开户信息
     * @param userId
     * @return
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        BankOpenAccountVO bankAccount = this.rechargeClient.selectById(userId);
        return bankAccount;
    }
}
