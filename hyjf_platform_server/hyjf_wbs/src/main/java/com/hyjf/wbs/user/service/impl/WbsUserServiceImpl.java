/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.wbs.client.CsUserClient;
import com.hyjf.wbs.mq.base.CommonProducer;
import com.hyjf.wbs.mq.base.MessageContent;
import com.hyjf.wbs.qvo.WbsUserAuthInfo;
import com.hyjf.wbs.qvo.WebUserBindQO;
import com.hyjf.wbs.qvo.csuser.LoginRequestVO;
import com.hyjf.wbs.user.dao.mapper.auto.UserMapper;
import com.hyjf.wbs.user.dao.model.auto.User;
import com.hyjf.wbs.user.service.WbsUserService;

/**
 * @author cui
 * @version WbsUserServiceImpl, v0.1 2019/4/19 10:20
 */
@Service
public class WbsUserServiceImpl implements WbsUserService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CsUserClient csUserClient;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CommonProducer commonProducer;

	@Override
	public void bind(WebUserBindQO webUserBindQO, JSONObject response) {
		LoginRequestVO loginQO = new LoginRequestVO();
		loginQO.setUsername(webUserBindQO.getLoginUserName());
		loginQO.setPassword(webUserBindQO.getLoginPassword());

		WebViewUserVO webViewUserVO = csUserClient.login(loginQO);

		// 组织返回数据
		response.put("status", "000");
		response.put("statusCode", "0");
		response.put("statusDesc", "授权成功");
		response.put("mobile", webViewUserVO.getMobile());
		response.put("username", webViewUserVO.getUsername());
		response.put("token", webViewUserVO.getToken());
		response.put("roleId", webViewUserVO.getRoleId());
		response.put("iconUrl", webViewUserVO.getIconUrl());

		WbsRegisterMqVO vo = new WbsRegisterMqVO();
		vo.setAssetCustomerId(String.valueOf(webViewUserVO.getUserId()));
		vo.setUtmId(webUserBindQO.getUtmId());

		try {
			commonProducer.messageSendDelay(new MessageContent(MQConstant.WBS_REGISTER_TOPIC,
					MQConstant.WBS_REGISTER_TAG, UUID.randomUUID().toString(), vo), 1);
		} catch (MQException e) {
			logger.error("登录发送MQ失败！");
			throw new CheckException(e.getMessage());
		}
	}

	@Override
	public WbsUserAuthInfo queryUserAuthInfo(String assetCustomerId) {

		WbsUserAuthInfo wbsUserAuthInfo = new WbsUserAuthInfo();

		Integer userId;
		try {
			userId = Integer.parseInt(assetCustomerId);
		} catch (NumberFormatException e) {
			logger.error("客户ID=【{}】不是INTEGER类型", assetCustomerId);
			throw new CheckException("客户ID【" + assetCustomerId + "】不是INTEGER类型！");
		}

		User user = userMapper.selectByPrimaryKey(userId);

		if (null == user) {
			logger.error("未找到ID=【" + assetCustomerId + "】的客户信息");
			throw new CheckException("未找到ID=【" + assetCustomerId + "】的客户信息");
		}

		wbsUserAuthInfo.setUserName(user.getUsername());
		wbsUserAuthInfo.setMobile(user.getMobile());
		wbsUserAuthInfo.setRegisteDatetime(formatTime(user.getRegTime()));

		return wbsUserAuthInfo;
	}

	private String formatTime(Date registTime) {
		if (registTime != null) {
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			return format1.format(registTime);
		}
		return "";
	}

	@Override
	public void authorize(WbsRegisterMqVO qo) {
		if (Strings.isNullOrEmpty(qo.getAssetCustomerId()) || Strings.isNullOrEmpty(qo.getUtmId())) {
			throw new CheckException("快速授权请求参数为空！");
		}

		try {
			commonProducer.messageSendDelay(new MessageContent(MQConstant.WBS_REGISTER_TOPIC,
					MQConstant.WBS_REGISTER_TAG, UUID.randomUUID().toString(), qo), 1);
		} catch (MQException e) {
			logger.error("授权发送MQ失败！");
			throw new CheckException(e.getMessage());
		}

	}
}
