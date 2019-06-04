package com.hyjf.admin.controller.config;

import com.alibaba.fastjson.JSON;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.config.CustomerServiceGroupConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.CustomerServiceGroupConfigResponse;
import com.hyjf.am.resquest.config.CustomerServiceGroupConfigRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wgx
 * @date 2019/5/29
 */
@Api(tags = "配置中心-电销配置-客组配置")
@RestController
@RequestMapping("/hyjf-admin/tsrConfig/group")
public class CustomerServiceGroupConfigController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceGroupConfigController.class);

    private static final String PERMISSIONS = "customerServiceGroup";

    @Autowired
    private CustomerServiceGroupConfigService customerServiceGroupConfigService;

    @ApiOperation(value = "获取客组配置列表", notes = "获取客组配置列表")
    @PostMapping("/getCustomerServiceGroupConfigList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CustomerServiceGroupConfigVO>> getCustomerServiceGroupConfigList(@RequestBody CustomerServiceGroupConfigRequest request) {
        CustomerServiceGroupConfigResponse response = customerServiceGroupConfigService.getCustomerServiceGroupConfigList(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getTotal()));
    }

    @ApiOperation(value = "添加客组配置信息", notes = "添加客组配置信息")
    @PostMapping("/insertCustomerServiceGroupConfig")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult<CustomerServiceGroupConfigResponse> insertCustomerServiceGroupConfig(HttpServletRequest request, @RequestBody CustomerServiceGroupConfigRequest groupRequest) {
        // 校验客组信息
        CustomerServiceGroupConfigResponse checkResponse = customerServiceGroupConfigService.checkCustomerServiceGroupConfig(groupRequest);
        if (checkResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(checkResponse)) {
            return new AdminResult<>(FAIL, checkResponse.getMessage());
        }
        //获取登录用户Id
        AdminSystemVO adminSystemVO = this.getUser(request);
        groupRequest.setCreateUserId(Integer.parseInt(adminSystemVO.getId()));// 创建人
        CustomerServiceGroupConfigResponse response = customerServiceGroupConfigService.insertCustomerServiceGroupConfig(groupRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "修改客组配置信息", notes = "修改客组配置信息")
    @PostMapping("/updateCustomerServiceGroupConfig")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<CustomerServiceGroupConfigResponse> updateCustomerServiceGroupConfig(HttpServletRequest request, @RequestBody CustomerServiceGroupConfigRequest groupRequest) {
        if (groupRequest.getId() == null) {
            return new AdminResult<>(FAIL, "id不能为空！");
        }
        // 校验客组信息
        CustomerServiceGroupConfigResponse checkResponse = customerServiceGroupConfigService.checkCustomerServiceGroupConfig(groupRequest);
        if (checkResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(checkResponse)) {
            return new AdminResult<>(FAIL, checkResponse.getMessage());
        }
        //获取登录用户Id
        AdminSystemVO adminSystemVO = this.getUser(request);
        logger.info("【客组配置】{}开始修改客组配置，request：{}", adminSystemVO.getUsername(), JSON.toJSONString(groupRequest));
        groupRequest.setUpdateUserId(Integer.parseInt(adminSystemVO.getId()));// 修改人
        CustomerServiceGroupConfigResponse response = customerServiceGroupConfigService.updateCustomerServiceGroupConfig(groupRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "校验客组配置信息", notes = "校验客组配置信息")
    @PostMapping("/checkCustomerServiceGroupConfig")
    public AdminResult<CustomerServiceGroupConfigResponse> updateCustomerServiceRepresentiveConfig(@RequestBody CustomerServiceGroupConfigRequest groupRequest) {
        CustomerServiceGroupConfigResponse checkResponse = customerServiceGroupConfigService.checkCustomerServiceGroupConfig(groupRequest);
        if (checkResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(checkResponse)) {
            return new AdminResult<>(FAIL, checkResponse.getMessage());
        }
        return new AdminResult<>(checkResponse);
    }
}
