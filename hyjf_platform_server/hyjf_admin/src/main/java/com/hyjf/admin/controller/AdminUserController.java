package com.hyjf.admin.controller;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.AdminService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.AdminUserResponse;
import com.hyjf.am.resquest.config.AdminRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * @package com.hyjf.admin.maintenance.Admin
 * @author dongzeshan
 * @date 2018/09/04 17:00
 * @version V1.0  
 */
@Api(value = "系统中心-用户管理", tags = "系统中心用户管理")
@RestController
@RequestMapping("/hyjf-admin/adminuser")
public class AdminUserController extends BaseController {

	@Autowired
	private AdminService adminService;

	/**
	 * 画面查询
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "用户管理-画面查询", notes = "用户管理-画面查询")
	@PostMapping(value = "/searchAction")
	@ResponseBody
	public AdminResult<AdminUserResponse> search(@RequestBody AdminRequest adminRequest) {
		AdminUserResponse ap = adminService.search(adminRequest);
		return new AdminResult<AdminUserResponse>(ap);
	}

	/**
	 * 迁移到账户设置详细画面
	 * 
	 * @param request
	 * @param form
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@ApiOperation(value = "用户管理-迁移到账户设置详细画面", notes = "用户管理-迁移到账户设置详细画面")
	@PostMapping(value = "/moveToInfoAction")
	@ResponseBody
	public AdminResult<AdminUserResponse> moveToInfoAction(@RequestBody AdminRequest adminRequest) throws Exception {
		return new AdminResult<AdminUserResponse>(adminService.moveToInfoAction(adminRequest));
	}

	/**
	 * 添加账户设置信息
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "用户管理-添加账户设置信息", notes = "用户管理-添加账户设置信息")
	@PostMapping(value = "/insertAction")
	@ResponseBody
	public AdminResult insertAction(HttpServletRequest request,@RequestBody AdminRequest adminRequest) {
		adminRequest.setAdminId(Integer.valueOf(this.getUser(request).getId()));
		AdminUserResponse ap = adminService.insertAction(adminRequest);
		if (Response.isSuccess(ap)) {
			return new AdminResult<>();
		}
		return new AdminResult<>(FAIL, ap.getMessage());
	}

	/**
	 * 修改账户设置信息
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = " 用户管理-修改账户设置信息", notes = "用户管理-修改账户设置信息")
	@PostMapping(value = "/updateAction")
	@ResponseBody
	public AdminResult updateAction(HttpServletRequest request,@RequestBody AdminRequest adminRequest) {
		adminRequest.setAdminId(Integer.valueOf(this.getUser(request).getId()));
		AdminUserResponse ap = adminService.updateAction(adminRequest);
		if (Response.isSuccess(ap)) {
			//当用户为禁用的时候发送
			if(adminRequest.getState() != null && "1".equals(adminRequest.getState())){
				//当用户被删除或者禁用时，发送MQ处理业务流程配置异常处理
				if(adminRequest.getId() != null){
					adminService.sendAdminUser(adminRequest.getId());
				}

			}
			return new AdminResult<>();
		}
		return new AdminResult<>(FAIL, ap.getMessage());
	}

	/**
	 * 删除账户
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "  用户管理-删除账户", notes = " 用户管理-删除账户")
	@PostMapping(value = "/deleteAction")
	@ResponseBody
	public AdminResult deleteRecordAction(HttpServletRequest request,@RequestBody AdminRequest adminRequest) {
		adminRequest.setAdminId(Integer.valueOf(this.getUser(request).getId()));
		AdminUserResponse ap = adminService.deleteRecordAction(adminRequest);
		if (Response.isSuccess(ap)) {
			//当用户被删除或者禁用时，发送MQ处理业务流程配置异常处理
			if(adminRequest.getIds() != null && adminRequest.getIds().size() > 0){
				adminService.sendAdminUser(adminRequest.getIds().toArray());
			}
			return new AdminResult<>();
		}
		return new AdminResult<>(FAIL, ap.getMessage());
	}

	/**
	 * 重置密码账户
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = " 用户管理-重置密码账户", notes = "用户管理-重置密码账户")
	@PostMapping(value = "/resetPwdAction")
	@ResponseBody
	public AdminResult resetPwdAction(HttpServletRequest request,@RequestBody AdminRequest adminRequest) {
		adminRequest.setAdminId(Integer.valueOf(this.getUser(request).getId()));
		AdminUserResponse ap = adminService.resetPwdAction(adminRequest);
		if (Response.isSuccess(ap)) {
			return new AdminResult<>();
		}
		return new AdminResult<>(FAIL, ap.getMessage());
	}

	/**
	 * 检查手机号码或用户名唯一性
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = " 用户管理-检查手机号码或用户名唯一性", notes = " 用户管理-检查手机号码或用户名唯一性")
	@PostMapping(value = "/checkAction")
	@ResponseBody
	public AdminResult checkAction(@RequestBody AdminRequest adminRequest) {
		AdminUserResponse ap = adminService.checkAction(adminRequest);
		if (Response.isSuccess(ap)) {
			return new AdminResult<>();
		}
		return new AdminResult<>(FAIL, ap.getMessage());
	}

	/**
	 * 菜单管理画面初始化
	 *
	 * @param request
	 * @param form
	 * @return
	 */
//	@ApiOperation(value = "菜单管理-画面初始化", notes = "菜单管理-画面初始化")
//	@ResponseBody
//	@PostMapping("/infoAction")
//	public AdminResult<TreeVO> infoAction(@RequestBody AdminMenuRequest form) {
//			TreeResponse tree = this.adminService.infoAction(form);
//			return new AdminResult<TreeVO>(tree.getResult());
//	}
//
//	/**
//	 * 添加菜单管理信息
//	 *
//	 * @param request
//	 * @param form
//	 * @return
//	 */
//	@ApiOperation(value = "菜单管理-添加菜单管理信息", notes = "菜单管理-添加菜单管理信息")
//	@ResponseBody
//	@PostMapping("/insertMenuAction")
//	public AdminResult insertAction(@RequestBody AdminMenuRequest form) {
//		this.adminService.insertAction(form);
//		return new AdminResult<>();
//	}
//
//	/**
//	 * 修改菜单管理信息
//	 *
//	 * @param request
//	 * @param form
//	 * @return
//	 */
//	@ApiOperation(value = "菜单管理-修改菜单管理信息", notes = "菜单管理-修改菜单管理信息")
//	@ResponseBody
//	@PostMapping("/getuser")
//	public AdminResult getuser(@RequestBody AdminMenuRequest form) {
//		this.adminService.getuser(form);
//		return new AdminResult<>();
//	}
//
//
//
//	/**
//	 * 删除菜单
//	 *
//	 * @param request
//	 * @param form
//	 * @return
//	 */
//	@ApiOperation(value = "菜单管理-", notes = "菜单管理-")
//	@ResponseBody
//	@PostMapping("/deleteRecordAction")
//	public AdminResult deleteRecordAction(@RequestBody AdminMenuRequest form) {
//		this.adminService.deleteRecordAction(form);
//		return new AdminResult<>();
//	}
//
//	/**
//	 * 迁移到授权画面
//	 *
//	 * @param request
//	 * @param form
//	 * @return
//	 * @throws Exception
//	 */
//	@ApiOperation(value = "菜单管理-迁移到授权画面", notes = "菜单管理-迁移到授权画面")
//	@ResponseBody
//	@PostMapping("/moveToAuthAction")
//	public AdminResult<ListResult<AdminSystemVO>> moveToAuthAction(@RequestBody AdminMenuRequest form) throws Exception {
//		AdminSystemResponse move = this.adminService.moveToAuthAction(form);
//		if(Response.isSuccess(move)) {
//			return new AdminResult<ListResult<AdminSystemVO>>(ListResult.build(move.getResultList(), 0));
//		}else {
//			return new AdminResult<>(FAIL, move.getMessage());
//		}
//		
//	}
//
//	/**
//	 * 修改菜单权限管理信息
//	 *
//	 * @param request
//	 * @param form
//	 * @return
//	 */
//	@ApiOperation(value = "菜单管理-修改菜单权限管理信息", notes = "菜单管理-修改菜单权限管理信息")
//	@ResponseBody
//	@PostMapping("/updateMenuPermissionsAction")
//	public AdminResult updateMenuPermissionsAction(@RequestBody AdminMenuRequest form) throws Exception {
//		AdminSystemResponse move = this.adminService.updateMenuPermissionsAction(form);
//		if(Response.isSuccess(move)) {
//			return new AdminResult<ListResult<AdminSystemVO>>(ListResult.build(move.getResultList(), 0));
//		}else {
//			return new AdminResult<>(FAIL, move.getMessage());
//		}
//	}
//	  /**
//     * 画面查询
//     * 
//     * @param request
//     * @param form
//     * @return
//     */
//	@ApiOperation(value = "角色管理-画面查询", notes = "角色管理-画面查询")
//	@ResponseBody
//	@PostMapping("/searchRole")
//    public AdminRoleResponse search(@RequestBody AdminRoleRequest form) {
//        // 创建分页
//       return this.adminService.search(form);
//
//    }
//
//
//    /**
//     * 迁移到权限维护详细画面
//     * 
//     * @param request
//     * @param form
//     * @return
//     */
//	@ApiOperation(value = "角色管理-迁移到权限维护详细画面", notes = "角色管理-迁移到权限维护详细画面")
//	@ResponseBody
//	@PostMapping("/moveToInfoRoleAction")
//    public AdminRoleResponse moveToInfoAction(@RequestBody AdminRoleRequest form) {
//
//		return this.adminService.moveToInfoAction(form);
//    }
//
//    /**
//     * 添加权限维护信息
//     * 
//     * @param request
//     * @param form
//     * @return
//     */
//	@ApiOperation(value = "角色管理-添加权限维护信息", notes = "角色管理-添加权限维护信息")
//	@ResponseBody
//	@PostMapping("/insertRoleAction")
//    public AdminRoleResponse insertAction(@RequestBody AdminRoleRequest form) {
//		return this.adminService.insertAction(form);
//    }
//
//    /**
//     * 修改权限维护信息
//     * 
//     * @param request
//     * @param form
//     * @return
//     */
//	@ApiOperation(value = "角色管理-", notes = "角色管理-")
//	@ResponseBody
//	@PostMapping("/updateRoleAction")
//    public AdminRoleResponse updateAction(@RequestBody AdminRoleRequest form) {
//		return this.adminService.updateAction(form);
//    }
//
//
//    /**
//     * 删除权限维护
//     * 
//     * @param request
//     * @param form
//     * @return
//     */
//	@ApiOperation(value = "角色管理-", notes = "角色管理-")
//	@ResponseBody
//	@PostMapping("/deleteRecordRoleAction")
//    public AdminRoleResponse deleteRecordAction(@RequestBody AdminRoleRequest form) {
//        return this.adminService.deleteRecordAction(form);
//
//    }
//
//    /**
//     * 菜单管理画面初始化
//     * 
//     * @param request
//     * @param form
//     * @return
//     */
//	@ApiOperation(value = "角色管理-菜单管理画面初始化", notes = "角色管理-菜单管理画面初始化")
//	@ResponseBody
//	@PostMapping("/getAdminRoleMenu")
//    public String getAdminRoleMenu(@PathVariable String roleId) {
//		return adminService.getAdminRoleMenu(roleId);
//    }
//
//    /**
//     * 插入或更新[角色菜单权限表]数据
//     * 
//     * @param request
//     * @return
//     */
//	@ApiOperation(value = "角色管理- 插入或更新[角色菜单权限表]数据", notes = "角色管理- 插入或更新[角色菜单权限表]数据")
//	@ResponseBody
//	@PostMapping("/modifyPermissionAction")
//    public AdminRoleResponse modifyPermissionAction(@RequestBody AdminRoleRequest bean) {
//		return adminService.modifyPermissionAction(bean);
//    }
//
//    /**
//     * 检查角色名称唯一性
//     * 
//     * @param request
//     * @return
//     */
//	@ApiOperation(value = "角色管理-", notes = "角色管理-")
//	@ResponseBody
//	@PostMapping("/checkRoleAction")
//    public AdminRoleResponse checkAction(@RequestBody AdminRoleRequest bean) {
//		return adminService.checkAction(bean);
//    }
}
