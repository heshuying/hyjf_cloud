package com.hyjf.cs.trade.client;

import java.util.List;

import com.hyjf.am.resquest.trade.TenderCancelRequest;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;

/**
 * 投资撤销异常
 * @author jijun
 * @since 20180625
 */
public interface BankTenderCancelClient {

    List<BorrowTenderTmpVO> getBorrowTenderTmpsForTenderCancel();

    boolean updateBidCancelRecord(TenderCancelRequest para);

    boolean updateTenderCancelExceptionData(BorrowTenderTmpVO info);
}
