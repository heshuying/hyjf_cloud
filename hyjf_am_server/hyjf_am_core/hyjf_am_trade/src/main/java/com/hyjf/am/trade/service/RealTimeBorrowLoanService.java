/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import java.util.Map;

import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * @author dxj
 * @version RealTimeBorrowLoanService.java, v0.1 2018年6月23日 上午10:13:18
 */
public interface RealTimeBorrowLoanService extends BaseService {

	BankCallBean requestLoans(BorrowApicron apicron);

	BankCallBean requestLoans(BorrowApicron apicron, Map map);

	boolean loanBatchUpdateDetails(BorrowApicron apicron, BankCallBean bean);

	boolean updateBorrowApicron(BorrowApicron apicron, int status) throws Exception;

	BorrowApicron selApiCronByPrimaryKey(int id);

}
