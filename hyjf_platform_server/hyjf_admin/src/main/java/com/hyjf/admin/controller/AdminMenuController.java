/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.AdminRoleService;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.resquest.config.AdminMenuRequest;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author DongZeShan
 * @version AdminMenuController.java, v0.1 2018年10月11日 下午3:40:30
 */
@Api(value = "系统中心-菜单管理", tags = "系统中心-菜单管理")
@RestController
@RequestMapping("/hyjf-admin/menu")
public class AdminMenuController extends BaseController  {

	   @Autowired
	    private AdminRoleService adminRoleService;

	 

	    /**
	     * 画面查询
	     * 
	     * @param request
	     * @param form
	     * @return
	     */
		@ApiOperation(value = "系统中心-拉取用户可显示的菜单", notes = "系统中心-拉取用户可显示的菜单")
		@PostMapping(value = "/search")
		@ResponseBody
	    public AdminResult<JSONArray>  selectLeftMenuTree(HttpServletRequest request,@RequestBody AdminSystemRequest form) {
			
	    		  return new AdminResult<JSONArray>(adminRoleService.selectLeftMenuTree(this.getUser(request).getId()));
	    }

	    /**
	     * 迁移到权限维护详细画面
	     * 
	     * @param request
	     * @param form
	     * @return
	     */
		@ApiOperation(value = "系统中心-添加菜单管理信息", notes = "用户管理-添加菜单管理信息")
		@PostMapping(value = "/insertAction")
		@ResponseBody
	    public AdminResult insertAction(@RequestBody AdminMenuRequest form) {
			adminRoleService.insertAction(form);
			return new AdminResult<>();
	    }

	    /**
	     * 添加权限维护信息
	     * 
	     * @param request
	     * @param form
	     * @return
	     */
		@ApiOperation(value = "系统中心-修改菜单管理信息", notes = "用户管理-修改菜单管理信息")
		@PostMapping(value = "/getuser")
		@ResponseBody
	    public AdminResult<AdminSystemResponse> getuser(HttpServletRequest request,@RequestBody AdminMenuRequest form) {
			form.setAdminId(Integer.valueOf(this.getUser(request).getId()));
	        return new  AdminResult<AdminSystemResponse>(adminRoleService.getuser(form));
	    }

	    /**
	     * 修改权限维护信息
	     * 
	     * @param request
	     * @param form
	     * @return
	     */
		@ApiOperation(value = "系统中心-删除菜单", notes = "用户管理-删除菜单")
		@PostMapping(value = "/deleteRecordAction")
		@ResponseBody
	    public AdminResult<AdminSystemResponse> deleteRecordAction(HttpServletRequest request,@RequestBody AdminMenuRequest form) {
			form.setAdminId(Integer.valueOf(this.getUser(request).getId()));
	        return new  AdminResult<AdminSystemResponse>(adminRoleService.deleteRecordAction(form));
	    }


	    /**
	     * 删除权限维护
	     * 
	     * @param request
	     * @param form
	     * @return
	     */
		@ApiOperation(value = "系统中心- 迁移到授权画面", notes = "用户管理- 迁移到授权画面")
		@PostMapping(value = "/moveToAuthAction")
		@ResponseBody
	    public AdminResult<AdminSystemResponse> moveToAuthAction(HttpServletRequest request,@RequestBody AdminMenuRequest form) {
			form.setAdminId(Integer.valueOf(this.getUser(request).getId()));
	        return new  AdminResult<AdminSystemResponse>(adminRoleService.moveToAuthAction(form));
	    }


	    /**
	     *  修改菜单权限管理信息
	     * 
	     * @param request
	     * @param form
	     * @return
	     */
		@ApiOperation(value = "系统中心-菜单管理画面初始化", notes = "用户管理-菜单管理画面初始化")
		@PostMapping(value = "/updateMenuPermissionsAction")
		@ResponseBody
	    public AdminResult<AdminSystemResponse> updateMenuPermissionsAction(HttpServletRequest request,@RequestBody AdminMenuRequest form) {
			form.setAdminId(Integer.valueOf(this.getUser(request).getId()));
	        return new AdminResult<AdminSystemResponse>(this.adminRoleService.updateMenuPermissionsAction(form));

	    }

}
