package com.hyjf.am.user.service;

import java.util.List;

import com.hyjf.am.user.dao.model.auto.SpreadsUser;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.customize.EmployeeCustomize;
import com.hyjf.am.user.dao.model.customize.UserInfoCustomize;
import com.hyjf.am.user.dao.model.customize.crm.UserCrmInfoCustomize;

/**
 * @author xiasq
 * @version UserInfoService, v0.1 2018/4/23 9:56
 */
public interface UserInfoService {
    UserInfo findUserInfoById(int userId);

    /**
     * @Description 根据身份证号查询用户信息
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/6 11:43
     */
    UserInfo findUserInfoByIdNo(String idNo);

    /**
     * 获取用户信息
     * @param truename
     * @param idcard
     * @return
     */
    UserInfo selectUserInfoByNameAndCard(String truename, String idcard);

    /**
     * 根据用户ID查询crm信息
     * @param userId
     * @return
     */
    UserCrmInfoCustomize findUserCrmInfoByUserId(Integer userId);

    /**
     * 查询用户详情
     * @param userId
     * @return
     */
	UserInfoCustomize queryUserInfoCustomizeByUserId(Integer userId);

    /**
     *
     * @param userId
     * @return
     */
    List<SpreadsUser> querySpreadsUsersByUserId(Integer userId);

    /**
     * 获取员工信息
     * @param userId
     * @return
     */
    EmployeeCustomize selectEmployeeByUserId(Integer userId);
}
