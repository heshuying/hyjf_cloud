package com.hyjf.cs.user.client;

import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * @author xiasq
 * @version BankOpenClient, v0.1 2018/4/19 12:44
 */
public interface BankOpenClient {

	UserInfoVO findUserInfoByCardNo(String cradNo);

	int updateUserAccountLog(BankOpenRequest request);

	BankOpenAccountVO selectById(int userId);

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

	CorpOpenAccountRecordVO getCorpOpenAccountRecord(int userId);
}
