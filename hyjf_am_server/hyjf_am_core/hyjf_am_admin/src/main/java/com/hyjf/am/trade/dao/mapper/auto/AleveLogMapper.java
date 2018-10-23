package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.AleveLog;
import com.hyjf.am.trade.dao.model.auto.AleveLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AleveLogMapper {
    int countByExample(AleveLogExample example);

    int deleteByExample(AleveLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AleveLog record);

    int insertSelective(AleveLog record);

    List<AleveLog> selectByExample(AleveLogExample example);

    AleveLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AleveLog record, @Param("example") AleveLogExample example);

    int updateByExample(@Param("record") AleveLog record, @Param("example") AleveLogExample example);

    int updateByPrimaryKeySelective(AleveLog record);

    int updateByPrimaryKey(AleveLog record);
}