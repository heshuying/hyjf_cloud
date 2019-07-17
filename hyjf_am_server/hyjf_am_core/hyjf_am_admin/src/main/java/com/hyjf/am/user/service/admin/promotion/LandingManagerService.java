/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.promotion;

import com.hyjf.am.response.user.TemplateConfigResponse;
import com.hyjf.am.resquest.user.LandingManagerRequest;
import com.hyjf.am.user.dao.model.auto.TemplateConfig;
import com.hyjf.am.user.service.BaseService;

/**
 * @author nxl
 * @version LandingManagerService, v0.1 2018/7/9 12:00
 */
public interface LandingManagerService extends BaseService {
    /**
     * 根据查询条件查询着陆页列表
     *
     * @param request
     * @return
     */
    TemplateConfigResponse selectTempList(LandingManagerRequest request);

    /**
     * 根据id查找着陆页配置
     *
     * @param intId
     * @return
     */
    TemplateConfig selectTemplateById(Integer intId);

    /**
     * 保存着陆页模板配置
     *
     * @param config
     * @return
     */
    int insertTemplate(TemplateConfig config);

    /**
     * 修改着陆页模板配置
     *
     * @param config
     * @return
     */
    int updateTemplate(TemplateConfig config);
    /**
     * 删除着陆页配置
     * @param temId
     * @return
     */
    int deleteTemplate(int temId);

	int deleteTemplateCount(int temId);
}
