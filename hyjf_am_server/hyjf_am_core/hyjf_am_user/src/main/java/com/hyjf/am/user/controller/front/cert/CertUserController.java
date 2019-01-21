/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.cert;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.user.CertSendUserResponse;
import com.hyjf.am.response.user.CertUserResponse;
import com.hyjf.am.resquest.user.UserAuthRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.CertUser;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthLog;
import com.hyjf.am.user.dao.model.customize.CertSendUserCustomize;
import com.hyjf.am.user.service.front.user.cert.CertUserService;
import com.hyjf.am.vo.trade.cert.CertSendUserVO;
import com.hyjf.am.vo.trade.cert.CertUserVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description 应急中心上送用户
 * @Author sunss
 * @Date 2019/1/21 15:14
 */
@RestController
@RequestMapping("/am-user/certUser")
public class CertUserController extends BaseController {
	@Autowired
	private CertUserService certUserService;
	/**
	 * 插入国家互联网应急中心已上送用户表
	 * @return
	 */
	@PostMapping("/insertCertUser")
	public IntegerResponse insertCertUser(@RequestBody @Valid CertUserVO request) {
		IntegerResponse response = new IntegerResponse();
		try{
			CertUser certUser = CommonUtils.convertBean(request,CertUser.class);
			certUserService.insertCertUser(certUser);
			response.setResult(1);
		}catch (Exception e){
			response.setResult(0);
		}
		return response;
	}

	/**
	 * 根据userId查询需要上报的用户信息
	 * @return
	 */
	@GetMapping("/getCertSendUserByUserId")
	public CertSendUserResponse getCertSendUserByUserId(Integer userId) {
		CertSendUserCustomize certUsers = certUserService.getCertSendUserByUserId(userId);
		CertSendUserResponse response = new CertSendUserResponse();
		if (null != certUsers) {
			CertSendUserVO certSendUserVO = CommonUtils.convertBean(certUsers,CertSendUserVO.class);
			response.setResult(certSendUserVO);
		}
		return response;

	}

	/**
	 * 修改国家互联网应急中心已上送用户表
	 * @param request
	 */
	@PostMapping("/updateCertUser")
	public IntegerResponse updateCertUser(@RequestBody @Valid CertUserVO request) {
		IntegerResponse response = new IntegerResponse();
		try{
			CertUser certUser = CommonUtils.convertBean(request,CertUser.class);
			certUserService.updateCertUser(certUser);
			response.setResult(1);
		}catch (Exception e){
			response.setResult(0);
		}
		return response;
	}

	/**
	 * 根据borrowNid userId查询
	 */
	@GetMapping("/getCertUserByUserIdBorrowNid/{userId}/{borrowNid}")
	public CertUserResponse getCertUserByUserIdBorrowNid(@PathVariable("userId") Integer userId, @PathVariable("borrowNid")String borrowNid) {
		List<CertUser> certUsers = certUserService.getCertUserByUserIdBorrowNid(userId,borrowNid);
		CertUserResponse response = new CertUserResponse();
		if (null != certUsers) {
			CertUserVO certSendUserVO = CommonUtils.convertBean(certUsers.get(0),CertUserVO.class);
			response.setResult(certSendUserVO);
		}
		return response;
	}

}
