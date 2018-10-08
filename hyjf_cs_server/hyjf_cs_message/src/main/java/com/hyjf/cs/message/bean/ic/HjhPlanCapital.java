package com.hyjf.cs.message.bean.ic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author : huanghui
 */
@Document(collection = "ht_hjh_plan_capital")
public class HjhPlanCapital implements Serializable {

    private String id;

    private Date date;

    private String planNid;

    private String planName;

    private Integer lockPeriod;

    private Integer isMonth;

    private BigDecimal reinvestAccount;

    private BigDecimal creditAccount;

    private Integer createUser;

    private Integer createTime;

    private Integer updateUser;

    private String updateTime;

    private Integer delFlg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getLockPeriod() {
        return lockPeriod;
    }

    public void setLockPeriod(Integer lockPeriod) {
        this.lockPeriod = lockPeriod;
    }

    public Integer getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(Integer isMonth) {
        this.isMonth = isMonth;
    }

    public BigDecimal getReinvestAccount() {
        return reinvestAccount;
    }

    public void setReinvestAccount(BigDecimal reinvestAccount) {
        this.reinvestAccount = reinvestAccount;
    }

    public BigDecimal getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(BigDecimal creditAccount) {
        this.creditAccount = creditAccount;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
}
