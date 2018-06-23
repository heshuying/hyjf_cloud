/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import java.util.List;

import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.auto.CreditTender;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLog;
import com.hyjf.am.vo.trade.CreditTenderLogVO;

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

    CreditTender selectByAssignNidAndUserId(String assignNid, Integer userId);

    boolean updateTenderCreditInfo(CreditTenderRequest request) throws Exception;

	List<BorrowCredit> getBorrowCreditList(BorrowCreditRequest request);

}
