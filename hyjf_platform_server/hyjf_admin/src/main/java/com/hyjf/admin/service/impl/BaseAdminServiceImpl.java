package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.*;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.BaseAdminService;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.user.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class BaseAdminServiceImpl extends BaseServiceImpl implements BaseAdminService {

	public final Logger logger = LoggerFactory.getLogger(BaseAdminServiceImpl.class);

	@Autowired
	public AmAdminClient amAdminClient;

	@Autowired
	public AmUserClient amUserClient;

	@Autowired
	public AmTradeClient amTradeClient;

	@Autowired
	public AmConfigClient amConfigClient;

	@Autowired
	public CsMessageClient csMessageClient;

	@Autowired
	public SystemConfig systemConfig;

	@Override
	public UserVO getUserByUserName(String userName) {
		UserVO user = amUserClient.getUserByUserName(userName);
		return user;
	}

	public UserVO getUsersById(Integer userId) {
		UserVO userVO = amUserClient.findUserById(userId);
		return userVO;
	}

	/**
	 * 根据borrowNid获取borrow
	 * @param borrowNid
	 * @return
	 */
	@Override
	public BorrowAndInfoVO getBorrowByNid(String borrowNid) {
		return amTradeClient.selectBorrowByNid(borrowNid);
	}

	/**
	 * 获取银行账户余额
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public BigDecimal getBankBalance(Integer userId, String accountId) {
		return null;
	}
}
