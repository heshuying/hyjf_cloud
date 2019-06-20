/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.LandingManagerService;
import com.hyjf.am.response.user.TemplateConfigResponse;
import com.hyjf.am.resquest.user.LandingManagerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nixiaoling
 * @version LandingManagerServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class LandingManagerServiceImpl extends BaseServiceImpl implements LandingManagerService {


    @Autowired
    private AmUserClient amUserClient;

    private static Logger logger = LoggerFactory.getLogger(LandingManagerServiceImpl.class);

    /**
     * 根据筛选条件查找着陆页信息
     *
     * @param request
     * @return
     */
    @Override
    public TemplateConfigResponse selectTempConfigList(LandingManagerRequest request) {
        TemplateConfigResponse response = amUserClient.selectTempConfigList(request);
        return response;
    }

    /**
     * 根据id查找着陆页配置
     *
     * @param tempId
     * @return
     */
    @Override
    public TemplateConfigResponse selectTemplateById(Integer tempId) {
        TemplateConfigResponse response = amUserClient.selectTemplateById(tempId);
        return response;
    }
 /*   *//**
     * 添加着陆页模板配置 add by nxl
     * @param request
     * @return
     *//*
    @Override
    public int insertTemplate(LandingManagerRequest request){
        return amUserClient.insertTemplate(request);
    }*/

    /**
     * 修改着陆页模板配置 add by nxl
     *
     * @param request
     * @return
     */
    @Override
    public int updateOrInsertTemplate(LandingManagerRequest request) {
        return amUserClient.updateOrInsertTemplate(request);
    }

    /**
     * 删除着陆页模板配置 add by nxl
     *
     * @param tempId
     * @return
     */
    @Override
    public Boolean deleteTemplate(int tempId) {
        return amUserClient.deleteTemplate(tempId);
    }
}
