package com.hyjf.am.user.controller.admin.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.admin.TemplateDisposeResponse;
import com.hyjf.am.resquest.admin.TemplateDisposeRequest;
import com.hyjf.am.user.service.admin.template.TemplateDisposeService;

import io.swagger.annotations.Api;

@Api(tags = "推广中心-移动端着陆页配置")
@RestController
@RequestMapping("/am-user/templateDispose")
public class TemplateDisposeController {

	@Autowired
	private TemplateDisposeService templateDisposeService;
	

	/**
	 * 查询着陆页配置
 	 */
	@PostMapping("/templateDisposeList")
	public TemplateDisposeResponse templateDisposeList(@RequestBody  TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.templateDisposeList(templateDisposeRequest);
		return templateDisposeResponse;

	}
	/**
	 * 修改着陆页配置
 	 */
	@PostMapping("/updateTemplateDispose")
	public TemplateDisposeResponse updateTemplateDispose(@RequestBody  TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.updateTemplateDispose(templateDisposeRequest);
		return templateDisposeResponse;

	}
	/**
	 * 修改着陆页配置
 	 */
	@PostMapping("/insertTemplateDispose")
	public TemplateDisposeResponse insertTemplateDispose(@RequestBody  TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.insertTemplateDispose(templateDisposeRequest);
		return templateDisposeResponse;

	}
	/**
	 *删除或者修改状态 着陆页配置
 	 */
	@PostMapping("/deleteTemplateDispose")
	public TemplateDisposeResponse deleteTemplateDispose(@RequestBody  TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.deleteTemplateDispose(templateDisposeRequest);
		return templateDisposeResponse;

	}



}
