package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.AdminAndRole;
import com.hyjf.am.config.dao.model.auto.AdminAndRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminAndRoleMapper {
    int countByExample(AdminAndRoleExample example);

    int deleteByExample(AdminAndRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdminAndRole record);

    int insertSelective(AdminAndRole record);

    List<AdminAndRole> selectByExample(AdminAndRoleExample example);

    AdminAndRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdminAndRole record, @Param("example") AdminAndRoleExample example);

    int updateByExample(@Param("record") AdminAndRole record, @Param("example") AdminAndRoleExample example);

    int updateByPrimaryKeySelective(AdminAndRole record);

    int updateByPrimaryKey(AdminAndRole record);
}