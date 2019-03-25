package com.hyjf.cs.user.controller.api.bankopen;

import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bankopen.CrmRepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 微服务上线后，开户数据调用CRM接口有失败的情况，修复数据接口
 */
@Api(value = "CRM修复数据接口",tags = "CRM修复数据接口")
@Controller
@RequestMapping("/hyjf-api/crm")
public class CrmRepairController extends BaseUserController {
	@Autowired
	CrmRepairService crmRepairService;

	/**
	 * CRM数据修复
	 * @auth wangjun
	 * @param
	 * @return
	 */
	@ApiOperation(value = "CRM开户数据修复",notes = "CRM开户数据修复")
	@ResponseBody
	@GetMapping(value = "/repair")
	public String repair(){
		crmRepairService.getBankOpenAccountForCrmRepair();
		return "success";
	}
}
