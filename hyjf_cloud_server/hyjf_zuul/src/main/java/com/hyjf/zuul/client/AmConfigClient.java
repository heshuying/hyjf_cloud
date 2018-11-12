package com.hyjf.zuul.client;

import com.hyjf.am.vo.config.GatewayApiConfigVO;

import java.util.List;

/**
 * @author xiasq
 * @version AmConfigClient, v0.1 2018/4/19 17:59
 */
public interface AmConfigClient {
    /**
     * 查询动态路由url配置
     * @return
     */
    List<GatewayApiConfigVO> findGatewayConfigs();
}
