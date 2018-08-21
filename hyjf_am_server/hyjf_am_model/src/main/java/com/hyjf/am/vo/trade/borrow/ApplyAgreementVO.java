package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @version ApplyAgreementVO, v0.1 2018/8/10 14:18
 * @Author: Zha Daojian
 */
public class ApplyAgreementVO  extends BaseVO implements Serializable {

    private Integer id;

    @ApiModelProperty(value = "借款编号")
    private String borrowNid;

    @ApiModelProperty(value = "申请期数")
    private Integer repayPeriod;

    @ApiModelProperty(value = "申请人id")
    private Integer applyUserId;

    @ApiModelProperty(value = "申请人")
    private String applyUserName;

    @ApiModelProperty(value = "协议份数")
    private Byte agreementNumber;

    @ApiModelProperty(value = "申请状态 0 全部；1申请中：2申请成功")
    private Integer status;

    @ApiModelProperty(value = "删除标识")
    private Integer delFlag;

    @ApiModelProperty(value = "创建用户")
    private Integer createUserId;

    @ApiModelProperty(value = "更新用户")
    private Integer updateUserId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

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
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(Integer repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName == null ? null : applyUserName.trim();
    }

    public Byte getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(Byte agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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
