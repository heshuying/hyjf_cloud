package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.WorkFlow;
import com.hyjf.am.config.dao.model.auto.WorkFlowExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkFlowMapper {
    int countByExample(WorkFlowExample example);

    int deleteByExample(WorkFlowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WorkFlow record);

    int insertSelective(WorkFlow record);

    List<WorkFlow> selectByExample(WorkFlowExample example);

    WorkFlow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WorkFlow record, @Param("example") WorkFlowExample example);

    int updateByExample(@Param("record") WorkFlow record, @Param("example") WorkFlowExample example);

    int updateByPrimaryKeySelective(WorkFlow record);

    int updateByPrimaryKey(WorkFlow record);
}