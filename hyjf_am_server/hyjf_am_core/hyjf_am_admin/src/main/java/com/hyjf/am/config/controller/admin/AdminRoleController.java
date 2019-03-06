package com.hyjf.am.config.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.config.dao.model.auto.AdminRole;
import com.hyjf.am.config.dao.model.customize.AdminRoleCustomize;
import com.hyjf.am.config.service.AdminRoleService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminRoleResponse;
import com.hyjf.am.resquest.admin.UserRoleRequest;
import com.hyjf.am.resquest.config.AdminRoleRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.vo.admin.AdminRoleVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @package com.hyjf.admin.maintenance.AdminRole
 * @author dongzeshan
 * @date 2015/07/09 17:00
 * @version V1.0  
 */
@RestController
@RequestMapping("/am-config/role")
public class AdminRoleController extends BaseController {
    @Autowired
    private AdminRoleService adminRoleService;

 

    /**
     * 画面查询
     * 
     * @param request
     * @param form
     * @return
     */
    @RequestMapping("/search")
    public AdminRoleResponse search(@RequestBody AdminRoleRequest form) {

        // 创建分页
       return this.createPage(form);

    }

    /**
     * 创建权限维护分页机能
     * 
     * @param request
     * @param modelAndView
     * @param form
     */
    private AdminRoleResponse createPage(@RequestBody AdminRoleRequest form) {
        long cnt = this.adminRoleService.getRecordCount(form);
        AdminRoleResponse arr=new AdminRoleResponse();
        if (cnt > 0) {
            Paginator paginator = new Paginator(form.getCurrPage(), (int) cnt,form.getPageSize());
            List<AdminRole> recordList = this.adminRoleService.getRecordList(form, paginator.getOffset(), paginator.getLimit());
          
            arr.setRecordTotal((int)cnt);
            arr.setResultList(CommonUtils.convertBeanList(recordList, AdminRoleVO.class));

        }
        return arr;
    }

    /**
     * 迁移到权限维护详细画面
     * 
     * @param request
     * @param form
     * @return
     */
    @RequestMapping("/moveToInfoAction")
    public AdminRoleResponse moveToInfoAction(@RequestBody AdminRoleRequest form) {


    	AdminRoleResponse bean = new AdminRoleResponse();

        if ( form.getId()!=null) {

            // 根据主键判断该条数据在数据库中是否存在
    //        boolean isExists = this.adminRoleService.isExistsRecord(form.getId());

//            // 没有添加权限 同时 也没能检索出数据的时候异常
//            if (!isExists) {
//                Subject currentUser = SecurityUtils.getSubject();
//                currentUser.checkPermission(AdminRoleDefine.PERMISSIONS_ADD);
//            }

            // 根据主键检索数据
            AdminRole record = this.adminRoleService.getRecord(form.getId());
            AdminRoleVO arvo=new AdminRoleVO();
            BeanUtils.copyProperties(record, arvo);
            bean.setResult(arvo);
        }

//        modelAndView.addObject(AdminRoleDefine.ROLE_FORM, bean);
//        LogUtil.endLog(THIS_CLASS, AdminRoleDefine.INFO_ACTION);
        return bean;
    }

    /**
     * 添加权限维护信息
     * 
     * @param request
     * @param form
     * @return
     */
    @RequestMapping("/insertAction")
    public AdminRoleResponse insertAction(@RequestBody AdminRoleRequest form) {
//        LogUtil.startLog(THIS_CLASS, AdminRoleDefine.INSERT_ACTION);
//        ModelAndView modelAndView = new ModelAndView(AdminRoleDefine.INFO_PATH);
//        // 画面验证
//        this.validatorFieldCheck(modelAndView, form);
//
//        if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
//            modelAndView.addObject(AdminRoleDefine.ROLE_FORM, form);
//
//            LogUtil.errorLog(THIS_CLASS, AdminRoleDefine.INSERT_ACTION, "输入内容验证失败", null);
//            return modelAndView;
//        }
			if(form.getId()==-1) {
				this.adminRoleService.insertRecord(form);
			}else {
				this.adminRoleService.updateRecord(form);
			}        
//        // 创建分页
//        this.createPage(request, modelAndView, form);
//
//        modelAndView.addObject(AdminRoleDefine.SUCCESS, AdminRoleDefine.SUCCESS);
//        LogUtil.endLog(THIS_CLASS, AdminRoleDefine.INSERT_ACTION);
//        return modelAndView;
        return new AdminRoleResponse();
    }

