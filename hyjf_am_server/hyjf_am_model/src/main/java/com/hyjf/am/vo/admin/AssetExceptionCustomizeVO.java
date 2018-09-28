/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author PC-LIUSHOUYI
 * @version AssetExceptionCustomizeVO, v0.1 2018/9/28 18:05
 */
public class AssetExceptionCustomizeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "机构编号")
    private String instCode;

    @ApiModelProperty(value = "机构名称")
    private String instName;

    @ApiModelProperty(value = "借款编号")
    private String borrowNid;

    @ApiModelProperty(value = "借款金额")
    private BigDecimal account;

    @ApiModelProperty(value = "异常类型")
    private Integer exceptionType;

    @ApiModelProperty(value = "异常原因")
    private String exceptionRemark;

    @ApiModelProperty(value = "项目状态")
    private String status;

    @ApiModelProperty(value = "异常时间")
    private String exceptionTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(Integer exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionRemark() {
        return exceptionRemark;
    }

    public void setExceptionRemark(String exceptionRemark) {
        this.exceptionRemark = exceptionRemark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExceptionTime() {
        return exceptionTime;
    }

    public void setExceptionTime(String exceptionTime) {
        this.exceptionTime = exceptionTime;
    }
}
