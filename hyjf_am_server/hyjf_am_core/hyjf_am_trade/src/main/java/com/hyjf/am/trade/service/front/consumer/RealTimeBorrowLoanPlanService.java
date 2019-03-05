/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.consumer;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author dxj
 * @version RealTimeBorrowLoanPlanService.java, v0.1 2018年6月23日 上午11:02:11
 */
public interface RealTimeBorrowLoanPlanService extends BaseService {

	BankCallBean requestLoans(BorrowApicron apicron);

	BankCallBean requestLoans(BorrowApicron apicron, Map map);

	boolean updateBorrowApicron(BorrowApicron apicron, int status) throws Exception;

	BorrowApicron selApiCronByPrimaryKey(int id);

	/**
	 * 调用银行实时放款接口成功时，更新业务数据
	 * @param borrowApicron
	 */
	void updWhenPlanLoanSuccessed(BorrowApicron borrowApicron);

	/**
	 * 放款成功后，更新每笔投资相关信息
	 * @param apicron
	 * @param borrow
	 * @param borrowInfo
	 * @param serviceFee
	 * @param borrowTender
	 * @return
	 * @throws Exception
	 */
	void updateTenderInfo(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BigDecimal serviceFee, BorrowTender borrowTender) throws Exception;


	/**
	 * 更新借款人相关信息
	 * @param apicron
	 * @param borrow
	 * @param borrowInfo
	 * @throws Exception
	 */
	void updateBorrowerInfo(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo) throws Exception;
}
