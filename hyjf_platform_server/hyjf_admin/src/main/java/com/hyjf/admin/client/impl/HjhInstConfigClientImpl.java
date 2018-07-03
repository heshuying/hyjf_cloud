package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.HjhInstConfigClient;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author pangchengchao
 * @version HjhInstConfigClientImpl, v0.1 2018/7/2 10:43
 */
@Service
public class HjhInstConfigClientImpl implements HjhInstConfigClient {

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode) {
        HjhInstConfigResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/hjhPlan/selectHjhInstConfigByInstCode/" + instCode, HjhInstConfigResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
