package com.hyjf.cs.message.bean.ic;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author xiasq
 * @version AccountWebList, v0.1 2018/6/19 16:47
 */
@Document(collection = "t_account_web_list")
public class AccountWebList implements Serializable {
    private String id;

    private String ordid;

    private String borrowNid;

    private Double amount;

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

    private Integer createStartTime;

    private Integer createEndTime;

    private String operator;

    private Integer flag;

    private String username;

    private Integer createTime;

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
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
        this.ordid = ordid;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
        this.trade = trade;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
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
        this.usrcustid = usrcustid;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Integer createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Integer getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Integer createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
