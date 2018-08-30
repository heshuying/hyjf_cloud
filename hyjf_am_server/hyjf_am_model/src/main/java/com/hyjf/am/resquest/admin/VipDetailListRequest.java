/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;

import java.util.List;

/**
 * @author yaoyong
 * @version VipDetailListRequest, v0.1 2018/7/3 11:54
 */
public class VipDetailListRequest extends BasePage {
     /** vip用户id */
     public String userId;
    /** 投资标的号 */
    public String tenderNid;

    public List<BorrowTenderVO> borrowTenderList;

    public int limit;

    private int paginatorPage = 1;
    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getTenderNid() {
        return tenderNid;
    }

    public void setTenderNid(String tenderNid) {
        this.tenderNid = tenderNid;
    }

    public List<BorrowTenderVO> getBorrowTenderList() {
        return borrowTenderList;
    }

    public void setBorrowTenderList(List<BorrowTenderVO> borrowTenderList) {
        this.borrowTenderList = borrowTenderList;
    }
}
