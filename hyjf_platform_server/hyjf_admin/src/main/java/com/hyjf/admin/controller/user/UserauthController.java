package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.UserauthService;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.vo.user.AdminUserAuthListVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 董泽杉
 * @version V1.0  
 * @package
 * @date 2018/6/27
 */
@Api(value = "授权状态&授权记录",tags ="授权状态&授权记录")
@RestController
@RequestMapping("/hyjf-admin/userauth")
public class UserauthController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(UserauthController.class);
	@Autowired
	private UserauthService userauthService;

	/**
	 * 权限维护画面初始化
	 *
	 * @param request
	 * @param
	 * @return
	 */
	@ApiOperation(value = "授权状态", notes = "授权状态集合")
	@PostMapping(value = "/userauthlist")
	@ResponseBody
	public AdminResult<ListResult<AdminUserAuthListVO>> userManagerInit(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AdminUserAuthListRequest adminUserAuthListRequest) {
		AdminUserAuthListResponse rqes = userauthService.userauthlist(adminUserAuthListRequest);
		return new AdminResult<>(ListResult.build(rqes.getResultList(), rqes.getRecordTotal()));

	}


	/**
	 * 自动投资解约
	 *
	 * @param
	 */
	@ApiOperation(value = "授权状态", notes = "自动投资解约")
	@PostMapping(value = "/userinvescancel")
	@ResponseBody
	public AdminResult cancelInvestAuth(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Integer userId) {
		logger.info("自动投资解约开始，用户：{}", userId);
		if ("00".equals(userauthService.canCancelAuth(userId).getRtn())) {

			return new AdminResult<>("99",  "当前用户存在持有中计划，不能解约！");
		}

		//插入数据库
		userauthService.cancelInvestAuth(userId);
		return new AdminResult<>();
	}

	/**
	 * 自动债转解约
	 *
	 * @param
	 */
	@ApiOperation(value = "授权状态", notes = "自动债转解约")
	@PostMapping(value = "/usercreditcancel")
	@ResponseBody
	public AdminResult cancelCreditAuth(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Integer userId) {
		

		// 返回结果
		JSONObject result = new JSONObject();
		logger.info("自动债转授权开始，用户：{}", userId);
		if ("00".equals(userauthService.canCancelAuth(userId).getRtn())) {
			return new AdminResult<>("99",  "当前用户存在持有中计划，不能解约！");
		}
		//插入数据库
		userauthService.cancelCreditAuth(userId);
		return new AdminResult<>();
		
	}



}
