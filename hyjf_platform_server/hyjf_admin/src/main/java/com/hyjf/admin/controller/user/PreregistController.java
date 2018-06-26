/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.PreregistService;
import com.hyjf.am.response.user.AdminPreRegistListResponse;
import com.hyjf.am.resquest.user.AdminPreRegistListRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author DongZeShan
 * @version LoginController.java, v0.1 2018年6月15日 上午9:32:30
 */
@Api(value = "预注册用户")
@RestController
@RequestMapping("/preregist")
public class PreregistController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(PreregistController.class);

	@Autowired
	private PreregistService preregistService;

	/**
	 * @Author: dongzeshan
	 * @Desc :预注册用户页面载入
	 * @Param: * @param map
	 * @Date: 16:43 2018/6/15
	 * @Return: JSONObject
	 */
	@ApiOperation(value = "预注册用户页面载入", notes = "预注册用户页面载入")
	@PostMapping(value = "/init")
	@ResponseBody
	@AuthorityAnnotation(key = "312", value = "123")
	public JSONObject init(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> map) {
		System.out.println(request.getRequestURI());
		JSONObject info = new JSONObject();
		AdminPreRegistListRequest aprlr = new AdminPreRegistListRequest();
		aprlr.setMobile(map.get("mobile"));
		aprlr.setReferrer(map.get("referrer"));
		aprlr.setSource(map.get("source"));
		aprlr.setRegistFlag(map.get("registFlag"));
		aprlr.setPaginatorPage(Integer.valueOf(map.get("paginatorPage")));
		AdminPreRegistListResponse prs = preregistService.getRecordList(aprlr);
		if (StringUtils.isNotBlank(prs.getMessage())) {
			info.put("status", "99");
			info.put("msg", prs.getMessage());
			return info;
		}
		info.put("list", prs.getResultList());
		info.put("recordTotal", prs.getRecordTotal());
		info.put("status", "0");
		info.put("msg", "登录成功");
		return info;
	}

	/**
	 * @Author: dongzeshan
	 * @Desc :编辑页信息
	 * @Param: * @param map
	 * @Date: 16:43 2018/6/15
	 * @Return: JSONObject
	 */
	@ApiOperation(value = "编辑页信息", notes = "编辑页信息")
	@PostMapping(value = "/updatepreregistlist")
	@ResponseBody
	public JSONObject getMenuTree(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> map) {
		JSONObject info = new JSONObject();
		// AdminSystemVO user = this.getUser(request);
		AdminPreRegistListRequest aprlr = new AdminPreRegistListRequest();
		aprlr.setId(map.get("id"));
		AdminPreRegistListResponse prs = preregistService.getPreRegist(aprlr);
		if (StringUtils.isNotBlank(prs.getMessage())) {
			info.put("status", "99");
			info.put("msg", prs.getMessage());
			return info;
		}
		info.put("status", "0");
		info.put("msg", "成功");
		info.put("preRegist", prs.getResult());
		return info;
	}

	/**
	 * @Author: dongzeshan
	 * @Desc :获取权限列表
	 * @Param: *
	 * @Date: 16:43 2018/6/15
	 * @Return: JSONObject
	 */
	@ApiOperation(value = "编辑页保存", notes = "编辑页保存")
	@PostMapping(value = "/savepreregistlist")
	@ResponseBody
	public JSONObject getUserPermission(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> map) {
		JSONObject info = new JSONObject();
		AdminPreRegistListRequest aprlr = new AdminPreRegistListRequest();
		aprlr.setId(map.get("id"));
		aprlr.setReferrer(map.get("referrer"));
		aprlr.setSource(map.get("source"));
		aprlr.setUtm(map.get("utm"));
		aprlr.setRemark(map.get("remark"));
		AdminPreRegistListResponse prs = preregistService.savePreRegist(aprlr);
		if (StringUtils.isNotBlank(prs.getMessage())) {
			info.put("status", "99");
			info.put("msg", prs.getMessage());
			return info;
		}
		info.put("status", "0");
		info.put("msg", "成功");
		return info;
	}

}
