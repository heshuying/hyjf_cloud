package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.user.BankOpenAccountVO;

/**
 * <p>
 * BaseService
 * </p>
 *
 * @author
 * @version
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
