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
	 * 取得借款API任务表
	 *
	 * @return
	 */
	public List<BorrowApicron> getBorrowApicronList(Integer apiType);

	/**
	 * 更新借款API任务表
	 * 
	 * @param borrowNid
	 * @param batchNo
	 *
	 * @return
	 * @throws Exception 
	 */
	public boolean updateBorrowApicron(BorrowApicron borrowApicron,int status) throws Exception;

	/**
	 * 取出账户信息
	 *
	 * @param userId
	 * @return
	 */
	public Account getAccountByUserId(Integer userId);

	/**
	 * 取得借款列表
	 *
	 * @return
	 */
	public List<BorrowTender> getBorrowTenderList(String borrowNid);

	/**
	 * 查询批次还款状态
	 * 
	 * @param apicron
	 * @return
	 */
	public BankCallBean batchQuery(BorrowApicron apicron);

	/***
	 * 发起还款请求
	 * 
	 * @param apicron
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map requestRepay(BorrowApicron apicron);

	/**
	 * 查询还款请求明细，并且进行更新操作
	 * 
	 * @param apicron
	 * @return
	 */
	public boolean reapyBatchDetailsUpdate(BorrowApicron apicron);

	/**
	 * 获取相应的还款记录
	 * @param id
	 * @return
	 */
	public BorrowApicron getBorrowApicron(Integer id);

	/**
	 * 查询相应的放款明细
	 * @param borrowNid
	 * @return
	 */
	List<BorrowRecover> getBorrowRecoverList(String borrowNid, BorrowApicron apicron);

	/**
	 * 退出计划
	 * @param accedeOrderId
	 */
	public void updateQuitRepayInfo(String accedeOrderId);

	/**
	 * 锁定计划
	 * @param accedeOrderId
	 */
	public void updateLockRepayInfo(String accedeOrderId);

	/**
	 * 获得优先待处理的还款任务
	 * @param borrowNid
	 */
    public BorrowApicron getRepayPeriodSort(String borrowNid);

	BorrowApicron selApiCronByPrimaryKey(int id);

	boolean updateBorrowStatus(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo) throws Exception;

	boolean updateCreditRepay(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowRecover borrowRecover, HjhDebtCreditRepay creditRepay, JSONObject assignRepayDetail)
			throws Exception;

	boolean updateCreditRepay(BorrowApicron apicron, HjhDebtCreditRepay creditRepay) throws Exception;

	boolean updateRecover(BorrowApicron apicron, Borrow borrow, BorrowRecover borrowRecover) throws Exception;

	boolean updateTenderRepayStatus(BorrowApicron apicron, Borrow borrow, BorrowRecover borrowRecover) throws Exception;

	boolean updateTenderRepay(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowRecover borrowRecover, JSONObject repayDetail, boolean isCredit, BigDecimal sumCreditCapital)
			throws Exception;
}
