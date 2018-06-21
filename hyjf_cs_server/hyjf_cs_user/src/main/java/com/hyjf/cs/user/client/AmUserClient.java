package com.hyjf.cs.user.client;

import com.alibaba.fastjson.JSONObject;
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

	UserVO findUserByMobile(String mobile);

	int countUserByRecommendName(String reffer);

	UserVO register(RegisterUserRequest request);

	UserVO findUserById(int userId);

	UserInfoVO findUserInfoById(int userId);

	int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform);

	int checkMobileCode(String mobile, String verificationCode, String verificationType, String platform,
						Integer searchStatus, Integer updateStatus);

    UserVO findUserByUserNameOrMobile(String loginUserName);

	void updateLoginUser(int userId, String ip);

	HjhUserAuthVO getHjhUserAuthByUserId(Integer userId);

	BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);

	void insertUserAuthLog(HjhUserAuthLogVO hjhUserAuthLog);

	HjhUserAuthLogVO selectByExample(String orderId);

	void updateUserAuthInves(BankRequest bean);

	int updateUserById(UserVO user);

	JSONObject updatePassWd(Integer userId, String oldPW, String newPW);

	UserInfoVO findUsersInfoById(int userId);

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
	 * @return
	 */
	Integer updateUserAccountLogState(int userId, String logOrderId, int state);

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
}
