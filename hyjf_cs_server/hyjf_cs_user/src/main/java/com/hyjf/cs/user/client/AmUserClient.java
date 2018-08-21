package com.hyjf.cs.user.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
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
	void updateBindEmail(BindEmailLogRequest bean);

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
	void insertBindEmailLog(BindEmailLogRequest bean);

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

	EvalationVO getEvalationByCountScore(short countScore);

	List<EvalationVO> getEvalationRecord();

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

	UserInfoVO findUserInfoByCardNo(String cradNo);

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

	void deleteUserEvalationResultByUserId(Integer userId);

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

	int deleteUserCardByCardNo(String cardNo);

	int insertUserCard(BankCardRequest request);

	int updateUserCard(BankCardRequest request);

	int insertBindCardLog(BankCardLogRequest request);

	CorpOpenAccountRecordVO getCorpOpenAccountRecord(int userId);

    List<BankCardVO> getBankOpenAccountById(Integer userId);

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
	int selectMyInviteCount(MyInviteListRequest requestBean);

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
	 * 获取需要更新用户画像的userInfo --用户画像定时任务用
	 * */
	List<UserInfoVO> searchUserInfo();
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

	UserVO insertSurongUser(String mobile, String password, String ipAddr, String platform);

	UserVO surongRegister(RegisterUserRequest registerUserRequest);

	/**
	 * 获取银行卡信息，status=0
	 * @param userId
	 * @return
	 */
	List<BankCardVO> selectBankCardByUserIdAndStatus(Integer userId);

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

	/**
	 * 授权
	 * @param userId
	 * @param bindUniqueId
	 * @param pid
	 * @return
	 */
	Boolean bindThirdUser(Integer userId, int bindUniqueId, Integer pid);

	/**
	 * 获取银行卡信息
	 * @param userId
	 * @return
	 */
	BankCardVO getBankCardByUserId(Integer userId);

	/**
	 * 插入各种信息
	 * @param userActionUtmRequest
	 * @return
	 */
	UserVO insertUserActionUtm(UserActionUtmRequest userActionUtmRequest);

	/**
	 * 插入测评结果
	 * @param userId
	 * @param countScore
	 * @return
	 */
    UserEvalationResultVO skipEvaluate(Integer userId, int countScore);
}
