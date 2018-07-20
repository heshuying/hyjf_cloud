package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.SubConfigClient;
import com.hyjf.am.response.admin.AdminSubConfigResponse;
import com.hyjf.am.resquest.admin.AdminSubConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author by xiehuili on 2018/7/9.
 */
@Service
public class SubConfigClientImpl implements SubConfigClient {

    @Autowired
    private RestTemplate restTemplate;
    /**
     * 分页查询列表
     * @param request
     * @return
     */
    @Override
    public AdminSubConfigResponse selectSubConfigListByParam(AdminSubConfigRequest request){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/subconfig/list",request, AdminSubConfigResponse.class)
                .getBody();
    }
    /**
     * 页面详情
     * @param request
     * @return
     */
    @Override
    public AdminSubConfigResponse selectSubConfigInfo(AdminSubConfigRequest request){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/subconfig/info",request, AdminSubConfigResponse.class)
                .getBody();
    }
    /**
     *  分账名单配置添加
     * @param request
     * @return
     */
    @Override
    public AdminSubConfigResponse insertSubConfig(AdminSubConfigRequest request){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/subconfig/insert",request, AdminSubConfigResponse.class)
                .getBody();
    }
    /**
     * 分账名单配置修改
     * @param request
     * @return
     */
    @Override
    public AdminSubConfigResponse updateSubConfig(AdminSubConfigRequest request){
        // 查询用户名信息
//        String url = "http://AM-USER/am-user/config/queryUserInfoByUserName/" + request.getUsername();
//        UserInfoCustomizeResponse response = restTemplate.getForEntity(url,UserInfoCustomizeResponse.class).getBody();
//        if(response != null&&response.getResult().getUserId() != null){
//            request.setUserId(response.getResult().getUserId() );
//        }
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/subconfig/update",request, AdminSubConfigResponse.class)
                .getBody();
    }
    /**
     * 分账名单配置删除
     * @param request
     * @return
     */
    @Override
    public AdminSubConfigResponse deleteSubConfig(AdminSubConfigRequest request){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/subconfig/delete",request, AdminSubConfigResponse.class)
                .getBody();
    }

}
