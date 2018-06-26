package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class AleveErrorLog implements Serializable {
    private Integer id;

    private Integer fileline;

    private Integer saveline;

    private String filestring;

    private String filestats;

    private Integer createUserId;

    private Date createTime;

    private Integer updateUserId;

    private Date updateTime;

    private Integer delFlag;

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

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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
}