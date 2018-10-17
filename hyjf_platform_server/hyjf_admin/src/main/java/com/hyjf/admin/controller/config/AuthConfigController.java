/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.vo.HjhUserAuthConfigCustomizeAPIVO;
import com.hyjf.admin.beans.vo.HjhUserAuthConfigLogCustomizeAPIVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AuthConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminAuthConfigLogResponse;
import com.hyjf.am.response.admin.AdminAuthConfigResponse;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jun
 * @version AuthConfigController, v0.1 2018/10/14 11:36
 */
@Api(value = "配置中心-授权配置", tags = "配置中心-授权配置")
@RestController
@RequestMapping("/hyjf-admin/configCenter/authConfig")
public class AuthConfigController extends BaseController {

    public static final String PERMISSIONS = "authConfig";
    @Autowired
    private AuthConfigService authConfigService;

    @ApiOperation(value = "授权配置列表", notes = "授权配置列表")
    @PostMapping(value = "/authConfigList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<HjhUserAuthConfigCustomizeAPIVO>> getAuthConfigList() {
        AdminAuthConfigResponse authConfigResponse = authConfigService.getAuthConfigList();
        if (authConfigResponse == null) {
            return new AdminResult<>(FAIL, "获取授权配置列表失败");
        } else if (!Response.isSuccess(authConfigResponse)) {
            return new AdminResult<>(FAIL, authConfigResponse.getMessage());
        }

        List<HjhUserAuthConfigCustomizeAPIVO> apiList = CommonUtils.convertBeanList(authConfigResponse.getResultList(),HjhUserAuthConfigCustomizeAPIVO.class);
        return new AdminResult<ListResult<HjhUserAuthConfigCustomizeAPIVO>>(ListResult.build(apiList, authConfigResponse.getCount()));
    }

    @ApiOperation(value = "授权配置记录", notes = "授权配置记录")
    @PostMapping(value = "/authConfigLogList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<HjhUserAuthConfigLogCustomizeAPIVO>> getAuthConfigLogList(HjhUserAuthConfigLogCustomizeAPIVO request) {
        AdminAuthConfigLogResponse response = authConfigService.getAuthConfigLogList(CommonUtils.convertBean(request,HjhUserAuthConfigLogCustomizeVO.class));
        if (response == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }else if(!Response.isSuccess(response)){
            return new AdminResult<>(FAIL, response.getMessage());
        }

        List<HjhUserAuthConfigLogCustomizeAPIVO> apiList = CommonUtils.convertBeanList(response.getResultList(), HjhUserAuthConfigLogCustomizeAPIVO.class);
        return new AdminResult<ListResult<HjhUserAuthConfigLogCustomizeAPIVO>>(ListResult.build(apiList, response.getCount()));

    }


}
