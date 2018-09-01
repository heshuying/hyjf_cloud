package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.trade.TenderCityCountResponse;
import com.hyjf.am.resquest.trade.TenderCityCountRequest;
import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;
import com.hyjf.cs.market.client.AmAdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/9/1  13:51
 */
@Service
public class AmAdminClientImpl implements AmAdminClient{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<TenderCityCountVO> getTenderCityGroupBy(Date lastDay) {
        TenderCityCountRequest request = new TenderCityCountRequest();
        request.setCreateTime(lastDay);
        TenderCityCountResponse response = restTemplate.postForObject("http://AM-ADMIN/am-admin/tendercitycount/gettendercitygroupby",
                request,TenderCityCountResponse.class);
        if (response != null) {
            return response.getListTenderCityCountVO();
        }
        return null;
    }

    @Override
    public List<TenderSexCountVO> getTenderSexGroupBy(Date date) {
        TenderCityCountRequest request = new TenderCityCountRequest();
        request.setCreateTime(date);
        TenderCityCountResponse response = restTemplate.postForObject("http://AM-ADMIN/am-admin/tendercitycount/gettendersexgroupby",
                request,TenderCityCountResponse.class);
        if (response != null) {
            return response.getListTenderSexCountVO();
        }
        return null;
    }

    @Override
    public int getTenderAgeByRange(Date date, int firstAge, int endAge) {
        TenderCityCountRequest request = new TenderCityCountRequest();
        request.setCreateTime(date);
        request.setFirstAge(firstAge);
        request.setEndAge(endAge);
        TenderCityCountResponse response = restTemplate.postForObject("http://AM-ADMIN/am-admin/tendercitycount/gettenderagebyrange",
                request,TenderCityCountResponse.class);
        if (response != null) {
            return response.getAge();
        }
        return 0;
    }

}
