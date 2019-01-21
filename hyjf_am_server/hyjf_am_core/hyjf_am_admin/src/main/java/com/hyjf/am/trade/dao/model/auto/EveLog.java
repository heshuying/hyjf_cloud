package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EveLog implements Serializable {
    private Integer id;

    /**
     * 发送方标识码
     *
     * @mbggenerated
     */
    private String forcode;

    /**
     * 系统跟踪号
     *
     * @mbggenerated
     */
    private Integer seqno;

    /**
     * 交易传输时间
     *
     * @mbggenerated
     */
    private Integer cendt;

    /**
     * 主账号
     *
     * @mbggenerated
     */
    private String cardnbr;

    /**
     * 交易金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 交易金额符号--小于零等于C；大于零等于D
     *
     * @mbggenerated
     */
    private String crflag;

    /**
     * 消息类型--提现冲正交易是0420
     *
     * @mbggenerated
     */
    private Integer msgtype;

    /**
     * 交易类型码
     *
     * @mbggenerated
     */
    private Integer proccode;

    /**
     * 订单号
     *
     * @mbggenerated
     */
    private String orderno;

    /**
     * 内部交易流水号
     *
     * @mbggenerated
     */
    private String tranno;

    /**
     * 内部保留域
     *
     * @mbggenerated
     */
    private String reserved;

    /**
     * 冲正、撤销标志 --1-已撤销/冲正空或0-正常交易
     *
     * @mbggenerated
     */
    private Integer revind;

    /**
     * 日期，记录导入数据所属日期
     *
     * @mbggenerated
     */
    private String createDay;

    /**
     * 交易类型
     *
     * @mbggenerated
     */
    private Integer transtype;

    /**
     * 创建用户
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 更新用户
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

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

    public String getForcode() {
        return forcode;
    }

    public void setForcode(String forcode) {
        this.forcode = forcode == null ? null : forcode.trim();
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
        this.cardnbr = cardnbr == null ? null : cardnbr.trim();
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
        this.crflag = crflag == null ? null : crflag.trim();
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
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getTranno() {
        return tranno;
    }

    public void setTranno(String tranno) {
        this.tranno = tranno == null ? null : tranno.trim();
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved == null ? null : reserved.trim();
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
        this.createDay = createDay == null ? null : createDay.trim();
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

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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