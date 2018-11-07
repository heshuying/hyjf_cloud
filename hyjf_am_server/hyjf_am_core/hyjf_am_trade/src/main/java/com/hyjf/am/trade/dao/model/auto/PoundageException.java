package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PoundageException implements Serializable {
    private Integer id;

    /**
     * 分账金额
     *
     * @mbggenerated
     */
    private BigDecimal ledgerAmount;

    private Integer poundageId;

    /**
     * 手续费分账配置id
     *
     * @mbggenerated
     */
    private Integer ledgerId;

    /**
     * 收款人用户名
     *
     * @mbggenerated
     */
    private String payeeName;

    /**
     * 分账状态:0.未分账;1.已分账
     *
     * @mbggenerated
     */
    private Integer ledgerStatus;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getLedgerAmount() {
        return ledgerAmount;
    }

    public void setLedgerAmount(BigDecimal ledgerAmount) {
        this.ledgerAmount = ledgerAmount;
    }

    public Integer getPoundageId() {
        return poundageId;
    }

    public void setPoundageId(Integer poundageId) {
        this.poundageId = poundageId;
    }

    public Integer getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Integer ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName == null ? null : payeeName.trim();
    }

    public Integer getLedgerStatus() {
        return ledgerStatus;
    }

    public void setLedgerStatus(Integer ledgerStatus) {
        this.ledgerStatus = ledgerStatus;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}