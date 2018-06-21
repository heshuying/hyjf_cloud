/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller;
import java.util.List;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.PermissionsBean;
import com.hyjf.admin.service.LoginService;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.TreeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author DongZeShan
 * @version LoginController.java, v0.1 2018年6月15日 上午9:32:30
 */
@Api(value = "admin登陆相关")
@RestController
@RequestMapping("/login")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private LoginService loginService;
	
    /**
     * @Author: dongzeshan
     * @Desc :admin登陆验证密码
     * @Param: * @param map
     * @Date: 16:43 2018/6/15
     * @Return: JSONObject
     */
    @ApiOperation(value = "admin登陆验证密码", notes = "admin登陆验证密码")
    @PostMapping(value = "/login")
	@ResponseBody
	public JSONObject login(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String, String> map) {
    	JSONObject info = new JSONObject();
		String username=map.get("username");
		String password=map.get("password");
		AdminSystemRequest adminSystemRequest=new AdminSystemRequest();
		adminSystemRequest.setUsername(username);
		adminSystemRequest.setPassword(password);
		AdminSystemResponse prs = loginService.getUserInfo(adminSystemRequest);
		if(StringUtils.isNotBlank(prs.getMessage())) {
			info.put("status", "99");
			info.put("msg", prs.getMessage());
		}
		//TODO 需要把用户信息存入redis
		info.put("status", "0");
		info.put("msg", "登录成功");
		return info;
	}
    /**
     * @Author: dongzeshan
     * @Desc :获取菜单
     * @Param: * @param map
     * @Date: 16:43 2018/6/15
     * @Return: JSONObject
     */
    @ApiOperation(value = "admin登陆验证密码", notes = "admin登陆验证密码")
    @PostMapping(value = "/getMenuTree")
	@ResponseBody
	public JSONObject getMenuTree(HttpServletRequest request, HttpServletResponse response) {
    	JSONObject info = new JSONObject();
    	
    	//TODO需要从redis里取userId
		List<TreeVO> prs = loginService.selectLeftMenuTree2("");
		JSONArray jsonArray1 = (JSONArray) JSONArray.toJSON(prs);
		info.put("status", "0");
		info.put("msg", "成功");
		info.put("tree",jsonArray1);
		return info;
	}
    /**
     * @Author: dongzeshan
     * @Desc :获取权限列表
     * @Param: * 
     * @Date: 16:43 2018/6/15
     * @Return: JSONObject
     */
    @ApiOperation(value = "admin获取权限", notes = "admin获取权限")
    @PostMapping(value = "/getUserPermission")
	@ResponseBody
	public JSONObject getUserPermission(HttpServletRequest request, HttpServletResponse response) {
    	JSONObject info = new JSONObject();
    	//TODO 需要从redis里取userName
		 List<AdminSystemVO> prs = loginService.getUserPermission("hhhh");
		 JSONArray jsonArray1 = new JSONArray();
		 for (AdminSystemVO adminSystemVO : prs) {
				if (adminSystemVO != null) {
					PermissionsBean pb=new PermissionsBean();
					pb.setPermissionName(adminSystemVO.getMenuCtrl());
					pb.setPermissionKey(adminSystemVO.getPermission());
					jsonArray1.add(pb);
				}
		}
		info.put("status", "0");
		info.put("msg", "成功");
		info.put("permissions",jsonArray1);
		return info;
	}
    
}
