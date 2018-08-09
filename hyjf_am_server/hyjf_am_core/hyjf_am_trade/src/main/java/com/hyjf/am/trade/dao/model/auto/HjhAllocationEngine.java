package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhAllocationEngine implements Serializable {
    private Integer id;

    private String planNid;

    private String planName;

    private Integer configAddTime;

    private Integer configStatus;

    private Integer labelId;

    private String labelName;

    private Integer addTime;

    private Integer labelSort;

    private Integer transferTimeSort;

    private Integer transferTimeSortPriority;

    private Integer aprSort;

    private Integer aprSortPriority;

    private Integer actulPaySort;

    private Integer actulPaySortPriority;

    private Integer investProgressSort;

    private Integer investProgressSortPriority;

    private Integer labelStatus;

    private Integer createUser;

    private Integer updateUser;

    private Integer delFlag;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public Integer getConfigAddTime() {
        return configAddTime;
    }

    public void setConfigAddTime(Integer configAddTime) {
        this.configAddTime = configAddTime;
    }

    public Integer getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(Integer configStatus) {
        this.configStatus = configStatus;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getLabelSort() {
        return labelSort;
    }

    public void setLabelSort(Integer labelSort) {
        this.labelSort = labelSort;
    }

    public Integer getTransferTimeSort() {
        return transferTimeSort;
    }

    public void setTransferTimeSort(Integer transferTimeSort) {
        this.transferTimeSort = transferTimeSort;
    }

    public Integer getTransferTimeSortPriority() {
        return transferTimeSortPriority;
    }

    public void setTransferTimeSortPriority(Integer transferTimeSortPriority) {
        this.transferTimeSortPriority = transferTimeSortPriority;
    }

    public Integer getAprSort() {
        return aprSort;
    }

    public void setAprSort(Integer aprSort) {
        this.aprSort = aprSort;
    }

    public Integer getAprSortPriority() {
        return aprSortPriority;
    }

    public void setAprSortPriority(Integer aprSortPriority) {
        this.aprSortPriority = aprSortPriority;
    }

    public Integer getActulPaySort() {
        return actulPaySort;
    }

    public void setActulPaySort(Integer actulPaySort) {
        this.actulPaySort = actulPaySort;
    }

    public Integer getActulPaySortPriority() {
        return actulPaySortPriority;
    }

    public void setActulPaySortPriority(Integer actulPaySortPriority) {
        this.actulPaySortPriority = actulPaySortPriority;
    }

    public Integer getInvestProgressSort() {
        return investProgressSort;
    }

    public void setInvestProgressSort(Integer investProgressSort) {
        this.investProgressSort = investProgressSort;
    }

    public Integer getInvestProgressSortPriority() {
        return investProgressSortPriority;
    }

    public void setInvestProgressSortPriority(Integer investProgressSortPriority) {
        this.investProgressSortPriority = investProgressSortPriority;
    }

    public Integer getLabelStatus() {
        return labelStatus;
    }

    public void setLabelStatus(Integer labelStatus) {
        this.labelStatus = labelStatus;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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