package com.hyjf.am.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.HjhUserAuthLogResponse;
import com.hyjf.am.response.user.HjhUserAuthResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.user.dao.model.auto.HjhUserAuth;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthLog;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.UserService;
import com.hyjf.am.vo.user.HjhUserAuthLogVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

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

	@PostMapping("/register")
	public UserResponse register(@RequestBody @Valid RegisterUserRequest userRequest) {
		logger.info("user register:" + JSONObject.toJSONString(userRequest));
		UserResponse userResponse = new UserResponse();
		User user = null;
		try {
			user = userService.register(userRequest);

			if (user == null) {
				userResponse.setRtn(Response.FAIL);
				userResponse.setMessage(Response.FAIL_MSG);
				logger.error("user register error,user " + userRequest.getMobilephone());
			} else {
				UserVO userVO = new UserVO();
				BeanUtils.copyProperties(user, userVO);
				userResponse.setResult(userService.assembleUserVO(userVO));
			}
		} catch (MQException e) {
			logger.error("user register error...", e);
			userResponse.setRtn(Response.FAIL);
			userResponse.setMessage(Response.FAIL_MSG);
		}

		return userResponse;
	}

	/**
	 * 根据userId查询
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/findById/{userId}")
	public UserResponse findUserByUserId(@PathVariable int userId) {
		logger.info("findUserByUserId run...userId is :{}", userId);
		UserResponse response = new UserResponse();
		User user = userService.findUserByUserId(userId);
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
	@RequestMapping("/findByMobile/{mobile}")
	public UserResponse findUserByMobile(@PathVariable String mobile) {
		logger.info("findUserByMobile...mobile is :{}", mobile);
		UserResponse response = new UserResponse();
		User user = userService.findUserByMobile(mobile);
		if (user != null) {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			response.setResult(userVO);
		}
		return response;
	}

	/**
	 * 根据username 或者 mobile查询用户
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping("/findByCondition/{condition}")
	public UserResponse findUserByCondition(@PathVariable String condition) {
		logger.info("findUserByCondition run...condition is :{}", condition);
		UserResponse response = new UserResponse();
		User user = userService.findUserByUsernameOrMobile(condition);
		if (user != null) {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			response.setResult(userService.assembleUserVO(userVO));
		}
		return response;
	}

	/**
	 * 根据推荐人手机号或userId 查询推荐人
	 * 
	 * @param reffer
	 * @return
	 */
	@RequestMapping("/findReffer/{reffer}")
	public UserResponse findUserByRecommendName(@PathVariable String reffer) {
		logger.info("findUserByRecommendName run...reffer is :{}", reffer);
		UserResponse response = new UserResponse();
		User user = userService.findUserByRecommendName(reffer);
		if (user != null) {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			response.setResult(userVO);
		}
		return response;
	}

	@RequestMapping("/updateLoginUser/{userId}/{ip}")
	public void updateLoginUser(@PathVariable int userId, @PathVariable String ip) {
		/**
		 * ip version等作为请求一部分的时候，用base64解码
		 */
		String args = "";
		try {
			args = new String(Base64.decodeBase64(ip.getBytes()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new ReturnMessageException(Response.FAIL_MSG);
		}

		logger.info("updateLoginUser run...userId is :{}, ip is :{}", userId, args);
		userService.updateLoginUser(userId, args);
	}

	@RequestMapping("/getHjhUserAuthByUserId/{userId}")
	public HjhUserAuthResponse getHjhUserAuthByUserId(@PathVariable Integer userId) {
		logger.info("getHjhUserAuthByUserId run...userId is :{}", userId);
		HjhUserAuthResponse response = new HjhUserAuthResponse();
		HjhUserAuth hjhUserAuth = userService.getHjhUserAuthByUserId(userId);
		if (hjhUserAuth != null){
			HjhUserAuthVO hjhUserAuthVO = new HjhUserAuthVO();
			BeanUtils.copyProperties(hjhUserAuth,hjhUserAuthVO);
			response.setResult(hjhUserAuthVO);
		}
		return response;
	}

	@RequestMapping("/insertLogSelective")
	public void insertLogSelective(@RequestBody HjhUserAuthLog hjhUserAuthLog){
		logger.info("insertSelective:" + JSONObject.toJSONString(hjhUserAuthLog));
		userService.insertSelective(hjhUserAuthLog);
	}

	@RequestMapping("/selectByExample/{orderId}")
	public HjhUserAuthLogResponse selectByExample(@RequestBody String orderId){
		HjhUserAuthLogResponse response = new HjhUserAuthLogResponse();
		HjhUserAuthLog hjhUserAuthLog = userService.selectByExample(orderId);
		if (null != hjhUserAuthLog){
			HjhUserAuthLogVO hjhUserAuthLogVO = new HjhUserAuthLogVO();
			BeanUtils.copyProperties(hjhUserAuthLog,hjhUserAuthLogVO);
			response.setResult(hjhUserAuthLogVO);
		}
		return response;
	}

	@RequestMapping("/updateUserAuthInves")
	public void updateUserAuthInves(@RequestBody BankRequest bean){
		userService.updateUserAuthInves(bean);
	}
}
