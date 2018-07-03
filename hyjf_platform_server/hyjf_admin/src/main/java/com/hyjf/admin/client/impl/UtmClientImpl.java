package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.UtmClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UtmResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
@Service
public class UtmClientImpl implements UtmClient {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public UtmResponse getByPageList(Map<String, Object> map) {
        UtmResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/promotion/utm/getbypagelist", map, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public UtmResponse getCountByParam(Map<String, Object> map) {
        UtmResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/promotion/utm/getcount", map, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }
}
