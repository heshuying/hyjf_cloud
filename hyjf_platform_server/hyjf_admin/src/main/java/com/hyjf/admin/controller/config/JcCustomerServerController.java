/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.JcCustomerService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CustomerServerResponse;
import com.hyjf.am.resquest.message.JcCustomerServerRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yaoyong
 * @version JcCustomerServerController, v0.1 2019/6/21 10:54
 */
@Api(tags = "配置中心-客户服务")
@RestController
@RequestMapping("/hyjf-admin/customerServer")
public class JcCustomerServerController extends BaseController {

    @Autowired
    private JcCustomerService jcCustomerService;

    /** 权限关键字 */
    public static final String PERMISSIONS = "customerServer";

    @ApiOperation(value = "客户服务列表", notes = "客户服务列表")
    @PostMapping("/serverList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult getServerList(@RequestBody JcCustomerServerRequest request) {
        CustomerServerResponse response = jcCustomerService.getServerList(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "添加客户服务", notes = "添加客户服务")
    @PostMapping("/addCustomerServer")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult addCustomerServer(@RequestBody JcCustomerServerRequest request) {
        CustomerServerResponse response = jcCustomerService.addCustomerServer(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "根据id查询客户服务", notes = "根据id查询客户服务")
    @PostMapping("/getCustomerServer")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult getCustomerServer(@RequestBody JcCustomerServerRequest request) {
        CustomerServerResponse response = jcCustomerService.getCustomerServer(request.getId());
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "修改客服服务", notes = "修改客户服务")
    @PostMapping("/updateCustomerServer")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateCustomerServer(@RequestBody JcCustomerServerRequest request) {
        CustomerServerResponse response = jcCustomerService.updateCustomerServer(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }
}
