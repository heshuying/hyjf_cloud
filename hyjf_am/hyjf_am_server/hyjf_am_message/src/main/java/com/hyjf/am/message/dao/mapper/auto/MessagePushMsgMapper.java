package com.hyjf.am.message.dao.mapper.auto;

import com.hyjf.am.message.dao.model.auto.MessagePushMsg;
import com.hyjf.am.message.dao.model.auto.MessagePushMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessagePushMsgMapper {
    int countByExample(MessagePushMsgExample example);

    int deleteByExample(MessagePushMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MessagePushMsg record);

    int insertSelective(MessagePushMsg record);

    List<MessagePushMsg> selectByExampleWithBLOBs(MessagePushMsgExample example);

    List<MessagePushMsg> selectByExample(MessagePushMsgExample example);

    MessagePushMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MessagePushMsg record, @Param("example") MessagePushMsgExample example);

    int updateByExampleWithBLOBs(@Param("record") MessagePushMsg record, @Param("example") MessagePushMsgExample example);

    int updateByExample(@Param("record") MessagePushMsg record, @Param("example") MessagePushMsgExample example);

    int updateByPrimaryKeySelective(MessagePushMsg record);

    int updateByPrimaryKeyWithBLOBs(MessagePushMsg record);

    int updateByPrimaryKey(MessagePushMsg record);
}