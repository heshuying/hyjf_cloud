package com.hyjf.cs.trade.service.home;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.trade.bean.WechatHomePageResult;
import com.hyjf.cs.trade.bean.WechatPlanBorrowResultBean;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信端服务service
 * @author zhangyk
 * @date 2018/7/2 11:24
 */
public interface WechatProjectListService {

    /**
     * 获取散标详情
     * @author zhangyk
     * @date 2018/7/2 11:33
     */
    public JSONObject getProjectDetail(String borrowId,String type,Integer userId);


    /**
     * 获取散标详情：加入记录
     * @author zhangyk
     * @date 2018/7/30 10:28
     */
    public JSONObject getProjectInvestRecord(String borrowId, HttpServletRequest request,String userId);

    /**
     * 获取计划详情
     * @author zhangyk
     * @date 2018/7/2 15:39
     */
    public JSONObject getPlanDetail(String planId, Integer userId);

    /**
     * 获取计划标的组成
     * @author zhangyk
     * @date 2018/7/30 11:09
     */
    public WechatPlanBorrowResultBean getPlanBorrowList(String planId, int currPage, int pageSize);

    /**
     * 获取计划标的加入记录
     * @author zhangyk
     * @date 2018/7/30 11:28
     */
    public Object getPlanAccedeList(String planId,int currPage, int pageSize);


    /**
     * 获取微信首页统计数据
     * @author zhangyk
     * @date 2018/7/23 16:29
     */
    public WechatHomePageResult getHomeIndexData(String token);

    /**
     * 获取首页项目列表信息
     * @author zhangyk
     * @date 2018/7/24 10:46
     */
    public WechatHomePageResult  getHomeProejctList(int currPage,int pageSize,String showPlanFlag,Integer userId);
}
