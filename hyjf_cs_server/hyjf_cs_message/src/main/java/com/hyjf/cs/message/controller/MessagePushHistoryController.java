package com.hyjf.cs.message.controller;

import com.hyjf.am.response.admin.MessagePushHistoryResponse;
import com.hyjf.am.response.admin.MessagePushTagResponse;
import com.hyjf.am.resquest.admin.MessagePushHistoryRequest;
import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;
import com.hyjf.am.vo.admin.MessagePushTagVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushTag;
import com.hyjf.cs.message.service.MessagePushHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lisheng
 * @version MessagePushHistoryController, v0.1 2018/8/14 20:12
 */
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
    public MessagePushHistoryResponse getMessageList(
            @RequestBody MessagePushHistoryRequest request) {
        MessagePushHistoryResponse response = new MessagePushHistoryResponse();
        Integer count = messagePushHistoryService.getRecordCount(request);
        if (count > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), count);
            List<MessagePushMsgHistory> list = messagePushHistoryService.getRecordList(request, paginator.getOffset(), paginator.getLimit());
            if (!CollectionUtils.isEmpty(list)) {
                List<MessagePushMsgHistoryVO> voList = CommonUtils.convertBeanList(list,
                        MessagePushMsgHistoryVO.class);
                response.setResultList(voList);
            }
        }
        return response;
    }


    /**
     * 获取所有标签列表
     *
     * @return
     */
    @RequestMapping("/get_push_tag_list")
    public MessagePushTagResponse getAllPushTagList() {
        MessagePushTagResponse response = new MessagePushTagResponse();
            List<MessagePushTag> list = messagePushHistoryService.getPushTagList();
            if (!CollectionUtils.isEmpty(list)) {
                List<MessagePushTagVO> voList = CommonUtils.convertBeanList(list,
                        MessagePushTagVO.class);
                response.setResultList(voList);
            }
        return response;
    }


    /**
     * 获取标签列表
     *
     * @return
     */
    @RequestMapping("/get_push_tag")
    public MessagePushTagResponse getPushTagList() {
        MessagePushTagResponse response = new MessagePushTagResponse();
        List<MessagePushTag> list = messagePushHistoryService.getTagList();
        if (!CollectionUtils.isEmpty(list)) {
            List<MessagePushTagVO> voList = CommonUtils.convertBeanList(list,
                    MessagePushTagVO.class);
            response.setResultList(voList);
        }
        return response;
    }


}
