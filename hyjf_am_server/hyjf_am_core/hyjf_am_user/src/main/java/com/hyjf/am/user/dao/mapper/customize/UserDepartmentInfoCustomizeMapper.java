/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.UserDepartmentInfoCustomize;

/**
 * 获取用户部门信息
 *
 * @author liuyang
 * @version UserDepartmentInfoCustomizeMapper, v0.1 2018/7/18 15:38
 */
public interface UserDepartmentInfoCustomizeMapper {
    /**
     * 根据用户ID查询用户部门信息
     *
     * @param userId
     * @return
     */
    UserDepartmentInfoCustomize selectUserDepartmentInfo(Integer userId);
}
