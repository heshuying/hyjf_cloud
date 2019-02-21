package com.hyjf.admin.controller.config;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.ApplicantConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.ConfigApplicantResponse;
import com.hyjf.am.resquest.config.ConfigApplicantRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ConfigApplicantVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lisheng
 * @version ApplicantConfigController, v0.1 2019/2/21 11:10
 */
@Api(value = "配置中心-项目申请人配置", tags = "配置中心-项目申请人配置")
@RestController
@RequestMapping("/hyjf-admin/manager/config/applicantConfig")
public class ApplicantConfigController extends BaseController {

    public static final String PERMISSIONS = "applicantConfig";
    @Autowired
    private ApplicantConfigService applicantConfigService;

    @ApiOperation(value = "项目申请人配置列表", notes = "项目申请人配置列表")
    @PostMapping(value = "/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<ConfigApplicantVO>> getApplicantConfigList(@RequestBody ConfigApplicantRequest request) {
        ConfigApplicantResponse response = applicantConfigService.getApplicantConfigList(request);
        if (response == null) {
            return new AdminResult<>(FAIL, "获取项目申请人配置列表失败");
        } else if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<ConfigApplicantVO> list = CommonUtils.convertBeanList(response.getResultList(),ConfigApplicantVO.class);
        return new AdminResult<ListResult<ConfigApplicantVO>>(ListResult.build(list, response.getCount()));
    }

    @ApiOperation(value = "项目申请人配置添加", notes = "项目申请人配置添加")
    @PostMapping(value = "/add")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult addConfigApplicant(@RequestBody ConfigApplicantRequest request,HttpServletRequest req) {
        AdminSystemVO user = getUser(req);
        request.setCreateUserId(Integer.valueOf(user.getId()));
        ConfigApplicantResponse response = applicantConfigService.addApplicantConfigList(request);
        if (response == null) {
            return new AdminResult<>(FAIL, "添加项目申请人配置列表失败");
        } else if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
    }

    @ApiOperation(value = "申请人配置修改", notes = "项目申请人配置修改")
    @PostMapping(value = "/update")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateConfigApplicant(@RequestBody ConfigApplicantRequest request,HttpServletRequest req) {
        AdminSystemVO user = getUser(req);
        request.setUpdateUserId(Integer.valueOf(user.getId()));
        ConfigApplicantResponse response = applicantConfigService.updateApplicantConfigList(request);
        if (response == null) {
            return new AdminResult<>(FAIL, "修改项目申请人配置失败");
        } else if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
    }

    @ApiOperation(value = "申请人配置转跳", notes = "项目申请人配置转跳")
    @PostMapping(value = "/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_MODIFY,ShiroConstants.PERMISSION_ADD})
    public AdminResult<ConfigApplicantResponse> infoConfigApplicant(@RequestBody ConfigApplicantRequest request) {
        ConfigApplicantResponse response = applicantConfigService.findConfigApplicant(request);
        if (response == null) {
            return new AdminResult<>(FAIL, "转跳项目申请人配置列表失败");
        } else if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<ConfigApplicantResponse>(response);
    }
}
