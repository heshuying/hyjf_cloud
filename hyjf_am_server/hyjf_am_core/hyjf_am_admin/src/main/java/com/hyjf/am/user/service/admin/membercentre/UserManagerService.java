/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.membercentre;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.user.AdminUserRecommendRequest;
import com.hyjf.am.resquest.user.UpdCompanyRequest;
import com.hyjf.am.resquest.user.UserInfosUpdCustomizeRequest;
import com.hyjf.am.resquest.user.UserManagerUpdateRequest;
import com.hyjf.am.trade.dao.model.auto.ROaDepartment;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.*;
import com.hyjf.am.user.service.BaseService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerService, v0.1 2018/6/20 9:47
 *          后台管理系统：会员中心->会员管理
 */
public interface UserManagerService extends BaseService {

    /**
     * -开户掉单更新用户
     *
     * @param userInfo
     * @return
     */
    int updateUserInfoByUserInfoSelective(UserInfo userInfo);

    /**
     *  更新用户表-开户掉单更新用户
     * @param user
     * @return
     */
    int updateUserSelective(User user);
    /**
     * 根据筛选条件查找会员列表
     * @param mapParam
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<UserManagerCustomize> selectUserMemberList(Map<String, Object> mapParam, int limitStart, int limitEnd);

    /**
     * 根据条件查询用户管理总数
     *
     * @return
     */
    int countUserRecord(Map<String, Object> mapParam);

    /**
     * 根据用户id获取用户详情
     *
     * @param userId
     * @return
     */
    UserManagerDetailCustomize selectUserDetailById(int userId);

    /**
     * 根据用户id获取开户信息
     *
     * @param userId
     * @return
     */
    UserBankOpenAccountCustomize selectBankOpenAccountByUserId(int userId);

    /**
     * 根据用户id获取企业用户开户信息
     *
     * @param userId
     * @return
     */
    CorpOpenAccountRecord selectCorpOpenAccountRecordByUserId(int userId);

    /**
     * 根据用户id获取第三方平台绑定信息
     *
     * @param userId
     * @return
     */
    BindUser selectBindUserByUserId(int userId);

    /**
     * 根据用户id获取用户CA认证记录表
     *
     * @param userId
     * @return
     */
    CertificateAuthority selectCertificateAuthorityByUserId(int userId);

    /**
     * 根据用户id获取用户修改信息
     *
     * @param userId
     * @return
     */
    UserManagerUpdateCustomize selectUserUpdateInfoByUserId(int userId);

    /**
     * 更新用户信息
     *
     * @param request
     * @return
     */
    int updataUserInfo(UserManagerUpdateRequest request);

    /**
     * 校验手机号
     *
     * @param userId
     * @param mobile
     * @return
     */
    int countUserByMobile(int userId, String mobile);

    /**
     * 统计手机号
     * @param mobile
     * @return
     */
    int countByMobile(String mobile);

    /**
     * 校验推荐人
     *
     * @param userId
     * @param recommendName
     * @return
     */
    int selectCheckRecommend(int userId, String recommendName);

    /**
     * 根据用户id查找用户表
     *
     * @param userId
     * @param userId
     * @return
     */
    User selectUserByUserId(int userId);

    BankOpenAccount selectBankOpenAccountByAccountId(String accountId);

    /**
     * 更新企业用户开户记录
     *
     * @param corpOpenAccountRecord
     * @return
     */
    int updateCorpOpenAccountRecord(CorpOpenAccountRecord corpOpenAccountRecord);

    /**
     * 插入企业用户开户记录
     *
     * @param corpOpenAccountRecord
     * @return
     */
    int insertCorpOpenAccountRecord(CorpOpenAccountRecord corpOpenAccountRecord);

    /**
     * 单表查询开户信息
     *
     * @return
     */
    BankOpenAccount queryBankOpenAccountByUserId(int userId);

    BankOpenAccount queryBankOpenAccountByUserName(String userName);

    /**
     * 更新开户信息
     *
     * @param request
     * @return
     */
    int updateBankOpenAccount(BankOpenAccount request);

