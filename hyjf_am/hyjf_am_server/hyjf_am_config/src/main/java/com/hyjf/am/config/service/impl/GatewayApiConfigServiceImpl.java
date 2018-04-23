package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.GatewayApiConfigMapper;
import com.hyjf.am.config.dao.model.auto.GatewayApiConfig;
import com.hyjf.am.config.dao.model.auto.GatewayApiConfigExample;
import com.hyjf.am.config.service.GatewayApiConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiasq
 * @version GatewayApiConfigServiceImpl, v0.1 2018/4/18 20:35
 */
@Service
public class GatewayApiConfigServiceImpl implements GatewayApiConfigService {
    @Autowired
    GatewayApiConfigMapper mapper;

    @Override
    public List<GatewayApiConfig> findAllConfigs() {
        return mapper.selectByExample(new GatewayApiConfigExample());
    }
}
