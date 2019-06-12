/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.pointsshop.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.market.DuiBaPointsDetailRequest;
import com.hyjf.am.vo.market.DuiBaPointsDetailVO;
import com.hyjf.am.vo.user.CreditConsumeResultVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.cs.user.bean.DuiBaPointsDetailRequestBean;
import com.hyjf.cs.user.client.AmMarketClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.pointsshop.DuiBaService;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeResult;
import com.hyjf.pay.lib.duiba.sdk.CreditTool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangjun
 * @version DuiBaServiceImpl, v0.1 2019/5/29 16:36
 */
@Service
public class DuiBaServiceImpl implements DuiBaService {
	private static final Logger logger = LoggerFactory.getLogger(DuiBaServiceImpl.class);

	private static String NOT_LOGIN = "not_login";

	private static String WECHAT = "1";

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	AmUserClient amUserClient;

	@Autowired
	AmMarketClient amMarketClient;

	/**
	 * 微信端获取兑吧url接口
	 * @param userId
	 * @param request
	 * @return
	 */
	@Override
	public JSONObject getUrl(Integer userId, HttpServletRequest request) {
		JSONObject ret = new JSONObject();
		try {
			// 平台
			String platform = request.getParameter("platform");
			// 用户id(未登录时前端传"not_login")
			String uid = request.getParameter("uid");
			// 用户积分
			String credits = request.getParameter("credits");
			// 登录前url(用于游客登录后子再跳转回之前的页面)
			String redirect = request.getParameter("redirect");
			// 校验参数
			if (StringUtils.isBlank(uid) || StringUtils.isBlank(credits)) {
				ret.put("status", "1");
				ret.put("statusDesc", "参数错误");
				ret.put("duiBaUrl", "");
				return ret;
			}
			// 非游客模式下，判断网关传的userId是否为空
			if (!NOT_LOGIN.equals(uid) && null == userId){
				ret.put("status", "1");
				ret.put("statusDesc", "参数错误");
				ret.put("duiBaUrl", "");
				return ret;
			}
			logger.info("生成兑吧跳转url参数：用户:{}，用户积分:{}，平台:{}，登录前url:{}", uid, credits, platform, redirect);
			CreditTool tool = new CreditTool(systemConfig.getDuiBaAppKey(), systemConfig.getDuiBaAppSecret());
			// 生成url
			String url = tool.buildAutoLoginRequest(uid, Long.valueOf(credits), redirect);
			if (StringUtils.isNotBlank(url)) {
				if(WECHAT.equals(platform)){
					ret.put("status", "000");
				} else {
					ret.put("status", "0");
				}
				ret.put("statusDesc", "成功");
				ret.put("duiBaUrl", url);
			} else {
				ret.put("status", "1");
				ret.put("statusDesc", "生成url失败");
				ret.put("duiBaUrl", "");
			}

		} catch (Exception e) {
			logger.error("生成兑吧跳转url发生错误：", e);
			ret.put("status", "1");
			ret.put("statusDesc", e.getMessage());
			ret.put("duiBaUrl", "");
		}
		return ret;
	}

	/**
	 * 微信端获取配置接口 可扩展
	 * @param userId
	 * @param request
	 * @return
	 */
    @Override
    public JSONObject getConfig(Integer userId, HttpServletRequest request) {
		JSONObject ret = new JSONObject();
		try {
			// 积分商城开关
			ret.put("pointsShopSwitch", RedisUtils.get(RedisConstants.POINTS_SHOP_SWITCH));

			// 微信端返000
			ret.put("status", "000");
			ret.put("statusDesc", "成功");
		} catch (Exception e){
			logger.error("微信端获取配置发生错误：", e);
			ret.put("status", "1");
			ret.put("statusDesc", e.getMessage());
		}
		return ret;
    }

	@Override
	public CreditConsumeResult deductPoints(CreditConsumeParams consumeParams) {
		CreditConsumeResult result = null;
		CreditConsumeResultVO resultVO = amUserClient.deductPoints(consumeParams);
		// 成功返回相应结果，失败直接返回Null
		if(null != resultVO && resultVO.isSuccess()){
			result = new CreditConsumeResult(true);
			result.setBizId(resultVO.getBizId());
			result.setErrorMessage(resultVO.getErrorMessage());
			result.setCredits(resultVO.getCredits());
		}
		return result;
	}

    @Override
    public JSONObject getPointsDetail(Integer userId, DuiBaPointsDetailRequestBean requestBean) {
		JSONObject result = new JSONObject();
		// 设置查询参数
		DuiBaPointsDetailRequest request = new DuiBaPointsDetailRequest();
		BeanUtils.copyProperties(requestBean, request);
		request.setYear(requestBean.getDate()[0]);
		request.setMonth(requestBean.getDate()[1]);
		request.setUserId(userId);
		DuiBaPointsDetailVO vo = amMarketClient.getPointsDetail(request);
		if(null != vo){
			result.put("status", "000");
			result.put("statusDesc","成功");
			result.put("data", vo);
		} else {
			result.put("status", "1");
			result.put("statusDesc","查询数据失败");
			result.put("data", null);
		}
		return result;
    }
}
