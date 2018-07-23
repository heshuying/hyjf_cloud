package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.SubConfigClient;
import com.hyjf.admin.service.SubConfigService;
import com.hyjf.am.response.admin.AdminSubConfigResponse;
import com.hyjf.am.resquest.admin.AdminSubConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by xiehuili on 2018/7/9.
 */
@Service
public class SubConfigServiceImpl implements SubConfigService {
    @Autowired
    private SubConfigClient subConfigClient;

    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    @Override
    public AdminSubConfigResponse selectSubConfigListByParam(AdminSubConfigRequest adminRequest){
        return subConfigClient.selectSubConfigListByParam(adminRequest);
    }
    /**
     * 页面详情
     * @param adminRequest
     * @return
     */
    @Override
    public AdminSubConfigResponse selectSubConfigInfo(AdminSubConfigRequest adminRequest){
        return subConfigClient.selectSubConfigInfo(adminRequest);
    }
    /**
     * 分账名单配置添加
     * @param adminRequest
     * @return
     */
    @Override
    public AdminSubConfigResponse insertSubConfig(AdminSubConfigRequest adminRequest){
        return subConfigClient.insertSubConfig(adminRequest);
    }

    /**
     * 分账名单配置修改
     * @param adminRequest
     * @return
     */
    @Override
    public AdminSubConfigResponse updateSubConfig(AdminSubConfigRequest adminRequest){
        return subConfigClient.updateSubConfig(adminRequest);
    }

    /**
     * 分账名单配置删除
     * @param adminRequest
     * @return
     */
    @Override
    public AdminSubConfigResponse deleteSubConfig(AdminSubConfigRequest adminRequest){
        return subConfigClient.deleteSubConfig(adminRequest);
    }


}
