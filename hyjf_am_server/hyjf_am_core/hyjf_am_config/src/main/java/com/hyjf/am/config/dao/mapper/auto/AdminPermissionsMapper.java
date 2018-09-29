package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.AdminPermissions;
import com.hyjf.am.config.dao.model.auto.AdminPermissionsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminPermissionsMapper {
    int countByExample(AdminPermissionsExample example);

    int deleteByExample(AdminPermissionsExample example);

    int deleteByPrimaryKey(String permissionUuid);

    int insert(AdminPermissions record);

    int insertSelective(AdminPermissions record);

    List<AdminPermissions> selectByExample(AdminPermissionsExample example);

    AdminPermissions selectByPrimaryKey(String permissionUuid);

    int updateByExampleSelective(@Param("record") AdminPermissions record, @Param("example") AdminPermissionsExample example);

    int updateByExample(@Param("record") AdminPermissions record, @Param("example") AdminPermissionsExample example);

    int updateByPrimaryKeySelective(AdminPermissions record);

    int updateByPrimaryKey(AdminPermissions record);
}