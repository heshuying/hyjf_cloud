package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProtocolLog;
import com.hyjf.am.trade.dao.model.auto.ProtocolLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProtocolLogMapper {
    int countByExample(ProtocolLogExample example);

    int deleteByExample(ProtocolLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProtocolLog record);

    int insertSelective(ProtocolLog record);

    List<ProtocolLog> selectByExample(ProtocolLogExample example);

    ProtocolLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProtocolLog record, @Param("example") ProtocolLogExample example);

    int updateByExample(@Param("record") ProtocolLog record, @Param("example") ProtocolLogExample example);

    int updateByPrimaryKeySelective(ProtocolLog record);

    int updateByPrimaryKey(ProtocolLog record);
}