package com.hyjf.admin.client;

import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmUserClient, v0.1 2018/4/19 12:44
 */
public interface AmUserClient {

    /**
     * 根据userName查询Account
     * @auth sunpeikai
     * @param
     * @return
     */
    List<UserVO> searchUserByUsername(String userName);

    /**
     * 根据userId查询accountChinapnr开户信息
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AccountChinapnrVO> searchAccountChinapnrByUserId(Integer userId);

    /**
     * 根据userId查询用户信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    UserVO searchUserByUserId(Integer userId);

    /**
     * 根据手机号获取用户信息
     * @param userName
     * @return
     */
    UserVO getUserByUserName(String userName);

    /**
     * 根据userId查询用户信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    UserInfoVO findUsersInfoById(int userId);

    /**
     * 根据userId查询用户
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    UserVO findUserById(final int userId);

}
