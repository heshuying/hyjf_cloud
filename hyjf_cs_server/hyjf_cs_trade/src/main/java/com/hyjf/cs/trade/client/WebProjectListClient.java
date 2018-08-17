package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;

import java.util.List;

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
    List<WebProjectListCustomizeVO> searchProjectList(ProjectListRequest request);

    /**
     * 查询计划专区总数据list
     * @author zhangyk
     * @date 2018/6/21 15:29
     */
     List<HjhPlanCustomizeVO> searchPlanList(ProjectListRequest request);

    /**
     * 查询计划基本详情
     * @author zhangyk
     * @date 2018/7/14 18:20
     */
     PlanDetailCustomizeVO getPlanDetail(String planNid);


}
