package com.hyjf.cs.trade.bean.api;

import com.hyjf.cs.trade.bean.BaseBean;

/**
 * api端 标的信息请求参数
 * @author zhangyk
 * @date 2018/8/27 10:00
 */
public class ApiBorrowReqBean extends BaseBean {

    private String borrowStatus;

    private String borrowNid;

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
