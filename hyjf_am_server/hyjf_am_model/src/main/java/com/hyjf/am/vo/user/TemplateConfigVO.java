/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nxl
 * @version UserMemberListVO, v0.1 2018/6/19 17:36
 *          推广中心-着陆页管理(列表）
 */
public class TemplateConfigVO extends BaseVO implements Serializable {
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
    private Integer tempType;

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
    private Integer status;

    /**
     * 备注
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
    private Integer isJumt;

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
    /**
     * 状态，1启用，0禁用
     *
     * @mbggenerated
     */
    private String statusStr;

    private String createTimeStr;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTempType() {
        return tempType;
    }

    public void setTempType(Integer tempType) {
        this.tempType = tempType;
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

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsJumt() {
        return isJumt;
    }

    public void setIsJumt(Integer isJumt) {
        this.isJumt = isJumt;
    }
}