    /**
     * 修改权限维护信息
     * 
     * @param request
     * @param form
     * @return
     */
    @RequestMapping("/updateAction")
    public AdminRoleResponse updateAction(@RequestBody AdminRoleRequest form) {
//        LogUtil.startLog(THIS_CLASS, AdminRoleDefine.UPDATE_ACTION);
//        ModelAndView modelAndView = new ModelAndView(AdminRoleDefine.INFO_PATH);
//        // 画面验证
//        this.validatorFieldCheck(modelAndView, form);
//
//        if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
//            modelAndView.addObject(AdminRoleDefine.ROLE_FORM, form);
//
//            LogUtil.errorLog(THIS_CLASS, AdminRoleDefine.UPDATE_ACTION, "输入内容验证失败", null);
//            return modelAndView;
//        }

        // 维护角色是当前用户角色时,不能禁用
//        if (form.getId() == ShiroUtil.getLoginUserRoleId() && String.valueOf(CustomConstants.FLAG_STATUS_DISABLE).equals(form.getState())) {
//            modelAndView.addObject(AdminRoleDefine.ROLE_FORM, form);
//            modelAndView.addObject("state_error", "该角色正在被当前用户使用中,不能禁用,请重新操作!");
//            return modelAndView;
//        }

//        AdminRole adminRole = new AdminRole();
//        try {
//            BeanUtils.copyProperties(form, adminRole);
//        } catch (Exception e) {
//            LogUtil.errorLog(THIS_CLASS, AdminRoleDefine.UPDATE_ACTION, e);
//        }
        this.adminRoleService.updateRecord(form);
//        // 创建分页
//        this.createPage(request, modelAndView, form);
//
//        modelAndView.addObject(AdminRoleDefine.SUCCESS, AdminRoleDefine.SUCCESS);
//        LogUtil.endLog(THIS_CLASS, AdminRoleDefine.UPDATE_ACTION);
//        return modelAndView;
        return new AdminRoleResponse();
    }

    /**
     * 画面校验
     * 
     * @param modelAndView
     * @param form
     */
//    private void validatorFieldCheck(ModelAndView modelAndView, AdminRoleRequest form) {
//        // 角色名字(必须,最大长度)
//        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "roleName", form.getRoleName(), 20, true);
//        // 排序(最大长度)
//        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "sort", String.valueOf(form.getSort()), 5, false);
//        // 角色状态(必须)
//        ValidatorFieldCheckUtil.validateRequired(modelAndView, "state", form.getState());
//
//        if (Validator.isNull(form.getId()) && !ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
//            // 检查角色名字唯一性
//            int cnt = adminRoleService.countRoleByname(form.getId(), form.getRoleName());
//            if (cnt > 0) {
//                ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "roleName", "repeat");
//            }
//        }
//    }

    /**
     * 删除权限维护
     * 
     * @param request
     * @param form
     * @return
     */
    @RequestMapping("/deleteRecordAction")
//    @RequestMapping(AdminRoleDefine.DELETE_ACTION)
//    @RequiresPermissions(AdminRoleDefine.PERMISSIONS_DELETE)
    public AdminRoleResponse deleteRecordAction(@RequestBody AdminRoleRequest form) {
//        LogUtil.startLog(THIS_CLASS, AdminRoleDefine.DELETE_ACTION);
//        ModelAndView modelAndView = new ModelAndView(AdminRoleDefine.LIST_PATH);

//        List<Integer> recordList = JSONArray.parseArray(form.getIds(), Integer.class);
//
//        // 维护角色是当前用户角色时
//        if (recordList.contains(ShiroUtil.getLoginUserRoleId())) {
//            attr.addFlashAttribute("delete_error", "该角色正在被当前用户使用中,不能删除,请重新操作!");
//            modelAndView.setViewName("redirect:" + AdminRoleDefine.REQUEST_MAPPING + "/" + AdminRoleDefine.INIT);
//            return modelAndView;
//        }
        // 删除角色
        this.adminRoleService.deleteRecord(form.getIds(),form.getAdminId());
       
//        LogUtil.endLog(THIS_CLASS, .DELETE_ACTION);
//        modelAndView.setViewName("redirect:" + AdminRoleDefine.REQUEST_MAPPING + "/" + AdminRoleDefine.INIT);
//        return modelAndView;
        return new AdminRoleResponse();
    }

    /**
     * 迁移到授权画面
     * 
     * @param request
     * @param form
     * @return
     * @throws Exception
     */
//    @RequestMapping("/moveToAuthAction")
//    public AdminRoleResponse moveToAuthAction( AdminRoleRequest form) throws Exception {
////        LogUtil.startLog(THIS_CLASS, AdminRoleDefine.INFO_ACTION);
////        ModelAndView modelAndView = new ModelAndView(AdminRoleDefine.AUTH_PATH);
//        String ids = form.getIds();
//        AdminRoleRequest bean = new AdminRoleRequest();
//
//        if (Validator.isNotNull(ids) && NumberUtils.isNumber(ids)) {
//            Integer id = Integer.valueOf(ids);
//            bean.setId(id);
//        } else {
//            throw new Exception("角色不存在");
//        }
//
////        modelAndView.addObject(AdminRoleDefine.ROLE_FORM, bean);
////        LogUtil.endLog(THIS_CLASS, AdminRoleDefine.INFO_ACTION);
////        return modelAndView;
//    }

