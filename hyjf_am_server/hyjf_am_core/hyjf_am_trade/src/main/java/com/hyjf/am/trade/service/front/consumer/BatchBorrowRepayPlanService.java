package com.hyjf.am.trade.service.front.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BatchBorrowRepayPlanService extends BaseService {

	/**
	 * 更新借款API任务表
	 * 
	 * @param borrowNid
	 * @param batchNo
	 *
	 * @return
	 * @throws Exception 
	 */
	boolean updateBorrowApicron(BorrowApicron borrowApicron,int status) throws Exception;

	/**
	 * 取出账户信息
	 *
	 * @param userId
	 * @return
	 */
	Account getAccountByUserId(Integer userId);

	/**
	 * 查询批次还款状态
	 * 
	 * @param apicron
	 * @return
	 */
	BankCallBean batchQuery(BorrowApicron apicron);

	/***
	 * 发起还款请求
	 * 
	 * @param apicron
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Map requestRepay(BorrowApicron apicron);

	/**
	 * 查询还款请求明细，并且进行更新操作
	 * 
	 * @param apicron
	 * @return
	 */
	boolean reapyBatchDetailsUpdate(BorrowApicron apicron);

	/**
	 * 查询相应的放款明细
	 * @param borrowNid
	 * @return
	 */
	List<BorrowRecover> getBorrowRecoverList(String borrowNid, BorrowApicron apicron);

	BorrowApicron selApiCronByPrimaryKey(int id);

	boolean updateBorrowStatus(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo) throws Exception;

	boolean updateCreditRepay(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowRecover borrowRecover, HjhDebtCreditRepay creditRepay, JSONObject assignRepayDetail)
			throws Exception;

	boolean updateCreditRepay(BorrowApicron apicron, HjhDebtCreditRepay creditRepay) throws Exception;

	boolean updateRecover(BorrowApicron apicron, Borrow borrow, BorrowRecover borrowRecover) throws Exception;

	boolean updateTenderRepayStatus(BorrowApicron apicron, Borrow borrow, BorrowRecover borrowRecover) throws Exception;

	boolean updateTenderRepay(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowRecover borrowRecover, JSONObject repayDetail, boolean isCredit, BigDecimal sumCreditCapital, boolean creditEndAllFlag)
			throws Exception;
}
