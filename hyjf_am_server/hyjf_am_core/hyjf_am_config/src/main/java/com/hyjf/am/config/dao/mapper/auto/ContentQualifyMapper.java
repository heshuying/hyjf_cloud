package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.ContentQualify;
import com.hyjf.am.config.dao.model.auto.ContentQualifyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentQualifyMapper {
    int countByExample(ContentQualifyExample example);

    int deleteByExample(ContentQualifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ContentQualify record);

    int insertSelective(ContentQualify record);

    List<ContentQualify> selectByExample(ContentQualifyExample example);

    ContentQualify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ContentQualify record, @Param("example") ContentQualifyExample example);

    int updateByExample(@Param("record") ContentQualify record, @Param("example") ContentQualifyExample example);

    int updateByPrimaryKeySelective(ContentQualify record);

    int updateByPrimaryKey(ContentQualify record);
}