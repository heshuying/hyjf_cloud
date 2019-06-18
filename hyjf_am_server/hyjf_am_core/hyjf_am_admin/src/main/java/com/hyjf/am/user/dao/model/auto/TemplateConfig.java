package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class TemplateConfig implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 模板类型
     *
     * @mbggenerated
     */
    private String tempType;

    /**
     * 模板名称
     *
     * @mbggenerated
     */
    private String tempName;

    /**
     * 页面title
     *
     * @mbggenerated
     */
    private String tempTitle;

    /**
     * 顶部图片
     *
     * @mbggenerated
     */
    private String topImg;

    /**
     * 底部图片
     *
     * @mbggenerated
     */
    private String bottomImg;

    /**
     * 主色
     *
     * @mbggenerated
     */
    private String dominantColor;

    /**
     * 副色
     *
     * @mbggenerated
     */
    private String secondaryColor;

    /**
     * 背景色
     *
     * @mbggenerated
     */
    private String backgroundColor;

    /**
     * 按钮文字
     *
     * @mbggenerated
     */
    private String buttonText;

    /**
     * 状态，1启用，0禁用
     *
     * @mbggenerated
     */
    private Boolean status;

    /**
     * 按钮文字
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 弹层图片
     *
     * @mbggenerated
     */
    private String layerImg;

    /**
     * 弹层名称
     *
     * @mbggenerated
     */
    private String layerName;

    /**
     * 是否可点击跳转 1:是，0：否
     *
     * @mbggenerated
     */
    private Boolean isJumt;

    /**
     * 跳转地址
     *
     * @mbggenerated
     */
    private String jumtUrl;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 创建人id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 更新人id
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTempType() {
        return tempType;
    }

    public void setTempType(String tempType) {
        this.tempType = tempType == null ? null : tempType.trim();
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName == null ? null : tempName.trim();
    }

    public String getTempTitle() {
        return tempTitle;
    }

    public void setTempTitle(String tempTitle) {
        this.tempTitle = tempTitle == null ? null : tempTitle.trim();
    }

    public String getTopImg() {
        return topImg;
    }

    public void setTopImg(String topImg) {
        this.topImg = topImg == null ? null : topImg.trim();
    }

    public String getBottomImg() {
        return bottomImg;
    }

    public void setBottomImg(String bottomImg) {
        this.bottomImg = bottomImg == null ? null : bottomImg.trim();
    }

    public String getDominantColor() {
        return dominantColor;
    }

    public void setDominantColor(String dominantColor) {
        this.dominantColor = dominantColor == null ? null : dominantColor.trim();
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor == null ? null : secondaryColor.trim();
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor == null ? null : backgroundColor.trim();
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText == null ? null : buttonText.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getLayerImg() {
        return layerImg;
    }

    public void setLayerImg(String layerImg) {
        this.layerImg = layerImg == null ? null : layerImg.trim();
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName == null ? null : layerName.trim();
    }

    public Boolean getIsJumt() {
        return isJumt;
    }

    public void setIsJumt(Boolean isJumt) {
        this.isJumt = isJumt;
    }

    public String getJumtUrl() {
        return jumtUrl;
    }

    public void setJumtUrl(String jumtUrl) {
        this.jumtUrl = jumtUrl == null ? null : jumtUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
}