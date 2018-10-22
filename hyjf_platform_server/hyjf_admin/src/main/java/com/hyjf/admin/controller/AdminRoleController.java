/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.service.AdminRoleService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminRoleResponse;
import com.hyjf.am.resquest.admin.UserRoleRequest;
import com.hyjf.am.resquest.config.AdminRoleRequest;
import com.hyjf.am.vo.admin.AdminRoleVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author DongZeShan
 * @version AdminRoleController.java, v0.1 2018年10月11日 下午3:40:30
 */
@Api(value = "系统中心-角色权限", tags = "系统中心-角色权限")
@RestController
@RequestMapping("/hyjf-admin/role")
public class AdminRoleController extends BaseController  {

	   @Autowired
	    private AdminRoleService adminRoleService;

	 

	    /**
	     * 画面查询
	     * 
	     * @param request
	     * @param form
	     * @return
	     */
		@ApiOperation(value = "系统中心-画面查询", notes = "用户管理-画面查询")
		@PostMapping(value = "/search")
		@ResponseBody
	    public  AdminResult<ListResult<AdminRoleVO>> search(@RequestBody AdminRoleRequest form) {
	    		  AdminRoleResponse arr = adminRoleService.search(form);
	    		  return new AdminResult<ListResult<AdminRoleVO>>(ListResult.build(arr.getResultList(), arr.getRecordTotal()));
	    }

	    /**
	     * 迁移到权限维护详细画面
	     * 
	     * @param request
	     * @param form
	     * @return
	     */
		@ApiOperation(value = "系统中心-迁移到权限维护详细画面", notes = "用户管理-迁移到权限维护详细画面")
		@PostMapping(value = "/moveToInfoAction")
		@ResponseBody
	    public AdminResult<AdminRoleResponse> moveToInfoAction(@RequestBody AdminRoleRequest form) {
			return new AdminResult<AdminRoleResponse>(adminRoleService.moveToInfoAction(form));
	    }

	    /**
	     * 添加权限维护信息
	     * 
	     * @param request
	     * @param form
	     * @return
	     */
		@ApiOperation(value = "系统中心-添加权限维护信息", notes = "用户管理-添加权限维护信息")
		@PostMapping(value = "/insertAction")
		@ResponseBody
	    public AdminResult<AdminRoleResponse> insertAction(HttpServletRequest request,@RequestBody AdminRoleRequest form) {
			form.setAdminId(Integer.valueOf(this.getUser(request).getId()));
	        return new  AdminResult<AdminRoleResponse>(adminRoleService.insertRecord(form));
	    }

	    /**
	     * 修改权限维护信息
	     * 
	     * @param request
	     * @param form
	     * @return
	     */
		@ApiOperation(value = "系统中心-修改权限维护信息", notes = "用户管理-修改权限维护信息")
		@PostMapping(value = "/updateAction")
		@ResponseBody
	    public AdminResult<AdminRoleResponse> updateAction(HttpServletRequest request,@RequestBody AdminRoleRequest form) {
			form.setAdminId(Integer.valueOf(this.getUser(request).getId()));
	        return new  AdminResult<AdminRoleResponse>(adminRoleService.updateRecord(form));
	    }


	    /**
	     * 删除权限维护
	     * 
	     * @param request
	     * @param form
	     * @return
	     */
		@ApiOperation(value = "系统中心- 删除权限维护", notes = "用户管理- 删除权限维护")
		@PostMapping(value = "/deleteRecordAction")
		@ResponseBody
	    public AdminResult<AdminRoleResponse> deleteRecordAction(HttpServletRequest request,@RequestBody AdminRoleRequest form) {
			form.setAdminId(Integer.valueOf(this.getUser(request).getId()));
	        return new  AdminResult<AdminRoleResponse>(adminRoleService.deleteRecord(form));
	    }


	    /**
	     * 菜单管理画面初始化
	     * 
	     * @param request
	     * @param form
	     * @return
	     */
		@ApiOperation(value = "系统中心-菜单管理画面初始化", notes = "用户管理-菜单管理画面初始化")
		@PostMapping(value = "/getAdminRoleMenu")
		@ResponseBody
	    public AdminResult< Map<String,Object>> getAdminRoleMenu(@RequestBody Map<String,String> roleId) {

	        JSONArray ja = this.adminRoleService.getAdminRoleMenu(roleId.get("roleId"));
	        List<String> list = this.adminRoleService.getPermissionId(roleId.get("roleId"));
	        Map<String,Object> result=new HashMap<>();
	        result.put("adminRoleMenu", ja);
	        result.put("permission", list);
	        return new AdminResult< Map<String,Object>>(result);

	    }

	    /**
	     * 插入或更新[角色菜单权限表]数据
	     * 
	     * @param request
	     * @return
	     */
		@ApiOperation(value = "系统中心-插入或更新[角色菜单权限表]数据", notes = "用户管理-插入或更新[角色菜单权限表]数据")
		@PostMapping(value = "/modifyPermissionAction")
		@ResponseBody
	    public AdminResult<AdminRoleResponse> modifyPermissionAction(HttpServletRequest request,@RequestBody UserRoleRequest bean) {
		//	bean.setAdminId(Integer.valueOf(this.getUser(request).getId()));
			AdminRoleResponse arr = this.adminRoleService.modifyPermissionAction( bean);
			if(Response.isSuccess(arr)) {
				return new AdminResult<>();
			}
			return new AdminResult<>(arr.getRtn(),arr.getMessage());

	    }

	    /**
	     * 检查角色名称唯一性
	     * 
	     * @param request
	     * @return
	     */
		@ApiOperation(value = "系统中心-检查角色名称唯一性", notes = "用户管理-检查角色名称唯一性")
		@PostMapping(value = "/checkAction")
		@ResponseBody
	    public AdminResult<AdminRoleResponse> checkAction(@RequestBody AdminRoleRequest bean) {
			AdminRoleResponse arr = this.adminRoleService.checkAction( bean);
			if(Response.isSuccess(arr)) {
				return new AdminResult<>();
			}
			return new AdminResult<>(arr.getRtn(),arr.getMessage());

	    }
}
