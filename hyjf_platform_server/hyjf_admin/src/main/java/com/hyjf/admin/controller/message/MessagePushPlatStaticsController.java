/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MessagePushPlatStaticsService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushPlatStaticsResponse;
import com.hyjf.am.resquest.config.MessagePushPlatStaticsRequest;
import com.hyjf.am.vo.admin.MessagePushPlatStaticsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fq
 * @version MessagePushTagController, v0.1 2018/8/14 15:41
 */
@Api(tags = "消息中心-app消息推送-平台消息统计报表")
@RestController
@RequestMapping("/hyjf-admin/msgpush/statics/plat")
public class MessagePushPlatStaticsController extends BaseController {
    @Autowired
    private MessagePushPlatStaticsService tagService;

    @ApiOperation(value = "查询平台消息统计报表", notes = "查询平台消息统计报表")
    @PostMapping("/select")
    public AdminResult<ListResult<MessagePushPlatStaticsVO>> select(
            @RequestBody MessagePushPlatStaticsRequest request) {
        MessagePushPlatStaticsResponse response = tagService.selectTemplateStatics(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }
}
