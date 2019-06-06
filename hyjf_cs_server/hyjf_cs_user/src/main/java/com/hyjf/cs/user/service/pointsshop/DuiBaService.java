/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.pointsshop;

import com.alibaba.fastjson.JSONObject;
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
}
