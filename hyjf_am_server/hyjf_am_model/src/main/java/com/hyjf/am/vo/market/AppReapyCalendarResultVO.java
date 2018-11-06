/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.market;

/**
 * @author dangzw
 * @version AppReapyCalendarResultVO, v0.1 2018/7/27 11:47
 */
public class AppReapyCalendarResultVO {

    // 标签：无，加息券，体验金，代金券
    private String label;
    // 项目详情url
    private String borrowUrl;
    private String borrowTheFirst;
    private String borrowTheFirstDesc;
    private String borrowTheSecond;
    private String borrowTheSecondDesc;
    private String borrowTheThird;
    private String borrowTheThirdDesc;
    private String borrowNid;
    private String borrowName;

    /**优惠券类型*/
    private String couponType;
    // 0-月 1-明细数据  适应app的字段
    private String isMonth;
    // 月份标题
    private String month;
    //退出标记
    private int isExiting;


    public int getIsExiting() {
        return isExiting;
    }

    public void setIsExiting(int isExiting) {
        this.isExiting = isExiting;
    }

    public String getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(String isMonth) {
        this.isMonth = isMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBorrowUrl() {
        return borrowUrl;
    }

    public void setBorrowUrl(String borrowUrl) {
        this.borrowUrl = borrowUrl;
    }

    public String getBorrowTheFirst() {
        return borrowTheFirst;
    }

    public void setBorrowTheFirst(String borrowTheFirst) {
        this.borrowTheFirst = borrowTheFirst;
    }

    public String getBorrowTheFirstDesc() {
        return borrowTheFirstDesc;
    }

    public void setBorrowTheFirstDesc(String borrowTheFirstDesc) {
        this.borrowTheFirstDesc = borrowTheFirstDesc;
    }

    public String getBorrowTheSecond() {
        return borrowTheSecond;
    }

    public void setBorrowTheSecond(String borrowTheSecond) {
        this.borrowTheSecond = borrowTheSecond;
    }

    public String getBorrowTheSecondDesc() {
        return borrowTheSecondDesc;
    }

    public void setBorrowTheSecondDesc(String borrowTheSecondDesc) {
        this.borrowTheSecondDesc = borrowTheSecondDesc;
    }

    public String getBorrowTheThird() {
        return borrowTheThird;
    }

    public void setBorrowTheThird(String borrowTheThird) {
        this.borrowTheThird = borrowTheThird;
    }

    public String getBorrowTheThirdDesc() {
        return borrowTheThirdDesc;
    }

    public void setBorrowTheThirdDesc(String borrowTheThirdDesc) {
        this.borrowTheThirdDesc = borrowTheThirdDesc;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
}
