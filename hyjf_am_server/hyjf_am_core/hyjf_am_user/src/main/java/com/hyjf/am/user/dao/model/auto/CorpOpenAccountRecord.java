package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class CorpOpenAccountRecord implements Serializable {
    /**
     * 主键id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户编号
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 组织机构代码/社会信用号
     *
     * @mbggenerated
     */
    private String busiCode;

    /**
     * 企业名称
     *
     * @mbggenerated
     */
    private String busiName;

    /**
     * 是否担保类型，Y：是 N：否
     *
     * @mbggenerated
     */
    private String guarType;

    /**
     * 开户银行编号
     *
     * @mbggenerated
     */
    private String openBankId;

    /**
     * 银行账号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 企业开户状态  0初始 1提交 2审核中 3 审核拒绝 4开户失败 5开户中 6开户成功
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 是否银行 0汇付 1江西银行
     *
     * @mbggenerated
     */
    private Integer isBank;

    /**
     * 证件类型 20：其他证件（组织机构代码）25：社会信用号
     *
     * @mbggenerated
     */
    private Integer cardType;

    /**
     * 税务登记证
     *
     * @mbggenerated
     */
    private String taxRegistrationCode;

    /**
     * 营业执照编号
     *
     * @mbggenerated
     */
    private String buseNo;

    /**
     * 备注（失败原因）
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 创建者
     *
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * 更新者
     *
     * @mbggenerated
     */
    private Integer updateUser;

    /**
     * 添加时间
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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