package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.WorkFlowNode;
import com.hyjf.am.config.dao.model.auto.WorkFlowNodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorkFlowNodeMapper {
    int countByExample(WorkFlowNodeExample example);

    int deleteByExample(WorkFlowNodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WorkFlowNode record);

    int insertSelective(WorkFlowNode record);

    List<WorkFlowNode> selectByExample(WorkFlowNodeExample example);

    WorkFlowNode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WorkFlowNode record, @Param("example") WorkFlowNodeExample example);

    int updateByExample(@Param("record") WorkFlowNode record, @Param("example") WorkFlowNodeExample example);

    int updateByPrimaryKeySelective(WorkFlowNode record);

    int updateByPrimaryKey(WorkFlowNode record);
}