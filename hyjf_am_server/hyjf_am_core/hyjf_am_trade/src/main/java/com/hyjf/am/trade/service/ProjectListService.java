/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectListCustomize;
import com.hyjf.am.util.Page;

import javax.validation.Valid;
import java.util.List;

/**
 * Web端项目列表Service
 * @author liuyang
 * @version ProjectListService, v0.1 2018/6/13 11:37
 */
public interface ProjectListService {


    /**
     * 获取首页散标专区列表
     * @param request
     * @return
     */
    List<WebProjectListCustomize> getHomePageProjectList(@Valid ProjectListRequest request, Page page);

    int countHomePageProjectList(@Valid ProjectListRequest request,Page page);
}
