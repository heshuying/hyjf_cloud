package com.hyjf.cs.trade.client;

import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.ProjectListRequest;

/**
 * Web端项目列表Client
 *
 * @author liuyang
 * @version WebProjectListClient, v0.1 2018/6/13 10:21
 */
public interface WebProjectListClient {

    /**
     * 获取项目列表
     * @param request
     * @return
     */
    ProjectListResponse searchProjectList(ProjectListRequest request);

    /**
     * 查询所有分页总数
     * @param request
     * @return
     */
    public ProjectListResponse countProjectList(ProjectListRequest request);

}
