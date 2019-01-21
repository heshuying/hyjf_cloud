package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class Submissions implements Serializable {
    private Integer id;

    /**
     * 标题
     *
     * @mbggenerated
     */
    private String title;

    /**
     * 反馈类型
     *
     * @mbggenerated
     */
    private String problem;

    private String content;

    /**
     * 图片地址
     *
     * @mbggenerated
     */
    private String img;

    /**
     * 会员id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 系统类别 0：PC，1：微官网，2：Android，3：IOS，4：其他
     *
     * @mbggenerated
     */
    private Integer sysType;

    /**
     * 操作系统版本号
     *
     * @mbggenerated
     */
    private String sysVersion;

    /**
     * 状态 0未审 1已审核
     *
     * @mbggenerated
     */
    private Integer state;

    /**
     * 客服回复
     *
     * @mbggenerated
     */
    private String reply;

    /**
     * 平台版本号
     *
     * @mbggenerated
     */
    private String platformVersion;

    /**
     * 手机型号
     *
     * @mbggenerated
     */
    private String phoneType;

    /**
     * 回复时间
     *
     * @mbggenerated
     */
    private Integer replytime;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem == null ? null : problem.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSysType() {
        return sysType;
    }

    public void setSysType(Integer sysType) {
        this.sysType = sysType;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion == null ? null : sysVersion.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply == null ? null : reply.trim();
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion == null ? null : platformVersion.trim();
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType == null ? null : phoneType.trim();
    }

    public Integer getReplytime() {
        return replytime;
    }

    public void setReplytime(Integer replytime) {
        this.replytime = replytime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}