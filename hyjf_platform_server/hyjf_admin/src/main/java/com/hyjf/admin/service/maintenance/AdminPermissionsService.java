/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.maintenance;

import com.hyjf.admin.service.BaseAdminService;
import com.hyjf.am.resquest.admin.AdminPermissionsRequest;
import com.hyjf.am.vo.admin.AdminPermissionsVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AdminPermissionsService, v0.1 2018/9/5 13:56
 */
public interface AdminPermissionsService extends BaseAdminService {
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
    List<AdminPermissionsVO> searchPermissionsList(AdminPermissionsRequest request);

    /**
     * 添加权限
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean insertPermission(AdminPermissionsVO adminPermissionsVO);

    /**
     * 修改权限
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean updatePermission(AdminPermissionsVO adminPermissionsVO);

    /**
     * 删除权限
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean deletePermission(AdminPermissionsVO adminPermissionsVO);
}
