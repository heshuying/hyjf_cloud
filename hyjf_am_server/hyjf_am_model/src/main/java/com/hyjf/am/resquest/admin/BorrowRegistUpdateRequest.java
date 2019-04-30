/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowRegistUpdateRequest, v0.1 2018/7/31 19:32
 */
public class BorrowRegistUpdateRequest implements Serializable {
    /**
     * 标的
     */
    private BorrowAndInfoVO borrowVO;

    /**
     * 标的信息
     */
    private BorrowInfoVO borrowInfoVO;

    /**
     * 项目编号
     */
    private String borrowNid;

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

    /**
     * 状态-admin银行标的备案异常处理异常时用到
     */
    private int status;

    /**
     * 备案状态-admin银行标的备案异常处理异常时用到
     */
    private int registStatus;

    /**
     * 更新类型（1更新标的备案 2更新受托支付标的备案）-admin银行标的备案异常处理异常时用到
     */
    private int type;

    public BorrowAndInfoVO getBorrowVO() {
        return borrowVO;
    }

    public void setBorrowVO(BorrowAndInfoVO borrowVO) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRegistStatus() {
        return registStatus;
    }

    public void setRegistStatus(int registStatus) {
        this.registStatus = registStatus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
