/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.membercentre;

import com.hyjf.am.resquest.user.UserAuthRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.admin.membercentre.UserAuthService;
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


}
