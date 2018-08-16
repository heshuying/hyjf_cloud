/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.MessagePushTagService;
import com.hyjf.am.response.config.MessagePushTagResponse;
import com.hyjf.am.resquest.config.MessagePushTagRequest;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.ParamNameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaoyong
 * @version MessagePushTagServiceImpl, v0.1 2018/8/14 14:53
 */
@Service
public class MessagePushTagServiceImpl implements MessagePushTagService {

    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 根据条件查询消息推送标签管理表
     * @param request
     * @return
     */
    @Override
    public MessagePushTagResponse searchList(MessagePushTagRequest request) {
        return amConfigClient.getMessagePushTagList(request);
    }

    @Override
    public List<ParamNameVO> getParamNameList(String nameClass) {
        return amConfigClient.getParamNameList(nameClass);
    }

    @Override
    public MessagePushTagResponse getRecord(Integer id) {
        return amConfigClient.getMessagePushTag(id);
    }

    @Override
    public MessagePushTagResponse insertMessagePushTag(MessagePushTagRequest request) {
        return amConfigClient.insretMessagePushTag(request);
    }

    @Override
    public MessagePushTagResponse updateMessagePushTag(MessagePushTagRequest tagRequest) {
        return amConfigClient.updateMessagePushTag(tagRequest);
    }

    @Override
    public MessagePushTagResponse deleteAction(Integer id) {
        return amConfigClient.deleteMessagePushTag(id);
    }

    @Override
    public MessagePushTagResponse updatePushTag(MessagePushTagVO record) {
        return amConfigClient.updatePushTag(record);
    }

    @Override
    public MessagePushTagResponse countByTagCode(Integer id, String tagCode) {
        return amConfigClient.countByTagCode(id,tagCode);
    }

    @Override
    public List<MessagePushTagVO> getAllPushTagList() {
        return amConfigClient.getAllPushTagList();
    }

    @Override
    public List<MessagePushTagVO> getTagList() {
        return amConfigClient.getTagList();
    }
}
