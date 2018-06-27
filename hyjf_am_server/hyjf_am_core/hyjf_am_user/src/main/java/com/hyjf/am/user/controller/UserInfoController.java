package com.hyjf.am.user.controller;

import com.hyjf.am.response.user.*;
import com.hyjf.am.user.dao.model.auto.SpreadsUser;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.customize.UserInfoCustomize;
import com.hyjf.am.user.dao.model.customize.EmployeeCustomize;
import com.hyjf.am.user.dao.model.customize.crm.UserCrmInfoCustomize;
import com.hyjf.am.user.service.UserInfoService;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

	@RequestMapping("/findByIdNo/{idNo}")
	public UserInfoResponse findUserInfoByIdNo(@PathVariable String idNo) {
		UserInfoResponse response = new UserInfoResponse();
		UserInfo usersInfo = userInfoService.findUserInfoByIdNo(idNo);
		if (usersInfo != null) {
			UserInfoVO userInfoVO = new UserInfoVO();
			BeanUtils.copyProperties(usersInfo, userInfoVO);
			response.setResult(userInfoVO);
		}
		return response;
	}

	@GetMapping("/selectUserInfoByNameAndCard/{truename}/{idcard}")
	public UserInfoResponse selectUserInfoByNameAndCard(@PathVariable String truename, @PathVariable String idcard) {
		UserInfoResponse response = new UserInfoResponse();
		UserInfo userInfo = userInfoService.selectUserInfoByNameAndCard(truename, idcard);
		UserInfoVO userInfoVO = new UserInfoVO();
		if (userInfo != null) {
			BeanUtils.copyProperties(userInfo, userInfoVO);
			response.setResult(userInfoVO);
		}
		return response;
	}

	/**
	 * @Description 根据用户ID查询CRM信息
	 * @Author sunss
	 * @Date 2018/6/21 17:25
	 */
	@RequestMapping("/findUserCrmInfoByUserId/{userId}")
	public UserInfoCrmResponse findUserCrmInfoByUserId(@PathVariable Integer userId) {
		UserInfoCrmResponse response = new UserInfoCrmResponse();
		UserCrmInfoCustomize usersInfo = userInfoService.findUserCrmInfoByUserId(userId);
		if (usersInfo != null) {
			UserInfoCrmVO userInfoVO = new UserInfoCrmVO();
			BeanUtils.copyProperties(usersInfo, userInfoVO);
			response.setResult(userInfoVO);
		}
		return response;
	}


	/**
	 * 获取用户详细信息
	 */
	@GetMapping("/queryUserInfoCustomizeByUserId/{userId}")
	public UserInfoCustomizeResponse queryUserInfoCustomizeByUserId(@PathVariable Integer userId){
		UserInfoCustomizeResponse response = new UserInfoCustomizeResponse();
		UserInfoCustomize userInfoCustomize=userInfoService.queryUserInfoCustomizeByUserId(userId);
		if (userInfoCustomize!=null){
			response.setResult(CommonUtils.convertBean(userInfoCustomize,UserInfoCustomizeVO.class));
		}
		return response;
	}


	@GetMapping("/querySpreadsUsersByUserId/{userId}")
	public SpreadsUserResponse querySpreadsUsersByUserId(@PathVariable Integer userId){
		SpreadsUserResponse response = new SpreadsUserResponse();
		List<SpreadsUser> list=userInfoService.querySpreadsUsersByUserId(userId);
		if(CollectionUtils.isNotEmpty(list)){
			SpreadsUser su=list.get(0);
			response.setResult(CommonUtils.convertBean(su,SpreadsUserVO.class));
		}
		return response;
	}


	@GetMapping("/selectEmployeeByUserId/{userId}")
	public EmployeeCustomizeResponse selectEmployeeByUserId(@PathVariable Integer userId){
		EmployeeCustomizeResponse response = new EmployeeCustomizeResponse();
		EmployeeCustomize employeeCustomize=userInfoService.selectEmployeeByUserId(userId);
		if (employeeCustomize!=null){
			response.setResult(CommonUtils.convertBean(employeeCustomize,EmployeeCustomizeVO.class));
		}
		return response;
	}


}
