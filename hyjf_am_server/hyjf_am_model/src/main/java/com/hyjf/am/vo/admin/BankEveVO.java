/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: sunpeikai
 * @version: BindLogVO, v0.1 2018/7/5 15:41
 */
public class BankEveVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    @ApiModelProperty(value = "发送方标识码")
    private String forcode;

    @ApiModelProperty(value = "系统跟踪号")
    private Integer seqno;

    @ApiModelProperty(value = "交易传输时间")
    private Integer cendt;

    @ApiModelProperty(value = "交易传输时间字符串")
    private String cendtString; //交易传输时间

    @ApiModelProperty(value = "主账号")
    private String cardnbr;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "交易金额符号--小于零等于C；大于零等于D")
    private String crflag;

    @ApiModelProperty(value = "消息类型--提现冲正交易是0420")
    private Integer msgtype;

    @ApiModelProperty(value = "交易类型码")
    private Integer proccode;

    @ApiModelProperty(value = "订单号")
    private String orderno;

    @ApiModelProperty(value = "内部交易流水号")
    private String tranno;

    @ApiModelProperty(value = "内部保留域")
    private String reserved;

    @ApiModelProperty(value = "冲正、撤销标志 --1-已撤销/冲正空或0-正常交易")
    private Integer revind;

    @ApiModelProperty(value = "日期，记录导入数据所属日期")
    private String createDay;

    @ApiModelProperty(value = "交易类型")
    private Integer transtype;

    @ApiModelProperty(value = "创建用户")
    private Integer createUserId;

    @ApiModelProperty(value = "创建时间")
    private Integer createTime;

    @ApiModelProperty(value = "更新用户")
    private Integer updateUserId;

    @ApiModelProperty(value = "更新时间")
    private Integer updateTime;

    @ApiModelProperty(value = "删除标识")
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

    public String getCendtString() {
        return cendtString;
    }

    public void setCendtString(String cendtString) {
        this.cendtString = cendtString;
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
}
