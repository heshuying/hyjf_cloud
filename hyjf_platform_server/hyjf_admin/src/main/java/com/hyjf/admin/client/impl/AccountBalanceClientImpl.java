package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AccountBalanceClient;
import com.hyjf.am.response.admin.HjhAccountBalanceResponse;
import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/7  11:01
 */
@Service
public class AccountBalanceClientImpl implements AccountBalanceClient {

    @Autowired
    private RestTemplate template;

    @Override
    public int getHjhAccountBalanceMonthCountNew(HjhAccountBalanceRequest request){
        String url = "http://AM-TRADE/am-trade/manager/statis/getHjhAccountBalanceMonthCountNew";
        HjhAccountBalanceResponse response = template.postForEntity(url,request,HjhAccountBalanceResponse.class).getBody();
        if(response != null){
            return response.getCount();
        }
        return 0;
    }

    @Override
    public int getHjhAccountBalanceMonthCount(HjhAccountBalanceRequest request){
        String url = "http://AM-TRADE/am-trade/manager/statis/getHjhAccountBalanceMonthCount";
        HjhAccountBalanceResponse response = template.postForEntity(url,request,HjhAccountBalanceResponse.class).getBody();
        if(response != null){
            return response.getCount();
        }
        return 0;
    }

    @Override
    public List<HjhAccountBalanceVO> getHjhAccountBalanceMonthList(HjhAccountBalanceRequest request){
        String url = "http://AM-TRADE/am-trade/manager/statis/getHjhAccountBalanceMonthList";
        HjhAccountBalanceResponse response = template.postForEntity(url,request,HjhAccountBalanceResponse.class).getBody();
        if(response != null){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int getHjhAccountBalancecountByDay (HjhAccountBalanceRequest request) {
        String url = "http://AM-TRADE/am-trade/manager/statis/getHjhAccountBalancecountByDay";
        HjhAccountBalanceResponse response = template.postForEntity(url, request, HjhAccountBalanceResponse.class).getBody();
        if (response != null) {
            return response.getCount();
        }
        return 0;
    }

    @Override
    public List<HjhAccountBalanceVO> getHjhAccountBalanceListByDay(HjhAccountBalanceRequest request){
        String url = "http://AM-TRADE/am-trade/manager/statis/getHjhAccountBalanceListByDay";
        HjhAccountBalanceResponse response = template.postForEntity(url,request,HjhAccountBalanceResponse.class).getBody();
        if(response != null){
            return response.getResultList();
        }
        return null;
    }
}
