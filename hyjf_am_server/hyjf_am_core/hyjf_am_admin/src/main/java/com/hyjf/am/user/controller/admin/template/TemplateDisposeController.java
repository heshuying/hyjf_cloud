package com.hyjf.am.user.controller.admin.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.user.service.admin.promotion.UtmRegService;

@RestController
@RequestMapping("/am-user/templateDispose")
public class TemplateDisposeController {

	@Autowired
	private TemplateDisposeService templateDisposeService}
