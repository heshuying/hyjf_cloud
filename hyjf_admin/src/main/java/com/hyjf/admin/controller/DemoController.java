package com.hyjf.admin.controller;

import com.hyjf.admin.api.Api;
import com.hyjf.am.vo.config.GatewayApiConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiasq
 * @version DemoController, v0.1 2018/5/23 10:56
 */
@RestController
@RequestMapping("/")
public class DemoController {

	@Autowired
	Api api;

	@RequestMapping("/demo")
	public List<GatewayApiConfigVO> get() {
		return api.test();
	}
}
