package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.cs.common.bean.result.WebResult;

/**
 * web端项目列表Service
 *
 * @author liuyang
 * @version WebProjectListService, v0.1 2018/6/13 10:21
 */
public interface WebProjectListService extends BaseTradeService{

    /**
     * 获取Web端项目列表
     * @param request
     * @author liuyang
     * @return
     */
    public WebResult searchProjectList(ProjectListRequest request);


    /**
     * 获取散标专区债转列表
     * @author zhangyk
     * @date 2018/6/19 16:33
     */
    public WebResult searchCreditList(CreditListRequest request);
}
