package com.hyjf.admin.service;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.vo.user.UserVO;

import java.math.BigDecimal;


public interface BaseAdminService extends BaseService {

	/**
	 * 查询用户对象
	 * @param userName
	 * @return
	 */
	UserVO getUserByUserName(String userName);

	/**
	 * 获取银行账户余额
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	BigDecimal getBankBalance(Integer userId, String accountId);
}
