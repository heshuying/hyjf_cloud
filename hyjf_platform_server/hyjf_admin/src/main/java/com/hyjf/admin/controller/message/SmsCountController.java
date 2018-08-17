/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.request.SmsCountRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.SmsCountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fq
 * @version SmsCountController, v0.1 2018/8/17 9:14
 */
@Api(tags = "消息中心-短信统计")
@RestController
@RequestMapping("/hyjf-admin/message/smsCount")
public class SmsCountController extends BaseController {
    @Autowired
    private SmsCountService smsCountService;

    @ApiOperation(value = "根据条件查询短信统计", notes = "根据条件查询短信统计")
    @RequestMapping("/sms_count_list")
    public AdminResult smsCountList(SmsCountRequestBean request) {
        return new AdminResult();// todo;
    }
}
