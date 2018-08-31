package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProtocalVersion;
import com.hyjf.am.trade.dao.model.auto.ProtocalVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProtocalVersionMapper {
    int countByExample(ProtocalVersionExample example);

    int deleteByExample(ProtocalVersionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProtocalVersion record);

    int insertSelective(ProtocalVersion record);

    List<ProtocalVersion> selectByExample(ProtocalVersionExample example);

    ProtocalVersion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProtocalVersion record, @Param("example") ProtocalVersionExample example);

    int updateByExample(@Param("record") ProtocalVersion record, @Param("example") ProtocalVersionExample example);

    int updateByPrimaryKeySelective(ProtocalVersion record);

    int updateByPrimaryKey(ProtocalVersion record);
}