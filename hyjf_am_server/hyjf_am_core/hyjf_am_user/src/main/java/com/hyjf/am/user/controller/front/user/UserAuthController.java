/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.UserAuthRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthLog;
import com.hyjf.am.user.service.front.user.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
@RestController
@RequestMapping("/am-user/userauth")
public class UserAuthController extends BaseController {
	@Autowired
	private UserAuthService userauthService;
	/**
	 * 更新授权信息
	 * @return
	 */
	@PostMapping("/updateUserAuth")
	public void updateUserAuth(@RequestBody @Valid UserAuthRequest request) {
		userauthService.updateUserAuth(request);
	}

	/**
	 * 更新授权log信息
	 * @return
	 */
	@PostMapping("/updateUserAuthLog/{logOrderId}/{message}")
	public void updateUserAuthLog(@PathVariable String logOrderId,@PathVariable String message) {
		userauthService.updateUserAuthLog(logOrderId,message);
	}

}
