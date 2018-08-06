package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.trade.CorpOpenAccountRecordRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
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

    UserVO getUserByAccountId(String accountId);

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

    BankOpenAccountVO queryBankOpenAccountByUserName(String userName);


    /**
     * 查找用户信息
     * @auth nxl
     * @param request
     * @return
     */
    UserManagerResponse selectUserMemberList(UserManagerRequest request);
     /**
     * 根据筛选条件查找用户总数
      * @auth nxl
     * @param request
     * @return
     */
    int countRecordTotal(UserManagerRequest request);

    /**
     * 根据用户id获取用户详情
     * @auth nxl
     * @param userId
     * @return
     */
    UserManagerDetailVO selectUserDetailById(String userId);

    /**
     * 根据用户id获取测评信息
     * @auth nxl
     * @param userId
     * @return
     */
    UserEvalationResultVO getUserEvalationResult(String userId);

    /**
     * 根据用户id获取开户信息
     * @auth nxl
     * @param userId
     * @return
     */
    UserBankOpenAccountVO selectBankOpenAccountByUserId(String userId);

    /**
     * 根据用户id获取企业用户开户信息
     * @auth nxl
     * @param userId
     * @return
     */
    CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(String userId);

    /**
     * 根据用户id获取第三方平台绑定信息
     * @auth nxl
     * @param userId
     * @return
     */
    BindUserVo selectBindeUserByUserId(String userId);

    /**
     * 根据用户id获取用户CA认证记录表
     * @auth nxl
     * @param userId
     * @return
     */
    CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId);

    /**
     * 根据用户id获取用户修改信息
     * @auth nxl
     * @param userId
     * @return
     */
    UserManagerUpdateVO selectUserUpdateInfoByUserId(String userId);

    /**
     * 更新用户信息
     * @auth nxl
     * @param request
     * @return
     */
    int updataUserInfo(UserManagerUpdateRequest request);

    /**
     * 根据用户id获取推荐人信息
     * @auth nxl
     * @param userId
     * @return
     */
    UserRecommendCustomizeVO selectUserRecommendByUserId(String userId);

    /**
     * 校验手机号
     * @auth nxl
     * @param userId
     * @param mobile
     * @return
     */
    int countUserByMobile(int userId, String mobile);

    /**
     * 校验推荐人
     * @auth nxl
     * @param userId
     * @param recommendName
     * @return
     */
    int checkRecommend(String userId, String recommendName);

    /**
     * 根据用户id查找用户表
     * @auth nxl
     * @param userId
     * @param userId
     * @return
     */
    UserVO selectUserByUserId(int userId);

    /**
     * 根据用户id查找用户信息
     * @auth nxl
     * @param userId
     * @return
     */
    List<BankCardVO> selectBankCardByUserId(int userId);

    /**
     * 更新企业用户开户记录
     * @auth nxl
     * @param request
     * @return
     */
    int updateCorpOpenAccountRecord(CorpOpenAccountRecordRequest request);

    /**
     * 插入企业用户开户记录
     * @auth nxl
     * @param request
     * @return
     */
    int insertCorpOpenAccountRecord(CorpOpenAccountRecordRequest request);



    /**
     * 获取银行卡配置信息
     * @auth nxl
     * @param bankId
     * @return
     */
