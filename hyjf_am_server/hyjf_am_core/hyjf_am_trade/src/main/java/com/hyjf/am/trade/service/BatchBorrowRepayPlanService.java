/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import java.util.Map;

import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * @author dxj
 * @version BatchBorrowRepayPlanService.java, v0.1 2018年6月23日 下午12:03:33
 */
public interface BatchBorrowRepayPlanService extends BaseService {


	Map requestRepay(BorrowApicron apicron);

	boolean updateBorrowApicron(BorrowApicron apicron, int status) throws Exception;

	BankCallBean batchQuery(BorrowApicron apicron);

	boolean updateBatchDetailsQuery(BorrowApicron apicron);

	void updateQuitRepayInfo(String accedeOrderId);

    void updateLockRepayInfo(String accedeOrderId);

}
