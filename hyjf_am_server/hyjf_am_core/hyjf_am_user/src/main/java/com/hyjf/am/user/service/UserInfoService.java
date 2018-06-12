package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.auto.UserInfo;

/**
 * @author xiasq
 * @version UserInfoService, v0.1 2018/4/23 9:56
 */
public interface UserInfoService {
    UserInfo findUserInfoById(int userId);

    /**
     * 获取用户信息
     * @param truename
     * @param idcard
     * @return
     */
    UserInfo selectUserInfoByNameAndCard(String truename, String idcard);
}
