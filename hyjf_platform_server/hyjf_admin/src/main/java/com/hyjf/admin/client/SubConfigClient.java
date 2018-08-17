package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AdminSubConfigResponse;
import com.hyjf.am.response.user.UserInfoCustomizeResponse;
import com.hyjf.am.resquest.admin.AdminSubConfigRequest;

/**
 * @author by xiehuili on 2018/7/9.
 */
public interface SubConfigClient {

    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    AdminSubConfigResponse selectSubConfigListByParam(AdminSubConfigRequest adminRequest);
    /**
     *  分账名单配置添加  查询用户名信息
     * @param request
     * @return
     */
    UserInfoCustomizeResponse queryUserInfoByUserName(AdminSubConfigRequest request);
    /**
     * 页面详情
     * @param adminRequest
     * @return
     */
    AdminSubConfigResponse selectSubConfigInfo(AdminSubConfigRequest adminRequest);
    /**
     *  分账名单配置添加
     * @param adminRequest
     * @return
     */
    AdminSubConfigResponse insertSubConfig(AdminSubConfigRequest adminRequest);
    /**
     * 分账名单配置修改
     * @param adminRequest
     * @return
     */
    AdminSubConfigResponse updateSubConfig(AdminSubConfigRequest adminRequest);
    /**
     * 分账名单配置删除
     * @param adminRequest
     * @return
     */
    AdminSubConfigResponse deleteSubConfig(AdminSubConfigRequest adminRequest);
}
