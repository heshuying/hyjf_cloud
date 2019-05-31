/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.pointsshop.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.pointsshop.DuiBaService;
import com.hyjf.pay.lib.duiba.sdk.CreditTool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	@Autowired
	SystemConfig systemConfig;

	@Override
	public JSONObject getUrl(HttpServletRequest request) {
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
			logger.info("生成兑吧跳转url参数：用户:{}，用户积分:{}，平台:{}，登录前url:{}", uid, credits, platform, redirect);
			CreditTool tool = new CreditTool(systemConfig.getDuiBaAppKey(), systemConfig.getDuiBaAppSecret());
			// 生成url
			String url = tool.buildAutoLoginRequest(uid, Long.valueOf(credits), redirect);
			if (StringUtils.isNotBlank(url)) {
				ret.put("status", "0");
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
}
