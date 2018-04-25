package com.hyjf.cs.user.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.cs.user.client.AmConfigClient;

/**
 * @author xiasq
 * @version AmConfigClientImpl, v0.1 2018/4/23 20:01
 */
@Service
public class AmConfigClientImpl implements AmConfigClient {
    private static Logger logger = LoggerFactory.getLogger(AmConfigClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SmsConfigVO findSmsConfig() {
        SmsConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/smsConfig/findOne", SmsConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
