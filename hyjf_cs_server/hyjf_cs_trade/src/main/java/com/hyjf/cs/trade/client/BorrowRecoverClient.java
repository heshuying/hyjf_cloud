/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.TenderAgreementRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;

/**
 * @author jijun
 * @date 20180629
 */
public interface BorrowRecoverClient {


    BorrowRecoverVO selectBorrowRecoverByTenderNid(String tenderAgreementID);

    void updateBorrowRecover(BorrowRecoverVO borrowRecover);
}
