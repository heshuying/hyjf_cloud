package com.hyjf.am.user.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.dao.model.auto.Users;
import com.hyjf.am.user.request.RegisterUserRequest;
import com.hyjf.am.user.request.SmsCodeRequest;
import com.hyjf.am.user.response.UserResponse;
import com.hyjf.am.user.service.SmsService;
import com.hyjf.am.user.service.UserService;
import com.hyjf.am.user.vo.UserVO;
import com.hyjf.common.exception.ServiceException;

/**
 * @author xiasq
 * @version UserController, v0.1 2018/1/21 22:37
 */

@RestController
@RequestMapping("/am-user/user")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SmsService smsService;

	@RequestMapping("/register")
	public UserResponse register(@RequestBody @Valid RegisterUserRequest userRequest) {
		logger.info("user register:" + JSONObject.toJSONString(userRequest));
		UserResponse userResponse = new UserResponse();
		try {
			Users user = userService.register(userRequest);
			if (user == null) {
				userResponse.setRtn(UserResponse.USER_EXISTS);
				userResponse.setMessage(UserResponse.USER_EXISTS_MSG);
				logger.info("user register ,user " + userRequest.getMobilephone() + " has exists");
			} else {
				UserVO userVO = new UserVO();
				BeanUtils.copyProperties(user, userVO);
				userResponse.setResult(userVO);
			}
		} catch (ServiceException e) {
			userResponse.setRtn(UserResponse.FAIL);
			userResponse.setMessage(UserResponse.FAIL_MSG);
			logger.error("user register error", e);
		}
		return userResponse;
	}

	@RequestMapping("/findById/{userId}")
	public UserResponse findUserByUserId(@PathVariable int userId) {
		UserResponse response = new UserResponse();
		Users user = userService.findUserByUserId(userId);
		if (user != null) {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			response.setResult(userVO);
		}
		return response;
	}

	/**
	 * 根据mobile查找用户
	 * 
	 * @param mobile
	 * @return
	 */
	@RequestMapping("/findUserByMobile/{mobile}")
	public UserResponse findUserByMobile(@PathVariable String mobile) {
		UserResponse response = new UserResponse();
		Users user = userService.findUserByMobile(mobile);
		if (user != null) {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			response.setResult(userVO);
		}
		return response;
	}

	/**
	 * 检查短信验证码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateCheckMobileCode")
	public int updateCheckMobileCode(SmsCodeRequest request) {
		String mobile = request.getMobile();
		String verificationCode = request.getVerificationCode();
		String verificationType = request.getVerificationType();
		String platform = request.getPlatform();
		Integer status = request.getStatus();
		Integer updateStatus = request.getUpdateStatus();
		int result = smsService.updateCheckMobileCode(mobile, verificationCode, verificationType, platform, status,
				updateStatus);
		return result;
	}

	/**
	 * 根据推荐人手机号或userId查询推荐人
	 * 
	 * @param reffer
	 * @return
	 */
	@RequestMapping("/findUserByRecommendName/{reffer}")
	public UserResponse findUserByRecommendName(@PathVariable String reffer) {
		UserResponse response = new UserResponse();
		Users user = userService.findUserByRecommendName(reffer);
		if (user != null) {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			response.setResult(userVO);
		}
		return response;
	}
}
