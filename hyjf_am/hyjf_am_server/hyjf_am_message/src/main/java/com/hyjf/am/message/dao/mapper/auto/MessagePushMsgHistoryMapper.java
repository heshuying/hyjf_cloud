package com.hyjf.am.message.dao.mapper.auto;

import com.hyjf.am.message.dao.model.auto.MessagePushMsgHistory;
import com.hyjf.am.message.dao.model.auto.MessagePushMsgHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessagePushMsgHistoryMapper {
    int countByExample(MessagePushMsgHistoryExample example);

    int deleteByExample(MessagePushMsgHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MessagePushMsgHistory record);

    int insertSelective(MessagePushMsgHistory record);

    List<MessagePushMsgHistory> selectByExample(MessagePushMsgHistoryExample example);

    MessagePushMsgHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MessagePushMsgHistory record, @Param("example") MessagePushMsgHistoryExample example);

    int updateByExample(@Param("record") MessagePushMsgHistory record, @Param("example") MessagePushMsgHistoryExample example);

    int updateByPrimaryKeySelective(MessagePushMsgHistory record);

    int updateByPrimaryKey(MessagePushMsgHistory record);
}