/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.user.TemplateConfigResponse;
import com.hyjf.am.resquest.user.LandingManagerRequest;

/**
 * @author nxl
 * @version LandingManagerService, v0.1 2018/6/20 15:34
 */
public interface LandingManagerService {

    /**
     * 根据筛选条件查找着陆页信息
     * @param request
     * @return
     */
    TemplateConfigResponse selectTempConfigList(LandingManagerRequest request);
    /**
     * 根据id查找着陆页配置
     *
     * @param tempId
     * @return
     */
    TemplateConfigResponse selectTemplateById(Integer tempId);

    /**
     * 修改或添加着陆页模板配置 add by nxl
     * @param request
     * @return
     */
    int updateOrInsertTemplate(LandingManagerRequest request);
    /**
     * 删除着陆页模板配置 add by nxl
     * @param tempId
     * @return
     */
    TemplateConfigResponse deleteTemplate(int tempId);
   }
