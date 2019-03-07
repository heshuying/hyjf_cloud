package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.*;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.BaseAdminService;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
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
		// 账户可用余额
		BigDecimal balance = BigDecimal.ZERO;
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 版本号
		bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);// 交易代码
		bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
		bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
		bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
		bean.setAccountId(accountId);// 电子账号
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));// 订单号
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
		bean.setLogUserId(String.valueOf(userId));
		bean.setLogRemark("电子账户余额查询");
		bean.setLogClient(0);// 平台
		try {
			BankCallBean resultBean = BankCallUtils.callApiBg(bean);
			if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
				balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return balance;
	}
}
