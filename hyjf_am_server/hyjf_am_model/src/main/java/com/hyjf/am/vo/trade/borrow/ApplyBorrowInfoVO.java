package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version ApplyAgreementVO, v0.1 2018/8/10 14:18
 * @Author: Zha Daojian
 */
public class ApplyBorrowInfoVO extends BaseVO implements Serializable {


    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "标的号")
    private String borrowNid;

    @ApiModelProperty(value = "智投编号")
    private String planNid;

    @ApiModelProperty(value = "标的期限")
    private Integer borrowPeriod;

    @ApiModelProperty(value = "资产来源")
    private String borrowProjectSource;

    @ApiModelProperty(value = "借款人")
    private String borrowUserName;

    @ApiModelProperty(value = "借款金额")
    private BigDecimal account;

    @ApiModelProperty(value = "项目状态(0:备案中,1:初审中,2:投资中,3:复审中(满标),4:还款中,5:已还款,6:流标,7:受托,8:逾期中)")
    private Integer status;

    @ApiModelProperty(value = "放款时间")
    private String recoverLastTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowProjectSource() {
        return borrowProjectSource;
    }

    public void setBorrowProjectSource(String borrowProjectSource) {
        this.borrowProjectSource = borrowProjectSource;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(String recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }
}
