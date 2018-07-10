package com.hyjf.admin.client;

import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.am.vo.user.*;

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

    /**
     * 根据userId列表查询user列表
     * @auth sunpeikai
     * @param userIds 用户id列表
     * @return
     */
    List<UserVO> findUserListByUserIds(String userIds);

    /**
     * 利用borrowNid查询出来的异常标的借款人userId查询银行账户
     * @auth sunpeikai
     * @param
     * @return
     */
    BankOpenAccountVO searchBankOpenAccount(Integer userId);

    /**
     * 根据用户名查询自定义用户信息
     * @auth sunpeikai
     * @param userName 用户名
     * @return
     */
    UserInfoCustomizeVO getUserInfoCustomizeByUserName(String userName);

    /**
     * 根据用户id查询自定义用户信息
     * @auth sunpeikai
     * @param userId 用户名
     * @return
     */
    UserInfoCustomizeVO getUserInfoCustomizeByUserId(Integer userId);

}
