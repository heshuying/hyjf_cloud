package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AdminSubConfigResponse;
import com.hyjf.am.response.user.UserInfoCustomizeResponse;
import com.hyjf.am.resquest.admin.AdminSubConfigRequest;

/**
 * @author by xiehuili on 2018/7/9.
 */
public interface SubConfigService {

    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    AdminSubConfigResponse selectSubConfigListByParam(AdminSubConfigRequest adminRequest);
    /**
     * 页面详情
     * @param adminRequest
     * @return
     */
    AdminSubConfigResponse selectSubConfigInfo(AdminSubConfigRequest adminRequest);
    /**
     * 分账名单配置添加
     * @param request
     * @return
     */
    AdminSubConfigResponse insertSubConfig(AdminSubConfigRequest request);

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
    /**
     *
     * 查询用户名信息
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    UserInfoCustomizeResponse userMap(AdminSubConfigRequest adminRequest);

    /**
     * 根据用户名查询分账名单是否存在
     * @author xiehuili
     * @param username
     * @return
     */
    AdminSubConfigResponse subconfig(AdminSubConfigRequest adminRequest);

}
