package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.SpreadsUserLog;
import com.hyjf.am.user.dao.model.auto.SpreadsUserLogExample;

public interface SpreadsUserLogMapper {
    int countByExample(SpreadsUserLogExample example);

    int deleteByExample(SpreadsUserLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SpreadsUserLog record);

    int insertSelective(SpreadsUserLog record);

    List<SpreadsUserLog> selectByExample(SpreadsUserLogExample example);

    SpreadsUserLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SpreadsUserLog record, @Param("example") SpreadsUserLogExample example);

    int updateByExample(@Param("record") SpreadsUserLog record, @Param("example") SpreadsUserLogExample example);

    int updateByPrimaryKeySelective(SpreadsUserLog record);

    int updateByPrimaryKey(SpreadsUserLog record);
}