package com.hyjf.cs.trade.service;

import com.hyjf.cs.common.bean.result.WeChatResult;

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
    public WeChatResult getProjectDetail(Map<String,Object> map,String token);

    /**
     * 获取计划详情
     * @author zhangyk
     * @date 2018/7/2 15:39
     */
    public WeChatResult getPlanDetail(Map<String,Object> map,String token);
}
