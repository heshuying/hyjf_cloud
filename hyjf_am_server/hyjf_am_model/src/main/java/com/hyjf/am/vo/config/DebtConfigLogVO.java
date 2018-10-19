package com.hyjf.am.vo.config;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtConfigLogVO extends BaseVO implements Serializable {
    private Integer id;

    private Integer hyjfDebtConfigId;

    private BigDecimal attornRate;

    private BigDecimal concessionRateUp;

    private BigDecimal concessionRateDown;

    private Integer toggle;

    private String closeDes;

    private Integer updateUser;
    private String updateUserName;
    private Date updateTime;

    private String ipAddress;

    private String macAddress;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHyjfDebtConfigId() {
        return hyjfDebtConfigId;
    }

    public void setHyjfDebtConfigId(Integer hyjfDebtConfigId) {
        this.hyjfDebtConfigId = hyjfDebtConfigId;
    }

    public BigDecimal getAttornRate() {
        return attornRate;
    }

    public void setAttornRate(BigDecimal attornRate) {
        this.attornRate = attornRate;
    }

    public BigDecimal getConcessionRateUp() {
        return concessionRateUp;
    }

    public void setConcessionRateUp(BigDecimal concessionRateUp) {
        this.concessionRateUp = concessionRateUp;
    }

    public BigDecimal getConcessionRateDown() {
        return concessionRateDown;
    }

    public void setConcessionRateDown(BigDecimal concessionRateDown) {
        this.concessionRateDown = concessionRateDown;
    }

    public Integer getToggle() {
        return toggle;
    }

    public void setToggle(Integer toggle) {
        this.toggle = toggle;
    }

    public String getCloseDes() {
        return closeDes;
    }

    public void setCloseDes(String closeDes) {
        this.closeDes = closeDes == null ? null : closeDes.trim();
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress == null ? null : macAddress.trim();
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
}