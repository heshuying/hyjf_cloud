package com.hyjf.am.config.dao.mapper.customize;


import com.hyjf.am.config.dao.model.auto.AdminRoleMenuPermissions;

import java.util.List;

/**
 * @author dongzeshan
 * @version AdminRoleMenuPermissionsMapper, v0.1 2018/8/28 19:40
 */

public interface AdminRoleMenuPermissionsCustomizeMapper {

    List<AdminRoleMenuPermissions> selectByExample(AdminRoleMenuPermissions example);

    int updateByRoleId(AdminRoleMenuPermissions record);

    int insertSelective(AdminRoleMenuPermissions record);

    /**
     * 插入菜单权限角色表
     * @param record
     * @return
     */
    int  insertMenuPerssion(List<AdminRoleMenuPermissions> adminList);
    /**
     * 查询菜单级别
     * @param perm
     * @return
     */
    String checkLevel(String perm);

    /**
     * 根据二级菜单查询 菜单下权限
     * @param mennuid
     * @return
     */
    List<String> selectMenuPerssion(String mennuid);

    /**
     * 查询子菜单
     * @param mennuid
     * @return
     */
    List<String> selectChildMenu(String mennuid);

    int deleteMenubyRoleId(Integer id);
}
