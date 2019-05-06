package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.WorkName;
import com.hyjf.am.config.dao.model.auto.WorkNameExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkNameMapper {
    int countByExample(WorkNameExample example);

    int deleteByExample(WorkNameExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WorkName record);

    int insertSelective(WorkName record);

    List<WorkName> selectByExample(WorkNameExample example);

    WorkName selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WorkName record, @Param("example") WorkNameExample example);

    int updateByExample(@Param("record") WorkName record, @Param("example") WorkNameExample example);

    int updateByPrimaryKeySelective(WorkName record);

    int updateByPrimaryKey(WorkName record);
}