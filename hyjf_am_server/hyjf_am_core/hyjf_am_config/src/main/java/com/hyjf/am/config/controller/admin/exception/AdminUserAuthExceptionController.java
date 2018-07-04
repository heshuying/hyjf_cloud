/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.exception;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.service.AdminUserAuthExceptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunpeikai
 * @version: AdminUserAuthExceptionController, v0.1 2018/7/2 16:41
 */
@Api(value = "admin异常中心")
@RestController
@RequestMapping(value = "/am-config/adminException")
public class AdminUserAuthExceptionController extends BaseConfigController {

    @Autowired
    private AdminUserAuthExceptionService adminUserAuthExceptionService;
    /**
     * 根据银行错误码查询错误信息
     * @auth 孙沛凯
     * @param retCode 银行返回的错误码
     * @return retMsg 错误码对应的错误信息
     */
    @ApiOperation(value = "查询错误信息",notes = "根据银行错误码查询错误信息")
    @GetMapping(value = "/getBankRetMsg/{retCode}")
    public String getBankRetMsg(@PathVariable String retCode){
        logger.info("retCode========{}",retCode);
        String retMsg = adminUserAuthExceptionService.getBankRetMsg(retCode);
        if(StringUtils.isNotEmpty(retMsg)){
            return retMsg;
        }
        return "未知错误";
    }

}
