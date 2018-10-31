package com.hyjf.am.config.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.customize.AdminSystem;
import com.hyjf.am.config.dao.model.customize.Tree;
import com.hyjf.am.config.service.AdminMenuService;
import com.hyjf.am.config.service.AdminRoleService;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.TreeResponse;
import com.hyjf.am.resquest.config.AdminMenuRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.TreeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @package com.hyjf.admin.maintenance.Admin
 * @author dongzeshan
 * @date 2018/09/06 17:00
 * @version V1.0  
 */
@RestController
@RequestMapping("/am-config/menu")
public class AdminMenuController extends BaseConfigController {

	@Autowired
	private AdminMenuService adminMenuService;
	@Autowired
	private AdminRoleService adminRoleService;

	/**
	 * 菜单管理画面初始化
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/infoAction")
	public TreeResponse infoAction(@RequestBody AdminMenuRequest form) {
		// LogUtil.startLog(THIS_CLASS, AdminMenuDefine.INFO_ACTION);

		TreeResponse adminR = new TreeResponse();
		List<Tree> tree = this.adminMenuService.getRecordList(form);
		if (null != tree) {
			List<TreeVO> tvo = CommonUtils.convertBeanList(tree, TreeVO.class);
			adminR.setResultList(tvo);
		}
		return adminR;
	}

	/**
	 * 添加菜单管理信息
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/insertAction")
	public AdminSystemResponse insertAction(@RequestBody AdminMenuRequest form) {

		// 设置选中的菜单
		form.setSelectedNode(form.getMenuUuid());

		// 画面验证
		// this.validatorFieldCheck(modelAndView, form);

		// if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
		// modelAndView.addObject(AdminMenuDefine.ADMIN_MENU_FORM, form);
		//
		// LogUtil.errorLog(THIS_CLASS, AdminMenuDefine.INSERT_ACTION,
		// "输入内容验证失败", null);
		// return modelAndView;
		// }

		this.adminMenuService.insertRecord(form);

		// // 更新权限
		// ShiroUtil.updateAuth();
		//
		// attr.addFlashAttribute(AdminMenuDefine.ADMIN_MENU_FORM, form);
		// modelAndView = new ModelAndView("redirect:" +
		// AdminMenuDefine.REQUEST_MAPPING + "/" + AdminMenuDefine.INIT);
		// LogUtil.endLog(THIS_CLASS, AdminMenuDefine.INSERT_ACTION);
		// return modelAndView;
		return new AdminSystemResponse();
	}

	/**
	 * 修改菜单管理信息
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/getuser")
	public AdminSystemResponse getuser(@RequestBody AdminMenuRequest form) {
		// LogUtil.startLog(THIS_CLASS, AdminMenuDefine.UPDATE_ACTION);
		// ModelAndView modelAndView = new
		// ModelAndView(AdminMenuDefine.LIST_PATH);

		// 设置选中的菜单
		form.setSelectedNode(form.getMenuUuid());

		// // 画面验证
		// this.validatorFieldCheck(modelAndView, form);
		//
		// if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
		// modelAndView.addObject(AdminMenuDefine.ADMIN_MENU_FORM, form);
		//
		// LogUtil.errorLog(THIS_CLASS, AdminMenuDefine.INSERT_ACTION,
		// "输入内容验证失败", null);
		// return modelAndView;
		// }
		// 更新菜单
		this.adminMenuService.updateRecord(form);

		// // 更新权限
		// ShiroUtil.updateAuth();
		//
		// attr.addFlashAttribute(AdminMenuDefine.ADMIN_MENU_FORM, form);
		// modelAndView = new ModelAndView("redirect:" +
		// AdminMenuDefine.REQUEST_MAPPING + "/" + AdminMenuDefine.INIT);
		// LogUtil.endLog(THIS_CLASS, AdminMenuDefine.UPDATE_ACTION);
		return new AdminSystemResponse();
	}

	// /**
	// * 画面校验
	// *
	// * @param modelAndView
	// * @param form
	// */
	// private void validatorFieldCheck(ModelAndView modelAndView, AdminMenuBean
	// form) {
	// // 菜单名称
	// ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "menuName",
	// form.getMenuName(), 20, true);
	// // 权限名称
	// ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "menuCtrl",
	// form.getMenuCtrl(), 20, false);
	// // URL
	// ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "menuUrl",
	// form.getMenuUrl(), 255, false);
	// // 图标
	// ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "menuIcon",
	// form.getMenuIcon(), 50, false);
	// // 排序
	// ValidatorFieldCheckUtil.validateNum(modelAndView, "menuSort",
	// GetterUtil.getString(form.getMenuSort()), false);
	// }

