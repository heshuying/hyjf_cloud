package com.hyjf.cs.trade.bean;

import java.util.List;

/**
 * @author xiehuili on 2019/3/12.
 */
public class AemsOverdueRequestBean extends BaseBean {

    /**
     * 项目编号
     */
    private List<String> borrowNids;

    /**
     *当前期数
     */
    private Integer repayPeriods;
    /**
     *还款方式
     */
    private String borrowStyle;

    public List<String> getBorrowNids() {
        return borrowNids;
    }

    public void setBorrowNids(List<String> borrowNids) {
        this.borrowNids = borrowNids;
    }

    public Integer getRepayPeriods() {
        return repayPeriods;
    }

    public void setRepayPeriods(Integer repayPeriods) {
        this.repayPeriods = repayPeriods;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }
}
