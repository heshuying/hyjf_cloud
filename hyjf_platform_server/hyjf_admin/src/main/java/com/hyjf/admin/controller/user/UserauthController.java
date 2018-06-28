package com.hyjf.admin.controller.user;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.UserauthService;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 董泽杉
 * @version V1.0  
 * @package
 * @date 2018/6/27
 */
@Api(value = "授权状态&授权记录")
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
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "授权状态", notes = "授权状态集合")
	@RequestMapping(value = "/userauthlist")
	@ResponseBody
	public JSONObject userManagerInit(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> map) {
		JSONObject info = new JSONObject();
		AdminUserAuthListRequest adminUserAuthListRequest = new AdminUserAuthListRequest();
		adminUserAuthListRequest.setPaginatorPage(Integer.valueOf(map.get("paginatorPage")));
		adminUserAuthListRequest.setUserName(map.get("userName"));
		adminUserAuthListRequest.setRecommendName(map.get("recommendName"));
		adminUserAuthListRequest.setAutoInvesStatus(map.get("autoInvesStatus"));
		adminUserAuthListRequest.setAutoCreditStatus(map.get("autoCreditStatus"));
		adminUserAuthListRequest.setInvesAddTimeStart(map.get("invesAddTimeStart"));
		adminUserAuthListRequest.setInvestEndTimeEnd(map.get("investEndTimeEnd"));
		adminUserAuthListRequest.setInvesAddTimeEnd(map.get("invesAddTimeEnd"));
		adminUserAuthListRequest.setInvestEndTimeStart(map.get("investEndTimeStart"));
		AdminUserAuthListResponse rqes = userauthService.userauthlist(adminUserAuthListRequest);
		info.put("list", rqes.getResultList());
		info.put("recordTotal", rqes.getRecordTotal());
		info.put("status", "00");
		info.put("msg", "成功");
		return info;

	}


	/**
	 * 自动投资解约
	 *
	 * @param userId
	 */
	@ApiOperation(value = "授权状态", notes = "自动投资解约")
	@RequestMapping(value = "/userinvescancel")
	@ResponseBody
	public JSONObject cancelInvestAuth(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> map) {
		int userid=Integer.valueOf(map.get("userId"));
		// 返回结果
		JSONObject result = new JSONObject();
		logger.info("自动投资解约开始，用户：{}", userid);
		if (userauthService.canCancelAuth(userid).getRtn().equals("00")) {
			result.put("success", "99");
			result.put("msg", "当前用户存在持有中计划，不能解约！");
			return result;
		}
		//TODO 请求江苏银行
		/*
		 * 		result.put("success", "1");
				result.put("msg", "调用银行接口失败");
				result.put("success", "1");
				result.put("msg", e.getMessage());
		 */
		//插入数据库
		userauthService.cancelInvestAuth(userid, "123");
		result.put("success", "00");
		result.put("msg", "自动投资解约成功！");

		return result;
	}

	/**
	 * 自动债转解约
	 *
	 * @param userId
	 */
	@ApiOperation(value = "授权状态", notes = "自动债转解约")
	@RequestMapping(value = "/usercreditcancel")
	@ResponseBody
	public JSONObject cancelCreditAuth(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> map) {
		
		
		int userid=Integer.valueOf(map.get("userId"));
		// 返回结果
		JSONObject result = new JSONObject();
		logger.info("自动债转授权开始，用户：{}", userid);
		if (userauthService.canCancelAuth(userid).getRtn().equals("00")) {
			result.put("success", "99");
			result.put("msg", "当前用户存在持有中计划，不能解约！");
			return result;
		}
		//TODO 请求江苏银行
		/*
		 * 		result.put("success", "1");
				result.put("msg", "调用银行接口失败");
				result.put("success", "1");
				result.put("msg", e.getMessage());
		 */
		//插入数据库
		userauthService.cancelCreditAuth(userid, "123");
		result.put("success", "00");
		result.put("msg", "自动投资解约成功！");

		return result;
		
	}



}
