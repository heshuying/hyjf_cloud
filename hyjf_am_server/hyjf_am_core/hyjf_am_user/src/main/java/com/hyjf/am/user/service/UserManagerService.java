/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service;

import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.user.dao.model.customize.UserManagerCustomize;
import java.util.List;

/**
 * @author nxl
 * @version UserManagerService, v0.1 2018/6/20 9:47
 * 后台管理系统：会员中心->会员管理
 */
public interface UserManagerService {

    /**
     * 根据筛选条件查找会员列表
     * @param userRequest
     * @return
     */
    List<UserManagerCustomize> selectUserMemberList(UserManagerRequest userRequest);

    /**
     * 根据条件查询用户管理总数
     * @return
     */
   int countUserRecord(UserManagerRequest userRequest);
}
