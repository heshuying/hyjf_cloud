package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.SubConfigService;
import com.hyjf.am.response.admin.AdminSubConfigResponse;
import com.hyjf.am.response.user.UserInfoCustomizeResponse;
import com.hyjf.am.resquest.admin.AdminSubConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by xiehuili on 2018/7/9.
 */
@Service
public class SubConfigServiceImpl implements SubConfigService {
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient userClient;

    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    @Override
    public AdminSubConfigResponse selectSubConfigListByParam(AdminSubConfigRequest adminRequest){
        return amTradeClient.selectSubConfigListByParam(adminRequest);
    }
    /**
     * 页面详情
     * @param adminRequest
     * @return
     */
    @Override
    public AdminSubConfigResponse selectSubConfigInfo(AdminSubConfigRequest adminRequest){
        return amTradeClient.selectSubConfigInfo(adminRequest);
    }
    /**
     * 分账名单配置添加
     * @param adminRequest
     * @return
     */
    @Override
    public AdminSubConfigResponse insertSubConfig(AdminSubConfigRequest adminRequest){
        // 查询用户名信息
        UserInfoCustomizeResponse userResponse= userClient.queryUserInfoByUserName(adminRequest);
        if (userResponse != null&& userResponse.getResult().getUserId() !=null) {
            // 设置用户名信息
            adminRequest.setUserId(userResponse.getResult().getUserId());
        }
        return amTradeClient.insertSubConfig(adminRequest);
    }

    /**
     * 分账名单配置修改
     * @param adminRequest
     * @return
     */
    @Override
    public AdminSubConfigResponse updateSubConfig(AdminSubConfigRequest adminRequest){
        return amTradeClient.updateSubConfig(adminRequest);
    }

    /**
     * 分账名单配置删除
     * @param adminRequest
     * @return
     */
    @Override
    public AdminSubConfigResponse deleteSubConfig(AdminSubConfigRequest adminRequest){
        return amTradeClient.deleteSubConfig(adminRequest);
    }


}
