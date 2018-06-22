/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.HolidaysConfigResponse;
import com.hyjf.am.vo.trade.HolidaysConfigVO;
import com.hyjf.cs.trade.client.HolidaysConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author yaoy
 * @version HolidaysConfigClientImpl, v0.1 2018/6/21 17:08
 */
@Service
public class HolidaysConfigClientImpl implements HolidaysConfigClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<HolidaysConfigVO> selectHolidaysConfig(String orderByClause) {
        String url = "http://AM-CONFIG/am-config/holidaysConfig/selectHolidaysConfig/"+orderByClause;
        HolidaysConfigResponse response = restTemplate.getForEntity(url,HolidaysConfigResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
