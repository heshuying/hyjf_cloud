/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.user.dao.model.customize.UserBankOpenAccountCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerDetailCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerUpdateCustomize;

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
    Integer countUserRecord(Map<String, Object> userRequest);

    /**
     * 根据用户id获取用户详情
     * @param userId
     * @return
     */
    List<UserManagerDetailCustomize> selectUserDetailById(int userId);

    /**
     *根据用户id获取开户信息
     * @param userId
     * @return
     */
    List<UserBankOpenAccountCustomize> selectBankOpenAccountByUserId(int userId);

    /**
     * 根据用户id获取用户修改信息
     * @param userId
     * @return
     */
    List<UserManagerUpdateCustomize> selectUserUpdateInfoByUserId(int userId);

    /**
     * 查询用户投资数量
     * @param userId
     * @return
     */
    Integer selectTenderCount(Integer userId);
}
