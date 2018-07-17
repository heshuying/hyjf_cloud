package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.response.user.VipAuthResponse;
import com.hyjf.am.vo.user.VipAuthVO;
import com.hyjf.cs.trade.client.VipAuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 16:47
 * @Description: VipAuthClientImpl
 */
@Service
public class VipAuthClientImpl implements VipAuthClient {
    @Autowired
    private RestTemplate restTemplate;
    public static final String urlBase = "http://AM-USER/am-user/";

    @Override
    public List<VipAuthVO> getVipAuthList(int vipId) {
        String url = urlBase + "vipauth/getvipauthlist/" + vipId;
        VipAuthResponse response = restTemplate.getForEntity(url, VipAuthResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
