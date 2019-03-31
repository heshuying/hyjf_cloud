/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.admin.beans.response.CompanyInfoSearchResponseBean;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;

/**
 * @author nxl
 * @version UserCenterService, v0.1 2018/6/20 15:34
 */
public interface UserCenterService {

    /**
     *查找用户信息
     * @param request
     * @return
     */
    UserManagerResponse selectUserMemberList(UserManagerRequest request);

    /**
     * 根据机构编号获取机构列表
     * @return
     */
    List<HjhInstConfigVO> selectInstConfigAll();

    /**
     * 根据用户id获取用户详情
     * @param userId
     * @return
     */
    UserManagerDetailVO selectUserDetail(String userId);

    /**
     * 根据用户id获取测评信息
     * @param userId
     * @return
     */
    UserEvalationResultVO getUserEvalationResult(String userId);
    /**
     * 根据用户id获取开户信息
     * @param userId
     * @return
     */
    UserBankOpenAccountVO selectBankOpenAccountByUserId(String userId);
    /**
     * 根据用户id获取企业用户开户信息
     * @param userId
     * @return
     */
    CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(String userId);
    /**
     * 根据用户id获取第三方平台绑定信息
     * @param userId
     * @return
     */
    BindUserVo selectBindeUserByUserI(String userId);

    /**
     * 根据用户id获取用户CA认证记录表
     * @param userId
     * @return
     */
    CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId);

    /**
     * 根据用户id获取用户修改信息
     * @param userId
     * @return
     */
    UserManagerUpdateVO selectUserUpdateInfoByUserId (String userId);

    /**
     * 更新用户信息
     */
    int updataUserInfo(UserManagerUpdateRequest userRequest);

    /**
     * 根据用户id获取推荐人信息
     * @param userId
     * @return
     */
    UserRecommendCustomizeVO selectUserRecommendByUserId(String userId);

    /**
     * 校验手机号
     * @param mobile
     * @return
     */
    int countUserByMobile(String mobile,int userId);

    /**
     * 校验推荐人
     * @param userId
     * @param recommendName
     * @return
     */
    int checkRecommend(String userId, String recommendName);
    /**
     * @Description 根据accountid调用接口查找企业信息
     */
    CompanyInfoSearchResponseBean queryCompanyInfoByAccoutnId(Integer userId, String accountId);
    /**
     * 根据用户id查找用户表
     * @param userId
     * @param userId
     * @return
     */
    UserVO selectUserByUserId(String userId);

    /**
     * 根据用户id查找企业信息
     * @param userId
     * @return
     */
    CompanyInfoVO selectCompanyInfoByUserId(String userId);


    /**
     * 获取某一用户的信息修改列表
     * @param request
     * @return
     */
    List<UserChangeLogVO> selectUserChageLog(UserChangeLogRequest request);
    /**
     * 根据用户id查找推荐人信息
     * @param userId
     * @return
     */
    SpreadsUserVO selectSpreadsUsersByUserId(String userId);

    /**
     * 根据推荐人姓名查找用户
     * @param recommendName
     * @return
     */
    UserVO selectUserByRecommendName(String recommendName);

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
    /**
     * 单表查询开户信息
     *
     * @return
     */
    BankOpenAccountVO queryBankOpenAccountByUserId(int userId);

    /**
     * 保存企业信息
     * @param updCompanyRequest
     * @return
     */
    Response saveCompanyInfo(UpdCompanyRequest updCompanyRequest);
    /**
     * 发送CA认证信息修改MQ
     * @param form
     */
    void sendCAChangeMQ(UserManagerUpdateRequest form);
    /**
     * 发送CA认证信息修改MQ
     * @param form
     */
    void sendCAChangeMQ(AdminUserRecommendRequest form);
    /**
     *
     * @Description:通过身份证号获取户籍所在地
     * @param idCard
     * @return String
     * @exception:
     */
    String getAreaByIdCard(String idCard);
    /**
     * 根据推荐人id查找用信息
     * @param userId
     * @return
     */
    List<SpreadsUserVO> selectSpreadsUserBySpreadUserId(int userId);

    /**
     * 部门树形结构
     * @return
     */
    JSONArray getCrmDepartmentList(String[] list);
    /**
     * 根据用户id获取开户信息
     *
     * @auther: nxl
     * @param userId
     * @return
     */
    BankCardVO getBankCardByUserId(String userId);
    /**
     * 调用江西银行查询联行号
     * @param cardNo
     * @return
     */
    BankCallBean payAllianceCodeQuery(String cardNo, Integer userId);
    /**
     * 根据银行名获取江西银行配置信息
     * @param bankName
     * @return
     * @auth nxl
     */
    JxBankConfigVO getBankConfigByBankName(String bankName);
    /**
     * 更新用户信息(基本信息,手机号,邮箱,用户角色)
     *
     * @param request
     * @auther: nxl
     * @return
     */
    int updateUserBaseInfo(UserInfosUpdCustomizeRequest request);
    /**
     * 更新银行卡信息
     *
     * @param request
     * @auther: nxl
     * @return
     */
    int updateUserBankInfo(UserInfosUpdCustomizeRequest request);

    /**
     * 根据userId获取userInfo
     * @param userId
     * @return
     */
    UserInfoVO selectUserInfoByUserId(String userId);

    /**
     * 通过当前用户ID 查询用户所在一级分部,从而关联用户所属渠道
     * @param userId
     * @return
     * @Author : huanghui
     */
    UserUtmInfoCustomizeVO getUserUtmInfo(Integer userId);
    /**
     * 企业信息补录时查询，根据对公账号查找银行信息
     * @param account
     * @param userId
     * @return
     * @auther: nxl
     */
    BankCardResponse getBankInfoByAccount(String account,String userId);


    /**
     * 用户销户操作
     *
     * @param userId
     * @param bankOpenAccount
     * @return
     */
    int cancellationAccountAction(String userId,Integer bankOpenAccount);

    /**
     * 用户销户后,删除用户账户表
     *
     * @param userId
     */
    int deleteUserAccountAction(String userId);

    /**
     * 用户保存用户销户记录
     *
     * @param bankCancellationAccountRequest
     */
    int saveCancellationAccountRecordAction(BankCancellationAccountRequest bankCancellationAccountRequest);
}
