/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushErrorResponse;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.MessagePushErrorVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushTag;
import com.hyjf.cs.message.service.msgpush.MessagePushErrorService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
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

    @Value("file.domain.url")
    private String FILE_DOMAIN_URL;

    @Autowired
    private MessagePushErrorService messagePushErrorService;

    /**
     * 获取列表记录数
     *
     * @return
     */
    @RequestMapping("getRecordCount")
    private MessagePushErrorResponse getRecordCount(@RequestBody MessagePushErrorRequest request) {
        MessagePushErrorResponse response = new MessagePushErrorResponse();
        Integer recordCount = messagePushErrorService.getRecordCount(request);
        response.setCount(recordCount);
        return response;
    }

    /**
     * 获取列表
     *
     * @return
     */
    @RequestMapping("getRecordListT/{request}/{limitStart}/{limitEnd}")
    public MessagePushErrorResponse getRecordListT(@PathVariable MessagePushErrorRequest request, @PathVariable int limitStart,
                                                        @PathVariable int limitEnd) {
        MessagePushErrorResponse response = new MessagePushErrorResponse();
        List<MessagePushMsgHistory> messagePushMsgHistory = messagePushErrorService.getRecordList(request, limitStart, limitEnd);
        if (!CollectionUtils.isEmpty(messagePushMsgHistory)){
            List<MessagePushErrorVO> messagePushErrorVO = CommonUtils.convertBeanList(messagePushMsgHistory, MessagePushErrorVO.class);
            response.setResultList(messagePushErrorVO);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 获取标签列表
     *
     * @return
     */
    @RequestMapping("getTagList")
    public MessagePushErrorResponse getTagList() {
        MessagePushErrorResponse response = new MessagePushErrorResponse();
        List<MessagePushTag> tagList = messagePushErrorService.getTagList();
        if (!CollectionUtils.isEmpty(tagList)){
            List<MessagePushErrorVO> messagePushErrorVO = CommonUtils.convertBeanList(tagList, MessagePushErrorVO.class);
            response.setResultList(messagePushErrorVO);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 获取单个信息
     *
     * @return
     */
    @RequestMapping("getRecord/{id}")
    public MessagePushErrorResponse getRecord(@PathVariable Integer id) {
        MessagePushErrorResponse response = new MessagePushErrorResponse();
        MessagePushMsgHistory msg = messagePushErrorService.getRecord(id);
        if (msg != null){
            MessagePushErrorVO messagePushErrorVO = CommonUtils.convertBean(msg, MessagePushErrorVO.class);
            response.setResult(messagePushErrorVO);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 推送极光消息
     * @param msg
     * @return 成功返回消息id  失败返回 error
     * @author Michael
     */
    @RequestMapping("sendMessage/{msg}")
    public void sendMessage(@PathVariable MessagePushErrorVO msg) {
        messagePushErrorService.sendMessage(msg);
    }
}
