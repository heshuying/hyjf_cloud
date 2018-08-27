package com.hyjf.am.config.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.config.dao.model.auto.MessagePushTemplate;
import com.hyjf.am.config.dao.model.auto.MessagePushTemplateExample;

public interface MessagePushTemplateMapper {
    int countByExample(MessagePushTemplateExample example);

    int deleteByExample(MessagePushTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MessagePushTemplate record);

    int insertSelective(MessagePushTemplate record);

    List<MessagePushTemplate> selectByExample(MessagePushTemplateExample example);

    MessagePushTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MessagePushTemplate record, @Param("example") MessagePushTemplateExample example);

    int updateByExample(@Param("record") MessagePushTemplate record, @Param("example") MessagePushTemplateExample example);

    int updateByPrimaryKeySelective(MessagePushTemplate record);

    int updateByPrimaryKey(MessagePushTemplate record);
}