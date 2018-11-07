package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class SmsCode implements Serializable {
    private Integer id;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 手机号码
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 加密验证码
     *
     * @mbggenerated
     */
    private String checkfor;

    /**
     * 验证码
     *
     * @mbggenerated
     */
    private String checkcode;

    /**
     * 不详
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 发送时间
     *
     * @mbggenerated
     */
    private Integer posttime;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCheckfor() {
        return checkfor;
    }

    public void setCheckfor(String checkfor) {
        this.checkfor = checkfor == null ? null : checkfor.trim();
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode == null ? null : checkcode.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPosttime() {
        return posttime;
    }

    public void setPosttime(Integer posttime) {
        this.posttime = posttime;
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