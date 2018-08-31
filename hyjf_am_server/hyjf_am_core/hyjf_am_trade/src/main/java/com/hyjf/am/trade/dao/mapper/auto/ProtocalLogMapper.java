package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProtocalLog;
import com.hyjf.am.trade.dao.model.auto.ProtocalLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProtocalLogMapper {
    int countByExample(ProtocalLogExample example);

    int deleteByExample(ProtocalLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProtocalLog record);

    int insertSelective(ProtocalLog record);

    List<ProtocalLog> selectByExample(ProtocalLogExample example);

    ProtocalLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProtocalLog record, @Param("example") ProtocalLogExample example);

    int updateByExample(@Param("record") ProtocalLog record, @Param("example") ProtocalLogExample example);

    int updateByPrimaryKeySelective(ProtocalLog record);

    int updateByPrimaryKey(ProtocalLog record);
}