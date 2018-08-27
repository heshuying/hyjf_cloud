package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.EveLog;
import com.hyjf.am.trade.dao.model.auto.EveLogExample;

public interface EveLogMapper {
    int countByExample(EveLogExample example);

    int deleteByExample(EveLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EveLog record);

    int insertSelective(EveLog record);

    List<EveLog> selectByExample(EveLogExample example);

    EveLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EveLog record, @Param("example") EveLogExample example);

    int updateByExample(@Param("record") EveLog record, @Param("example") EveLogExample example);

    int updateByPrimaryKeySelective(EveLog record);

    int updateByPrimaryKey(EveLog record);
}