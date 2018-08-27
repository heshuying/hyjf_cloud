package com.hyjf.am.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author：yinhui
 * @Date: 2018/8/7  9:50
 */
public class HjhAccountBalanceVO extends BaseVO implements Serializable {

    public static final long serialVersionUID = 1L;

    /**
     * 主键       db_column: id
     */
    public Integer id;
    /**
     * 日期       db_column: date
     */
    @ApiModelProperty(value = "每日交易量日期")
    public Date rptDate;
    /**
     * 原始资产交易额(元)       db_column: invest_account
     */
    @ApiModelProperty(value = "原始资产交易额(元)")
    public BigDecimal investAccount;
    /**
     * 债转资产交易额(元)       db_column: credit_account
     */
    @ApiModelProperty(value = "债转资产交易额(元)")
    public BigDecimal creditAccount;
    /**
     * 复投资金额(元)       db_column: reinvest_account
     */
    @ApiModelProperty(value = "复投资金额(元)")
    public BigDecimal reinvestAccount;
    /**
     * 新加入资金额(元)       db_column: add_account
     */
    @ApiModelProperty(value = "新加入资金额(元)")
    public BigDecimal addAccount;
    /**
     * 创建人id       db_column: create_user
     */
    public Integer createUser;
    /**
     * 创建时间       db_column: create_time
     */
    public Date createTime;
    /**
     * 更新人id       db_column: update_user
     */
    public Integer updateUser;
    /**
     * 更新时间       db_column: update_time
     */
    public Date updateTime;
    /**
     * 删除标识       db_column: del_flg
     */
    @ApiModelProperty(value = "删除标识，0=显示")
    public Integer delFlag;

    @ApiModelProperty(value = "每月交易量日期")
    private String dataFormt;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public Date getRptDate() {
        return rptDate;
    }

    public void setRptDate(Date rptDate) {
        this.rptDate = rptDate;
    }

    public BigDecimal getInvestAccount() {
        return investAccount;
    }

    public void setInvestAccount(BigDecimal investAccount) {
        this.investAccount = investAccount;
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

    public BigDecimal getAddAccount() {
        return addAccount;
    }

    public void setAddAccount(BigDecimal addAccount) {
        this.addAccount = addAccount;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getDataFormt() {
        return dataFormt;
    }

    public void setDataFormt(String dataFormt) {
        this.dataFormt = dataFormt;
    }

}
