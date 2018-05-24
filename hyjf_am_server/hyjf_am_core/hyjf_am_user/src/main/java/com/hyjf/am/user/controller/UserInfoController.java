package com.hyjf.am.user.controller;

import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.vo.user.UserInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.service.UserInfoService;

/**
 * @author xiasq
 * @version UserInfoController, v0.1 2018/4/23 9:54
 */

@RestController
@RequestMapping("/am-user/userInfo")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping("/findById/{userId}")
	public UserInfoResponse findUserInfoById(@PathVariable int userId) {
		UserInfoResponse response = new UserInfoResponse();
		UserInfo usersInfo = userInfoService.findUserInfoById(userId);
		if (usersInfo != null) {
			UserInfoVO userInfoVO = new UserInfoVO();
			BeanUtils.copyProperties(usersInfo, userInfoVO);
			response.setResult(userInfoVO);
		}
		return response;
	}
}
