package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.util.Result;

/**
 * web端项目列表Service
 *
 * @author liuyang
 * @version WebProjectListService, v0.1 2018/6/13 10:21
 */
public interface WebProjectListService {

    /**
     * 获取Web端项目列表
     * @param request
     * @author liuyang
     * @return
     */
    public Result searchProjectList(ProjectListRequest request);
}
