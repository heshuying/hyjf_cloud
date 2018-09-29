package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.config.dao.model.auto.ParamNameExample;
import com.hyjf.am.config.dao.model.auto.ParamNameKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParamNameMapper {
    int countByExample(ParamNameExample example);

    int deleteByExample(ParamNameExample example);

    int deleteByPrimaryKey(ParamNameKey key);

    int insert(ParamName record);

    int insertSelective(ParamName record);

    List<ParamName> selectByExample(ParamNameExample example);

    ParamName selectByPrimaryKey(ParamNameKey key);

    int updateByExampleSelective(@Param("record") ParamName record, @Param("example") ParamNameExample example);

    int updateByExample(@Param("record") ParamName record, @Param("example") ParamNameExample example);

    int updateByPrimaryKeySelective(ParamName record);

    int updateByPrimaryKey(ParamName record);
}