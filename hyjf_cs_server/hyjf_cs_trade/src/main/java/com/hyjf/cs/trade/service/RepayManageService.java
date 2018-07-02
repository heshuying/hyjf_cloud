package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;

import java.util.List;

/**
 * @author hesy
 * @version RepayManageService, v0.1 2018/6/23 18:02
 */
public interface RepayManageService extends BaseTradeService{
    void checkForRepayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectRepayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean);

    Integer selectRepayCount(RepayListRequest requestBean);

    Integer selectOrgRepayCount(RepayListRequest requestBean);

    Integer selectOrgRepayedCount(RepayListRequest requestBean);
}
