package com.hyjf.zuul.client;

import com.hyjf.am.config.vo.GatewayApiConfigVO;

import java.util.List;

/**
 * @author xiasq
 * @version AmConfigClient, v0.1 2018/4/19 17:59
 */
public interface AmConfigClient {
    List<GatewayApiConfigVO> findGatewayConfigs();
}
