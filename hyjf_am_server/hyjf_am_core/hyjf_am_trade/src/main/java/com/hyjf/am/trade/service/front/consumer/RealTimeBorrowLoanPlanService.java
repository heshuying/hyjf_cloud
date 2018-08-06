/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.consumer;

import java.util.Map;

import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * @author dxj
 * @version RealTimeBorrowLoanPlanService.java, v0.1 2018年6月23日 上午11:02:11
 */
public interface RealTimeBorrowLoanPlanService extends BaseService {

	BankCallBean requestLoans(BorrowApicron apicron);

	BankCallBean requestLoans(BorrowApicron apicron, Map map);

	boolean planLoanBatchUpdateDetails(BorrowApicron apicron, BankCallBean bean);

	boolean updateBorrowApicron(BorrowApicron apicron, int status) throws Exception;

	BorrowApicron selApiCronByPrimaryKey(int id);

}
