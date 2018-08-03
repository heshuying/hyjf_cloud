/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: sunpeikai
 * @version: BindLogVO, v0.1 2018/7/5 15:41
 */
public class BankAleveVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer bank;

    private String cardnbr;

    private BigDecimal amount;

    private Integer curNum;

    private String crflag;

    private String valdate;

    private String inpdate;

    private String reldate;

    private Integer inptime;

    private String tranno;

    private Integer oriTranno;

    private Integer transtype;

    private String desline;

    private BigDecimal currBal;

    private String forcardnbr;

    private Integer revind;

    private String accchg;

    private Integer seqno;

    private Integer oriNum;

    private String resv;

    private String createDay;

    private Integer createUserId;

    private Integer createTime;

    private Integer updateUserId;

    private Integer updateTime;

    private Integer delFlg;

    private Integer updFlg;


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
        this.valdate = valdate;
    }

    public String getInpdate() {
        return inpdate;
    }

    public void setInpdate(String inpdate) {
        this.inpdate = inpdate;
    }

    public String getReldate() {
        return reldate;
    }

    public void setReldate(String reldate) {
        this.reldate = reldate;
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

    /**
     * updFlg
     * @return the updFlg
     */

    public Integer getUpdFlg() {
        return updFlg;
    }

    /**
     * @param updFlg the updFlg to set
     */

    public void setUpdFlg(Integer updFlg) {
        this.updFlg = updFlg;
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
}
