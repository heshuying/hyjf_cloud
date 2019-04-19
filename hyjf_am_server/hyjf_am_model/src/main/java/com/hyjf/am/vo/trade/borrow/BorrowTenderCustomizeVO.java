package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;
import java.util.Date;


public class BorrowTenderCustomizeVO extends BaseVO {
    //用户id
    private Integer userId;
    //标的编号
    private String borrowNid;
    //投资订单号
    private String nid;
    //智投加入订单号
    private String accedeOrderId;
    // 投资时间
    private String createTime;
    //智投编号
    private String planNid;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }
}
