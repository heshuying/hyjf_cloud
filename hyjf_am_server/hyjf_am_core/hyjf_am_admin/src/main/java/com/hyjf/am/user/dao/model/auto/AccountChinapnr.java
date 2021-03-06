package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class AccountChinapnr implements Serializable {
    private Integer id;

    private Integer userId;

    /**
     * 汇付帐号
     *
     * @mbggenerated
     */
    private String chinapnrUsrid;

    /**
     * 客户号
     *
     * @mbggenerated
     */
    private Long chinapnrUsrcustid;

    private Integer isok;

    private String createIp;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getChinapnrUsrid() {
        return chinapnrUsrid;
    }

    public void setChinapnrUsrid(String chinapnrUsrid) {
        this.chinapnrUsrid = chinapnrUsrid == null ? null : chinapnrUsrid.trim();
    }

    public Long getChinapnrUsrcustid() {
        return chinapnrUsrcustid;
    }

    public void setChinapnrUsrcustid(Long chinapnrUsrcustid) {
        this.chinapnrUsrcustid = chinapnrUsrcustid;
    }

    public Integer getIsok() {
        return isok;
    }

    public void setIsok(Integer isok) {
        this.isok = isok;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
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