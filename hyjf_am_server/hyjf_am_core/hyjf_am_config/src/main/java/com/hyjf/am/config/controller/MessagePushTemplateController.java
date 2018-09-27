/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.MessagePushTemplate;
import com.hyjf.am.config.service.MessagePushTemplateServcie;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.config.MessagePushTemplateResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消息推送模板
 *
 * @author fuqiang
 * @version MessagePushTemplateController, v0.1 2018/5/8 10:32
 */
@RestController
@RequestMapping("/am-config/messagePushTemplate")
public class MessagePushTemplateController {

    Logger logger = LoggerFactory.getLogger(MessagePushTemplateController.class);

    @Autowired
    private MessagePushTemplateServcie templateServcie;

    /**
     * 根据tplCode查询消息推送模板
     *
     * @param tplCode
     * @return
     */
    @RequestMapping("/findMessagePushTemplateByCode/{tplCode}")
    public MessagePushTemplateResponse findMessagePushTemplateByCode(@PathVariable String tplCode) {
        logger.info("查询消息推送模板开始... tplCode is :{}", tplCode);
        MessagePushTemplateResponse response = new MessagePushTemplateResponse();
        MessagePushTemplateVO messagePushTemplateVO = null;
        MessagePushTemplate messagePushTemplate = templateServcie.findMessagePushTemplateByCode(tplCode);
        if (messagePushTemplate != null) {
            messagePushTemplateVO = new MessagePushTemplateVO();
            BeanUtils.copyProperties(messagePushTemplate, messagePushTemplateVO);
        }
        logger.info("messagePushTemplateVO is :{}", messagePushTemplateVO);
        response.setResult(messagePushTemplateVO);
        return response;
    }

    @RequestMapping("/getAllTemplates")
    public MessagePushTemplateResponse getAllTemplates() {
        logger.info("查询所有消息推送模板开始...");
        MessagePushTemplateResponse response = new MessagePushTemplateResponse();
        List<MessagePushTemplate> list = templateServcie.getAllTemplates();

        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(CommonUtils.convertBeanList(list, MessagePushTemplateVO.class));
        }
        return response;
    }

    /**
     * 根据条件查询消息推送模板
     *
     * @param request
     * @return
     */
    @RequestMapping("/findMsgPushTemplate")
    public MessagePushTemplateResponse findMsgPushTemplate(@RequestBody MsgPushTemplateRequest request) {
        logger.info("根据条件查询消息推送模板开始...");
        MessagePushTemplateResponse response = new MessagePushTemplateResponse();
        List<MessagePushTemplate> list = templateServcie.findMsgPushTemplate(request);

        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(CommonUtils.convertBeanList(list, MessagePushTemplateVO.class));
        }
        return response;
    }

    @RequestMapping("/insertMsgPushTemplate")
    public IntegerResponse insertMsgPushTemplate(MsgPushTemplateRequest request) {
        int num = templateServcie.insertMsgPushTemplate(request);
        return new IntegerResponse(num);
    }

}
