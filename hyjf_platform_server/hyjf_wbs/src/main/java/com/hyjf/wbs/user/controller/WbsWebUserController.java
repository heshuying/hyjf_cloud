/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.wbs.qvo.WbsUserAuthInfo;
import com.hyjf.wbs.qvo.WebUserBindQO;
import com.hyjf.wbs.user.service.WbsUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cui
 * @version WbsCustomerBindController, v0.1 2019/4/18 15:21
 */
@RestController
@Api(value = "财富端", tags = "财富端绑定、授权")
@RequestMapping(value = "/wbs-web/user")
public class WbsWebUserController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WbsUserService wbsUserService;

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

		wbsUserService.bind(webUserBindQO, response);

		return response;
	}


	@ApiOperation(value = "获取资产端客户信息",notes = "获取资产端客户信息")
	@GetMapping("/userinfo/{assetCustomerId}")
	public BaseResult queryUserInfo(@PathVariable String assetCustomerId){

		BaseResult result=new BaseResult();

		WbsUserAuthInfo wbsUserAuthInfo=wbsUserService.queryUserAuthInfo(assetCustomerId);

		result.setData(wbsUserAuthInfo);

		return result;
	}

	@ApiOperation(value = "",notes = "")
	@ResponseBody
	@RequestMapping(value = "authorize",method = RequestMethod.POST)
	public BaseResult authorize(HttpServletRequest request, @RequestBody WbsRegisterMqVO qo){

		BaseResult result=new BaseResult();

		wbsUserService.authorize(qo);

		return result;

	}


}
