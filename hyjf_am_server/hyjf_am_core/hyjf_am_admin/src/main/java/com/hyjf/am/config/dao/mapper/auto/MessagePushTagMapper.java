package com.hyjf.am.config.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.config.dao.model.auto.MessagePushTag;
import com.hyjf.am.config.dao.model.auto.MessagePushTagExample;

public interface MessagePushTagMapper {
    int countByExample(MessagePushTagExample example);

    int deleteByExample(MessagePushTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MessagePushTag record);

    int insertSelective(MessagePushTag record);

    List<MessagePushTag> selectByExample(MessagePushTagExample example);

    MessagePushTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MessagePushTag record, @Param("example") MessagePushTagExample example);

    int updateByExample(@Param("record") MessagePushTag record, @Param("example") MessagePushTagExample example);

    int updateByPrimaryKeySelective(MessagePushTag record);

    int updateByPrimaryKey(MessagePushTag record);
}