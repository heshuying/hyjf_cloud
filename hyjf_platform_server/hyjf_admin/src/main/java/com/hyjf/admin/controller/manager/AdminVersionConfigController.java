package com.hyjf.admin.controller.manager;

import com.hyjf.admin.beans.request.VersionRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.VersionConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminVersionResponse;
import com.hyjf.am.resquest.admin.AdminVersionRequest;
import com.hyjf.am.vo.admin.VersionVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/16.
 */
@Api(tags ="配置中心-版本配置")
@RestController
@RequestMapping("/hyjf-admin/config/versionconfig")
public class AdminVersionConfigController extends BaseController {
    //权限名称
    private static final String PERMISSIONS = "versionconfig";
    @Autowired
    private VersionConfigService adminVersionConfigService;

    @ApiOperation(value = "查询配置中心版本配置", notes = "查询配置中心版本配置")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<AdminVersionResponse> versionConfigInit(@RequestBody VersionRequestBean versionRequestBean) {
        AdminVersionRequest adminRequest= new AdminVersionRequest();
        if(StringUtils.isNotEmpty(versionRequestBean.getNameSrh())){
            adminRequest.setNameSrh(versionRequestBean.getNameSrh());
        }
        if(StringUtils.isNotEmpty(versionRequestBean.getVersionSrh())){
            adminRequest.setVersionSrh(versionRequestBean.getVersionSrh());
        }
        if(versionRequestBean.getCurrPage() > -1){
            adminRequest.setCurrPage(versionRequestBean.getCurrPage());
        }
        if(versionRequestBean.getPageSize() > -1){
            adminRequest.setPageSize(versionRequestBean.getPageSize());
        }
        AdminVersionResponse response=adminVersionConfigService.versionConfigInit(adminRequest);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<AdminVersionResponse>(response) ;
    }

    @ApiOperation(value = "版本配置详情页面", notes = "版本配置详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<AdminVersionResponse> versionConfigInfo(@RequestBody VersionRequestBean versionRequestBean) {
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
        return new AdminResult<AdminVersionResponse>(adminResponse) ;
    }
    @ApiOperation(value = "版本配置添加", notes = "版本配置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult<AdminVersionResponse> insertVersionConfig(@RequestBody VersionRequestBean versionRequestBean)  {
        AdminVersionRequest adminRequest= new AdminVersionRequest();
        BeanUtils.copyProperties(versionRequestBean, adminRequest);
        AdminVersionResponse adminResponse= new AdminVersionResponse();
        // 画面验证
        String message= this.validatorFieldCheck(adminRequest);
        if (StringUtils.isNotBlank(message)) {
            //数据字典
            List<ParamNameVO> versionName = this.adminVersionConfigService.getParamNameList("VERSION_NAME");
            List<ParamNameVO> isUpdate = this.adminVersionConfigService.getParamNameList("IS_UPDATE");
            adminResponse.setVersionNames(versionName);
            adminResponse.setIsUpdates(isUpdate);
            adminResponse.setRtn(Response.FAIL);
            adminResponse.setMessage(message);
            return new AdminResult<AdminVersionResponse>(adminResponse) ;
        }
        adminResponse = adminVersionConfigService.saveVersionConfig(adminRequest);
        if(adminResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());

        }
        return new AdminResult<AdminVersionResponse>(adminResponse) ;
    }

    @ApiOperation(value = "版本配置修改", notes = "版本配置修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<AdminVersionResponse> updateVersionConfig(@RequestBody VersionRequestBean versionRequestBean)  {
        AdminVersionRequest adminRequest= new AdminVersionRequest();
        BeanUtils.copyProperties(versionRequestBean, adminRequest);
        // modify by libin sonar start
        AdminVersionResponse adminResponse=  new AdminVersionResponse();
        // modify by libin sonar end
        // 画面验证
       String message= this.validatorFieldCheck(adminRequest);
        if (StringUtils.isNotBlank(message)) {
            //数据字典
            List<ParamNameVO> versionName = this.adminVersionConfigService.getParamNameList("VERSION_NAME");
            List<ParamNameVO> isUpdate = this.adminVersionConfigService.getParamNameList("IS_UPDATE");
            adminResponse.setVersionNames(versionName);
            adminResponse.setIsUpdates(isUpdate);
            return new AdminResult<AdminVersionResponse>(adminResponse) ;
        }
        adminResponse = adminVersionConfigService.updateVersionConfig(adminRequest);
        if(adminResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());

        }
        return new AdminResult<AdminVersionResponse>(adminResponse) ;
    }

    @ApiOperation(value = "版本配置删除", notes = "版本配置删除")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteVersionConfig(@RequestBody VersionRequestBean versionRequestBean)  {
        AdminVersionResponse adminResponse= null;
        List<Integer> ids = versionRequestBean.getDelids();
        if(!CollectionUtils.isEmpty(ids)){
            adminResponse = adminVersionConfigService.deleteVersionConfig(ids);
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
     * @param form
     */
    private String validatorFieldCheck(AdminVersionRequest form) {
        // 系统名称
        if(null == form.getType()){
            return "系统名称 不能为空！";
        }
        // 版本号
        if(null == form.getVersion()){
            return "版本号 不能为空！";
        }
        if(null != form.getVersion()&&  form.getVersion().length()>12){
            return "version长度不能超过12位！";
        }
        // 地址
        if(null == form.getUrl()){
            return "地址 不能为空！";
        }
        if(null != form.getUrl()&&  form.getUrl().length()>255){
            return "地址长度不能超过12位！";
        }
        // 版本描述
        if(null == form.getContent()){
            return "版本描述 不能为空！";
        }
        if(null != form.getContent()&&  form.getContent().length()>500){
            return "版本描述长度不能超过12位！";
        }
        if (StringUtils.isNotEmpty(form.getVersion())) {
            VersionVO version = this.adminVersionConfigService.getVersionByCode(form.getId(), form.getType(), form.getVersion());
            if (version != null) {
                return "该系统下已存在此版本";
            }
        }
        return "";
    }

}
