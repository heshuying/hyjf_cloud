/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.SmsLogService;
import com.hyjf.am.resquest.message.SmsLogRequest;

import io.swagger.annotations.ApiOperation;

/**
 * @author fuqiang
 * @version SmsLogController, v0.1 2018/6/23 15:09
 */
@RestController
@RequestMapping("/hyjf-admin/message/smsLog")
public class SmsLogController extends BaseController {

    @Autowired
    private SmsLogService smsLogService;

    @ApiOperation(value = "消息中心短信发送记录", notes = "查询消息中心短信发送记录")
    @RequestMapping("/smsLogList")
    public JSONObject smsLogList() {
        JSONObject jsonObject = new JSONObject();
        return smsLogService.smsLogList();
    }

    @ApiOperation(value = "消息中心短信发送记录", notes = "根据条件查询消息中心短信发送记录")
    @RequestMapping("/findSmsLog")
    public JSONObject findSmsLog(@RequestBody SmsLogRequest request) {
        return smsLogService.findSmsLog(request);
    }
}
