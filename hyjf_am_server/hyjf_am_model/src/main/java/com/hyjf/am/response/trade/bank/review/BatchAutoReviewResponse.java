package com.hyjf.am.response.trade.bank.review;

import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/13 10:01
 * @Description: BatchAutoReviewResponse
 */
public class BatchAutoReviewResponse {
    private List<BorrowCommonCustomizeVO> borrowList;

    public List<BorrowCommonCustomizeVO> getBorrowList() {
        return borrowList;
    }

    public void setBorrowList(List<BorrowCommonCustomizeVO> borrowList) {
        this.borrowList = borrowList;
    }
}
