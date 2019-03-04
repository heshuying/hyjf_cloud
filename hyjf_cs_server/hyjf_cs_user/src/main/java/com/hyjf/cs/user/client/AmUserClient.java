package com.hyjf.cs.user.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.api.WrbRegisterRequest;
import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/4/19 12:44
 */
public interface AmUserClient {

	/**
	 * 根据手机号查询User
	 * @param mobile
	 * @return
	 */
	UserVO findUserByMobile(String mobile);

	/**
	 * 根据userId查询User
	 * @param userId
	 * @return
	 */
	UserVO findUserById(int userId);

	/**
	 * 通过当前用户ID 查询用户所在一级分部,从而关联用户所属渠道
	 * @param userId
	 * @return
	 * @Author : huanghui
	 */
	UserUtmInfoCustomizeVO getUserUtmInfo(Integer userId);

	/**
	 * 根据用户名或者手机号查询user
	 * @param loginUserName
	 * @return
	 */
	UserVO findUserByUserNameOrMobile(String loginUserName);

	/**
	 * 根据推荐人手机号或userId查询推荐人
	 * @param reffer
	 * @return
	 */
	int countUserByRecommendName(String reffer);

	/**
	 * 注册
	 * @param request
	 * @return
	 */
	UserVO register(RegisterUserRequest request);

	/**
	 * 根据userId获取userInfo
	 * @param userId
	 * @return
	 */
	UserInfoVO findUserInfoById(int userId);

