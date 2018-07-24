/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: sunpeikai
 * @version: AccountBankVO, v0.1 2018/7/23 15:21
 */
public class AccountBankVO extends BaseVO implements Serializable {
    private Integer userId;

    private Integer bankStatus;

    private String account;

    private String bank;

    private String branch;

    private String province;

    private String city;

    private Integer area;

    private String cardType;

    private String respcode;

    private String respdesc;

    private String createIp;

    private String updateIp;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

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
        this.account = account;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
        this.cardType = cardType;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    public String getRespdesc() {
        return respdesc;
    }

    public void setRespdesc(String respdesc) {
        this.respdesc = respdesc;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp;
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
