package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.auto.UserInfo;

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
}
