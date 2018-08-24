package com.hyjf.am.config.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.config.dao.model.auto.AdminGroup;
import com.hyjf.am.config.dao.model.auto.AdminGroupExample;

public interface AdminGroupMapper {
    int countByExample(AdminGroupExample example);

    int deleteByExample(AdminGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdminGroup record);

    int insertSelective(AdminGroup record);

    List<AdminGroup> selectByExample(AdminGroupExample example);

    AdminGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdminGroup record, @Param("example") AdminGroupExample example);

    int updateByExample(@Param("record") AdminGroup record, @Param("example") AdminGroupExample example);

    int updateByPrimaryKeySelective(AdminGroup record);

    int updateByPrimaryKey(AdminGroup record);
}