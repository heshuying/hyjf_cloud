package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProtocolVersion;
import com.hyjf.am.trade.dao.model.auto.ProtocolVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProtocolVersionMapper {
    int countByExample(ProtocolVersionExample example);

    int deleteByExample(ProtocolVersionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProtocolVersion record);

    int insertSelective(ProtocolVersion record);

    List<ProtocolVersion> selectByExample(ProtocolVersionExample example);

    ProtocolVersion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProtocolVersion record, @Param("example") ProtocolVersionExample example);

    int updateByExample(@Param("record") ProtocolVersion record, @Param("example") ProtocolVersionExample example);

    int updateByPrimaryKeySelective(ProtocolVersion record);

    int updateByPrimaryKey(ProtocolVersion record);
}