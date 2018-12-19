package com.hyjf.cs.market.service.zuul;

import com.hyjf.am.response.config.GatewayApiConfigResponse;

/**
 * @author lisheng
 * @version GateWayService, v0.1 2018/12/19 14:47
 */

public interface GateWayService {
    /**
     * 查询动态路由url配置
     * @return
     */
    GatewayApiConfigResponse findGatewayConfigs();
}
