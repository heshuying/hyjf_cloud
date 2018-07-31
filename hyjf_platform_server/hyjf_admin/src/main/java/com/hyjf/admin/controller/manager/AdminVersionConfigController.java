package com.hyjf.admin.controller.manager;

import com.hyjf.admin.beans.request.VersionRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.VersionConfigService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminVersionResponse;
import com.hyjf.am.resquest.admin.AdminVersionRequest;
import com.hyjf.am.vo.admin.VersionVO;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/16.
 */
@Api(value = "配置中心版本配置",description ="配置中心版本配置")
@RestController
@RequestMapping("/hyjf-admin/config/versionconfig")
public class AdminVersionConfigController extends BaseController {
    //权限名称
    private static final String PERMISSIONS = "versionconfig";
    @Autowired
    private VersionConfigService adminVersionConfigService;

    @ApiOperation(value = "配置中心版本配置", notes = "查询配置中心版本配置")
    @RequestMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult versionConfigInit(@RequestBody VersionRequestBean versionRequestBean) {
        AdminVersionRequest adminRequest= new AdminVersionRequest();
        if(StringUtils.isNotEmpty(versionRequestBean.getNameSrh())){
            adminRequest.setNameSrh(versionRequestBean.getNameSrh());
        }
        if(StringUtils.isNotEmpty(versionRequestBean.getVersionSrh())){
            adminRequest.setVersionSrh(versionRequestBean.getVersionSrh());
        }
        AdminVersionResponse response=adminVersionConfigService.versionConfigInit(adminRequest);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<VersionVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }

    @ApiOperation(value = "配置中心版本配置", notes = "版本配置详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult versionConfigInfo(@RequestBody VersionRequestBean versionRequestBean) {
        AdminVersionRequest adminRequest= new AdminVersionRequest();
        AdminVersionResponse adminResponse= null;
        if (Validator.isNotNull(versionRequestBean.getIds())) {
            Integer id = Integer.valueOf(versionRequestBean.getIds());
            adminRequest.setId(id);
            // 根据主键检索数据
            adminResponse= adminVersionConfigService.searchVersionConfigInfo(adminRequest);
        }
        if (adminResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());
        }
        return new AdminResult<VersionVO>(adminResponse.getResult()) ;
    }
    @ApiOperation(value = "配置中心版本配置", notes = "版本配置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertVersionConfig(@RequestBody VersionRequestBean versionRequestBean)  {
        AdminVersionRequest adminRequest= new AdminVersionRequest();
        BeanUtils.copyProperties(versionRequestBean, adminRequest);
        AdminVersionResponse adminResponse= null;
        ModelAndView mv = new ModelAndView();
        // 画面验证
        this.validatorFieldCheck(mv, adminRequest);
        if (ValidatorFieldCheckUtil.hasValidateError(mv)) {
            //数据字典
            List<ParamName> versionName = this.adminVersionConfigService.getParamNameList("VERSION_NAME");
            List<ParamName> isUpdate = this.adminVersionConfigService.getParamNameList("IS_UPDATE");
            adminResponse.getResult().setVersionNames(versionName);
            adminResponse.getResult().setIsUpdates(isUpdate);
            return new AdminResult<VersionVO>(adminResponse.getResult()) ;
        }
        adminResponse = adminVersionConfigService.saveVersionConfig(adminRequest);
        if(adminResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());

        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "配置中心版本配置", notes = "版本配置修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateVersionConfig(@RequestBody VersionRequestBean versionRequestBean)  {
        AdminVersionRequest adminRequest= new AdminVersionRequest();
        BeanUtils.copyProperties(versionRequestBean, adminRequest);
        AdminVersionResponse adminResponse= null;
        ModelAndView mv = new ModelAndView();
        // 画面验证
        this.validatorFieldCheck(mv, adminRequest);
        if (ValidatorFieldCheckUtil.hasValidateError(mv)) {
            //数据字典
            List<ParamName> versionName = this.adminVersionConfigService.getParamNameList("VERSION_NAME");
            List<ParamName> isUpdate = this.adminVersionConfigService.getParamNameList("IS_UPDATE");
            adminResponse.getResult().setVersionNames(versionName);
            adminResponse.getResult().setIsUpdates(isUpdate);
            return new AdminResult<VersionVO>(adminResponse.getResult()) ;
        }
        adminResponse = adminVersionConfigService.updateVersionConfig(adminRequest);
        if(adminResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());

        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "配置中心版本配置", notes = "版本配置删除")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteVersionConfig(@RequestBody VersionRequestBean versionRequestBean)  {
        AdminVersionResponse adminResponse= null;
        if(StringUtils.isNotBlank(versionRequestBean.getIds())){
            Integer id = Integer.valueOf(versionRequestBean.getIds());
            adminResponse = adminVersionConfigService.deleteVersionConfig(id);
        }
        if(adminResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());

        }
        return new AdminResult<>();
    }

    /**
     * 画面校验
     *
     * @param modelAndView
     * @param form
     */
    private void validatorFieldCheck(ModelAndView modelAndView, AdminVersionRequest form) {
        // 系统名称
        boolean typeFlag = ValidatorFieldCheckUtil.validateRequired(modelAndView, "type", String.valueOf(form.getType()));
        // 版本号
        boolean verFlag = ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "version", form.getVersion(), 12, false);
        // 地址
        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "url", form.getUrl(), 255, false);
        // 版本描述
        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "content", form.getContent(), 500, false);

        if (typeFlag && verFlag && StringUtils.isNotEmpty(form.getVersion())) {
            VersionVO version = this.adminVersionConfigService.getVersionByCode(form.getId(), form.getType(), form.getVersion());
            if (version != null) {
                ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "type-verdioncode", "exists.type.versioncode");
            }
        }
    }

}
