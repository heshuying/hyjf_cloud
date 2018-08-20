/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import com.hyjf.am.response.admin.SmsOntimeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.SmsLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.am.vo.admin.SmsOntimeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fuqiang
 * @version SmsLogController, v0.1 2018/6/23 15:09
 */
@Api(tags = "消息中心-短信")
@RestController
@RequestMapping("/hyjf-admin/message/smsLog")
public class SmsLogController extends BaseController {

    @Autowired
    private SmsLogService smsLogService;

    @ApiOperation(value = "查询消息中心短信发送记录", notes = "查询消息中心短信发送记录")
    @GetMapping("/smsLogList")
    public JSONObject smsLogList() {
        return smsLogService.smsLogList();
    }

    @ApiOperation(value = "根据条件查询消息中心短信发送记录", notes = "根据条件查询消息中心短信发送记录")
    @PostMapping("/findSmsLog")
    public JSONObject findSmsLog(@RequestBody SmsLogRequest request) {
        return smsLogService.findSmsLog(request);
    }

    @ApiOperation(value = "查询定时发送短信列表", notes = "查询定时发送短信列表")
    @PostMapping("/timeinit")
    public AdminResult<ListResult<SmsOntimeVO>> timeinit(@RequestBody SmsLogRequest request) {
        SmsOntimeResponse response = smsLogService.queryTime(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }
}