	/**
	 * 保存短信信息
	 * @param mobile
	 * @param checkCode
	 * @param validCodeType
	 * @param status
	 * @param platform
	 * @return
	 */
	int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform);

	/**
	 * 校验验证码
	 * @param mobile
	 * @param verificationCode
	 * @param verificationType
	 * @param platform
	 * @param searchStatus
	 * @param updateStatus
	 * @param isUpdate
	 * @return
	 */
	int checkMobileCode(String mobile, String verificationCode, String verificationType, String platform,
						Integer searchStatus, Integer updateStatus,boolean isUpdate);

    UserVO updateByCondition(String loginUserName);

    /**
	 * 更新登录信息
	 * @param userId
	 * @param ip
	 */
	void updateLoginUser(int userId, String ip);

	/**
	 * 检查用户授权状态
	 * @param userId
	 * @return
	 */
	HjhUserAuthVO getHjhUserAuthByUserId(Integer userId);

	BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);

	/**
	 * 插入UserAuth信息
	 * @param hjhUserAuthLog
	 */
	void insertUserAuthLog(HjhUserAuthLogVO hjhUserAuthLog);

	/**
	 * 查询授权错误信息
	 * @param orderId
	 * @return
	 */
	HjhUserAuthLogVO selectByExample(String orderId);

	/**
	 * 更新签约状态和日志表
	 * @param bean
	 */
	void updateUserAuthInves(BankRequest bean);

	/**
	 * 更新用户表
	 * @param user
	 * @return
	 */
	Integer updateUserById(UserVO user);

	/**
	 * 手机手机号
	 * @param mobile
	 * @return
	 */
	int countByMobile(String mobile);

	/**
	 * 修改密码
	 * @param userId
	 * @param oldPW
	 * @param newPW
	 * @return
	 */
	JSONObject updatePassWd(Integer userId, String oldPW, String newPW);

	/**
	 * 获取汇付信息
	 * @param userId
	 * @return
	 */
    AccountChinapnrVO getAccountChinapnr(Integer userId);

	/**
	 * 校验邮箱
	 * @param email
	 * @return
	 */
	boolean checkEmailUsed(String email);

	/**
	 * 更新绑定邮箱信息
	 * @param bean
	 */
	Integer updateBindEmail(BindEmailLogRequest bean);

	/**
	 * 获取绑定邮箱日志信息表
	 * @param userId
	 * @return
	 */
	BindEmailLogVO getBindEmailLog(Integer userId);

	/**
	 * 插入绑定邮箱日志信息表
	 * @param bean
	 */
	Integer insertBindEmailLog(BindEmailLogRequest bean);

	/**
	 * 保存紧急联系人
	 * @param bean
	 * @return
	 */
	int updateUserContract(UsersContractRequest bean);

	/**
	 * 查询紧急联系人
	 * @param userId
	 * @return
	 */
    UsersContactVO selectUserContact(Integer userId);

	/**
	 * 更新用户信息
	 * @param requestBean
	 * @return
	 */
	int updateUserNoticeSet(UserNoticeSetRequest requestBean);

	/**
	 * 根据idno获取用户信息
	 * @param idNo
	 * @return
	 */
	UserInfoVO getUserByIdNo(String idNo);

    UserLoginLogVO getUserLoginById(Integer userId);

	BankOpenAccountVO selectById(int userId);

	boolean updateBankSmsLog(BankSmsLogRequest request);

	String selectBankSmsLog(BankSmsLogRequest request);

	List<EvalationCustomizeVO> getEvalationRecord();

	/**
	 * 获取测评结果
	 * @param evalationType
	 * @return
	 */
    EvalationVO getEvalationByEvalationType(String evalationType);

	/**
	 * 插入测评信息
	 * @param userEvalationRequest
	 * @return
	 */
	UserEvalationResultVO insertUserEvalationResult(UserEvalationRequest userEvalationRequest);

	/**
	 * 保存开户日志
	 * @param request
	 * @return
	 */
	int updateUserAccountLog(BankOpenRequest request);

	/**
	 * 根据电子账户号查询用户ID
	 * @param accountId
	 * @return
	 */
	BankOpenAccountVO selectByAccountId(String accountId);

	/**
	 * 风险测评结果
	 * @param userId
	 * @return
	 */
	UserEvalationResultVO selectUserEvalationResultByUserId(Integer userId);

	/**
	 * 修改开户日志表的状态
	 * @param userId
	 * @param logOrderId
	 * @param state
	 * @param retCode
     * @return
	 */
	Integer updateUserAccountLogState(int userId, String logOrderId, int state, String retCode,String retMsg);

	/**
	 * 开户成功后保存用户开户信息
	 * @param bean
	 * @return
	 */
	Integer saveUserAccount(BankCallBean bean);

	/**
	 * 开户成功后保存银行卡信息
	 * @param request
	 * @return
	 */
	Integer saveCardNoToBank(BankCardRequest request);

	/**
	 * 待解绑卡校验
	 * @param userId
	 * @param cardNo
	 * @return
	 */
    BankCardVO queryUserCardValid(String userId, String cardNo);

	/**
	 * 已绑卡校验
	 * @param userId
	 * @return
	 */
	int countUserCardValid(String userId);

	int deleteUserCardByUserId(String userId);

	int insertUserCard(BankCardRequest request);

	int updateUserCard(BankCardRequest request);

	int insertBindCardLog(BankCardLogRequest request);

	CorpOpenAccountRecordVO getCorpOpenAccountRecord(int userId);

    List<BankCardVO> getTiedCardForBank(Integer userId);

    /**
     * @Description 查询开户失败原因
     * @Author sunss
     * @Date 2018/6/21 15:45
     */
	String getBankOpenAccountFiledMess(String logOrdId);

	/**
	 * 判断企业用户是否已开户
	 * @param userId
	 * @return
	 */
	int isCompAccount(Integer userId);

	/**
	 * 根据渠道号检索推广渠道是否存在
	 * @param utmId
	 * @return
	 */
	UtmPlatVO selectUtmPlatByUtmId(String utmId);

	/**
	 * 我的邀请列表
	 * @param requestBean
	 * @return
	 */
    List<MyInviteListCustomizeVO> selectMyInviteList(MyInviteListRequest requestBean);

	/**
	 * 我的邀请记录总数
	 * @param requestBean
	 * @return
	 */
	Integer selectMyInviteCount(MyInviteListRequest requestBean);

    /**
	 * 查询同步银行卡号
	 * @param flag
	 * @return
	 */
	List<AccountMobileSynchVO> searchAccountMobileSynch(String flag);

	/**
	 * 更新银行卡号手机号同步表
	 * @param accountMobileSynchRequest
	 * @return
	 */
	boolean updateAccountMobileSynch(AccountMobileSynchRequest accountMobileSynchRequest);
	/**
	 * 集团组织信息查询
	 * */
	List<OrganizationStructureVO> searchGroupInfo();
	/**
	 * 更新用户画像 99:更新三个月的用户画像,else:更新昨日登录的用户画像
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	List<UserAndSpreadsUserVO> searchUserIdForUserPortrait(int flag);
	/**
	 * 保存用户画像信息 --用户画像定时任务用
	 * */
	void saveUserPortrait(BatchUserPortraitQueryRequest batchUserPortraitQueryRequest);


    int saveUserEvaluation(UserEvalationResultVO userEvalationResult);

	/**
	 * 插入behavior数据并返回id
	 * @param userId
	 * @param s
	 * @return
	 */
    Integer insertUserEvalationBehavior(Integer userId, String s);

	Integer updateUserEvaluationBehavior(UserEvalationBehaviorVO userEvalationBehavior);

    void clearMobileCode(Integer userId, String sign);

	UserVO surongRegister(RegisterUserRequest registerUserRequest);

    Boolean updateAfterDeleteCard(BankCardUpdateRequest requestBean);

	/**
	 * 获取银行卡信息
	 * @param userId
	 * @param status
	 * @return
	 */
	List<BankCardVO> selectBankCardByUserIdAndStatus(Integer userId,Integer status);

	/**
	 *查询唯一标识
	 * @param userId
	 * @return
	 */
    UserAliasVO findAliasesByUserId(Integer userId);

	/**
	 * 更新唯一标识
	 * @param mobileCode
	 */
	void updateAliases(UserAliasVO mobileCode);

	/**
	 * 插入唯一标识
	 * @param mobileCode
	 */
	void insertMobileCode(UserAliasVO mobileCode);

	/**
	 * 根据用户id获取银行卡信息
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @return
	 */
	List<AccountBankVO> getAccountBankByUserId(Integer userId);

	/**
	 * 获取用户信息中vip信息
	 * @param vipId
	 * @return
	 */
    VipInfoVO findVipInfoById(Integer vipId);

	/**
	 *
	 * @param userId
	 * @param status
	 * @return
	 */
    List<AccountBankVO> selectAccountBank(Integer userId, int status);

	/**
	 *
	 * @param userId
	 * @return
	 */
	List<AdminBankAccountCheckCustomizeVO> queryAllBankOpenAccount(Integer userId);

	/**
	 * 获取开户账号
	 * @param accountId
	 * @return
	 */
	BankOpenAccountVO selectBankOpenAccountByAccountId(String accountId);

	/**
	 * 根据绑定信息取得用户id
	 * @param bindUniqueId
	 * @param bindPlatformId
	 * @return
	 */
	Integer getUserIdByBind(Integer bindUniqueId, Integer bindPlatformId);

	/**
	 * 插入各种信息
	 * @param userId
	 * @param bindPlatformId
	 * @return
	 */
	String getBindUniqueIdByUserId(int userId, int bindPlatformId);

    BindUserVo getBindUser(int userId, int bindPlatformId);

    /**
	 * 授权
	 * @param userId
	 * @param bindUniqueId
	 * @param pid
	 * @return
	 */
	Boolean bindThirdUser(Integer userId, Integer bindUniqueId, Integer pid);

	/**
	 * 获取银行卡信息
	 * @param userId
	 * @return
	 */
	BankCardVO getBankCardByUserId(Integer userId);

	/**
	 * 插入测评结果
	 * @param userId
	 * @param countScore
	 * @return
	 */
    UserEvalationResultVO skipEvaluate(Integer userId, int countScore);

	/**
	 * 插入ht_hjh_user_auth表
	 * @param hjhUserAuthRequest
	 * @auth nxl
	 * @return
	 */
	int insertHjhUserAuth(HjhUserAuthRequest hjhUserAuthRequest);
	/**
	 * 更新ht_hjh_user_auth表
	 * @param hjhUserAuthRequest
	 * @auth nxl
	 * @return
	 */
	int updateHjhUserAuth(HjhUserAuthRequest hjhUserAuthRequest);
	/**
	 * 更新 ht_hjh_user_auth_log 表
	 * @param hjhUserAuthRequest
	 * @auth nxl
	 * @return
	 */
	int updateHjhUserAuthLog(HjhUserAuthLogRequest hjhUserAuthRequest);

	/**
	 * 同步手机号
	 * @param accountMobileAynch
	 * @return
	 */
	boolean updateMobileSynch(AccountMobileSynchRequest accountMobileAynch);

	/**
	 *修改用户信息
	 * @param userVO
	 * @return
	 */
	boolean updateByPrimaryKey(UserVO userVO);

	/**
	 * 更新用户信息表
	 *
	 * @auther: nxl
	 * @return
	 */
	 int updateUserInfoByUserInfo(UserInfoVO userInfoVO);

	/**
	 * 根据手机号密码注册用户
	 * @return
	 */
	Integer insertUserAction(WrbRegisterRequest wrbRegisterRequest);

    BankOpenAccountVO selectBankAccountById(Integer userId);

	/**
	 * 插入密码错误超限用户信息
	 * @author kdl 2018.10.29
	 * @param lockedUserInfoVO
	 * @return
	 */
	public void inserLockedUser(LockedUserInfoVO lockedUserInfoVO);

    void updateUserAuth(UserAuthRequest request);

    void updateUserAuthLog(HjhUserAuthLogVO hjhUserAuthLog);
	/**
	 * 根据用户Id,银行卡Id查询用户银行卡信息
	 * @param userId
	 * @param cardId
	 * @auther: nxl
	 * @return
	 */
	BankCardVO getBankCardById(int userId, String cardId);

	/**
	 * 插入app渠道统计数据
	 * @param wrbRegisterRequest
	 * @return
	 */
	boolean insertAppChannelStatisticsDetail(WrbRegisterRequest wrbRegisterRequest);

    AccountVO getAccount(Integer userId);

	UserVO fUserById(int userId);

	UserInfoVO fUserInfoById(int userId);

	/**
	 * 主从延迟问题，查询主库
	 * @param userId
	 * @return
	 */
    UserVO updateUsersById(Integer userId);

	public void fddCertificate();

	/**
	 * 员工入职修改客户属性
	 */
	void updateEntey();

	/**
	 * 员工离职，修改客户属性
	 */
	void updateUserLeave();

	/**
	 * 获取用户信息
	 * @param userId
	 * @return
	 */
    WebViewUserVO getWebViewUserByUserId(Integer userId);

	/**
	 * 更新用户信息
	 * @param u
	 * @param ipAddr
	 */
	void updateUser(UserVO u, String ipAddr);
}
