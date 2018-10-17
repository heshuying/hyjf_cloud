package com.hyjf.am.resquest.admin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author：yinhui
 * @Date: 2018/8/7  9:43
 */
public class HjhAccountBalanceRequest  implements Serializable {

    public static final long serialVersionUID = 5454155825314635342L;

    private int paginatorPage = 1;

    private int pageSize = 10;

    @ApiModelProperty(value = "请求时间，month=每月交易，day=每日交易")
    private String time;

    @ApiModelProperty(value = "添加时间开始")
    private String addTimeStart;

    @ApiModelProperty(value = "添加时间结束")
    private String addTimeEnd;

    public int limitStart = -1;

    public int limitEnd = -1;

    //查询用变量
    private Date yuechu;
    private Date yuemo;



    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    @ApiModelProperty(value = "主键查询条件")
    public java.lang.Integer idSer;

    @ApiModelProperty(value = "日期查询条件")
    public java.sql.Date dateSer;

    @ApiModelProperty(value = " 原始资产交易额(元)查询条件")
    public java.math.BigDecimal investAccountSer;

    @ApiModelProperty(value = "债转资产交易额(元)查询条件")
    public java.math.BigDecimal creditAccountSer;

    @ApiModelProperty(value = "复投资金额(元)查询条件")
    public java.math.BigDecimal reinvestAccountSer;

    @ApiModelProperty(value = "新加入资金额(元)查询条件")
    public java.math.BigDecimal addAccountSer;

    @ApiModelProperty(value = "创建人id查询条件")
    public java.lang.Integer createUserSer;

    @ApiModelProperty(value = "创建时间查询条件")
    public Date createTimeSer;

    @ApiModelProperty(value = "更新人id查询条件")
    public Date updateUserSer;

    @ApiModelProperty(value = "更新时间查询条件")
    public java.lang.Integer updateTimeSer;

    @ApiModelProperty(value = "删除标识查询条件")
    public java.lang.Boolean delFlgSer;


    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public String getAddTimeStart() {
        return addTimeStart;
    }

    public void setAddTimeStart(String addTimeStart) {
        this.addTimeStart = addTimeStart;
    }

    public String getAddTimeEnd() {
        return addTimeEnd;
    }

    public void setAddTimeEnd(String addTimeEnd) {
        this.addTimeEnd = addTimeEnd;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getIdSer() {
        return idSer;
    }

    public void setIdSer(Integer idSer) {
        this.idSer = idSer;
    }

    public java.sql.Date getDateSer() {
        return dateSer;
    }

    public void setDateSer(java.sql.Date dateSer) {
        this.dateSer = dateSer;
    }

    public BigDecimal getInvestAccountSer() {
        return investAccountSer;
    }

    public void setInvestAccountSer(BigDecimal investAccountSer) {
        this.investAccountSer = investAccountSer;
    }

    public BigDecimal getCreditAccountSer() {
        return creditAccountSer;
    }

    public void setCreditAccountSer(BigDecimal creditAccountSer) {
        this.creditAccountSer = creditAccountSer;
    }

    public BigDecimal getReinvestAccountSer() {
        return reinvestAccountSer;
    }

    public void setReinvestAccountSer(BigDecimal reinvestAccountSer) {
        this.reinvestAccountSer = reinvestAccountSer;
    }

    public BigDecimal getAddAccountSer() {
        return addAccountSer;
    }

    public void setAddAccountSer(BigDecimal addAccountSer) {
        this.addAccountSer = addAccountSer;
    }

    public Integer getCreateUserSer() {
        return createUserSer;
    }

    public void setCreateUserSer(Integer createUserSer) {
        this.createUserSer = createUserSer;
    }

    public Date getCreateTimeSer() {
        return createTimeSer;
    }

    public void setCreateTimeSer(Date createTimeSer) {
        this.createTimeSer = createTimeSer;
    }

    public Date getUpdateUserSer() {
        return updateUserSer;
    }

    public void setUpdateUserSer(Date updateUserSer) {
        this.updateUserSer = updateUserSer;
    }

    public Integer getUpdateTimeSer() {
        return updateTimeSer;
    }

    public void setUpdateTimeSer(Integer updateTimeSer) {
        this.updateTimeSer = updateTimeSer;
    }

    public Boolean getDelFlgSer() {
        return delFlgSer;
    }

    public void setDelFlgSer(Boolean delFlgSer) {
        this.delFlgSer = delFlgSer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getYuechu() {
        return yuechu;
    }

    public void setYuechu(Date yuechu) {
        this.yuechu = yuechu;
    }

    public Date getYuemo() {
        return yuemo;
    }

    public void setYuemo(Date yuemo) {
        this.yuemo = yuemo;
    }
}
