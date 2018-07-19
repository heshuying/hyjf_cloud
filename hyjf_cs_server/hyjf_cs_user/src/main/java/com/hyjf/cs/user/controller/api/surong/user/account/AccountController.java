package com.hyjf.cs.user.controller.api.surong.user.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.account.RdfAccountService;

import io.swagger.annotations.Api;

@Api(value = "融东风用户账户接口")
@Controller
@RequestMapping("/surong/account")
public class AccountController extends BaseUserController{
     
	@Autowired
	private RdfAccountService rdfAccountService;
	
	
}
