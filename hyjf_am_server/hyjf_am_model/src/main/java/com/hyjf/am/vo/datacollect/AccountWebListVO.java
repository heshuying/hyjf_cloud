package com.hyjf.am.vo.datacollect;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiasq
 * @version AccountWebListVO, v0.1 2018/6/19 16:38
 */
public class AccountWebListVO extends BaseVO implements Serializable {

    private int limitStart = -1;
    private int limitEnd = -1;
    /**
     * 创建时间 起始
     */
    @ApiModelProperty(value = "创建时间 起始")
    private String startDate;
    /**创建时间  结束*/
    @ApiModelProperty(value = "创建时间  结束")
    private String endDate;
    @ApiModelProperty(value = "用户名")
    private String usernameSearch;
    @ApiModelProperty(value = "真是姓名")
    private String truenameSearch;
    /**收支类型  1：收入  2：支出*/
    @ApiModelProperty(value = "收支类型")
    private int typeSearche ;

    /**交易类型*/
    @ApiModelProperty(value = "交易类型")
    private String tradeTypeSearch;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;


    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    private String id;

    private String ordid;

    private String borrowNid;

    private BigDecimal amount;

    private Integer type;

    private String trade;

    private String tradeType;

    private Integer userId;

    private String usrcustid;

    private String truename;

    private String regionName;

    private String branchName;

    private String departmentName;

    private String remark;

    private String note;

    private Integer createTime;

    private String operator;

    private Integer flag;

    private String username;

    private static final long serialVersionUID = 1L;

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

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUsernameSearch() {
        return usernameSearch;
    }

    public void setUsernameSearch(String usernameSearch) {
        this.usernameSearch = usernameSearch;
    }

    public String getTruenameSearch() {
        return truenameSearch;
    }

    public void setTruenameSearch(String truenameSearch) {
        this.truenameSearch = truenameSearch;
    }

    public int getTypeSearche() {
        return typeSearche;
    }

    public void setTypeSearche(int typeSearche) {
        this.typeSearche = typeSearche;
    }

    public String getTradeTypeSearch() {
        return tradeTypeSearch;
    }

    public void setTradeTypeSearch(String tradeTypeSearch) {
        this.tradeTypeSearch = tradeTypeSearch;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) {
        this.ordid = ordid == null ? null : ordid.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade == null ? null : trade.trim();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsrcustid() {
        return usrcustid;
    }

    public void setUsrcustid(String usrcustid) {
        this.usrcustid = usrcustid == null ? null : usrcustid.trim();
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
