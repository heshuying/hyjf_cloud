package com.hyjf.am.config.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.config.dao.model.auto.AdminMenuPermssions;
import com.hyjf.am.config.dao.model.auto.AdminMenuPermssionsExample;

public interface AdminMenuPermssionsMapper {
    int countByExample(AdminMenuPermssionsExample example);

    int deleteByExample(AdminMenuPermssionsExample example);

    int insert(AdminMenuPermssions record);

    int insertSelective(AdminMenuPermssions record);

    List<AdminMenuPermssions> selectByExample(AdminMenuPermssionsExample example);

    int updateByExampleSelective(@Param("record") AdminMenuPermssions record, @Param("example") AdminMenuPermssionsExample example);

    int updateByExample(@Param("record") AdminMenuPermssions record, @Param("example") AdminMenuPermssionsExample example);
}