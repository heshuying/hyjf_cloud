/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.user.dao.model.customize.UserBankOpenAccountCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerDetailCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version UserManagerCustomizeMapper, v0.1 2018/6/20 10:01
 */
public interface UserManagerCustomizeMapper {

    /**
     *  根据筛选条件查找会员列表
     * @param userRequest 筛选条件
     * @return
     */
    List<UserManagerCustomize> selectUserMemberList(Map<String,Object> userRequest);
    /**
     * 根据条件获取用户列表总数
     * @param userRequest
     * @return
     */
    Integer countUserRecord(UserManagerRequest userRequest);

    /**
     * 根据用户id获取用户详情
     * @param userId
     * @return
     */
    UserManagerDetailCustomize selectUserDetailById(int userId);

    /**
     *根据用户id获取开户信息
     * @param userId
     * @return
     */
    UserBankOpenAccountCustomize selectBankOpenAccountByUserId(int userId);
}