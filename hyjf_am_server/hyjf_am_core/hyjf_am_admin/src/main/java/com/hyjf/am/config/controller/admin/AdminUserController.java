package com.hyjf.am.config.controller.admin;

import com.hyjf.am.config.dao.model.auto.Admin;
import com.hyjf.am.config.dao.model.customize.AdminCustomize;
import com.hyjf.am.config.service.AdminUserService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.AdminUserResponse;
import com.hyjf.am.resquest.config.AdminRequest;
import com.hyjf.am.user.service.admin.adminuser.DepartmentService;
import com.hyjf.am.vo.admin.AdminCustomizeVO;
import com.hyjf.am.vo.admin.AdminRoleVO;
import com.hyjf.am.vo.admin.AdminVO;
import com.hyjf.am.vo.admin.ROaDepartmentVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @package com.hyjf.admin.maintenance.Admin
 * @author dongzeshan
 * @date 2018/09/04 17:00
 * @version V1.0  
 */
@RestController
@RequestMapping("/am-config/admin")
public class AdminUserController {
	@Autowired
	private AdminUserService adminService;
	@Autowired
	private DepartmentService baseService;

	/**
	 * 画面查询
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/searchAction")
	public AdminUserResponse search(@RequestBody AdminRequest adminRequest) {
		// 创建分页
		return this.createPage(adminRequest);
	}

	/**
	 * 创建账户设置分页机能
	 * 
	 * @param request
	 * @param modelAndView
	 * @param form
	 */
	private AdminUserResponse createPage(@RequestBody AdminRequest adminRequest) {
		AdminCustomize adminCustomize = new AdminCustomize();
		BeanUtils.copyProperties(adminRequest, adminCustomize);
		int count = this.adminService.selectAdminListCount(adminCustomize);
		AdminUserResponse ar = new AdminUserResponse();
		if (count>0) {
			ar.setRecordTotal(count);
			Paginator paginator = new Paginator(adminRequest.getCurrPage(), count,
					adminRequest.getPageSize());
			adminCustomize.setLimitStart(paginator.getOffset());
			adminCustomize.setLimitEnd(paginator.getLimit());
			adminCustomize.setDelFlag(0);
			List<AdminCustomize> recordList = this.adminService.getRecordList(adminCustomize);
			List<AdminCustomize> recordList2=new  ArrayList<AdminCustomize>();
			for (AdminCustomize adminCustomize2 : recordList) {
				adminCustomize2.setPassword("");
				recordList2.add(adminCustomize2);
			}
			ar.setResultList(CommonUtils.convertBeanList(recordList2, AdminCustomizeVO.class));
			return ar;
		}
		return ar;
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
	@RequestMapping(value = "/infoAction")
	public AdminUserResponse moveToInfoAction(@RequestBody AdminRequest adminRequest) throws Exception {

		int ids = adminRequest.getId();
		AdminUserResponse bean = new AdminUserResponse();

		if (Validator.isNotNull(ids)) {
			Integer id = Integer.valueOf(ids);
			// 根据主键判断该条数据在数据库中是否存在
			// boolean isExists = this.adminService.isExistsRecord(id);

			// // 没有添加权限 同时 也没能检索出数据的时候异常
			// if (!isExists) {
			// Subject currentUser = SecurityUtils.getSubject();
			// currentUser.checkPermission(AdminDefine.PERMISSIONS_ADD);
			// }

			// 根据主键检索数据
			AdminCustomize record = this.adminService.getRecord(id);
			AdminCustomizeVO acv = new AdminCustomizeVO();
			BeanUtils.copyProperties(record, acv);
			bean.setResult(acv);
		}

		// 设置部门列表
		bean.setDepartmentList(
				CommonUtils.convertBeanList(this.baseService.getDepartmentList(), ROaDepartmentVO.class));
		// 设置角色列表
		bean.setAdminRoleList(CommonUtils.convertBeanList(this.adminService.getAdminRoleList(), AdminRoleVO.class));

		return bean;
	}

	/**
	 * 添加账户设置信息
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/insertAction")
	public AdminUserResponse insertAction(@RequestBody AdminRequest adminRequest) {

		// // 画面验证
		// this.validatorFieldCheck(modelAndView, form);
		//
		// if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
		// // 设置部门列表
		// form.setDepartmentList(this.adminService.getDepartmentList());
		// // 设置角色列表
		// form.setAdminRoleList(this.adminService.getAdminRoleList());
		//
		// modelAndView.addObject(AdminDefine.ADMIN_FORM, form);
		//
		// LogUtil.errorLog(THIS_CLASS, AdminDefine.INSERT_ACTION, "输入内容验证失败",
		// null);
		// return modelAndView;
		// }

		this.adminService.insertRecord(adminRequest);
		// // 更新权限
		// ShiroUtil.updateAuth();
		//
		// this.createPage(request, modelAndView, form);
		//
		// modelAndView.addObject(AdminDefine.SUCCESS, AdminDefine.SUCCESS);
		return new AdminUserResponse();
	}

	/**
	 * 修改账户设置信息
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/updateAction")
	public AdminUserResponse updateAction(@RequestBody AdminRequest adminRequest) {
		// ModelAndView modelAndView = new ModelAndView(AdminDefine.INFO_PATH);

		// // 画面验证
		// this.validatorFieldCheck(modelAndView, form);
		//
		// if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
		// // 设置部门列表
		// form.setDepartmentList(this.adminService.getDepartmentList());
		// // 设置角色列表
		// form.setAdminRoleList(this.adminService.getAdminRoleList());
		//
		// modelAndView.addObject(AdminDefine.ADMIN_FORM, form);
		//
		// LogUtil.errorLog(THIS_CLASS, AdminDefine.INSERT_ACTION, "输入内容验证失败",
		// null);
		// return modelAndView;
		// }

		this.adminService.updateRecord(adminRequest);
		// // 创建分页
		// this.createPage(request, modelAndView, form);
		//
		// modelAndView.addObject(AdminDefine.SUCCESS, AdminDefine.SUCCESS);
		return new AdminUserResponse();
	}

	// /**
	// * 画面校验
	// *
	// * @param modelAndView
	// * @param form
	// */
	// private void validatorFieldCheck(@RequestBody AdminRequest adminRequest)
	// {
	// // 用户名(必须,最大长度)
	// ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "username",
	// form.getUsername(), 20, true);
	// // 姓名(必须,最大长度,汉字)
	// ValidatorFieldCheckUtil.validateChinese(modelAndView, "truename",
	// form.getTruename(), 10, true);
	// // 所属部门(必须)
	// ValidatorFieldCheckUtil.validateRequired(modelAndView, "departmentId",
	// String.valueOf(form.getDepartmentId()));
	// // 邮箱(必须,邮箱,最大长度)
	// ValidatorFieldCheckUtil.validateMailAndMaxLength(modelAndView, "email",
	// form.getEmail(), 100, false);
	// // 手机号码(必须,数字,最大长度)
	// ValidatorFieldCheckUtil.validateNumJustLength(modelAndView, "mobile",
	// form.getMobile(), 11, true);
	// // 账户状态
	// ValidatorFieldCheckUtil.validateRequired(modelAndView, "state",
	// form.getState());
	//
	// if (!ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
	// // 检查手机号码唯一性
	// int cnt = adminService.countAdminByMobile(form.getId(),
	// form.getMobile());
	// if (cnt > 0) {
	// ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "mobile",
	// "repeat");
	// }
	// // 检查用户名唯一性
	// cnt = adminService.countAdminByUsername(form.getId(),
	// form.getUsername());
	// if (cnt > 0) {
	// ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "username",
	// "repeat");
	// }
	// }
	// }

	/**
	 * 删除账户
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/deleteAction")
	public AdminUserResponse deleteRecordAction(@RequestBody AdminRequest adminRequest) {
		// ModelAndView modelAndView = new ModelAndView(AdminDefine.LIST_PATH);
		//
		// List<Integer> recordList = JSONArray.parseArray(form.getIds(),
		// Integer.class);
		//
		// if
		// (recordList.contains(GetterUtil.getInteger(ShiroUtil.getLoginUserId())))
		// {
		// attr.addFlashAttribute("delete_error", "不能删除自己,请重新选择!");
		// modelAndView.setViewName("redirect:" + AdminDefine.REQUEST_MAPPING +
		// "/" + AdminDefine.INIT);
		// return modelAndView;
		// }

		this.adminService.deleteRecord(adminRequest.getIds(),adminRequest.getAdminId());
		// modelAndView.setViewName("redirect:" + AdminDefine.REQUEST_MAPPING +
		// "/" + AdminDefine.INIT);
		return new AdminUserResponse();
	}

	/**
	 * 重置密码账户
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/resetPwdAction")
	public AdminUserResponse resetPwdAction(@RequestBody AdminRequest adminRequest) {
		// ModelAndView modelAndView = new ModelAndView(AdminDefine.LIST_PATH);
		//
		// List<Integer> recordList = JSONArray.parseArray(form.getIds(),
		// Integer.class);
		// if (recordList.size()==0) {
		// attr.addFlashAttribute("delete_error", "当前用户不存在");
		// modelAndView.setViewName("redirect:" + AdminDefine.REQUEST_MAPPING +
		// "/" + AdminDefine.INIT);
		// return modelAndView;
		// }
		this.adminService.resetPwdAction(adminRequest.getIds(),adminRequest.getAdminId());
		// modelAndView.setViewName("redirect:" + AdminDefine.REQUEST_MAPPING +
		// "/" + AdminDefine.INIT);
		// modelAndView.addObject(AdminDefine.SUCCESS, AdminDefine.SUCCESS);
		return new AdminUserResponse();
	}

	/**
	 * 检查手机号码或用户名唯一性
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkAction")
	public AdminUserResponse checkAction(@RequestBody AdminRequest adminRequest) {
		AdminUserResponse ar = new AdminUserResponse();
		// 检查手机号码唯一性
		if (!StringUtils.isEmpty(adminRequest.getMobile())) {
			int cnt = adminService.countAdminByMobile(adminRequest.getId(), adminRequest.getMobile());
			if (cnt > 0) {
				ar.setRtn(Response.FAIL);
				ar.setMessage("手机号重复");
			}
		} else if (!StringUtils.isEmpty(adminRequest.getUsername())) {
			// 检查用户名唯一性
			int cnt = adminService.countAdminByUsername(adminRequest.getId(), adminRequest.getUsername());
			if (cnt > 0) {
				ar.setRtn(Response.FAIL);
				ar.setMessage("用户名重复");
			}
		} else if (!StringUtils.isEmpty(adminRequest.getEmail())) {
			// 检查邮箱唯一性
			int cnt = adminService.countAdminByEmail(adminRequest.getId(), adminRequest.getEmail());
			if (cnt > 0) {
				ar.setRtn(Response.FAIL);
				ar.setMessage("邮箱重复");
			}
		}
		return ar;
	}


	@RequestMapping("/getAdminByUsername/{auditUser}")
	public AdminResponse getAdminByUsername(@PathVariable String auditUser) {
		AdminResponse response = new AdminResponse();
		Admin admin = adminService.getAdminByName(auditUser);
		if (admin != null) {
			AdminVO adminVO = new AdminVO();
			BeanUtils.copyProperties(admin, adminVO);
			response.setResult(adminVO);
		}
		return response;
	}
}
