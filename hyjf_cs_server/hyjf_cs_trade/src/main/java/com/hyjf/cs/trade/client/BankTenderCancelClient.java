package com.hyjf.cs.trade.client;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;

/**
 * 投资撤销异常
 * @author jijun
 * @since 20180625
 */
public interface BankTenderCancelClient {

    List<BorrowTenderTmpVO> getBorrowTenderTmpsForTenderCancel();

    void updateBidCancelRecord(JSONObject para);

    void updateTenderCancelExceptionData(BorrowTenderTmpVO info);
}
