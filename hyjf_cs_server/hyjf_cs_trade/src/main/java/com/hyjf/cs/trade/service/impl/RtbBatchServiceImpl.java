package com.hyjf.cs.trade.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hyjf.am.resquest.trade.RtbIncreaseRepayRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.trade.client.AmBankOpenClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.BorrowApicronClient;
import com.hyjf.cs.trade.client.BorrowClient;
import com.hyjf.cs.trade.service.RtbBatchService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

/**
 * @author xiasq
 * @version RtbBatchServiceImpl, v0.1 2018/6/19 15:52
 */
@Service
public class RtbBatchServiceImpl implements RtbBatchService {
	private static final Logger logger = LoggerFactory.getLogger(RtbBatchServiceImpl.class);

	@Autowired
	BorrowApicronClient borrowApicronClient;
	@Autowired
	BorrowClient borrowClient;
	@Autowired
	AmTradeClient amTradeClient;
	@Autowired
	AmBankOpenClient amBankOpenClient;

	@Value("${hyjf.bank.merrp.account}")
	private String merrpAccount;

	@Override
	public void execute() {
		// 取得未放款任务
		List<BorrowApicronVO> listApicron = borrowApicronClient.getBorrowApicronList(0, 1);
		if (!CollectionUtils.isEmpty(listApicron)) {
			// 循环进行还款
			for (BorrowApicronVO borrowApicron : listApicron) {
				// 借款编号
				String borrowNid = borrowApicron.getBorrowNid();
				// 借款人ID
				Integer borrowUserId = borrowApicron.getUserId();
				// 从redis取开户等状态 todo key 是啥
				UserVO userVO = RedisUtils.getObj(borrowApicron.getUserId() + "", UserVO.class);
				// 判断未开户的错误
				if(userVO == null || StringUtils.isBlank(userVO.getBankOpenAccount())){
					throw new RuntimeException("用户不存在或者未开户...");
				}

				// 取得借款详情
				BorrowVO borrow = borrowClient.selectBorrowByNid(borrowNid);
				if (borrow == null) {
					throw new RuntimeException("借款详情不存在。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
				}

				// 取得融通宝还款金额
				BigDecimal repayAccount = amTradeClient.selectRtbRepayAmount(borrowNid, borrow.getBorrowStyle(),
						borrowApicron.getPeriodNow());

				// 查询公司子账户金额
				BigDecimal account = this.selectCompanyAccount();
				// 还款金额大于公司子账户可用余额
				if (repayAccount.compareTo(account) > 0) {
					logger.info("公司子账户可用金额不足。" + "[借款编号：" + borrowNid + "]，" + "[可用余额：" + account + "]，" + "[还款金额："
							+ repayAccount + "]");
					throw new RuntimeException("公司子账户可用金额不足。" + "[借款编号：" + borrowNid + "]，" + "[可用余额：" + account + "]，"
							+ "[还款金额：" + repayAccount + "]");
				}
				amTradeClient.rtbIncreaseReapy(borrowApicron, userVO.getBankOpenAccount(), merrpAccount);
 			}
		}

	}

	/**
	 * 检索公司子账户可用余额
	 *
	 * @Title selectCompanyAccount
	 * @return
	 */
	private BigDecimal selectCompanyAccount() {
		// 账户可用余额
		BigDecimal balance = BigDecimal.ZERO;

		logger.info("公司红包账户： ｛｝", merrpAccount);
		// 查询商户子账户余额
		BankOpenAccountVO bankOpenAccountVO = amBankOpenClient.selectByAccountId(merrpAccount);

		if (bankOpenAccountVO == null)
			throw new RuntimeException("公司红包账户未开户...");

		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 版本号
		bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);// 交易代码
		bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
		bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
		bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
		bean.setAccountId(merrpAccount);// 电子账号
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(bankOpenAccountVO == null ? 0 : bankOpenAccountVO.getUserId()));// 订单号
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
		bean.setLogUserId(bankOpenAccountVO == null ? "0" : String.valueOf(bankOpenAccountVO.getUserId()));
		bean.setLogClient(0);// 平台
		try {
			BankCallBean resultBean = BankCallUtils.callApiBg(bean);
			if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
				// 账户余额
				balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
			}
		} catch (Exception e) {
			logger.error("调用银行接口失败...", e);
		}
		return balance;
	}

}