	/**
	 * 删除菜单
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/deleteRecordAction")
	public AdminSystemResponse deleteRecordAction(@RequestBody AdminMenuRequest form) {
		// LogUtil.startLog(THIS_CLASS, AdminMenuDefine.DELETE_ACTION);
//		List<String> recordList = JSONArray.parseArray(form.getIds(), String.class);

		this.adminMenuService.deleteRecord(form.getIds(),form.getAdminId());

		// // 更新权限
		// ShiroUtil.updateAuth();

		// 设置选中的菜单
		// form.setSelectedNode(form.getMenuPuuid());
		// attr.addFlashAttribute(AdminMenuDefine.ADMIN_MENU_FORM, form);
		// LogUtil.endLog(THIS_CLASS, AdminMenuDefine.DELETE_ACTION);
		// return "redirect:" + AdminMenuDefine.REQUEST_MAPPING + "/" +
		// AdminMenuDefine.INIT;
		return new AdminSystemResponse();
	}

	/**
	 * 迁移到授权画面
	 *
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/moveToAuthAction")
	public AdminSystemResponse moveToAuthAction(@RequestBody AdminMenuRequest form) throws Exception {
		// LogUtil.startLog(THIS_CLASS, AdminMenuDefine.SETTING_ACTION);
		// ModelAndView modelAndView = new
		// ModelAndView(AdminMenuDefine.SETTING_PATH);
		String menuUuid = form.getMenuUuid();
		AdminSystemResponse bean = new AdminSystemResponse();

		if (Validator.isNotNull(menuUuid)) {
			List<AdminSystem> list = this.adminMenuService.getMenuPermissionsList(menuUuid);
			bean.setResultList(CommonUtils.convertBeanList(list, AdminSystemVO.class));
		} else {
			//throw new Exception("菜单不存在");
			logger.error("menuUuid is null,菜单不存在");
			return new AdminSystemResponse();
		}
		return bean;
		// modelAndView.addObject(AdminMenuDefine.ADMIN_MENU_FORM, bean);
		// LogUtil.endLog(THIS_CLASS, AdminMenuDefine.SETTING_ACTION);
		// return modelAndView;
	}

	/**
	 * 修改菜单权限管理信息
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/updateMenuPermissionsAction")
	public AdminSystemResponse updateMenuPermissionsAction(@RequestBody AdminMenuRequest form) throws Exception {
		// LogUtil.startLog(THIS_CLASS, AdminMenuDefine.UPDATE_SETTING_ACTION);
		// ModelAndView modelAndView = new
		// ModelAndView(AdminMenuDefine.SETTING_PATH);

		String menuUuid = form.getMenuUuid();
		AdminSystemResponse bean = new AdminSystemResponse();
		if (Validator.isNotNull(menuUuid)) {
			// 更新菜单权限
			this.adminMenuService.updateMenuPermissions(form);

			// bean.setMenuUuid(menuUuid);
			List<AdminSystem> list = this.adminMenuService.getMenuPermissionsList(menuUuid);
			bean.setResultList(CommonUtils.convertBeanList(list, AdminSystemVO.class));
		} else {
			throw new Exception("菜单不存在");
		}
		return bean;
		// modelAndView.addObject(AdminMenuDefine.ADMIN_MENU_FORM, bean);
		// modelAndView.addObject(AdminRoleDefine.SUCCESS,
		// AdminRoleDefine.SUCCESS);
		// LogUtil.endLog(THIS_CLASS, AdminMenuDefine.UPDATE_SETTING_ACTION);
		// return modelAndView;
	}
    /**
     * 拉取用户可显示的菜单
     * @param userId
     * @return
     */
    @RequestMapping("/selectLeftMenuTree/{adminId}")
    public JSONArray selectLeftMenuTree2(@PathVariable String adminId) {
        AdminSystem adminSystem = new AdminSystem();
        adminSystem.setId(adminId);
        adminSystem.setPermission("VIEW");
        JSONArray tree = adminMenuService.selectLeftMenuTree2(adminSystem);
        return tree;
    }
}
