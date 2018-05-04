package com.hyjf.cs.borrow.service;

import com.hyjf.am.user.dao.model.auto.BankOpenAccount;

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
	 BankOpenAccount getBankOpenAccount(Integer userId);

}
