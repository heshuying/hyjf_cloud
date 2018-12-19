package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PoundageDetail implements Serializable {
    private Integer id;

    /**
     * 订单号
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 项目编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 项目类型
     *
     * @mbggenerated
     */
    private String borrowType;

    /**
     * 出借人用户名
     *
     * @mbggenerated
     */
    private String usernname;

    /**
     * 分账类型
     *
     * @mbggenerated
     */
    private String poundageType;

    /**
     * 分账来源
     *
     * @mbggenerated
     */
    private String source;

    /**
     * 推荐人公司
     *
     * @mbggenerated
     */
    private String inviteRegionName;

    /**
     * 推荐人id
     *
     * @mbggenerated
     */
    private Integer inviteUserId;

    /**
     * 分账金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 实际分账时间段
     *
     * @mbggenerated
     */
    private Integer ledgerTime;

    /**
     * 手续费分账配置id
     *
     * @mbggenerated
     */
    private Integer ledgerId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType == null ? null : borrowType.trim();
    }

    public String getUsernname() {
        return usernname;
    }

    public void setUsernname(String usernname) {
        this.usernname = usernname == null ? null : usernname.trim();
    }

    public String getPoundageType() {
        return poundageType;
    }

    public void setPoundageType(String poundageType) {
        this.poundageType = poundageType == null ? null : poundageType.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getInviteRegionName() {
        return inviteRegionName;
    }

    public void setInviteRegionName(String inviteRegionName) {
        this.inviteRegionName = inviteRegionName == null ? null : inviteRegionName.trim();
    }

    public Integer getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(Integer inviteUserId) {
        this.inviteUserId = inviteUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getLedgerTime() {
        return ledgerTime;
    }

    public void setLedgerTime(Integer ledgerTime) {
        this.ledgerTime = ledgerTime;
    }

    public Integer getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Integer ledgerId) {
        this.ledgerId = ledgerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}