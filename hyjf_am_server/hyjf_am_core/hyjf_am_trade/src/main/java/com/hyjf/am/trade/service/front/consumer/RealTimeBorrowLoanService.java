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
 * @version RealTimeBorrowLoanService.java, v0.1 2018年6月23日 上午10:13:18
 */
public interface RealTimeBorrowLoanService extends BaseService {

	/**
	 * 自动扣款（放款）(调用江西银行满标接口)包装的方法
	 * @param apicron
	 * @return
	 */
	BankCallBean requestLoans(BorrowApicron apicron);

	/**
	 * 自动扣款（放款）(调用江西银行满标接口)
	 * @param apicron
	 * @param map
	 * @return
	 */
	BankCallBean requestLoans(BorrowApicron apicron, Map map);

	/**
	 * 更新借款API任务表
	 *
	 * @param apicron
	 * @param status
	 * @return
	 * @throws Exception
	 */
	boolean updateBorrowApicron(BorrowApicron apicron, int status) throws Exception;

	/**
	 * 根据主键从主库查询apicron 表
	 * 这里不加select是想直接从主库查询
	 * @param id
	 * @return
	 */
	BorrowApicron selApiCronByPrimaryKey(int id);

	/**
	 * 调用银行实时放款接口成功时，更新业务数据
	 * @param borrowApicron
	 */
	void updWhenLoanSuccessed(BorrowApicron borrowApicron);

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
	Map updateTenderInfo(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BigDecimal serviceFee, BorrowTender borrowTender) throws Exception;

	/**
	 * 更新借款人相关信息
	 * @param apicron
	 * @param borrow
	 * @param borrowInfo
	 * @param recoverInterestSum
	 * @throws Exception
	 */
	void updateBorrowerInfo(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BigDecimal recoverInterestSum) throws Exception;
}
