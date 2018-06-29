/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.HjhDebtCreditTenderRequest;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTender;

import java.util.List;

/**
 * @author jijun
 * @date 20180629
 */
public interface HjhDebtCreditTenderService {


    List<HjhDebtCreditTender> getHjhDebtCreditTenderList(HjhDebtCreditTenderRequest request);
}
