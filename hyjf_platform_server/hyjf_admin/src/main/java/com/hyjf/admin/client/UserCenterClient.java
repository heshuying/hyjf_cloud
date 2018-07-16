/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.trade.CorpOpenAccountRecordRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;

import java.util.List;

/**
 * @author nixiaoling
 * @version UserCenterClient, v0.1 2018/6/20 15:37
 */
public interface UserCenterClient {
    /**
     * 查找用户信息
     *
     * @param request
     * @return
     */
    UserManagerResponse selectUserMemberList(UserManagerRequest request);

    /**
     * 根据机构编号获取机构列表
     *
     * @return
     */
    List<HjhInstConfigVO> selectInstConfigAll();

    /**
     * 根据筛选条件查找用户总数
     *
     * @param request
     * @return
     */
    int countRecordTotal(UserManagerRequest request);

    /**
     * 根据用户id获取用户详情
     *
     * @param userId
     * @return
     */
    UserManagerDetailVO selectUserDetailById(String userId);

    /**
     * 根据用户id获取测评信息
     *
     * @param userId
     * @return
     */
    UserEvalationResultVO getUserEvalationResult(String userId);

    /**
     * 根据用户id获取开户信息
     *
     * @param userId
     * @return
     */
    UserBankOpenAccountVO selectBankOpenAccountByUserId(String userId);

    /**
     * 根据用户id获取企业用户开户信息
     *
     * @param userId
     * @return
     */
    CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(String userId);

    /**
     * 根据用户id获取第三方平台绑定信息
     *
     * @param userId
     * @return
     */
    BindUserVo selectBindeUserByUserId(String userId);

    /**
     * 根据用户id获取用户CA认证记录表
     *
     * @param userId
     * @return
     */
    CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId);

    /**
     * 根据用户id获取用户修改信息
     *
     * @param userId
     * @return
     */
    UserManagerUpdateVO selectUserUpdateInfoByUserId(String userId);

    /**
     * 更新用户信息
     *
     * @param request
     * @return
     */
    int updataUserInfo(UserManagerUpdateRequest request);

    /**
     * 根据用户id获取推荐人信息
     *
     * @param userId
     * @return
     */
    UserRecommendCustomizeVO selectUserRecommendByUserId(String userId);

    /**
     * 校验手机号
     *
     * @param userId
     * @param mobile
     * @return
     */
    int countUserByMobile(int userId, String mobile);

    /**
     * 校验推荐人
     *
     * @param userId
     * @param recommendName
     * @return
     */
    int checkRecommend(String userId, String recommendName);

    /**
     * 根据用户id查找用户表
     *
     * @param userId
     * @param userId
     * @return
     */
    UserVO selectUserByUserId(int userId);

    /**
     * 根据用户id查找用户信息
     *
     * @param userId
     * @return
     */
    List<BankCardVO> selectBankCardByUserId(int userId);

    /**
     * 根據accounId獲取開戶信息
     *
     * @param accountId
     * @return
     */
    BankOpenAccountVO selectBankOpenAccountByAccountId(String accountId);

    /**
     * 更新企业用户开户记录
     *
     * @param request
     * @return
     */
    int updateCorpOpenAccountRecord(CorpOpenAccountRecordRequest request);

    /**
     * 插入企业用户开户记录
     *
     * @param request
     * @return
     */
    int insertCorpOpenAccountRecord(CorpOpenAccountRecordRequest request);

    /**
     * 根据银行卡号获取bankId
     *
     * @param cardNo
     * @return
     */
    String queryBankIdByCardNo(String cardNo);

    /**
     * 获取银行卡配置信息
     *
     * @param bankId
     * @return
     */
    BanksConfigVO getBanksConfigByBankId(int bankId);

    /**
     * 更新用户绑定的银行卡
     *
     * @param request
     * @return
     */
    int updateUserCard(BankCardRequest request);

    /**
     * 保存用户绑定的银行卡
     *
     * @param request
     * @return
     */
    int insertUserCard(BankCardRequest request);

    /**
     * 单表查询开户信息
     *
     * @return
     */
    BankOpenAccountVO queryBankOpenAccountByUserId(int userId);

    /**
     * 更新开户信息
     *
     * @param request
     * @return
     */
    int updateBankOpenAccount(BankOpenAccountRequest request);

    /**
     * 插入开户信息
     *
     * @param request
     * @return
     */
    int insertBankOpenAccount(BankOpenAccountRequest request);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    UserInfoVO findUserInfoById(int userId);

    /**
     * 更新用户表
     *
     * @param userInfoVO
     * @return
     */
    int updateUserInfoByUserInfo(UserInfoVO userInfoVO);

    /**
     * 更新用户表
     *
     * @param userVO
     * @return
     */
    int updateUser(UserVO userVO);

    /**
     * 获取某一用户的信息修改列表
     * @param request
     * @return
     */
   List<UserChangeLogVO> selectUserChageLog(UserChangeLogRequest request);

    /**
     * 根据推荐人姓名查找用户
     * @param recommendName
     * @return
     */
    UserVO selectUserByRecommendName(String recommendName);

    SpreadsUserVO selectSpreadsUsersByUserId(String userId);
    /**
     * 修改推荐人信息
     * @param request
     * @return
     */
    int updateUserRecommend(AdminUserRecommendRequest request);

    /**
     * 修改用户身份证
     * @param request
     * @return
     */
    int updateUserIdCard(AdminUserRecommendRequest request);
}