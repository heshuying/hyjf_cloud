package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class Evalation implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 评分上限
     *
     * @mbggenerated
     */
    private Short scoreUp;

    /**
     * 评分下线
     *
     * @mbggenerated
     */
    private Short scoreDown;

    /**
     * 测评类型
     *
     * @mbggenerated
     */
    private String evalType;

    /**
     * 测评分析
     *
     * @mbggenerated
     */
    private String summary;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 状态0是可用，1是不可用
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private String createUser;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private String updateUser;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
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

    public Short getScoreUp() {
        return scoreUp;
    }

    public void setScoreUp(Short scoreUp) {
        this.scoreUp = scoreUp;
    }

    public Short getScoreDown() {
        return scoreDown;
    }

    public void setScoreDown(Short scoreDown) {
        this.scoreDown = scoreDown;
    }

    public String getEvalType() {
        return evalType;
    }

    public void setEvalType(String evalType) {
        this.evalType = evalType == null ? null : evalType.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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