package com.hyjf.admin.controller.config;

import com.alibaba.fastjson.JSON;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.config.CustomerServiceGroupConfigService;
import com.hyjf.admin.service.config.CustomerServiceRepresentiveConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.CustomerServiceGroupConfigResponse;
import com.hyjf.am.response.config.CustomerServiceRepresentiveConfigResponse;
import com.hyjf.am.resquest.config.CustomerServiceGroupConfigRequest;
import com.hyjf.am.resquest.config.CustomerServiceRepresentiveConfigRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
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
import java.util.List;

/**
 * @author wgx
 * @date 2019/5/30
 */
@Api(tags = "配置中心-电销配置-坐席配置")
@RestController
@RequestMapping("/hyjf-admin/tsrConfig/group")
public class CustomerServiceRepresentiveConfigController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceRepresentiveConfigController.class);

    private static final String PERMISSIONS = "customerServiceRepresentiveConfig";

    @Autowired
    private CustomerServiceRepresentiveConfigService customerServiceRepresentiveConfigService;

    @Autowired
    private CustomerServiceGroupConfigService customerServiceGroupConfigService;

    @ApiOperation(value = "获取坐席配置列表", notes = "获取坐席配置列表")
    @PostMapping("/getCustomerServiceRepresentiveConfigList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<CustomerServiceRepresentiveConfigResponse> getCustomerServiceRepresentiveConfigList(@RequestBody CustomerServiceRepresentiveConfigRequest request) {
        CustomerServiceRepresentiveConfigResponse response = customerServiceRepresentiveConfigService.getCustomerServiceRepresentiveConfigList(request);
        List<CustomerServiceGroupConfigVO> groupList = getGroupConfigList();
        response.setGroupList(groupList);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        ListResult.build(response.getResultList(), response.getTotal());
        return new AdminResult<>(response) ;
    }

    /**
     * 获取客组列表
     * @return
     */
    private List<CustomerServiceGroupConfigVO> getGroupConfigList() {
        CustomerServiceGroupConfigRequest groupRequest = new CustomerServiceGroupConfigRequest();
        groupRequest.setPageSize(-1);
        groupRequest.setStatus(1);// 启用状态
        CustomerServiceGroupConfigResponse groupConfigResponse = customerServiceGroupConfigService.getCustomerServiceGroupConfigList(groupRequest);
        return groupConfigResponse.getResultList();
    }

    @ApiOperation(value = "添加坐席配置信息", notes = "添加坐席配置信息")
    @PostMapping("/insertCustomerServiceRepresentiveConfig")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult<CustomerServiceRepresentiveConfigResponse> insertCustomerServiceRepresentiveConfig(HttpServletRequest request, @RequestBody CustomerServiceRepresentiveConfigRequest groupRequest) {
        // 校验坐席信息
        CustomerServiceRepresentiveConfigResponse checkResponse = customerServiceRepresentiveConfigService.checkCustomerServiceRepresentiveConfig(groupRequest);
        if (checkResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(checkResponse)) {
            return new AdminResult<>(FAIL, checkResponse.getMessage());
        }
        //获取登录用户Id
        AdminSystemVO adminSystemVO = this.getUser(request);
        groupRequest.setCreateUserId(Integer.parseInt(adminSystemVO.getId()));// 创建人
        CustomerServiceRepresentiveConfigResponse response = customerServiceRepresentiveConfigService.insertCustomerServiceRepresentiveConfig(groupRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "修改坐席配置信息", notes = "修改坐席配置信息")
    @PostMapping("/updateCustomerServiceRepresentiveConfig")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<CustomerServiceRepresentiveConfigResponse> updateCustomerServiceRepresentiveConfig(HttpServletRequest request, @RequestBody CustomerServiceRepresentiveConfigRequest groupRequest) {
        if (groupRequest.getId() == null) {
            return new AdminResult<>(FAIL, "id不能为空！");
        }
        // 校验坐席信息
        CustomerServiceRepresentiveConfigResponse checkResponse = customerServiceRepresentiveConfigService.checkCustomerServiceRepresentiveConfig(groupRequest);
        if (checkResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(checkResponse)) {
            return new AdminResult<>(FAIL, checkResponse.getMessage());
        }
        //获取登录用户Id
        AdminSystemVO adminSystemVO = this.getUser(request);
        logger.info("【坐席配置】{}开始修改坐席配置，request：{}", adminSystemVO.getUsername(), JSON.toJSONString(groupRequest));
        groupRequest.setUpdateUserId(Integer.parseInt(adminSystemVO.getId()));// 修改人
        CustomerServiceRepresentiveConfigResponse response = customerServiceRepresentiveConfigService.updateCustomerServiceRepresentiveConfig(groupRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "删除坐席配置信息", notes = "删除坐席配置信息")
    @PostMapping("/deleteCustomerServiceRepresentiveConfig")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult<CustomerServiceRepresentiveConfigResponse> deleteCustomerServiceRepresentiveConfig(HttpServletRequest request, @RequestBody CustomerServiceRepresentiveConfigRequest groupRequest) {
        if (groupRequest.getId() == null) {
            return new AdminResult<>(FAIL, "id不能为空！");
        }
        //获取登录用户Id
        AdminSystemVO adminSystemVO = this.getUser(request);
        logger.info("【坐席配置】{}开始删除坐席配置，request：{}", adminSystemVO.getUsername(), JSON.toJSONString(groupRequest));
        CustomerServiceRepresentiveConfigResponse response = customerServiceRepresentiveConfigService.deleteCustomerServiceRepresentiveConfig(groupRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }
}
