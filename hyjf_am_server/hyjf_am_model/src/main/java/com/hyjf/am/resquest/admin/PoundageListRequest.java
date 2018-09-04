/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: sunpeikai
 * @version: PoundageListRequest, v0.1 2018/9/3 14:50
 */
@ApiModel(value = "手续费分账列表请求参数")
public class PoundageListRequest extends BasePage implements Serializable {

    @ApiModelProperty(value = "收款方用户名")
    private String userNameSer;

    @ApiModelProperty(value = "收款方姓名")
    private String realNameSer;

    @ApiModelProperty(value = "分账状态 0：未审核 1：审核通过 2：分账成功 3：分账失败 4：处理中")
    private Integer statusSer;

    @ApiModelProperty(value = "交易凭证号")
    private String nidSer;

    @ApiModelProperty(value = "请求流水号")
    private String seqNoSer;

    @ApiModelProperty(value = "分账时间段开始时间")
    private String poundageTimeStart;
    @ApiModelProperty(value = "分账时间段结束时间")
    private String poundageTimeEnd;

    @ApiModelProperty(value = "分账时间")
    private Integer addTimeSer;
    @ApiModelProperty(value = "分账时间开始时间")
    private String addTimeStart;
    @ApiModelProperty(value = "分账时间结束时间")
    private String addTimeEnd;
    /**
     * id查询条件
     */
    private Integer idSer;
    /**
     * 用户id查询条件
     */
    private Integer ledgerIdSer;
    /**
     * 分账总金额查询条件
     */
    private BigDecimal amountSer;
    /**
     * 分账数量查询条件
     */
    private Integer quantitySer;

    /**
     * 银行返回流水号查询条件
     */
    private String bankSeqNoSer;
    /**
     * 创建时间查询条件
     */
    private Integer createTimeSer;
    /**
     * 创建人id查询条件
     */
    private Integer createrSer;
    /**
     * 更新时间查询条件
     */
    private Integer updateTimeSer;
    /**
     * 修改人id查询条件
     */
    private Integer updaterSer;

    /**
     * 江西银行电子账号查询条件
     */
    private String accountSer;
    //columns END

    private int limitStart = -1;
    private int limitEnd = -1;

    public void setAddTimeSer(Integer value) {
        this.addTimeSer = value;
    }

    public Integer getAddTimeSer() {
        return this.addTimeSer;
    }

    public String getAddTimeStart() {
        return addTimeStart;
    }

    public void setAddTimeStart(String addTimeStart) {
        this.addTimeStart = addTimeStart;
    }

    public String getAddTimeEnd() {
        return addTimeEnd;
    }

    public void setAddTimeEnd(String addTimeEnd) {
        this.addTimeEnd = addTimeEnd;
    }

    public String getNidSer() {
        return nidSer;
    }

    public void setNidSer(String nidSer) {
        this.nidSer = nidSer;
    }

    public String getSeqNoSer() {
        return seqNoSer;
    }

    public String getBankSeqNoSer() {
        return bankSeqNoSer;
    }

    public void setBankSeqNoSer(String bankSeqNoSer) {
        this.bankSeqNoSer = bankSeqNoSer;
    }

    public void setSeqNoSer(String seqNoSer) {
        this.seqNoSer = seqNoSer;
    }

    public void setCreateTimeSer(Integer value) {
        this.createTimeSer = value;
    }

    public Integer getCreateTimeSer() {
        return this.createTimeSer;
    }

    public void setCreaterSer(Integer value) {
        this.createrSer = value;
    }

    public Integer getCreaterSer() {
        return this.createrSer;
    }

    public void setUpdateTimeSer(Integer value) {
        this.updateTimeSer = value;
    }

    public Integer getUpdateTimeSer() {
        return this.updateTimeSer;
    }

    public void setUpdaterSer(Integer value) {
        this.updaterSer = value;
    }

    public Integer getUpdaterSer() {
        return this.updaterSer;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getUserNameSer() {
        return userNameSer;
    }

    public void setUserNameSer(String userNameSer) {
        this.userNameSer = userNameSer;
    }

    public String getRealNameSer() {
        return realNameSer;
    }

    public void setRealNameSer(String realNameSer) {
        this.realNameSer = realNameSer;
    }

    public String getAccountSer() {
        return accountSer;
    }

    public void setAccountSer(String accountSer) {
        this.accountSer = accountSer;
    }

    public String getPoundageTimeStart() {
        return poundageTimeStart;
    }

    public void setPoundageTimeStart(String poundageTimeStart) {
        this.poundageTimeStart = poundageTimeStart;
    }

    public String getPoundageTimeEnd() {
        return poundageTimeEnd;
    }

    public void setPoundageTimeEnd(String poundageTimeEnd) {
        this.poundageTimeEnd = poundageTimeEnd;
    }

    public void setStatusSer(Integer value) {
        this.statusSer = value;
    }

    public Integer getStatusSer() {
        return this.statusSer;
    }

    public Integer getIdSer() {
        return idSer;
    }

    public void setIdSer(Integer idSer) {
        this.idSer = idSer;
    }

    public Integer getLedgerIdSer() {
        return ledgerIdSer;
    }

    public void setLedgerIdSer(Integer ledgerIdSer) {
        this.ledgerIdSer = ledgerIdSer;
    }

    public BigDecimal getAmountSer() {
        return amountSer;
    }

    public void setAmountSer(BigDecimal amountSer) {
        this.amountSer = amountSer;
    }

    public Integer getQuantitySer() {
        return quantitySer;
    }

    public void setQuantitySer(Integer quantitySer) {
        this.quantitySer = quantitySer;
    }
}
