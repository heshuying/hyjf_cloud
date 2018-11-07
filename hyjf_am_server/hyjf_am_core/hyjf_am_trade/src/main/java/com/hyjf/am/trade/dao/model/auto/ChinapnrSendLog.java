package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class ChinapnrSendLog implements Serializable {
    private Integer id;

    private String ordid;

    private String borrowNid;

    private String orddate;

    private Integer userId;

    private String msgType;

    private String remark;

    /**
     * 平台 0PC 1微官网 2Android 3IOS 4其他
     *
     * @mbggenerated
     */
    private Integer client;

    private Integer createTime;

    /**
     * 交易日期
     *
     * @mbggenerated
     */
    private Integer txDate;

    /**
     * 交易时间
     *
     * @mbggenerated
     */
    private Integer txTime;

    /**
     * 交易流水号
     *
     * @mbggenerated
     */
    private String seqNo;

    /**
     * 交易渠道
     *
     * @mbggenerated
     */
    private String channel;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getOrddate() {
        return orddate;
    }

    public void setOrddate(String orddate) {
        this.orddate = orddate == null ? null : orddate.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getTxDate() {
        return txDate;
    }

    public void setTxDate(Integer txDate) {
        this.txDate = txDate;
    }

    public Integer getTxTime() {
        return txTime;
    }

    public void setTxTime(Integer txTime) {
        this.txTime = txTime;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo == null ? null : seqNo.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }
}