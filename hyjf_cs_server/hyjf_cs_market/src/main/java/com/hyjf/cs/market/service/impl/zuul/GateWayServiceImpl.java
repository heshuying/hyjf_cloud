package com.hyjf.cs.market.service.impl.zuul;

import com.hyjf.am.response.config.GatewayApiConfigResponse;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.service.zuul.GateWayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author lisheng
 * @version ZuulServiceImpl, v0.1 2018/12/19 14:47
 */
@Service
public class GateWayServiceImpl implements GateWayService {
    @Autowired
    AmConfigClient amConfigClient;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GatewayApiConfigResponse findGatewayConfigs() {
        GatewayApiConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/gateConfig/findAll", GatewayApiConfigResponse.class).getBody();
        return response;
    }
}
