package com.hyjf.zuul.client.impl;

import com.hyjf.am.response.config.GatewayApiConfigResponse;
import com.hyjf.am.vo.config.GatewayApiConfigVO;
import com.hyjf.zuul.client.AmConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${am.config.service.name}")
    private String configService;

    @Override
    public List<GatewayApiConfigVO> findGatewayConfigs() {
        GatewayApiConfigResponse response = restTemplate
                .getForEntity(configService+"/gateConfig/findAll", GatewayApiConfigResponse.class).getBody();
        return response.getResultList();
    }
}
