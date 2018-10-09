/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.MessagePushTagMapper;
import com.hyjf.am.config.dao.model.auto.MessagePushTag;
import com.hyjf.am.config.dao.model.auto.MessagePushTagExample;
import com.hyjf.am.config.service.MessagePushTagService;
import com.hyjf.am.resquest.config.MessagePushTagRequest;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version MessagePushTagServiceImpl, v0.1 2018/6/26 11:48
 */
@Service
public class MessagePushTagServiceImpl implements MessagePushTagService {

    @Autowired
    private MessagePushTagMapper messagePushTagMapper;

    @Override
    public MessagePushTag findMsgTagByTagName(String tagName) {
        MessagePushTagExample example = new MessagePushTagExample();
        example.createCriteria().andTagNameEqualTo(tagName);
        List<MessagePushTag> messagePushTagList = messagePushTagMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(messagePushTagList)) {
            return messagePushTagList.get(0);
        }
        return null;
    }

    @Override
    public Integer countRecord(MessagePushTagRequest request) {
        MessagePushTagExample example = new MessagePushTagExample();
        MessagePushTagExample.Criteria criteria = example.createCriteria();
        if (request.getTagName() != null) {
            criteria.andTagNameLike("%" + request.getTagName() + "%");
        }
        int count = messagePushTagMapper.countByExample(example);
        return count;
    }

    @Override
    public List<MessagePushTag> searchList(MessagePushTagRequest request, int limitStart, int limitEnd) {
        MessagePushTagExample example = new MessagePushTagExample();
        MessagePushTagExample.Criteria criteria = example.createCriteria();
        // 条件查询
        if (request.getTagName() != null) {
            criteria.andTagNameLike("%" + request.getTagName() + "%");
        }
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        example.setOrderByClause("sort_id asc");
        return this.messagePushTagMapper.selectByExample(example);
    }

    @Override
    public MessagePushTag getRecord(Integer id) {
        return messagePushTagMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer insertAction(MessagePushTag messagePushTag) {
        return messagePushTagMapper.insertSelective(messagePushTag);
    }

    @Override
    public Integer updateAction(MessagePushTag messagePushTag) {
        return messagePushTagMapper.updateByPrimaryKeySelective(messagePushTag);
    }

    @Override
    public Integer deleteAction(Integer id) {
        return messagePushTagMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int countByTagCode(Integer id, String tagCode) {
        MessagePushTagExample example = new MessagePushTagExample();
        MessagePushTagExample.Criteria cra = example.createCriteria();
        if (Validator.isNotNull(id)) {
            cra.andIdNotEqualTo(id);
        }
        cra.andTagCodeEqualTo(tagCode);
        int cnt = messagePushTagMapper.countByExample(example);
        return cnt;
    }

    @Override
    public List<MessagePushTag> getAllPushTagList() {
        MessagePushTagExample example = new MessagePushTagExample();
        return this.messagePushTagMapper.selectByExample(example);
    }

    @Override
    public List<MessagePushTag> getTagList() {
        MessagePushTagExample example = new MessagePushTagExample();
        MessagePushTagExample.Criteria criteria = example.createCriteria();
        //启用状态
        criteria.andStatusEqualTo(1);
        example.setOrderByClause("sort_id asc");
        return this.messagePushTagMapper.selectByExample(example);
    }

    @Override
    public MessagePushTag getTagByTagId(Integer tagId) {
        return  messagePushTagMapper.selectByPrimaryKey(tagId);
    }
}
