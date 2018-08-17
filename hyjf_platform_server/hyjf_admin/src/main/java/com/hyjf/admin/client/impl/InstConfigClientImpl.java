package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.InstConfigClient;
import com.hyjf.am.response.admin.AdminInstConfigDetailResponse;
import com.hyjf.am.response.admin.AdminInstConfigListResponse;
import com.hyjf.am.resquest.admin.AdminInstConfigListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author by xiehuili on 2018/7/5.
 */
@Service
public class InstConfigClientImpl implements InstConfigClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询配置中心保证金配置
     * @return
     */
    @Override
    public AdminInstConfigDetailResponse instConfigInit(AdminInstConfigListRequest adminRequest){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/instconfig/list",adminRequest, AdminInstConfigDetailResponse.class)
                .getBody();
    }

    /**
     * 查询详情
     * @param adminRequest
     * @return
     */
    @Override
    public AdminInstConfigDetailResponse searchInstConfigInfo(AdminInstConfigListRequest adminRequest){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/instconfig/info",adminRequest, AdminInstConfigDetailResponse.class)
                .getBody();
    }

    /**
     * 编辑保存保证金配置
     * @return
     */
    @Override
    public AdminInstConfigListResponse saveInstConfig(AdminInstConfigListRequest req){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/instconfig/insert", req,AdminInstConfigListResponse.class)
                .getBody();
    }
    /**
     * 修改保证金配置
     * @return
     */
    @Override
    public AdminInstConfigListResponse updateInstConfig(AdminInstConfigListRequest req){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/instconfig/update", req,AdminInstConfigListResponse.class)
                .getBody();
    }
    /**
     * 删除保证金配置
     * @return
     */
    @Override
    public AdminInstConfigListResponse deleteInstConfig(AdminInstConfigListRequest req){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/instconfig/delete", req,AdminInstConfigListResponse.class)
                .getBody();
    }

}