    /**
     * 插入开户信息
     *
     * @param request
     * @return
     */
    int insertBankOpenAccount(BankOpenAccount request);

    /**
     * 更新用户信息表
     *
     * @param userInfo
     * @return
     */
    int updateUserInfoByUserInfo(UserInfo userInfo);

    /**
     * 更新用户表
     *
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 获取某一用户的信息修改列表
     * @param mapParam
     * @return
     */
    List<UserChangeLog> queryChangeLogList(Map<String, Object> mapParam);
    /**
     * 获取推荐人姓名查找用户
     * @param recommendName
     * @return
     */
    User selectUserByRecommendName(String recommendName);

    /**
     * 根据用户id获取推荐信息
     * @param userId
     * @return
     */
    UserRecommendCustomize searchUserRecommend(int userId);

    /**
     * 修改推荐人信息
     * @param request
     * @return
     */
    int updateUserRe(AdminUserRecommendRequest request);
    /**
     * 修改用户身份证
     * @param request
     * @return
     */
    int updateUserIdCard(AdminUserRecommendRequest request);
    /**
     * 保存企业开户信息
     * @param updCompanyRequest
     * @return
     */
    Response saveCompanyInfo(UpdCompanyRequest updCompanyRequest,User user,String bankId);

	Integer getUserIdByBind(int bindUniqueId, int bindPlatformId);

	Boolean bindThirdUser(Integer userId, int bindUniqueId, Integer bindPlatformId);

	String getBindUniqueIdByUserId(int userId, int bindPlatformId);
    /**
     * 根据关联关系查询OA表的内容,得到部门的线上线下属性
     * @param userId
     * @return
     */
    List<UserUpdateParamCustomize> queryUserAndDepartment(int userId);
    /**
     * 获取全部用户信息
     * @return
     */
    List<User> selectAllUser();
    /**
     * 查询此段时间的用户推荐人的修改记录
     * @param userId
     * @param repairStartDate
     * @param repairEndDate
     * @return
     */
    List<SpreadsUserLog> searchSpreadUsersLogByDate(Integer userId, String repairStartDate, String repairEndDate);
    /**
     * 查找员工信息(状态)
     * @param userId
     * @return
     */
     EmployeeCustomize selectEmployeeInfoByUserId(Integer userId);
    /**
     * 根据用户id获取离职信息
     * @param userId
     * @return
     */
    AdminEmployeeLeaveCustomize selectUserLeaveByUserId(Integer userId);

    List<BankCard> getTiedCardOfAccountBank(Integer userId);

    UserEvalationResult selectUserEvalationResultByUserId(Integer userId);

    /**
     * 根据推荐人id查找用信息
     * @param spreadUserId
     * @return
     */
    List<SpreadsUser> selectSpreadBySpreadUserId(Integer spreadUserId);

    /**
     * 根据手机号查找
     * @param mobile
     * @return
     */
   int countByMobileList(String mobile);
    /**
     * 根据部门id查找是否有自级菜单
     * @param deptId
     * @return
     */
    List<ROaDepartment> getDeptInfoByDeptId(int deptId);

    /**
     * 调用江西银行查询联行号
     * @param cardNo
     * @return
     */
    BankCallBean payAllianceCodeQuery(String cardNo, Integer userId);
    /**
     * 根据用户id查找银行卡信息
     * @param userId
     * @return
     */
    BankCard getBankCardByUserId(int userId);
    /**
     * 更新用户信息(基本信息,手机号,邮箱,用户角色)
     *
     * @param request
     * @return
     */
    int updateUserInfos(UserInfosUpdCustomizeRequest request);
    /**
     * 根据用户id查找开户信息
     * @param userId
     * @return
     */
    BankCard selectBankCardByUserId(Integer userId);

    /**
     * 更新银行卡信息(合规四期)
     * @param bankCard
     * @param bankAccountLog
     * @return
     */
   int updateUserBankInfo(BankCard bankCard, BankCardLog bankAccountLog);
}
