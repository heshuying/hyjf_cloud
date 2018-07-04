/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.TenderAgreementRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;

import java.util.List;

/**
 * @author jijun
 * @date 20180629
 */
public interface BorrowRecoverClient {


    BorrowRecoverVO selectBorrowRecoverByTenderNid(String tenderAgreementID);

    BorrowRecoverVO selectBorrowRecoverById(Integer id);

    BorrowRecoverVO selectBorrowRecoverByNid(String nid);

    List<BorrowRecoverVO> selectBorrowRecoverByBorrowNid(String borrowNid);

    void updateBorrowRecover(BorrowRecoverVO borrowRecover);
}
