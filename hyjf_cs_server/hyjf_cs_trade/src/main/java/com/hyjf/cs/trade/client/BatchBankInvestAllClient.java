package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.BorrowTenderTmpRequest;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;

import java.util.List;

/**
 * 投资异常全部掉单跑批任务
 * create by jijun 20180623
 */
public interface BatchBankInvestAllClient {

    List<BorrowTenderTmpVO> getBorrowTenderTmpList();

    Boolean updateTenderStart(BorrowTenderTmpRequest request);
}
