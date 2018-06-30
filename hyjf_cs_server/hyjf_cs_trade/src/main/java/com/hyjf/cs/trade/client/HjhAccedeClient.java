/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;

import java.util.List;

/**
 * @author jijun
 * @date 20180629
 */
public interface HjhAccedeClient {

    List<HjhAccedeVO> getHjhAccedeListByAccedeOrderId(String contract_id);
}
