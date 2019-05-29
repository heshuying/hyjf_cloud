package com.hyjf.am.config.dao.auto;


import com.hyjf.am.config.dao.mapper.auto.*;
import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoMapper {

    @Autowired
    protected WithdrawTimeConfigMapper withdrawTimeConfigMapper;

    @Autowired
    protected WithdrawRuleConfigMapper withdrawRuleConfigMapper;

    @Autowired
    protected CustomerServiceGroupConfigMapper customerServiceGroupConfigMapper;

    @Autowired
    protected CustomerServiceChannelMapper customerServiceChannelMapper;

    @Autowired
    protected CustomerServiceRepresentiveConfigMapper  customerServiceRepresentiveConfigMapper;
}

