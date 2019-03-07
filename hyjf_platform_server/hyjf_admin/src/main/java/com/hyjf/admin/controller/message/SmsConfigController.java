package com.hyjf.admin.controller.message;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.SmsConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author xiehuili on 2018/8/14.
 */
@Api(tags = "消息中心-短信加固")
@RestController
@RequestMapping("/hyjf-admin/message/messageConfig")
public class SmsConfigController extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "messageConfig";
    @Autowired
    private SmsConfigService smsConfigService;

    @ApiOperation(value = "短信加固详情页(含有id更新，不含有id添加)", notes = "短信加固详情页(含有id更新，不含有id添加)")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult initSmsConfig() {
        SmsConfigResponse response=smsConfigService.initSmsConfig(new SmsConfigRequest());
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<SmsConfigResponse>(response) ;
    }

    @ApiOperation(value = "短信加固添加模块", notes = "短信加固添加模块")
    @PostMapping("/addAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertSmsConfig(@RequestBody SmsConfigRequest request) {
        SmsConfigResponse response=smsConfigService.insertSmsConfig(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<SmsConfigRequest>(request) ;
    }
    @ApiOperation(value = "短信加固添加模块", notes = "短信加固添加模块")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateSmsConfig(@RequestBody SmsConfigRequest request) {
        SmsConfigResponse response=smsConfigService.updateSmsConfig(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        SmsConfigResponse smsConfigResponse=smsConfigService.initSmsConfig(new SmsConfigRequest());
        RedisUtils.setObjEx(RedisConstants.SMS_CONFIG, smsConfigResponse.getResult(), 24 * 60 * 60);
        return new AdminResult<SmsConfigRequest>(request) ;
    }
}
