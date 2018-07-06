package com.hyjf.am.trade.service.repay;

import com.hyjf.am.resquest.trade.BorrowAuthRequest;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;

import java.util.List;

/**
 * 借款人受托支付申请
 * @author hesy
 * @version BorrowAuthService, v0.1 2018/7/6 11:21
 */
public interface BorrowAuthService {

    int countBorrowNeedAuthRecordTotal(BorrowAuthRequest requestBean);

    List<BorrowAuthCustomizeVO> searchBorrowNeedAuthList(BorrowAuthRequest requestBean);

    int countBorrowAuthedRecordTotal(BorrowAuthRequest requestBean);

    List<BorrowAuthCustomizeVO> searchBorrowAuthedList(BorrowAuthRequest requestBean);
}