    /**
     * 菜单管理画面初始化
     * 
     * @param request
     * @param form
     * @return
     */
    @GetMapping("/getAdminRoleMenu/{roleId}")
    public String getAdminRoleMenu(@PathVariable String roleId) {
//        LogUtil.startLog(THIS_CLASS, AdminRoleDefine.MENU_INFO_ACTION);

        AdminRoleCustomize adminRoleCustomize = new AdminRoleCustomize();
        if (Validator.isNotNull(roleId)) {
            // 角色ID
            adminRoleCustomize.setRoleId(roleId);
        }
        // 取得角色菜单权限表数据
        JSONArray ja = this.adminRoleService.deleteAndgetAdminRoleMenu(adminRoleCustomize);
        if (ja != null) {
            return ja.toString();
        }

        return null;
    }
    @GetMapping("/getPermissionId/{menuId}")
    public List<String> getPermissionId(@PathVariable String menuId) {
//        LogUtil.startLog(THIS_CLASS, AdminRoleDefine.MENU_INFO_ACTION);

    	
        return this.adminRoleService.getPermissionId(menuId);
    }
    /**
     * 插入或更新[角色菜单权限表]数据
     * 
     * @param request
     * @return
     */
    @RequestMapping("/modifyPermissionAction")
    public AdminRoleResponse modifyPermissionAction(@RequestBody UserRoleRequest userRoleRequest) {
//        LogUtil.startLog(THIS_CLASS, AdminRoleDefine.MODIFY_PERMISSION_ACTION);
//
//        JSONObject ret = new JSONObject();
//        int cnt = -1;
//            Integer roleId = GetterUtil.getInteger(bean.getRoleId());
//            List<AdminRoleMenuPermissionsVO> list = bean.getPermList();
////            // 维护角色是当前用户角色时,不能禁用
////            if (roleId == ShiroUtil.getLoginUserRoleId() && (list == null || list.size() == 0)) {
////                ret.put(ReturncashDefine.JSON_STATUS_KEY, ReturncashDefine.JSON_STATUS_NG);
////                ret.put(ReturncashDefine.JSON_RESULT_KEY, "该角色正在被当前用户使用中,不能删除所有的权限,请重新操作!");
////                return ret.toString();
////            }
//            
//            if (Validator.isNotNull(roleId)) {
//                cnt = this.adminRoleService.updatePermission(roleId, CommonUtils.convertBeanList(list, AdminRoleMenuPermissions.class),bean.getAdminId());
//            }
            AdminRoleResponse arr=new AdminRoleResponse();
            
            try {
                adminRoleService.setRolePermission(userRoleRequest);
            } catch (Exception e) {
            	logger.error(e.getMessage());
            	arr.setRtn(Response.FAIL);
            	arr.setMessage("角色权限修改时发生错误,请重新操作!");
            }
            
            if(adminRoleService.getRole(userRoleRequest.getUserId()).getRoleId()==userRoleRequest.getRoleId()) {
            	arr.setIsRole(true);
            }else {
            	arr.setIsRole(false);
            }
        // 操作成功
//        if(cnt > -1) {
//
//        } else {
//        	arr.setRtn(Response.FAIL);
//        	arr.setMessage("角色权限修改时发生错误,请重新操作!");
//            ret.put(ReturncashDefine.JSON_STATUS_KEY, ReturncashDefine.JSON_STATUS_NG);
//            ret.put(ReturncashDefine.JSON_RESULT_KEY, "角色权限修改时发生错误,请重新操作!");
    //    }

//        LogUtil.endLog(THIS_CLASS, AdminRoleDefine.MODIFY_PERMISSION_ACTION);
        return arr;
    }

    /**
     * 检查角色名称唯一性
     * 
     * @param request
     * @return
     */
    @RequestMapping("/checkAction")
    public AdminRoleResponse checkAction(@RequestBody AdminRoleRequest bean) {
 
    	AdminRoleResponse arr=new AdminRoleResponse();
        // 检查角色名称唯一性
            int cnt = adminRoleService.countRoleByname(bean.getId(), bean.getRoleName());
            if (cnt > 0) {
//                String message = ValidatorFieldCheckUtil.getErrorMessage("repeat", "");
//                message = message.replace("{label}", "角色名称");
//                ret.put(AdminRoleDefine.JSON_VALID_INFO_KEY, message);
                arr.setRtn(Response.FAIL);
                arr.setMessage("角色名称重复");
            }
        // 没有错误时,返回y
//        if (!ret.containsKey(AdminRoleDefine.JSON_VALID_INFO_KEY)) {
//            ret.put(AdminRoleDefine.JSON_VALID_STATUS_KEY, AdminRoleDefine.JSON_VALID_STATUS_OK);
//        }

//        LogUtil.endLog(THIS_CLASS, AdminRoleDefine.CHECK_ACTION);
        return arr;
    }
}
