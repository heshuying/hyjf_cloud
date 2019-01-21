package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AleveLog implements Serializable {
    /**
     * 银行号
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 银行号
     *
     * @mbggenerated
     */
    private Integer bank;

    /**
     * 电子账号
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
     * 货币代码(156)
     *
     * @mbggenerated
     */
    private Integer curNum;

    /**
     * 交易金额符号 --小于零等于C；大于零等于D
     *
     * @mbggenerated
     */
    private String crflag;

    /**
     * 入帐日期 --YYYYMMDD 账务日期
     *
     * @mbggenerated
     */
    private String valdate;

    /**
     * 交易日期 --YYYYMMDD 卡系统日期
     *
     * @mbggenerated
     */
    private String inpdate;

    /**
     * 自然日期 --YYYYMMDD 服务器日期
     *
     * @mbggenerated
     */
    private String reldate;

    /**
     * 交易时间--HH24MISSTT
     *
     * @mbggenerated
     */
    private Integer inptime;

    /**
     * 交易流水号
     *
     * @mbggenerated
     */
    private String tranno;

    /**
     * 关联交易流水号
     *
     * @mbggenerated
     */
    private Integer oriTranno;

    /**
     * 交易类型
     *
     * @mbggenerated
     */
    private Integer transtype;

    /**
     * 交易描述
     *
     * @mbggenerated
     */
    private String desline;

    /**
     * 交易后余额
     *
     * @mbggenerated
     */
    private BigDecimal currBal;

    /**
     * 对手交易帐号
     *
     * @mbggenerated
     */
    private String forcardnbr;

    /**
     * 冲正、撤销标志 --1-已撤销/冲正空或0-正常交易
     *
     * @mbggenerated
     */
    private Integer revind;

    /**
     * 交易标识 --1-调账该字段为1时，标识该笔流水为行内调账交易
     *
     * @mbggenerated
     */
    private String accchg;

    /**
     * 系统跟踪号
     *
     * @mbggenerated
     */
    private Integer seqno;

    /**
     * 原交易流水号
     *
     * @mbggenerated
     */
    private Integer oriNum;

    /**
     * 保留域
     *
     * @mbggenerated
     */
    private String resv;

    /**
     * 日期，记录导入数据所属日期
     *
     * @mbggenerated
     */
    private String createDay;

    /**
     * 创建用户
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新用户
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 是否处理 0未处理 1 已处理
     *
     * @mbggenerated
     */
    private Integer updFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBank() {
        return bank;
    }

    public void setBank(Integer bank) {
        this.bank = bank;
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

    public Integer getCurNum() {
        return curNum;
    }

    public void setCurNum(Integer curNum) {
        this.curNum = curNum;
    }

    public String getCrflag() {
        return crflag;
    }

    public void setCrflag(String crflag) {
        this.crflag = crflag == null ? null : crflag.trim();
    }

    public String getValdate() {
        return valdate;
    }

    public void setValdate(String valdate) {
        this.valdate = valdate == null ? null : valdate.trim();
    }

    public String getInpdate() {
        return inpdate;
    }

    public void setInpdate(String inpdate) {
        this.inpdate = inpdate == null ? null : inpdate.trim();
    }

    public String getReldate() {
        return reldate;
    }

    public void setReldate(String reldate) {
        this.reldate = reldate == null ? null : reldate.trim();
    }

    public Integer getInptime() {
        return inptime;
    }

    public void setInptime(Integer inptime) {
        this.inptime = inptime;
    }

    public String getTranno() {
        return tranno;
    }

    public void setTranno(String tranno) {
        this.tranno = tranno == null ? null : tranno.trim();
    }

    public Integer getOriTranno() {
        return oriTranno;
    }

    public void setOriTranno(Integer oriTranno) {
        this.oriTranno = oriTranno;
    }

    public Integer getTranstype() {
        return transtype;
    }

    public void setTranstype(Integer transtype) {
        this.transtype = transtype;
    }

    public String getDesline() {
        return desline;
    }

    public void setDesline(String desline) {
        this.desline = desline == null ? null : desline.trim();
    }

    public BigDecimal getCurrBal() {
        return currBal;
    }

    public void setCurrBal(BigDecimal currBal) {
        this.currBal = currBal;
    }

    public String getForcardnbr() {
        return forcardnbr;
    }

    public void setForcardnbr(String forcardnbr) {
        this.forcardnbr = forcardnbr == null ? null : forcardnbr.trim();
    }

    public Integer getRevind() {
        return revind;
    }

    public void setRevind(Integer revind) {
        this.revind = revind;
    }

    public String getAccchg() {
        return accchg;
    }

    public void setAccchg(String accchg) {
        this.accchg = accchg == null ? null : accchg.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public Integer getOriNum() {
        return oriNum;
    }

    public void setOriNum(Integer oriNum) {
        this.oriNum = oriNum;
    }

    public String getResv() {
        return resv;
    }

    public void setResv(String resv) {
        this.resv = resv == null ? null : resv.trim();
    }

    public String getCreateDay() {
        return createDay;
    }

    public void setCreateDay(String createDay) {
        this.createDay = createDay == null ? null : createDay.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getUpdFlag() {
        return updFlag;
    }

    public void setUpdFlag(Integer updFlag) {
        this.updFlag = updFlag;
    }
}