package com.hyjf.cs.user.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.user.*;
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

	UserInfoVO findUserInfoById(int userId);

	int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform);

	int checkMobileCode(String mobile, String verificationCode, String verificationType, String platform,
						Integer searchStatus, Integer updateStatus);


	void updateLoginUser(int userId, String ip);

	HjhUserAuthVO getHjhUserAuthByUserId(Integer userId);

	BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);

	void insertUserAuthLog(HjhUserAuthLogVO hjhUserAuthLog);

	HjhUserAuthLogVO selectByExample(String orderId);

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

	JSONObject updatePassWd(Integer userId, String oldPW, String newPW);

    AccountChinapnrVO getAccountChinapnr(Integer userId);

	boolean checkEmailUsed(String email);

	void updateBindEmail(BindEmailLogRequest bean);

	BindEmailLogVO getBindEmailLog(Integer userId);

	void insertBindEmailLog(BindEmailLogRequest bean);

	int updateUserContract(UsersContractRequest bean);

    UsersContactVO selectUserContact(Integer userId);

    int updateUserNoticeSet(UserNoticeSetRequest requestBean);

	UserInfoVO getUserByIdNo(String idNo);

    UserLoginLogVO getUserLoginById(Integer userId);

	BankOpenAccountVO selectById(int userId);

	boolean updateBankSmsLog(BankSmsLogRequest request);

	String selectBankSmsLog(BankSmsLogRequest request);

	EvalationVO getEvalationByCountScore(short countScore);

	List<EvalationVO> getEvalationRecord();

    EvalationVO getEvalationByEvalationType(String evalationType);

    UserEvalationResultVO insertUserEvalationResult(UserEvalationRequest userEvalationRequest);

	UserInfoVO findUserInfoByCardNo(String cradNo);

	int updateUserAccountLog(BankOpenRequest request);

	BankOpenAccountVO selectByAccountId(String accountId);

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

    BankCardVO queryUserCardValid(String userId, String cardNo);

	int countUserCardValid(String userId);

	int deleteUserCardByUserId(String userId);

	int deleteUserCardByCardNo(String cardNo);

	int insertUserCard(BankCardRequest request);

	int updateUserCard(BankCardRequest request);

	int insertBindCardLog(BankCardLogRequest request);

	CorpOpenAccountRecordVO getCorpOpenAccountRecord(int userId);

    List<BankCardVO> getBankOpenAccountById(UserVO user);

    /**
     * @Description 查询开户失败原因
     * @Author sunss
     * @Date 2018/6/21 15:45
     */
	String getBankOpenAccountFiledMess(String logOrdId);

	int isCompAccount(Integer userId);

	UtmPlatVO selectUtmPlatByUtmId(String utmId);

    List<MyInviteListCustomizeVO> selectMyInviteList(MyInviteListRequest requestBean);

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
	List<AccountBankVO> getBankCardByUserId(Integer userId);
}
