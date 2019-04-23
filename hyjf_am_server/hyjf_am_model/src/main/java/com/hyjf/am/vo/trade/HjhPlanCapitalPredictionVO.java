/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实际资金计划
 * @author wenxin
 * @version HjhPlanCapitalPredictionVO, v0.1 2019/4/2 15:33
 */
public class HjhPlanCapitalPredictionVO extends BaseVO implements Serializable {

    private String id;

    /**
     * 日期
     */
    private Date date;

    @ApiModelProperty(value = "给前端的格式化后的日期")
    private String stringDate;

    @ApiModelProperty(value = "智投编号")
    private String planNid;

    @ApiModelProperty(value = "智投名称")
    private String planName;

    @ApiModelProperty(value = "天/月标")
    private Integer isMonth;

    private Integer lockPeriod;

    @ApiModelProperty(value = "拼接单位后的锁定期")
    private String lockPeriodView;

    @ApiModelProperty(value = "预计当日新增债转额")
    private BigDecimal creditAccount;

    @ApiModelProperty(value = "预计当日新增复投额")
    private BigDecimal reinvestAccount;

    @ApiModelProperty(value = "预计当日所需资金量")
    private BigDecimal capitalAccount;

    @ApiModelProperty(value = "预计当日所需资产量")
    private BigDecimal assetAccount;

    @ApiModelProperty(value = "预估数据日期")
    private Date dualBaseDate;

    @ApiModelProperty(value = "预估数据日期字符串")
    private String dualBaseDateStr;

    /**
     * 创建人id
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标识
     */
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

    public BigDecimal getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(BigDecimal creditAccount) {
        this.creditAccount = creditAccount;
    }

    public BigDecimal getReinvestAccount() {
        return reinvestAccount;
    }

    public void setReinvestAccount(BigDecimal reinvestAccount) {
        this.reinvestAccount = reinvestAccount;
    }

    public BigDecimal getCapitalAccount() {
        return capitalAccount;
    }

    public void setCapitalAccount(BigDecimal capitalAccount) {
        this.capitalAccount = capitalAccount;
    }

    public BigDecimal getAssetAccount() {
        return assetAccount;
    }

    public void setAssetAccount(BigDecimal assetAccount) {
        this.assetAccount = assetAccount;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public Date getDualBaseDate() {
        return dualBaseDate;
    }

    public void setDualBaseDate(Date dualBaseDate) {
        this.dualBaseDate = dualBaseDate;
    }

    public Integer getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(Integer isMonth) {
        this.isMonth = isMonth;
    }

    public String getLockPeriodView() {
        return lockPeriodView;
    }

    public void setLockPeriodView(String lockPeriodView) {
        this.lockPeriodView = lockPeriodView;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public String getDualBaseDateStr() {
        return dualBaseDateStr;
    }

    public void setDualBaseDateStr(String dualBaseDateStr) {
        this.dualBaseDateStr = dualBaseDateStr;
    }
}
