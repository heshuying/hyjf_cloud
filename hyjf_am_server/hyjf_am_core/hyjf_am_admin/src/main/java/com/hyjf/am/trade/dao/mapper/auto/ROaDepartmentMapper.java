package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ROaDepartment;
import com.hyjf.am.trade.dao.model.auto.ROaDepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ROaDepartmentMapper {
    int countByExample(ROaDepartmentExample example);

    int deleteByExample(ROaDepartmentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ROaDepartment record);

    int insertSelective(ROaDepartment record);

    List<ROaDepartment> selectByExample(ROaDepartmentExample example);

    ROaDepartment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ROaDepartment record, @Param("example") ROaDepartmentExample example);

    int updateByExample(@Param("record") ROaDepartment record, @Param("example") ROaDepartmentExample example);

    int updateByPrimaryKeySelective(ROaDepartment record);

    int updateByPrimaryKey(ROaDepartment record);
}