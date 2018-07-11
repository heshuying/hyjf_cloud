package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.ContentEnvironment;
import com.hyjf.am.config.dao.model.auto.ContentEnvironmentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentEnvironmentMapper {
    int countByExample(ContentEnvironmentExample example);

    int deleteByExample(ContentEnvironmentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ContentEnvironment record);

    int insertSelective(ContentEnvironment record);

    List<ContentEnvironment> selectByExample(ContentEnvironmentExample example);

    ContentEnvironment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ContentEnvironment record, @Param("example") ContentEnvironmentExample example);

    int updateByExample(@Param("record") ContentEnvironment record, @Param("example") ContentEnvironmentExample example);

    int updateByPrimaryKeySelective(ContentEnvironment record);

    int updateByPrimaryKey(ContentEnvironment record);
}