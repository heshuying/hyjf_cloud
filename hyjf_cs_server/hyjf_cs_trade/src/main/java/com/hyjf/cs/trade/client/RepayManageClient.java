package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequestUpdateRequest;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;

import java.util.List;

/**
 * @author hesy
 * @version RepayManageClient, v0.1 2018/6/19 15:33
 */
public interface RepayManageClient {
    List<RepayListCustomizeVO> repayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> orgRepayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> orgRepayedList(RepayListRequest requestBean);

    int repayCount(RepayListRequest requestBean);

    int orgRepayCount(RepayListRequest requestBean);

    int orgRepayedCount(RepayListRequest requestBean);

    Boolean repayRequestUpdate(RepayRequestUpdateRequest requestBean);
}
