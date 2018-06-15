/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

/**
 * @author fuqiang
 * @version BorrowFinmanNewChargeRequest, v0.1 2018/6/13 16:17
 */
public class BorrowFinmanNewChargeRequest {
    private String borrowClass;

    private String instCode;

    private Integer assetType;

    private String queryBorrowStyle;

    private Integer borrowPeriod;

    public String getBorrowClass() {
        return borrowClass;
    }

    public void setBorrowClass(String borrowClass) {
        this.borrowClass = borrowClass;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public String getQueryBorrowStyle() {
        return queryBorrowStyle;
    }

    public void setQueryBorrowStyle(String queryBorrowStyle) {
        this.queryBorrowStyle = queryBorrowStyle;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }
}