//    BanksConfigVO getBanksConfigByBankId(int bankId);

    /**
     * 更新用户绑定的银行卡
     * @auth nxl
     * @param request
     * @return
     */
    int updateUserCard(BankCardRequest request);

    /**
     * 保存用户绑定的银行卡
     * @auth nxl
     * @param request
     * @return
     */
    int insertUserCard(BankCardRequest request);

    /**
     * 单表查询开户信息
     * @auth nxl
     * @return
     */
    BankOpenAccountVO queryBankOpenAccountByUserId(int userId);

    /**
     * 更新开户信息
     * @auth nxl
     * @param request
     * @return
     */
    int updateBankOpenAccount(BankOpenAccountRequest request);

    /**
     * 插入开户信息
     * @auth nxl
     * @param request
     * @return
     */
    int insertBankOpenAccount(BankOpenAccountRequest request);

    /**
     * 根据用户id获取用户信息
     * @auth nxl
     * @param userId
     * @return
     */
    UserInfoVO findUserInfoById(int userId);

    /**
     * 更新用户表
     * @auth nxl
     * @param userInfoVO
     * @return
     */
    int updateUserInfoByUserInfo(UserInfoVO userInfoVO);

    /**
     * 更新用户表
     * @auth nxl
     * @param userVO
     * @return
     */
    int updateUser(UserVO userVO);

    /**
     * 获取某一用户的信息修改列表
     * @auth nxl
     * @param request
     * @return
     */
    List<UserChangeLogVO> selectUserChageLog(UserChangeLogRequest request);

    /**
     * 根据推荐人姓名查找用户
     * @param recommendName
     * @auth nxl
     * @return
     */
    UserVO selectUserByRecommendName(String recommendName);

    SpreadsUserVO selectSpreadsUsersByUserId(String userId);
    /**
     * 修改推荐人信息
     * @auth nxl
     * @param request
     * @return
     */
    int updateUserRecommend(AdminUserRecommendRequest request);

    /**
     * 修改用户身份证
     * @auth nxl
     * @param request
     * @return
     */
    int updateUserIdCard(AdminUserRecommendRequest request);
    /**
     * 保存企业开户信息
     * @auth nxl
     * @param updCompanyRequest
     * @return
     */
    Response saveCompanyInfo(UpdCompanyRequest updCompanyRequest);
    /**
     * 根据参数查询用户画像信息
     * @param request
     * @return
     * @author nxl
     */
    UserPortraitResponse selectRecordList(UserPortraitRequest request);

    /**
     * 根据用户id查找用户画像
     * @param userId
     * @author nxl
     * @return
     */
    UserPortraitVO selectUsersPortraitByUserId(Integer userId);

    /**
     * 修改用户画像
     * @author nxl
     */
    int updateUserPortrait(UserPortraitRequest request);


    /**
     * 根据UserID查询开户信息
     *
     * @param userId
     * @return
     */
    BankOpenAccountVO getBankOpenAccountByUserId(Integer userId);
    
    /*加入明细 start*/
	/**
	 * 通过userid获取user
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	UserVO getUserByUserId(int userId);

	/**
	 * 通过
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	UserInfoVO selectUsersInfoByUserId(int userid);
	/*加入明细 end*/

    /**
     * 查找注册记录列表
     * @author nxl
     * @param request
     * @return
     */
    RegistRecordResponse findRegistRecordList(RegistRcordRequest request);
    /**
     * 查找借款盖章用户信息
     * @author nxl
     * @param request
     * @return
     */
    LoanCoverUserResponse selectUserMemberList(LoanCoverUserRequest request);

    /**
     * 保存借款盖章用户信息
     * @param request
     * @return
     * @author nxl
     */
    int insertLoanCoverUser(LoanCoverUserRequest request);
    /**
     * 根据证件号码查找借款主体CA认证记录表
     * @param strIdNo
     * @author nxl
     * @return
     */
    LoanCoverUserVO selectIsExistsRecordByIdNo(String strIdNo);
    /**
     * 更新记录
     */
    int updateLoanCoverUserRecord(LoanCoverUserRequest request);
    /**
     * 根据id查找借款主体CA认证记录表
     * @param strId
     * @author nxl
     * @return
     */
    LoanCoverUserResponse selectIsExistsRecordById(String strId);
    /**
     * 根据筛选条件查找(用户测评列表显示)
     * @author nxl
     * @param request
     * @return
     */
    EvalationResultResponse selectUserEvalationResultList(EvalationRequest request);
    /**
     * 根据id查找用户测评结果
     * @param userId
     * @return
     * @author nxl
     */
    UserEvalationResultVO selectEvaluationDetailById(String userId);
    /**
     * 查找汇付银行开户记录列表
     * @author nixiaoling
     * @param request
     * @return
     */
    BankAccountRecordResponse findAccountRecordList(AccountRecordRequest request);

    /**
     * 查找江西银行开户记录列表
     * @author nixiaoling
     * @param request
     * @return
     */
    BankAccountRecordResponse findBankAccountRecordList(BankAccountRecordRequest request);
    /**
     *  根据筛选条件查找汇付银行卡信息列表
     * @author nixiaoling
     * @param request 筛选条件
     * @return
     */
    BankCardManagerResponse selectBankCardList(BankCardManagerRequest request);

    /**
     * 根据筛选条件查找江西银行卡信息列表
     * @author nixiaoling
     * @param request
     * @return
     */
    BankCardManagerResponse selectNewBankCardList (BankCardManagerRequest request);
    /**
     * 查找用户银行卡操作记录表
     * @param request
     * @author nixiaoling
     * @return
     */
    BankCardLogResponse selectBankCardLogByExample(BankCardLogRequest request);

    /**
     * 根据证件号码和姓名查找用户CA认证记录表
     * @param strIdNo
     * @param tureName
     * @return
     */
    CertificateAuthorityResponse selectCertificateAuthorityByIdNoName(String strIdNo, String tureName);
    CertificateAuthorityResponse isCAIdNoCheck(String param, String name);
    

	public AdminUserAuthListResponse userauthlist(AdminUserAuthListRequest adminUserAuthListRequest);
	public AdminUserAuthListResponse cancelInvestAuth(int userId);
	public AdminUserAuthListResponse cancelCreditAuth( int userId);
	public AdminUserAuthLogListResponse userauthLoglist(AdminUserAuthLogListRequest adminUserAuthListRequest);
	CertificateAuthorityResponse getRecordList(CertificateAuthorityExceptionRequest aprlr);
	CertificateAuthorityResponse updateUserCAMQ(int userId);
	/**
	 * 获取预注册数据列表
	 * 
	 * @return
	 */
	public  AdminPreRegistListResponse getRecordList(AdminPreRegistListRequest adminPreRegistListRequest);
	
	/**
     * 获取预注册页面信息
     * 
     * @return
     */
    public AdminPreRegistListResponse getPreRegist(AdminPreRegistListRequest adminPreRegistListRequest);
    
    /**
     * 编辑保存预注册页面信息
     * 
     * @return
     */
    public AdminPreRegistListResponse savePreRegist(AdminPreRegistListRequest adminPreRegistListRequest);

}
