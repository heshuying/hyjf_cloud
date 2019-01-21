package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class AccountBank implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 禁用状态
     *
     * @mbggenerated
     */
    private Integer bankStatus;

    /**
     * 银行账号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 所属银行
     *
     * @mbggenerated
     */
    private String bank;

    /**
     * 支行
     *
     * @mbggenerated
     */
    private String branch;

    /**
     * 省份
     *
     * @mbggenerated
     */
    private String province;

    /**
     * 城市
     *
     * @mbggenerated
     */
    private String city;

    /**
     * 区
     *
     * @mbggenerated
     */
    private Integer area;

    /**
     * 卡类型 0普通提现卡1默认卡2快捷支付卡
     *
     * @mbggenerated
     */
    private String cardType;

    private String respcode;

    private String respdesc;

    private String createIp;

    private String updateIp;

    private Date createTime;

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

    public Integer getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(Integer bankStatus) {
        this.bankStatus = bankStatus;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch == null ? null : branch.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode == null ? null : respcode.trim();
    }

    public String getRespdesc() {
        return respdesc;
    }

    public void setRespdesc(String respdesc) {
        this.respdesc = respdesc == null ? null : respdesc.trim();
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp == null ? null : updateIp.trim();
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