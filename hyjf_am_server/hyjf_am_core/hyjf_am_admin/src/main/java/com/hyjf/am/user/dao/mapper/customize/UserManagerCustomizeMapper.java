/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.user.dao.model.auto.UserChangeLog;
import com.hyjf.am.user.dao.model.customize.UserBankOpenAccountCustomize;
import com.hyjf.am.user.dao.model.customize.UserInfoForLogCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerDetailCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerUpdateCustomize;
import com.hyjf.am.user.dao.model.customize.UserRecommendCustomize;

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

    List<UserManagerUpdateCustomize> selectUserUpdateById(int userId);
    /**
     * 获取某一用户的信息修改列表
     * @param mapParam
     * @return
     */
   List<UserChangeLog> queryChangeLogList(Map<String,Object> mapParam);

    /**
     * 根据用户id获取推荐信息
     * @param userId
     * @return
     */
    List<UserRecommendCustomize> searchUserRecommend(int userId);

    /**
     * 根据用户id查询用户一条用户信息（添加用户更新日志用）
     *
     * @param userId
     * @return
     */
    List<UserInfoForLogCustomize> selectUserInfoByUserId(int userId);
}
