/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.request.JcCustomerServerRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.JcCustomerService;
import com.hyjf.am.response.admin.CustomerServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "客户服务列表", notes = "客户服务列表")
    @PostMapping("/serverList")
    public AdminResult getServerList(@RequestBody JcCustomerServerRequest request) {
        CustomerServerResponse response = jcCustomerService.getServerList(request);
        return null;
    }
}
