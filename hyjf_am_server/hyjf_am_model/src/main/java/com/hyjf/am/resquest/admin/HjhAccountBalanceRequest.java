package com.hyjf.am.resquest.admin;

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

    /**
     * 检索条件 添加时间开始
     */
    private String addTimeStart;

    /**
     * 检索条件 添加时间结束

     */
    private String addTimeEnd;

    public int limitStart = -1;

    public int limitEnd = -1;

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    /**  日查询 **/
    /**
     * 主键查询条件
     */
    public java.lang.Integer idSer;
    /**
     * 日期查询条件
     */
    public java.sql.Date dateSer;
    /**
     * 原始资产交易额(元)查询条件
     */
    public java.math.BigDecimal investAccountSer;
    /**
     * 债转资产交易额(元)查询条件
     */
    public java.math.BigDecimal creditAccountSer;
    /**
     * 复投资金额(元)查询条件
     */
    public java.math.BigDecimal reinvestAccountSer;
    /**
     * 新加入资金额(元)查询条件
     */
    public java.math.BigDecimal addAccountSer;
    /**
     * 创建人id查询条件
     */
    public java.lang.Integer createUserSer;
    /**
     * 创建时间查询条件
     */
    public Date createTimeSer;
    /**
     * 更新人id查询条件
     */
    public Date updateUserSer;
    /**
     * 更新时间查询条件
     */
    public java.lang.Integer updateTimeSer;
    /**
     * 删除标识查询条件
     */
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
}
