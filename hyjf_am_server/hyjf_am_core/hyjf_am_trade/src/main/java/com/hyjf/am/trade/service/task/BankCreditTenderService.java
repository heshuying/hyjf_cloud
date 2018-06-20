/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.dao.model.auto.CreditTenderLog;

import java.util.List;

/**
 * 银行债转异常处理
 * @author jun
 * @since 20180620
 */
public interface BankCreditTenderService {

	List<CreditTenderLog> selectCreditTenderLogs();
}
