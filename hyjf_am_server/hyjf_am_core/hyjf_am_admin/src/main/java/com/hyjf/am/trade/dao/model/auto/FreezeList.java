package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FreezeList implements Serializable {
    private Integer id;

    private Integer userId;

    private String ordid;

    private String borrowNid;

    private BigDecimal amount;

    private String respcode;

    private String usrcustid;

    private String trxid;

    private Integer xfrom;

    private Date createTime;

    private Integer unfreezeManual;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) {
        this.ordid = ordid == null ? null : ordid.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode == null ? null : respcode.trim();
    }

    public String getUsrcustid() {
        return usrcustid;
    }

    public void setUsrcustid(String usrcustid) {
        this.usrcustid = usrcustid == null ? null : usrcustid.trim();
    }

    public String getTrxid() {
        return trxid;
    }

    public void setTrxid(String trxid) {
        this.trxid = trxid == null ? null : trxid.trim();
    }

    public Integer getXfrom() {
        return xfrom;
    }

    public void setXfrom(Integer xfrom) {
        this.xfrom = xfrom;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUnfreezeManual() {
        return unfreezeManual;
    }

    public void setUnfreezeManual(Integer unfreezeManual) {
        this.unfreezeManual = unfreezeManual;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}