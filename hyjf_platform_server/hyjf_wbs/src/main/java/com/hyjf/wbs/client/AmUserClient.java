package com.hyjf.wbs.client;

import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * @author cui
 * @version AmUserClient, v0.1 2019/4/17 12:44
 */
public interface AmUserClient {

	/**
	 * 根据userId查询User
	 * @param userId
	 * @return
	 */
	UserVO findUserById(int userId);


	/**
	 * 根据userId查询开户行信息
	 * @param userId
	 * @return
	 */
	BankCardVO selectBankCardByUserId(Integer userId);


}
