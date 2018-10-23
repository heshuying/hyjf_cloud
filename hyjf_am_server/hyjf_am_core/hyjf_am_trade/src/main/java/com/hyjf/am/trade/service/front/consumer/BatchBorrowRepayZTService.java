/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.Map;

/**
 * @author dxj
 * @version BatchBorrowRepayZTService.java, v0.1 2018年6月28日 下午3:06:49
 */
public interface BatchBorrowRepayZTService extends BaseService {

	Map requestRepay(BorrowApicron apicron);

	boolean updateBorrowApicron(BorrowApicron apicron, int status) throws Exception;

	BankCallBean batchQuery(BorrowApicron apicron);

	boolean reapyBatchDetailsUpdate(BorrowApicron apicron);

	BorrowApicron selApiCronByPrimaryKey(int id);

	boolean updateBorrowStatus(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo) throws Exception;

	boolean updateTenderRepay(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowRecover borrowRecover,
			JSONObject repayDetail) throws Exception;

	boolean updateRecover(BorrowApicron apicron, Borrow borrow, BorrowRecover borrowRecover) throws Exception;

	boolean updateTenderRepayStatus(BorrowApicron apicron, Borrow borrow, BorrowRecover borrowRecover) throws Exception;

	boolean updateCreditRepay(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowRecover borrowRecover,
			CreditRepay creditRepay, JSONObject assignRepayDetail) throws Exception;

}
