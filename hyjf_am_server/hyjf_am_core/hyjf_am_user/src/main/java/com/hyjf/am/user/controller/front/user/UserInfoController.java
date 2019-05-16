package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.*;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.SpreadsUser;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.customize.EmployeeCustomize;
import com.hyjf.am.user.dao.model.customize.UserCrmInfoCustomize;
import com.hyjf.am.user.dao.model.customize.UserInfoCustomize;
import com.hyjf.am.user.service.front.user.UserInfoService;
import com.hyjf.am.vo.admin.AdminMsgPushCommonCustomizeVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
public class UserInfoController extends BaseController {

	@Autowired
	private UserInfoService userInfoService;

	@GetMapping("/findById/{userId}")
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
	@RequestMapping("/findMainById/{userId}")
	public UserInfoResponse findMainUserInfoById(@PathVariable int userId) {
		UserInfoResponse response = new UserInfoResponse();
		UserInfo usersInfo = userInfoService.fUserInfoById(userId);
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

	/**
	 * 获取用户详细信息
	 */
	@GetMapping("/queryUserInfoCustomizeByUserName/{userName}")
	public UserInfoCustomizeResponse queryUserInfoCustomizeByUserName(@PathVariable String userName){
		UserInfoCustomizeResponse response = new UserInfoCustomizeResponse();
		UserInfoCustomize userInfoCustomize=userInfoService.queryUserInfoCustomizeByUserName(userName);
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
	public EmployeeCustomizeResponse selectEmployeeByUserId(@PathVariable(value = "userId") String userIdStr){
		if(StringUtils.isBlank(userIdStr) || "null".equals(userIdStr)){
			return null;
		}
		Integer userId = Integer.valueOf(userIdStr);
		EmployeeCustomizeResponse response = new EmployeeCustomizeResponse();
		EmployeeCustomize employeeCustomize=userInfoService.selectEmployeeByUserId(userId);
		if (employeeCustomize!=null){
			response.setResult(CommonUtils.convertBean(employeeCustomize,EmployeeCustomizeVO.class));
		}
		return response;
	}

	/**
	 * 通过用户id获得用户真实姓名和身份证号
	 *
	 * @param userId
	 * @return
	 */
	@GetMapping("/selectUserInfoByUserId/{userId}")
	public UserInfoResponse selectUserInfoByUserId(@PathVariable Integer userId){
		UserInfoResponse response = new UserInfoResponse();
		UserInfo userInfo=userInfoService.selectUserInfoByUserId(userId);
		if(userInfo != null){
			response.setResult(CommonUtils.convertBean(userInfo, UserInfoVO.class));
			return response;
		}
		response.setRtn(Response.FAIL);
		response.setMessage(Response.FAIL_MSG);
		return response;
	}

	/**
	 * 获取用户部门信息
	 */
	@GetMapping("/queryDepartmentInfoByUserId/{userId}")
	public UserInfoListCustomizeReponse queryDepartmentInfoByUserId(@PathVariable Integer userId){
		UserInfoListCustomizeReponse response = new UserInfoListCustomizeReponse();
		List<UserInfoCustomize> userInfoCustomize=userInfoService.queryDepartmentInfoByUserId(userId);
		if (userInfoCustomize!=null){
			List<UserInfoCustomizeVO> userInfoCustomizeVOS = CommonUtils.convertBeanList(userInfoCustomize, UserInfoCustomizeVO.class);
			response.setResult(userInfoCustomizeVOS);
		}
		return response;
	}

	/**
	 * 通过手机号获取设备标识码
	 *
	 * @param mobile
	 * @return
	 */
	@GetMapping("/getMobileCodeByNumber/{mobile}")
	public AdminMsgPushCommonCustomizeVO getMobileCodeByNumber(@PathVariable String mobile){
		AdminMsgPushCommonCustomizeVO msgPushCommon = userInfoService.getMobileCodeByNumber(mobile);
		return msgPushCommon;
	}


}
