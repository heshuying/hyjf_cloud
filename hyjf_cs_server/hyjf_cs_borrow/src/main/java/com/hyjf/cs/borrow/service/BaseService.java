package com.hyjf.cs.borrow.service;

import com.hyjf.am.vo.user.BankOpenAccountVO;

/**
 * <p>
 * BaseService
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public interface BaseService {

	/**
	 * 获取银行开户信息
	 *
	 * @param userId
	 * @return
	 */
	BankOpenAccountVO getBankOpenAccount(Integer userId);

}
