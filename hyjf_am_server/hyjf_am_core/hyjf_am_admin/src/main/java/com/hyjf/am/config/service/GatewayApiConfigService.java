package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.GatewayApiConfig;

/**
 * @author xiasq
 * @version GatewayApiConfigService, v0.1 2018/4/18 20:34
 */
public interface GatewayApiConfigService {
    List<GatewayApiConfig> findAllConfigs();
}
