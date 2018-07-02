package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.HjhUserAuthResponse;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.cs.trade.client.UserAuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserAuthClientImpl implements UserAuthClient {


    @Autowired
    private RestTemplate restTemplate;


    @Override
    public HjhUserAuthVO getUserAuthByUserId(Integer userId) {
        HjhUserAuthResponse response = restTemplate.getForEntity("",HjhUserAuthResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }
}
