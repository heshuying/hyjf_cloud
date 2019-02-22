package com.hyjf.am.trade.dao.model.customize;

import java.math.BigDecimal;

/**
 * Created by zdj on 2018/7/22.
 */
public class EveLogCustomize {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -749099382246115294L;

    private Integer id;

    private String forcode;

    private Integer seqno;

    private Integer cendt;

    private String cendtString;

    private String cardnbr;

    private BigDecimal amount;

    private String crflag;

    private Integer msgtype;

    private Integer proccode;

    private String orderno;

    private String tranno;

    private String reserved;

    private Integer revind;

    private String createDay;

    private Integer transtype;

    private Integer createUserId;

    private Integer createTime;

    private Integer updateUserId;

    private Integer updateTime;

    private Integer delFlg;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getForcode() {
        return forcode;
    }

    public void setForcode(String forcode) {
        this.forcode = forcode;
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public Integer getCendt() {
        return cendt;
    }

    public void setCendt(Integer cendt) {
        this.cendt = cendt;
    }

    public String getCardnbr() {
        return cardnbr;
    }

    public void setCardnbr(String cardnbr) {
        this.cardnbr = cardnbr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCrflag() {
        return crflag;
    }

    public void setCrflag(String crflag) {
        this.crflag = crflag;
    }

    public Integer getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(Integer msgtype) {
        this.msgtype = msgtype;
    }

    public Integer getProccode() {
        return proccode;
    }

    public void setProccode(Integer proccode) {
        this.proccode = proccode;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getTranno() {
        return tranno;
    }

    public void setTranno(String tranno) {
        this.tranno = tranno;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public Integer getRevind() {
        return revind;
    }

    public void setRevind(Integer revind) {
        this.revind = revind;
    }

    public String getCreateDay() {
        return createDay;
    }

    public void setCreateDay(String createDay) {
        this.createDay = createDay;
    }

    public Integer getTranstype() {
        return transtype;
    }

    public void setTranstype(Integer transtype) {
        this.transtype = transtype;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public String getCendtString() {
        return cendtString;
    }

    public void setCendtString(String cendtString) {
        this.cendtString = cendtString;
    }
}
