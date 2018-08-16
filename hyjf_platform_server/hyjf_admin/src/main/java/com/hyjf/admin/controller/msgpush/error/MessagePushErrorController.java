/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.msgpush.error;

import com.hyjf.admin.beans.request.MessagePushErrorRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MessagePushErrorService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushErrorResponse;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.MessagePushErrorVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dangzw
 * @version MessagePushErrorController, v0.1 2018/8/14 21:37
 */
@Api(description = "消息中心-APP消息推送 异常处理", tags = "消息中心-APP消息推送 异常处理")
@RestController
@RequestMapping("/hyjf-admin/msgpush/error")
public class MessagePushErrorController extends BaseController {

    @Autowired
    private MessagePushErrorService messagePushErrorService;

    @GetMapping("getListByConditions")
    @ApiOperation(value = "(条件)查询 APP消息推送 异常处理 列表",
                                  notes = "(条件)查询 APP消息推送 异常处理 列表")
    public AdminResult<ListResult<MessagePushErrorVO>> getListByConditions(
                                            @RequestBody MessagePushErrorRequestBean requestBean) {
        MessagePushErrorRequest request = null;
        BeanUtils.copyProperties(requestBean, request);
        if (request == null) {
            return new AdminResult<>(FAIL, "请求为空");
        }
        //(条件)查询
        MessagePushErrorResponse response = messagePushErrorService.getListByConditions(request);

        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @PutMapping("update")
    @ApiOperation(value = "数据修改 APP消息推送 异常处理", notes = "数据修改 APP消息推送 异常处理")
    public AdminResult<ListResult<MessagePushErrorVO>> update(
                                    @RequestBody MessagePushErrorRequestBean requestBean) {
        MessagePushErrorRequest request = new MessagePushErrorRequest();
        BeanUtils.copyProperties(requestBean, request);
        if (request == null) {
            return new AdminResult<>(FAIL, "请求为空");
        }
        //修改
        MessagePushErrorResponse response = messagePushErrorService.update(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }
}
