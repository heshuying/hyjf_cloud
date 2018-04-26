package com.hyjf.cs.user.client;

import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.vo.user.UserInfoVO;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/4/19 12:44
 */
public interface AmBankOpenClient {

	UserInfoVO findUserInfoByCardNo(String cradNo);

	int updateUserAccountLog(BankOpenRequest request);
	
}
