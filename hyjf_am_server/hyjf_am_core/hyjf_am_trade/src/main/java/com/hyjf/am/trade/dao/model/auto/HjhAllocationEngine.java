package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhAllocationEngine implements Serializable {
    /**
     * 分配引擎表id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 计划编号
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 计划名称
     *
     * @mbggenerated
     */
    private String planName;

    /**
     * 计划专区(是否配置)添加时间
     *
     * @mbggenerated
     */
    private Integer configAddTime;

    /**
     * 计划专区(是否配置)状态 0：停用 1：启用
     *
     * @mbggenerated
     */
    private Integer configStatus;

    /**
     * 标签ID
     *
     * @mbggenerated
     */
    private Integer labelId;

    /**
     * 标签名称
     *
     * @mbggenerated
     */
    private String labelName;

    /**
     * 标签添加时间
     *
     * @mbggenerated
     */
    private Integer addTime;

    /**
     * 标签排序 默认值为0(最低)，优先级递增 1,2,3....
     *
     * @mbggenerated
     */
    private Integer labelSort;

    /**
     * 债转时间排序 0：按转让时间降序 1：按转让时间升序
     *
     * @mbggenerated
     */
    private Integer transferTimeSort;

    /**
     * 债转时间排序优先级
     *
     * @mbggenerated
     */
    private Integer transferTimeSortPriority;

    /**
     * 年化收益率排序 0：从低到高 1：从高到低
     *
     * @mbggenerated
     */
    private Integer aprSort;

    /**
     * 年化收益率优先级
     *
     * @mbggenerated
     */
    private Integer aprSortPriority;

    /**
     * 标的实际支付金额排序 0：从小到大 1：从大到小
     *
     * @mbggenerated
     */
    private Integer actulPaySort;

    /**
     * 年化收益率优先级
     *
     * @mbggenerated
     */
    private Integer actulPaySortPriority;

    /**
     * 出借进度排序 0：从小到大 1：从大到小
     *
     * @mbggenerated
     */
    private Integer investProgressSort;

    /**
     * 出借进度优先级
     *
     * @mbggenerated
     */
    private Integer investProgressSortPriority;

    /**
     * 标签状态 0：停用 1：启用
     *
     * @mbggenerated
     */
    private Integer labelStatus;

    /**
     * 创建人id
     *
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * 更新人id
     *
     * @mbggenerated
     */
    private Integer updateUser;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
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