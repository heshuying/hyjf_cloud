package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.BorrowAuthRequest;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;

import java.util.List;

/**
 * @author hesy
 * @version BorrowAuthService, v0.1 2018/7/6 14:16
 */
public interface BorrowAuthService extends BaseTradeService{
    abstract void checkForAuthList(BorrowAuthRequest requestBean);

    void checkForAuth(String borrowNid, WebViewUserVO user);

    Integer selectAuthCount(BorrowAuthRequest requestBean);

    List<BorrowAuthCustomizeVO> selectAuthList(BorrowAuthRequest requestBean);

    Integer selectAuthedCount(BorrowAuthRequest requestBean);

    List<BorrowAuthCustomizeVO> selectAuthedList(BorrowAuthRequest requestBean);
}
