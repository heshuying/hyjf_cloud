package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.model.auto.AccountTrade;
import com.hyjf.am.trade.dao.model.auto.AccountTradeExample;
import com.hyjf.am.trade.service.AccountTradeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version AccountTradeServiceImpl, v0.1 2018/6/27 11:10
 */
@Service
public class AccountTradeServiceImpl extends BaseServiceImpl implements AccountTradeService {

    @Override
    public List<AccountTrade> selectTradeTypes() {
        AccountTradeExample example = new AccountTradeExample();
        AccountTradeExample.Criteria crt=example.createCriteria();
        crt.andStatusEqualTo(1);
        return accountTradeMapper.selectByExample(example);
    }
}
