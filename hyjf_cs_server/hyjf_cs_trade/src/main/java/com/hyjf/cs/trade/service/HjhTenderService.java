package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
/**
 * @Description 加入计划业务操作
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 9:46
 */
public interface HjhTenderService extends BaseTradeService{

    /**
     * @Description 检查加入计划的参数
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 9:47
     */
    public void joinPlan(TenderRequest request);
}