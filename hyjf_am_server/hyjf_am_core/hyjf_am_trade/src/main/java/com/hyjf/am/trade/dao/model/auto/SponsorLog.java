package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class SponsorLog implements Serializable {
    private Integer id;

    /**
     * 标号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 原始担保人id
     *
     * @mbggenerated
     */
    private Integer oldSponsorId;

    /**
     * 原始担保人username
     *
     * @mbggenerated
     */
    private String oldSponsor;

    /**
     * 新担保人id
     *
     * @mbggenerated
     */
    private Integer newSponsorId;

    /**
     * 新担保人username
     *
     * @mbggenerated
     */
    private String newSponsor;

    /**
     * 0初始1修改成功2修改失败
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 0初始 1关闭 2删除
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建用户名
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private String updateUserName;

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

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getOldSponsorId() {
        return oldSponsorId;
    }

    public void setOldSponsorId(Integer oldSponsorId) {
        this.oldSponsorId = oldSponsorId;
    }

    public String getOldSponsor() {
        return oldSponsor;
    }

    public void setOldSponsor(String oldSponsor) {
        this.oldSponsor = oldSponsor == null ? null : oldSponsor.trim();
    }

    public Integer getNewSponsorId() {
        return newSponsorId;
    }

    public void setNewSponsorId(Integer newSponsorId) {
        this.newSponsorId = newSponsorId;
    }

    public String getNewSponsor() {
        return newSponsor;
    }

    public void setNewSponsor(String newSponsor) {
        this.newSponsor = newSponsor == null ? null : newSponsor.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
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