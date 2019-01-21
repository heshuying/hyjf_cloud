package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class WhereaboutsPageConfig implements Serializable {
    /**
     * 页面id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 页面title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * 渠道编号
     *
     * @mbggenerated
     */
    private Integer utmId;

    /**
     * 推荐人id
     *
     * @mbggenerated
     */
    private Integer referrer;

    /**
     * 注册按钮名称
     *
     * @mbggenerated
     */
    private String topButton;

    /**
     * 注册成功跳转地址
     *
     * @mbggenerated
     */
    private String jumpPath;

    /**
     * 下载按钮启用状态  0：启用   1：不启用 
     *
     * @mbggenerated
     */
    private Integer bottomButtonStatus;

    /**
     * 按钮名称  
     *
     * @mbggenerated
     */
    private String bottomButton;

    /**
     * 下载按钮跳转路径
     *
     * @mbggenerated
     */
    private String downloadPath;

    /**
     * 描述
     *
     * @mbggenerated
     */
    private String describe;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 样式 0:通用模板 1:大转盘
     *
     * @mbggenerated
     */
    private Integer style;

    /**
     * 开启状态：0 未开启  1 开启
     *
     * @mbggenerated
     */
    private Integer statusOn;

    /**
     * 删除状态  0：未删除   1：已删除 
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private String createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private String updateUserId;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getUtmId() {
        return utmId;
    }

    public void setUtmId(Integer utmId) {
        this.utmId = utmId;
    }

    public Integer getReferrer() {
        return referrer;
    }

    public void setReferrer(Integer referrer) {
        this.referrer = referrer;
    }

    public String getTopButton() {
        return topButton;
    }

    public void setTopButton(String topButton) {
        this.topButton = topButton == null ? null : topButton.trim();
    }

    public String getJumpPath() {
        return jumpPath;
    }

    public void setJumpPath(String jumpPath) {
        this.jumpPath = jumpPath == null ? null : jumpPath.trim();
    }

    public Integer getBottomButtonStatus() {
        return bottomButtonStatus;
    }

    public void setBottomButtonStatus(Integer bottomButtonStatus) {
        this.bottomButtonStatus = bottomButtonStatus;
    }

    public String getBottomButton() {
        return bottomButton;
    }

    public void setBottomButton(String bottomButton) {
        this.bottomButton = bottomButton == null ? null : bottomButton.trim();
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath == null ? null : downloadPath.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public Integer getStatusOn() {
        return statusOn;
    }

    public void setStatusOn(Integer statusOn) {
        this.statusOn = statusOn;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
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