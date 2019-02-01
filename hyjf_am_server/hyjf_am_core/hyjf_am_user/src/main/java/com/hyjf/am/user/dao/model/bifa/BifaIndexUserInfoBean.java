/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.model.bifa;

import java.io.Serializable;

/**
 * @author jun
 * @version BifaIndexUserInfoBean, v0.1 2018/12/11 14:55
 */
public class BifaIndexUserInfoBean implements Serializable {
    /**
     * 标的编号
     */
    private Integer userId;
    /**
     * 标的编号
     */
    private String borrowNid;
    /**
     * 真实姓名
     */
    private String trueName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 注册时间
     */
    private String regDate;

    /**
     * 贷款开始时间
     */
    private String borrowBeginDate;

    /**
     * 贷款结束时间
     */
    private String borrowEndDate;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getBorrowBeginDate() {
        return borrowBeginDate;
    }

    public void setBorrowBeginDate(String borrowBeginDate) {
        this.borrowBeginDate = borrowBeginDate;
    }

    public String getBorrowEndDate() {
        return borrowEndDate;
    }

    public void setBorrowEndDate(String borrowEndDate) {
        this.borrowEndDate = borrowEndDate;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowNid() {
        return borrowNid;
    }
}
