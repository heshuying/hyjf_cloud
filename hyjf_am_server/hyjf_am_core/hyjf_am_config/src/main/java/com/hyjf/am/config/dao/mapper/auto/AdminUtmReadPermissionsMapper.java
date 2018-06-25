package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.AdminUtmReadPermissions;
import com.hyjf.am.config.dao.model.auto.AdminUtmReadPermissionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminUtmReadPermissionsMapper {
    int countByExample(AdminUtmReadPermissionsExample example);

    int deleteByExample(AdminUtmReadPermissionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdminUtmReadPermissions record);

    int insertSelective(AdminUtmReadPermissions record);

    List<AdminUtmReadPermissions> selectByExample(AdminUtmReadPermissionsExample example);

    AdminUtmReadPermissions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdminUtmReadPermissions record, @Param("example") AdminUtmReadPermissionsExample example);

    int updateByExample(@Param("record") AdminUtmReadPermissions record, @Param("example") AdminUtmReadPermissionsExample example);

    int updateByPrimaryKeySelective(AdminUtmReadPermissions record);

    int updateByPrimaryKey(AdminUtmReadPermissions record);
}