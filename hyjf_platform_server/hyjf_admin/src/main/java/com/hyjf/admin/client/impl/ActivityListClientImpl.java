/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.ActivityListClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.market.ActivityListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author yaoy
 * @version ActivityListClientImpl, v0.1 2018/6/26 19:25
 */
@Service
public class ActivityListClientImpl implements ActivityListClient {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<ActivityListVO> getRecordList(ActivityListRequest activityListRequest) {
        String url = "http://AM-MARKET/am-market/activityList/getRecordList";
        ActivityListResponse response = restTemplate.postForEntity(url,activityListRequest,ActivityListResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int insertRecord(ActivityListRequest request) {
        String url = "http://AM-MARKET/am-market/activityList/insertRecord";
        Integer insertFlag = restTemplate.postForEntity(url,request,Integer.class).getBody();
        if (insertFlag > 0) {
            return insertFlag;
        }
        return 0;
    }

    @Override
    public ActivityListVO selectActivityById(int id) {
        String url = "http://AM-MARKET/am-market/activityList/selectActivityById/"+id;
        ActivityListResponse response = restTemplate.getForEntity(url,ActivityListResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int updateActivity(ActivityListRequest request) {
        String url = "http://AM-MARKET/am-market/activityList/updateActivity";
        Integer updateFlag = restTemplate.postForEntity(url,request,Integer.class).getBody();
        if (updateFlag > 0) {
            return updateFlag;
        }
        return 0;
    }
}
