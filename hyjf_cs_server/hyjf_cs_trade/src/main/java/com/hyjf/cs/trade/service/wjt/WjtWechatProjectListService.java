package com.hyjf.cs.trade.service.wjt;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.trade.bean.WechatHomePageResult;
import com.hyjf.cs.trade.bean.WechatPlanBorrowResultBean;
import com.hyjf.cs.trade.service.BaseTradeService;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信端服务service
 * @author zhangyk
 * @date 2018/7/2 11:24
 */
public interface WjtWechatProjectListService extends BaseTradeService {


    /**
     * 获取首页项目列表信息
     * @author zhangyk
     * @date 2018/7/24 10:46
     */
    public WechatHomePageResult  getHomeProejctList(int currPage, int pageSize, String showPlanFlag, Integer userId);
}
