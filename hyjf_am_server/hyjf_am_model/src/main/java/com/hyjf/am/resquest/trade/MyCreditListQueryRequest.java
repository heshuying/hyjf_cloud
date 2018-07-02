/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BaseVO;

/**
 * @Description 可转让列表
 * @Author sunss
 * @Date 2018/6/30 10:06
 */
public class MyCreditListQueryRequest extends BaseVO {


    private Integer userId;
    private String tenderTimeSort ;
    private String creditAccountSort;
    private String tenderPeriodSort ;
    private String remainDaysSort ;

    private Integer limitStart;

    private Integer limitEnd;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTenderTimeSort() {
        return tenderTimeSort;
    }

    public void setTenderTimeSort(String tenderTimeSort) {
        this.tenderTimeSort = tenderTimeSort;
    }

    public String getCreditAccountSort() {
        return creditAccountSort;
    }

    public void setCreditAccountSort(String creditAccountSort) {
        this.creditAccountSort = creditAccountSort;
    }

    public String getTenderPeriodSort() {
        return tenderPeriodSort;
    }

    public void setTenderPeriodSort(String tenderPeriodSort) {
        this.tenderPeriodSort = tenderPeriodSort;
    }

    public String getRemainDaysSort() {
        return remainDaysSort;
    }

    public void setRemainDaysSort(String remainDaysSort) {
        this.remainDaysSort = remainDaysSort;
    }

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
}
