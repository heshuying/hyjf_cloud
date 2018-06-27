/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditService, v0.1 2018/6/27 14:45
 */
public interface HjhDebtCreditService {

    List<HjhDebtCredit> selectHjhDebtCreditListByAccedeOrderId(String accedeOrderId);

    List<HjhDebtCredit> selectHjhDebtCreditListByOrderIdNid(String accedeOrderId,String borrowNid);
}
