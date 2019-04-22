package com.hyjf.am.config.dao.auto;


import com.hyjf.am.config.dao.mapper.auto.WithdrawRuleConfigMapper;
import com.hyjf.am.config.dao.mapper.auto.WithdrawTimeConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoMapper {

    @Autowired
    protected WithdrawTimeConfigMapper withdrawTimeConfigMapper;

    @Autowired
    protected WithdrawRuleConfigMapper withdrawRuleConfigMapper;
}

