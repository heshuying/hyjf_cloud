package com.hyjf.zuul.client.impl;

import com.hyjf.am.config.response.GatewayApiConfigResponse;
import com.hyjf.am.config.vo.GatewayApiConfigVO;
import com.hyjf.zuul.client.AmConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author xiasq
 * @version AmConfigClientImpl, v0.1 2018/4/19 17:59
 */
@Service
public class AmConfigClientImpl implements AmConfigClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<GatewayApiConfigVO> findGatewayConfigs() {
        GatewayApiConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am/config/gateConfig/findAll", GatewayApiConfigResponse.class).getBody();
        return response.getResultList();
    }
}
