package com.hyjf.cs.user.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.vo.UserVO;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.vo.RegisterVO;

/**
 * @author xiasq
 * @version RegisterController, v0.1 2018/4/21 15:06
 */

@RestController
@RequestMapping("/api/user")
public class RegisterController {
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private UserService userService;

	/**
	 * 发送短信
	 * 
	 * @param validCodeType
	 * @param mobile
	 * @param request
	 * @return
	 * @throws MQException
	 */
	@PostMapping(value = "/sendcode", produces = "application/json; charset=utf-8")
	public BaseResultBean sendSmsCode(@RequestParam String validCodeType, @RequestParam String mobile,
			HttpServletRequest request) throws MQException {
		logger.info("sendSmsCode start, validCodeType is :{}, mobile is: {}", validCodeType, mobile);
		BaseResultBean resultBean = new BaseResultBean();
		userService.sendSmsCode(validCodeType, mobile, request);
		return resultBean;
	}

	/**
	 * 注册
	 *
	 * @param registerVO
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public BaseResultBean register(@RequestBody @Valid RegisterVO registerVO, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("register start, registerVO is :{}", JSONObject.toJSONString(registerVO));
		BaseResultBean resultBean = new BaseResultBean();

		UserVO userVO = userService.register(registerVO, request, response);

		if (userVO != null) {
			logger.info("register success, userId is :{}", userVO.getUserId());
		} else {
			logger.error("register failed...");
			resultBean.setStatus("1");
			resultBean.setStatusDesc(RegisterError.REGISTER_ERROR.getMessage());
		}
		return resultBean;
	}
}
