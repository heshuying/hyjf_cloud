package com.hyjf.am.trade.dao.model.customize;

/**
 * @author lisheng
 * @version ContentEventsCustomize, v0.1 2018/8/2 13:56
 */

public class ContentEventsCustomize extends Event{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // 传参创建时间范围
    protected Integer startCreate;

    protected Integer endCreate;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public Integer getStartCreate() {
        return startCreate;
    }

    public void setStartCreate(Integer startCreate) {
        this.startCreate = startCreate;
    }

    public Integer getEndCreate() {
        return endCreate;
    }

    public void setEndCreate(Integer endCreate) {
        this.endCreate = endCreate;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
