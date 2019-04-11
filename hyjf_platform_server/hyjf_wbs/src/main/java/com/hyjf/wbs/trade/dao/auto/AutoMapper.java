package com.hyjf.wbs.trade.dao.auto;

import com.hyjf.wbs.trade.dao.mapper.auto.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wbsTradeAutoMapper")
public class AutoMapper {


    @Autowired
    protected AccountMapper accountMapper;

}

