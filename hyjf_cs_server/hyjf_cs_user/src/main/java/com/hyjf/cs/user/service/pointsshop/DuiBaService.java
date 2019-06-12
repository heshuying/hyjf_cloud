/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.pointsshop;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.user.bean.DuiBaPointsDetailRequestBean;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangjun
 * @version DuiBaService, v0.1 2019/5/29 16:36
 */
public interface DuiBaService {
    /**
     * 微信端获取兑吧url接口
     * @param userId
     * @param request
     * @return
     */
    JSONObject getUrl(Integer userId, HttpServletRequest request);

    /**
     * 微信端获取配置接口 可扩展
     * @param userId
     * @param request
     * @return
     */
    JSONObject getConfig(Integer userId, HttpServletRequest request);

    /**
     * 兑吧扣积分接口回调
     * @param consumeParams
     * @return
     */
    CreditConsumeResult deductPoints(CreditConsumeParams consumeParams);

    /**
     * 移动端获取积分明细
     * @param userId
     * @param requestBean
     * @return
     */
    JSONObject getPointsDetail(Integer userId, DuiBaPointsDetailRequestBean requestBean);

    /**
     * 移动端获取用户当前积分
     * @param userId
     * @return
     */
    JSONObject getUserPoints(Integer userId);
}
