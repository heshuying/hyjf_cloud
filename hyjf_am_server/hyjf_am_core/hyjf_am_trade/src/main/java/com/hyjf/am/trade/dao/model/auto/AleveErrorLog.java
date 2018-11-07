package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class AleveErrorLog implements Serializable {
    private Integer id;

    /**
     * 文件长度
     *
     * @mbggenerated
     */
    private Integer fileline;

    /**
     * 读取长度
     *
     * @mbggenerated
     */
    private Integer saveline;

    /**
     * 文件内容
     *
     * @mbggenerated
     */
    private String filestring;

    /**
     * 文件类型，aleve,eve
     *
     * @mbggenerated
     */
    private String filestats;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建用户
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 更新用户
     *
     * @mbggenerated
     */
    private Integer updateUserId;

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

    public Integer getFileline() {
        return fileline;
    }

    public void setFileline(Integer fileline) {
        this.fileline = fileline;
    }

    public Integer getSaveline() {
        return saveline;
    }

    public void setSaveline(Integer saveline) {
        this.saveline = saveline;
    }

    public String getFilestring() {
        return filestring;
    }

    public void setFilestring(String filestring) {
        this.filestring = filestring == null ? null : filestring.trim();
    }

    public String getFilestats() {
        return filestats;
    }

    public void setFilestats(String filestats) {
        this.filestats = filestats == null ? null : filestats.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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