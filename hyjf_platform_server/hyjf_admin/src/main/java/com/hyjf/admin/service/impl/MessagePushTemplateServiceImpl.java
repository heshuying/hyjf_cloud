/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.MessagePushTemplateService;
import com.hyjf.am.response.config.MessagePushTemplateResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.config.ParamNameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaoyong
 * @version MessagePushTemplateServiceImpl, v0.1 2018/8/14 20:07
 */
@Service
public class MessagePushTemplateServiceImpl implements MessagePushTemplateService {

    @Autowired
    private AmConfigClient amConfigClient;

    @Override
    public MessagePushTemplateResponse searchList(MsgPushTemplateRequest request) {
        return amConfigClient.getMessagePushTemplateList(request);
    }

    @Override
    public MessagePushTemplateResponse getRecord(Integer id) {
        return amConfigClient.findMsgPushTemplateById(id);
    }

    @Override
    public List<ParamNameVO> getParamNameList(String nameClass) {
        return amConfigClient.getParamNameList(nameClass);
    }

    @Override
    public MessagePushTemplateResponse insertAction(MessagePushTemplateVO templateVO) {
        return amConfigClient.insertMessageTemplate(templateVO);
    }

    @Override
    public MessagePushTemplateResponse updateRecord(MsgPushTemplateRequest templateRequest) {
        return amConfigClient.updateMsgPushTemplate(templateRequest);
    }

    @Override
    public MessagePushTemplateResponse deleteAction(List<Integer> recordList) {
        return amConfigClient.deleteMessagePushTemplate(recordList);
    }

    @Override
    public MessagePushTemplateResponse countByTemplateCode(Integer id, String templateCode) {
        return amConfigClient.countByTemplateCode(id, templateCode);
    }
}
