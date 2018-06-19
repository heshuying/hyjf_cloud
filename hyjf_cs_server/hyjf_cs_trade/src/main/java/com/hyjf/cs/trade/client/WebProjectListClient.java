package com.hyjf.cs.trade.client;

import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.CreditListRequest;
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

    /**
     *  查询债权转让所有分页总数
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    public CreditListResponse countCreditList(CreditListRequest request);

    /**
     *  查询债权转让数据列表
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    public CreditListResponse searchCreditList(CreditListRequest request);

}
