/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.AdminPermissions;
import com.hyjf.am.resquest.admin.AdminPermissionsRequest;
import com.hyjf.am.vo.admin.AdminPermissionsVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PermissionsService, v0.1 2018/9/5 14:25
 */
public interface PermissionsService {
    /**
     * 查询权限数量
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPermissionsCount(AdminPermissionsRequest request);

    /**
     * 查询权限列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AdminPermissions> searchPermissionsList(AdminPermissionsRequest request);

    /**
     * 检查数据库是否已存在该权限
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean isExistsPermission(AdminPermissionsVO adminPermissionsVO);

    /**
     * 插入权限
     * @auth sunpeikai
     * @param
     * @return
     */
    int insertPermission(AdminPermissionsVO adminPermissionsVO);

    /**
     * 修改权限
     * @auth sunpeikai
     * @param
     * @return
     */
    int updatePermission(AdminPermissionsVO adminPermissionsVO);

    /**
     * 根据uuid查询权限
     * @auth sunpeikai
     * @param
     * @return
     */
    AdminPermissions searchPermissionByUuid(String uuid);

    /**
     * 删除权限
     * @auth sunpeikai
     * @param
     * @return
     */
    int deletePermission(String uuid);
}
