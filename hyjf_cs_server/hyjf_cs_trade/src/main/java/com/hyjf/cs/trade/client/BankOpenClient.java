package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.am.vo.user.UserInfoVO;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
public interface BankOpenClient {

	UserInfoVO findUserInfoByCardNo(String cradNo);

	int updateUserAccountLog(BankOpenRequest request);

	BankOpenAccountVO selectById(int userId);

	BankOpenAccountVO selectByAccountId(String accountId);

    UserEvalationResultVO selectUserEvalationResultByUserId(Integer userId);

}
