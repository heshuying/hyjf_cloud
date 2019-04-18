/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.user.controller;

import javax.servlet.http.HttpServletRequest;

import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.wbs.mq.base.CommonProducer;
import com.hyjf.wbs.mq.base.MessageContent;
import com.hyjf.wbs.qvo.csuser.LoginRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.wbs.client.CsUserClient;
import com.hyjf.wbs.qvo.WebUserBindQO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.UUID;

/**
 * @author cui
 * @version WbsCustomerBindController, v0.1 2019/4/18 15:21
 */
@RestController
@Api(value = "财富端", tags = "财富端绑定")
@RequestMapping(value = "/wbs-web/user")
public class WbsWebUserBindController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CsUserClient csUserClient;

	@Autowired
	private CommonProducer commonProducer;

	@ApiOperation(value = "财富端客户绑定", notes = "财富端客户绑定")
	@ResponseBody
	@RequestMapping("bind")
	public JSONObject bind(HttpServletRequest request, @RequestBody WebUserBindQO webUserBindQO) {

		JSONObject response = new JSONObject();

		// 用户接受协议验证
		if (!webUserBindQO.getReadAgreement()) {
			response.put("status", "99");
			response.put("statusCode", "99");
			response.put("statusDesc", "授权失败，请仔细阅读并同意《汇盈金服授权协议》");
			return response;
		}

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

		try {
			commonProducer.messageSendDelay(new MessageContent(MQConstant.WBS_REGISTER_TOPIC,
					MQConstant.WBS_BORROW_INFO_TAG, UUID.randomUUID().toString(), webViewUserVO), 1);
		} catch (MQException e) {
			logger.error("登录发送MQ失败！");
			throw new CheckException(e.getMessage());
		}
		return response;
	}

}
