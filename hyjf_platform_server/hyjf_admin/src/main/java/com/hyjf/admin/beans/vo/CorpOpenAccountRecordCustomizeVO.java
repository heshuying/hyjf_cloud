package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class CorpOpenAccountRecordCustomizeVO extends BaseVO implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "用户编号")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "组织机构代码/社会信用号")
    private String busiCode;

    @ApiModelProperty(value = "企业名称")
    private String busiName;

    @ApiModelProperty(value = "是否担保类型，Y：是 N：否")
    private String guarType;

    @ApiModelProperty(value = "开户银行编号")
    private String openBankId;

    @ApiModelProperty(value = "银行账号")
    private String cardId;

    @ApiModelProperty(value = "企业开户状态  0初始 1提交 2审核中 3 审核拒绝 4开户失败 5开户中 6开户成功")
    private Integer status;

    @ApiModelProperty(value = "是否银行 0汇付 1江西银行")
    private Integer isBank;

    @ApiModelProperty(value = "添加时间")
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注（失败原因）")
    private String remark;

    @ApiModelProperty(value = "证件类型 20：其他证件（组织机构代码）25：社会信用号")
    private Integer cardType;

    @ApiModelProperty(value = "税务登记证")
    private String taxRegistrationCode;

    @ApiModelProperty(value = "营业执照编号")
    private String buseNo;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode == null ? null : busiCode.trim();
    }

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName == null ? null : busiName.trim();
    }

    public String getGuarType() {
        return guarType;
    }

    public void setGuarType(String guarType) {
        this.guarType = guarType == null ? null : guarType.trim();
    }

    public String getOpenBankId() {
        return openBankId;
    }

    public void setOpenBankId(String openBankId) {
        this.openBankId = openBankId == null ? null : openBankId.trim();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsBank() {
        return isBank;
    }

    public void setIsBank(Integer isBank) {
        this.isBank = isBank;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getTaxRegistrationCode() {
        return taxRegistrationCode;
    }

    public void setTaxRegistrationCode(String taxRegistrationCode) {
        this.taxRegistrationCode = taxRegistrationCode == null ? null : taxRegistrationCode.trim();
    }

    public String getBuseNo() {
        return buseNo;
    }

    public void setBuseNo(String buseNo) {
        this.buseNo = buseNo == null ? null : buseNo.trim();
    }
}