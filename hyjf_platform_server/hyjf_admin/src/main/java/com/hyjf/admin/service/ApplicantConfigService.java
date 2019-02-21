package com.hyjf.admin.service;

import com.hyjf.am.response.config.ConfigApplicantResponse;
import com.hyjf.am.resquest.config.ConfigApplicantRequest;

/**
 * @author lisheng
 * @version ApplicantConfigService, v0.1 2019/2/21 11:14
 */

public interface ApplicantConfigService {

    /**
     * 修改项目申请人配置
     * @param request
     * @return
     */
    ConfigApplicantResponse updateApplicantConfigList(ConfigApplicantRequest request);

    /**
     * 添加项目申请人配置
     * @param request
     * @return
     */
    ConfigApplicantResponse addApplicantConfigList(ConfigApplicantRequest request);

    /**
     * 获取项目申请人列表
     * @param request
     * @return
     */
    ConfigApplicantResponse getApplicantConfigList(ConfigApplicantRequest request);

    /**
     * 获取项目申请人详情
     * @param request
     * @return
     */
    ConfigApplicantResponse findConfigApplicant(ConfigApplicantRequest request);



}
