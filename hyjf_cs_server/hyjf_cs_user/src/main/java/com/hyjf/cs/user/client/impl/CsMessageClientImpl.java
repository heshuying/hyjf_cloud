package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.resquest.api.WrbRegisterRequest;
import com.hyjf.cs.user.client.CsMessageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author lisheng
 * @version CsMessageClientImpl, v0.1 2018/9/27 14:32
 */
@Service
public class CsMessageClientImpl implements CsMessageClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean insertAppChannelStatisticsDetail(WrbRegisterRequest wrbRegisterRequest) {
        boolean body = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/search/insertAppChannelStatisticsDetail", wrbRegisterRequest, BooleanResponse.class)
                .getBody().getResultBoolean();
        return body;
    }
}

