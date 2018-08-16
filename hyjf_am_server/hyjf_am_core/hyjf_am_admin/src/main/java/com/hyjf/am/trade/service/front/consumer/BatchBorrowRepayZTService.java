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
 * @version BatchBorrowRepayZTService.java, v0.1 2018年6月28日 下午3:06:49
 */
public interface BatchBorrowRepayZTService extends BaseService {

	Map requestRepay(BorrowApicron apicron);

	boolean updateBorrowApicron(BorrowApicron apicron, int status) throws Exception;

	BankCallBean batchQuery(BorrowApicron apicron);

	boolean reapyBatchDetailsUpdate(BorrowApicron apicron);

	BorrowApicron selApiCronByPrimaryKey(int id);

}
