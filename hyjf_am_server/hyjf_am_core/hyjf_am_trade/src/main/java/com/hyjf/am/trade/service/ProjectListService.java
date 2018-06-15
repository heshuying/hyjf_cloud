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
     * 获取项目列表
     * @param request
     * @return
     */
    List<WebProjectListCustomize> searchProjectList(@Valid ProjectListRequest request);

    /**
     * 获取项目列表件数
     * @param request
     * @return
     */
    int countProjectList(@Valid ProjectListRequest request);
}
