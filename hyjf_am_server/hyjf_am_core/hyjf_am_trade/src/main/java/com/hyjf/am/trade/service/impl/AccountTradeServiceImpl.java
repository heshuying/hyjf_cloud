package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.AccountTradeMapper;
import com.hyjf.am.trade.dao.model.auto.AccountTrade;
import com.hyjf.am.trade.dao.model.auto.AccountTradeExample;
import com.hyjf.am.trade.service.AccountTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version AccountTradeServiceImpl, v0.1 2018/6/27 11:10
 */
@Service
public class AccountTradeServiceImpl implements AccountTradeService {
    @Autowired
    protected AccountTradeMapper accountTradeMapper;
    @Override
    public List<AccountTrade> selectTradeTypes() {
        AccountTradeExample example = new AccountTradeExample();
        AccountTradeExample.Criteria crt=example.createCriteria();
        crt.andStatusEqualTo(1);
        List<AccountTrade> list=accountTradeMapper.selectByExample(example);
        return list;
    }
}
