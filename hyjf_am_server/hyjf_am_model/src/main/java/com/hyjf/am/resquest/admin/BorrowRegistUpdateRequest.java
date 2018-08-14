/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowRegistUpdateRequest, v0.1 2018/7/31 19:32
 */
public class BorrowRegistUpdateRequest implements Serializable {
    /**
     * 标的
     */
    private BorrowVO borrowVO;

    /**
     * 标的信息
     */
    private BorrowInfoVO borrowInfoVO;

    /**
     * 借款人电子账户
     */
    private String AccountId;

    /**
     * 当前登录用户ID
     */
    private String currUserId;

    /**
     * 当前登录用户名
     */
    private String currUserName;

    /**
     * 担保机构电子账户
     */
    private String bailAccountId;

    public BorrowVO getBorrowVO() {
        return borrowVO;
    }

    public void setBorrowVO(BorrowVO borrowVO) {
        this.borrowVO = borrowVO;
    }

    public BorrowInfoVO getBorrowInfoVO() {
        return borrowInfoVO;
    }

    public void setBorrowInfoVO(BorrowInfoVO borrowInfoVO) {
        this.borrowInfoVO = borrowInfoVO;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getCurrUserId() {
        return currUserId;
    }

    public void setCurrUserId(String currUserId) {
        this.currUserId = currUserId;
    }

    public String getCurrUserName() {
        return currUserName;
    }

    public void setCurrUserName(String currUserName) {
        this.currUserName = currUserName;
    }

    public String getBailAccountId() {
        return bailAccountId;
    }

    public void setBailAccountId(String bailAccountId) {
        this.bailAccountId = bailAccountId;
    }
}
