/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.extensioncenter;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.user.KeyCountResponse;
import com.hyjf.am.resquest.user.KeyCountRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.admin.extensioncenter.KeyCountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author tanyy
 * @version KeyCountController, v0.1 2018/7/18 16:03
 */
@Api(value = "关键词设计")
@RestController
@RequestMapping("/am-admin/extensioncenter/keycount")
public class KeyCountController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(KeyCountController.class);
	@Resource
	private KeyCountService keyCountService;

	@ApiOperation(value = "关键词设计", notes = "关键词设计列表")
	@PostMapping("/searchaction")
	public KeyCountResponse searchAction(@RequestBody KeyCountRequest request) {
		logger.info("关键词设计查询开始......");
		KeyCountResponse response = keyCountService.searchAction(request);
		return response;
	}


}
