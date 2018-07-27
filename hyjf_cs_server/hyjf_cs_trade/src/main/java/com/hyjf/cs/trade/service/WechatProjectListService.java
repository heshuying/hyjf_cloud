package com.hyjf.cs.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.trade.bean.BaseResultBean;
import com.hyjf.cs.trade.bean.WechatHomePageResult;

import java.util.Map;

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
    public JSONObject getProjectDetail(String borrowId,String type,String token);

    /**
     * 获取计划详情
     * @author zhangyk
     * @date 2018/7/2 15:39
     */
    public JSONObject getPlanDetail(String planId, String token);

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
    public WechatHomePageResult  getHomeProejctList(int currPage,int pageSize,String showPlanFlag,String token);
}
