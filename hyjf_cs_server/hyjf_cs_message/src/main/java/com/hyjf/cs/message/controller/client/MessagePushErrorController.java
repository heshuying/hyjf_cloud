/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushHistoryResponse;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.service.msgpush.MessagePushErrorService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author dangzw
 * @version MessagePushErrorController, v0.1 2018/8/14 22:39
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/msgpush/error")
public class MessagePushErrorController {

    @Autowired
    private MessagePushErrorService messagePushErrorService;

    /**
     * 获取列表记录数
     *
     * @return
     */
    @RequestMapping("getRecordCount")
    private MessagePushHistoryResponse getRecordCount(@RequestBody MessagePushErrorRequest request) {
        MessagePushHistoryResponse response = new MessagePushHistoryResponse();
        Integer recordCount = messagePushErrorService.getRecordCount(request);
        response.setRecordTotal(recordCount);
        return response;
    }

    /**
     * 获取列表
     *
     * @return
     */
    @PostMapping("getRecordListT")
    public MessagePushHistoryResponse getRecordListT(@RequestBody MessagePushErrorRequest request) {
        MessagePushHistoryResponse response = new MessagePushHistoryResponse();
        List<MessagePushMsgHistory> messagePushMsgHistory = messagePushErrorService.getRecordList(request, request.getLimitStart(), request.getLimitEnd());
        if (!CollectionUtils.isEmpty(messagePushMsgHistory)){
            List<MessagePushMsgHistoryVO> messagePushErrorVO = CommonUtils.convertBeanList(messagePushMsgHistory, MessagePushMsgHistoryVO.class);
            response.setResultList(messagePushErrorVO);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }
}
