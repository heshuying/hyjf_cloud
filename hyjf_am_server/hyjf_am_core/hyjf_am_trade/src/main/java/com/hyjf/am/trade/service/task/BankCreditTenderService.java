/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.resquest.trade.TenderCreditRequest;
import com.hyjf.am.trade.dao.model.auto.CreditTender;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLog;
import com.hyjf.am.vo.trade.CreditTenderLogVO;

import java.util.List;

/**
 * 银行债转异常处理
 * @author jun
 * @since 20180620
 */
public interface BankCreditTenderService {

	List<CreditTenderLog> selectCreditTenderLogs();

    List<CreditTender> selectCreditTender(String assignNid);

    int updateCreditTenderLog(CreditTenderLogVO creditTenderLog);

    CreditTenderLog selectCreditTenderLogByOrderId(String logOrderId);

    List<CreditTenderLog> selectByOrderIdAndUserId(String assignOrderId, Integer userId);

    int deleteByOrderIdAndUserId(String assignOrderId, Integer userId);
}
