/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.account.impl;

import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.customize.BatchBorrowTenderCustomize;
import com.hyjf.am.trade.service.front.account.BankInvestService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 投資掉單处理
 * @author jun
 * @since 20180623
 */
@Service
public class BankInvestExceptionServiceImpl extends BaseServiceImpl implements BankInvestService {

	private static final Logger logger = LoggerFactory.getLogger(BankInvestExceptionServiceImpl.class);

	@Autowired
	private SystemConfig systemConfig;

	 /**
	 * 查询出出借表authcode为空的记录
	 * @return
	 */
	@Override
	public List<BatchBorrowTenderCustomize> queryAuthCodeBorrowTenderList() {
		return this.batchBorrowTenderExceptionCustomizeMapper.queryAuthCodeBorrowTenderList();
	}

	/**
	 * 处理出借掉单
	 * @param list
	 */
	@Override
	public void insertAuthCode(List<BatchBorrowTenderCustomize> list) {
		if (CollectionUtils.isNotEmpty(list)){
			logger.info("开始处理掉单的出借,处理件数:[" + list.size() + "].");
			for (BatchBorrowTenderCustomize batchBorrowTenderCustomize : list) {
				// 获得原出借的订单号
				String orderid = batchBorrowTenderCustomize.getNid();
				logger.info("开始处理出借订单号:[" + orderid + "].");
				// 用户电子账户号
				String accountId = batchBorrowTenderCustomize.getAccountNo();
				// 出借用户ID
				String userId = String.valueOf(batchBorrowTenderCustomize.getUserId());
				// 根据相应信息接口查询订单
				BankCallBean bean = this.queryBorrowTenderList(accountId, orderid, userId);
				if (bean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
					if (StringUtils.isNoneBlank(bean.getAuthCode())) {
						// 将返回的authcode放入出借表中
						BorrowTender record = this.borrowTenderMapper.selectByPrimaryKey(batchBorrowTenderCustomize.getId());
						record.setAuthCode(bean.getAuthCode());
						borrowTenderMapper.updateByPrimaryKeySelective(record);
						logger.info("出借异常处理成功,出借记录ID:[" + batchBorrowTenderCustomize.getId() + "],出借订单号:[" + orderid + "],银行返回授权码:[" + bean.getAuthCode() + "].");
					}
				}



			}
		}
	}



	private BankCallBean queryBorrowTenderList(String accountId, String orgOrderId, String userId) {
		BankCallBean bean = new BankCallBean();
		bean.setVersion(ClientConstants.VERSION_10);// 接口版本号
		bean.setTxCode(ClientConstants.TXCODE_BID_APPLY_QUERY);// 消息类型
		bean.setInstCode(systemConfig.getBankInstcode());// 机构代码
		bean.setBankCode(systemConfig.getBankBankcode());
		bean.setTxDate(GetOrderIdUtils.getTxDate());
		bean.setTxTime(GetOrderIdUtils.getTxTime());
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
		bean.setChannel(BankCallConstant.CHANNEL_PC);
		bean.setAccountId(accountId);// 电子账号
		bean.setOrgOrderId(orgOrderId);
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.parseInt(userId)));
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
		bean.setLogUserId(userId);
		bean.setLogRemark("出借人投标申请查询");
		// 调用接口
		return BankCallUtils.callApiBg(bean);

	}
}
