package com.hyjf.cs.message.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.admin.MessagePushHistoryResponse;
import com.hyjf.am.resquest.admin.MessagePushHistoryRequest;
import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.service.MessagePushHistoryService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author lisheng
 * @version MessagePushHistoryController, v0.1 2018/8/14 20:12
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/history_message")
public class MessagePushHistoryController {
    @Autowired
    MessagePushHistoryService messagePushHistoryService;


    /**
     * 获取历史消息消息列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/get_message_list")
    public MessagePushHistoryResponse getMessageList(@RequestBody MessagePushHistoryRequest request) {
        MessagePushHistoryResponse response = new MessagePushHistoryResponse();
        Integer count = messagePushHistoryService.getRecordCount(request);
        if (count > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
            List<MessagePushMsgHistory> list = messagePushHistoryService.getRecordList(request, paginator.getOffset(), paginator.getLimit());
            if (!CollectionUtils.isEmpty(list)) {
                List<MessagePushMsgHistoryVO> voList = CommonUtils.convertBeanList(list,
                        MessagePushMsgHistoryVO.class);
                response.setResultList(voList);
                response.setRecordTotal(count);
            }
        }
        return response;
    }
}
