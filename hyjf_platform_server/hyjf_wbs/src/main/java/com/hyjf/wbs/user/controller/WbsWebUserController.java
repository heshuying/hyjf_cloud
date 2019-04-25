/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.user.controller;

import javax.servlet.http.HttpServletRequest;

import com.hyjf.common.util.GetCilentIP;
import com.hyjf.wbs.WbsConstants;
import com.hyjf.wbs.qvo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.wbs.user.service.WbsUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cui
 * @version WbsCustomerBindController, v0.1 2019/4/18 15:21
 */
@RestController
@Api(value = "Web财富端", tags = "财富端绑定、授权")
@RequestMapping(value = "/hyjf-wbs/web/user")
public class WbsWebUserController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WbsUserService wbsUserService;

	@ApiOperation(value = "PC快速绑定 提交", notes = "PC快速绑定 提交")
	@ResponseBody
	@RequestMapping("bind")
	public BaseResult bind(HttpServletRequest request, @RequestBody WebUserBindQO webUserBindQO) {

		BaseResult result=new BaseResult();

		// 用户接受协议验证
		if (!webUserBindQO.getReadAgreement()) {
			result.setStatus("99");
			result.setStatusDesc("授权失败，请仔细阅读并同意《汇盈金服授权协议》");
			return result;
		}

		wbsUserService.webBind(webUserBindQO, result);

		return result;
	}


	@ApiOperation(value = "PC快速授权获取用户信息",notes = "PC快速授权获取用户信息")
	@GetMapping("/userinfo/{assetCustomerId}")
	public BaseResult queryUserInfo(@PathVariable String assetCustomerId){

		BaseResult result=new BaseResult();

		WbsUserAuthInfo wbsUserAuthInfo=wbsUserService.queryUserAuthInfo(assetCustomerId);

		result.setData(wbsUserAuthInfo);

		return result;
	}

	@ApiOperation(value = "PC快速授权提交",notes = "PC快速授权提交")
	@ResponseBody
	@RequestMapping(value = "authorize",method = RequestMethod.POST)
	public BaseResult authorize(HttpServletRequest request, @RequestBody WbsRegisterMqVO qo){

		BaseResult result=new BaseResult();

		wbsUserService.authorize(qo);

		WbsUserAuthorizeVO vo=new WbsUserAuthorizeVO();
		// TODO by cui
		vo.setRetUrl("");
		result.setData(vo);

		return result;

	}

	@ApiOperation(value = "PC 纳觅端重定向接口",notes = "PC 纳觅端重定向接口")
	@ResponseBody
	@RequestMapping(value = "redirect")
	public BaseResult redirect(HttpServletRequest request, @RequestBody WbsRedirectQO qo){
		BaseResult result=new BaseResult();

		WebUserBindVO webUserBindVO=wbsUserService.redirect(qo, GetCilentIP.getIpAddr(request), WbsConstants.CHANNEL_PC,qo.getPresetProps());

		result.setData(webUserBindVO);

		return result;
	}


}
