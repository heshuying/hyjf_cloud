/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;

/**
 * @author liubin
 * @version BankCreditEndService, v0.1 2018/6/28 21:15
 */
public interface BankCreditEndService extends BaseService{

    /**
     * 结束债券
     * @param hjhDebtCredit
     * @param tenderAccountId
     * @param tenderAuthCode
     * @return
     */
    int insertBankCreditEndForCreditEnd(HjhDebtCredit hjhDebtCredit, String tenderAccountId, String tenderAuthCode);
}
