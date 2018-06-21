package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.bean.result.WebResult;

/**
 * 移动项目列表Service
 *
 * @author zhangyk
 *
 */
public interface AppProjectListService extends BaseTradeService{

    /**
     * 获取移动端散标投资列表
     * @param request
     * @author zhangyk
     * @return
     */
    public AppResult searchAppProjectList(ProjectListRequest request);


    /**
     *  获取移动端散标专区债转列表
     * @author zhangyk
     * @date 2018/6/20 15:26
     */
    public AppResult searchAppCreditList(ProjectListRequest request);
}
