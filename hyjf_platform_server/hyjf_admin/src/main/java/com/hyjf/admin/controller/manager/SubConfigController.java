package com.hyjf.admin.controller.manager;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.Utils.AdminValidatorFieldCheckUtil;
import com.hyjf.admin.beans.request.SubConfigRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.SubConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminSubConfigResponse;
import com.hyjf.am.resquest.admin.AdminSubConfigRequest;
import com.hyjf.am.vo.trade.SubCommissionListConfigVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author by xiehuili on 2018/7/9.
 * @version SubConfigController, v0.1 2018/7/9.
 */
@Api(value = "配置中心分账名单配置")
@RestController
@RequestMapping("/hyjf-admin/config/subconfig")
public class SubConfigController extends BaseController {
    //权限名称
    private static final String PERMISSIONS = "subconfig";

    @Autowired
    private SubConfigService subConfigService;
    @ApiOperation(value = "配置中心分账名单配置", notes = "查询分账名单配置")
    @RequestMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<SubCommissionListConfigVo>> subConfigInit(HttpServletRequest request, @RequestBody SubConfigRequestBean from) {
        AdminSubConfigRequest adminRequest= new AdminSubConfigRequest();
        //可以直接使用
        BeanUtils.copyProperties(from, adminRequest);
        AdminSubConfigResponse resList=subConfigService.selectSubConfigListByParam(adminRequest);
        if(resList==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(resList)) {
            return new AdminResult<>(FAIL, resList.getMessage());

        }
        return new AdminResult<ListResult<SubCommissionListConfigVo>>(ListResult.build(resList.getResultList(), resList.getRecordTotal())) ;
    }
    @ApiOperation(value = "配置中心分账名单配置", notes = "查询分账名单配置")
    @RequestMapping("/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    public AdminResult<ListResult<SubCommissionListConfigVo>> subConfigSearch(HttpServletRequest request, @RequestBody SubConfigRequestBean from) {
        AdminSubConfigRequest adminRequest= new AdminSubConfigRequest();
        //可以直接使用
        BeanUtils.copyProperties(from, adminRequest);
        AdminSubConfigResponse resList=subConfigService.selectSubConfigListByParam(adminRequest);
        if(resList==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(resList)) {
            return new AdminResult<>(FAIL, resList.getMessage());

        }
        return new AdminResult<ListResult<SubCommissionListConfigVo>>(ListResult.build(resList.getResultList(), resList.getRecordTotal())) ;
    }
    @ApiOperation(value = "配置中心分账名单配置", notes = "分账名单配置页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult<SubCommissionListConfigVo>  subConfigInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String ids) {
        AdminSubConfigRequest adminRequest= new AdminSubConfigRequest();
        adminRequest.setIds(ids);
        AdminSubConfigResponse result= subConfigService.selectSubConfigInfo(adminRequest);
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<SubCommissionListConfigVo>(result.getResult()) ;
    }
    @ApiOperation(value = "配置中心分账名单配置", notes = "分账名单配置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertSubConfig(HttpServletRequest request, HttpServletResponse response, @RequestBody SubConfigRequestBean from) {
        AdminSubConfigRequest adminRequest= new AdminSubConfigRequest();
        BeanUtils.copyProperties(from, adminRequest);
        //测试联调时放开
//        adminRequest.setUserId(Integer.valueOf(this.getUser(request).getId()));
        // 表单校验(双表校验)
        JSONObject json =new JSONObject();
        //表单字段校验
        json = this.validatorFieldCheck(json, adminRequest);
        if (AdminValidatorFieldCheckUtil.hasValidateError(json)) {
            return new AdminResult<>(FAIL, "校验失败");
        }
        AdminSubConfigResponse result= subConfigService.insertSubConfig(adminRequest);
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "配置中心分账名单配置", notes = "分账名单配置修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateSubConfig(HttpServletRequest request, HttpServletResponse response, @RequestBody SubConfigRequestBean from) {
        AdminSubConfigRequest adminRequest= new AdminSubConfigRequest();
        BeanUtils.copyProperties(from, adminRequest);
        //测试联调时放开
//        adminRequest.setUserId(Integer.valueOf(this.getUser(request).getId()));
        AdminSubConfigResponse result= subConfigService.updateSubConfig(adminRequest);
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "配置中心分账名单配置", notes = "分账名单配置删除")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteSubConfig(HttpServletRequest request, HttpServletResponse response, @RequestBody String ids) {
        AdminSubConfigRequest adminRequest= new AdminSubConfigRequest();
        AdminSubConfigResponse result=null;
        if(StringUtils.isNotBlank(ids)){
            adminRequest.setIds(ids);
            result= subConfigService.deleteSubConfig(adminRequest);
        }
        if (result == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());
        }
        return new AdminResult<>();
    }


    /**
     * 画面校验
     *
     * @param jsonObject
     * @param form
     */
    private JSONObject validatorFieldCheck(JSONObject jsonObject, AdminSubConfigRequest form) {
        // 用户名
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "username", form.getUsername());
        //姓名
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "truename", String.valueOf(form.getTruename()));
        //用户角色
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "roleName", form.getRoleName());
        //用户类型
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "userType", form.getUserType());
        //银行开户状态
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "bankOpenAccount", form.getBankOpenAccount());
        //江西银行电子账号
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "account", form.getAccount());
        // 用户状态
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "status", String.valueOf(form.getStatus()));
        return jsonObject;

    }

}
