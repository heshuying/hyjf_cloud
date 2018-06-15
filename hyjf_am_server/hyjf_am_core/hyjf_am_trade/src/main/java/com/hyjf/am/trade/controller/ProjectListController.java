/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectListCustomize;
import com.hyjf.am.trade.service.ProjectListService;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVo;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 项目列表
 * @author liuyang
 * @version ProjectListController, v0.1 2018/6/13 11:15
 */
@RestController
@RequestMapping("/am-trade/projectlist")
public class ProjectListController {

    @Autowired
   private ProjectListService projectListService;

    /**
     * 网站首页获取散标推荐
     * @param request
     * @return
     */
    @RequestMapping("/getHomePageProjectList")
    public ProjectListResponse getHomePageProjectList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        List<WebProjectListCustomize> list = projectListService.getHomePageProjectList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<WebProjectListCustomizeVo> webProjectListCustomizeVo = CommonUtils.convertBeanList(list,WebProjectListCustomizeVo.class);
            projectListResponse.setResultList(webProjectListCustomizeVo);
        }
        return projectListResponse;
    }
}
