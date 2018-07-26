/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
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
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.LoginService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.TreeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author DongZeShan
 * @version LoginController.java, v0.1 2018年6月15日 上午9:32:30
 */
@Api(value = "admin登陆相关")
@RestController
@RequestMapping("/hyjf-admin/login")
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private LoginService loginService;
	private static final String key="key";
	
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
	@ApiImplicitParams({ 
        @ApiImplicitParam(name = "username",value = "用户名称",dataType = "string"),
        @ApiImplicitParam(name = "password",value = "用户密码",dataType = "string")
	})
	@AuthorityAnnotation(key = key, value = ShiroConstants.PERMISSION_AUTH)
	public AdminResult<Map<String,Object>> login(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String, String> map) {
    	JSONObject info = new JSONObject();
		String username=map.get("username");
		logger.info("登陆开始用户:"+username);
		String password=map.get("password");
		AdminSystemRequest adminSystemRequest=new AdminSystemRequest();
		adminSystemRequest.setUsername(username);
		adminSystemRequest.setPassword(password);
		AdminSystemResponse prs = loginService.getUserInfo(adminSystemRequest);
		if(!Response.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());
		}
		String uuid=UUID.randomUUID().toString();
		RedisUtils.set(RedisConstants.ADMIN_REQUEST+username, uuid, 3600);
		this.setUser(request, prs.getResult());
		Map<String,Object> result = null;
		result.put("uuid", uuid);
		result.put("user", prs.getResult());
		return new AdminResult<Map<String,Object>>(result);
	}
    /**
     * @Author: dongzeshan
     * @Desc :获取菜单
     * @Param: * @param map
     * @Date: 16:43 2018/6/15
     * @Return: JSONObject
     */
    @ApiOperation(value = "admin获取菜单", notes = "admin获取菜单")
    @PostMapping(value = "/getMenuTree")
	@ResponseBody
	public JSONObject getMenuTree(HttpServletRequest request, HttpServletResponse response) {
    	JSONObject info = new JSONObject();
    	//AdminSystemVO user = this.getUser(request);
		List<TreeVO> prs = loginService.selectLeftMenuTree2(this.getUser(request).getId());
		JSONArray jsonArray1 = (JSONArray) JSONArray.toJSON(prs);
		info.put("status", "00");
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
		 List<AdminSystemVO> prs = loginService.getUserPermission(this.getUser(request).getUsername());
		 List<String> perm = new ArrayList<String>();
		 String key=null;
		 StringBuilder val=new StringBuilder();
		 JSONObject info2 = new JSONObject();
		 for (AdminSystemVO adminSystemVO : prs) {
				if (adminSystemVO != null) {
					if(key==null) {
						key=adminSystemVO.getMenuCtrl();
						val.append(adminSystemVO.getPermission());
					}
					if(key.equals(adminSystemVO.getMenuCtrl())) {
						val.append(",");
						val.append(adminSystemVO.getPermission());
					}
					if(!key.equals(adminSystemVO.getMenuCtrl())) {
						info2.put(key, val.toString().split(","));
						key=adminSystemVO.getMenuCtrl();
						val=new StringBuilder();
						val.append(adminSystemVO.getPermission());
					}
					perm.add(adminSystemVO.getMenuCtrl()+":"+adminSystemVO.getPermission());
				}
		}
		info2.put(key, val.toString().split(","));
		request.getSession().setAttribute("permission", perm);
		info.put("status", "00");
		info.put("msg", "成功");
		info.put("permissions",info2);
		return info;
	}
    
}
