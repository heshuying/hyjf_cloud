package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.AdminMenuPermissions;
import com.hyjf.am.config.dao.model.auto.AdminMenuPermissionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminMenuPermissionsMapper {
    int countByExample(AdminMenuPermissionsExample example);

    int deleteByExample(AdminMenuPermissionsExample example);

    int insert(AdminMenuPermissions record);

    int insertSelective(AdminMenuPermissions record);

    List<AdminMenuPermissions> selectByExample(AdminMenuPermissionsExample example);

    int updateByExampleSelective(@Param("record") AdminMenuPermissions record, @Param("example") AdminMenuPermissionsExample example);

    int updateByExample(@Param("record") AdminMenuPermissions record, @Param("example") AdminMenuPermissionsExample example);
}