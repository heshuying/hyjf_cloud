package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.BankCallBeanVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.borrow.BatchBorrowTenderCustomizeVO;

import java.util.List;

/**
 * 江西银行提现掉单异常处理Client
 * create by jijun 20180614
 */
public interface BatchBankInvestClient {

    List<BatchBorrowTenderCustomizeVO> queryAuthCodeBorrowTenderList();

    void insertAuthCode(List<BatchBorrowTenderCustomizeVO> list);
}
