/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.BaseVO;

/**
 * 债转列表请求参数
 *
 * @author zhangyk
 */
public class CreditListRequest extends BasePage {

    /**
     * 项目期限最小值
     */
    private  int borrowPeriodMin;
    /**
     * 项目期限最大值
     */
    private int borrowPeriodMax;
    /**
     * 项目收益最小值
     */
    private int borrowAprMin;
    /**
     * 项目收益最大值
     */
    private int borrowAprMax;
    /**
     * 折价比例排序
     */
    private String discountSort;
    /**
     * 期限排序
     */
    private String termSort;
    /**
     * 金额排序
     */
    private String capitalSort;
    /**
     * 进度排序
     */
    private String inProgressSort;

    private Integer limitStart;

    private Integer limitEnd;

    private  String creditNid;

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public int getBorrowPeriodMin() {
        return borrowPeriodMin;
    }

    public void setBorrowPeriodMin(int borrowPeriodMin) {
        this.borrowPeriodMin = borrowPeriodMin;
    }

    public int getBorrowPeriodMax() {
        return borrowPeriodMax;
    }

    public void setBorrowPeriodMax(int borrowPeriodMax) {
        this.borrowPeriodMax = borrowPeriodMax;
    }

    public int getBorrowAprMin() {
        return borrowAprMin;
    }

    public void setBorrowAprMin(int borrowAprMin) {
        this.borrowAprMin = borrowAprMin;
    }

    public int getBorrowAprMax() {
        return borrowAprMax;
    }

    public void setBorrowAprMax(int borrowAprMax) {
        this.borrowAprMax = borrowAprMax;
    }

    public String getDiscountSort() {
        return discountSort;
    }

    public void setDiscountSort(String discountSort) {
        this.discountSort = discountSort;
    }

    public String getTermSort() {
        return termSort;
    }

    public void setTermSort(String termSort) {
        this.termSort = termSort;
    }

    public String getCapitalSort() {
        return capitalSort;
    }

    public void setCapitalSort(String capitalSort) {
        this.capitalSort = capitalSort;
    }

    public String getInProgressSort() {
        return inProgressSort;
    }

    public void setInProgressSort(String inProgressSort) {
        this.inProgressSort = inProgressSort;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }
}
