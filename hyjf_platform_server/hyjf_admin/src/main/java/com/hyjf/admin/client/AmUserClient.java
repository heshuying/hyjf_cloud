package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
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

    /**
     * 根据用户id查询推荐人表信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    SpreadsUserVO searchSpreadsUserByUserId(Integer userId);


    /**
     * 根据用户id查询employee
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    EmployeeCustomizeVO searchEmployeeBuUserId(Integer userId);

    /**
     * 查询自动投资债转异常列表
     * @auth sunpeikai
     * @param
     * @return
     */
    AdminUserAuthListResponse userAuthList(AdminUserAuthListRequest adminUserAuthListRequest);

    /**
     * 同步用户授权状态
     * @auth sunpeikai
     * @param type 1自动投资授权  2债转授权
     * @return
     */
    JSONObject synUserAuth(Integer userId, Integer type);

}
