package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BatchBorrowTenderCustomizeVO;

import java.util.List;

/**
 * 投资异常掉单跑批任务
 * create by jijun 20180623
 */
public interface BatchBankInvestClient {

    List<BatchBorrowTenderCustomizeVO> queryAuthCodeBorrowTenderList();

    void insertAuthCode(List<BatchBorrowTenderCustomizeVO> list);
}
