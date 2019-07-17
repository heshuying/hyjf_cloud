/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author nxl
 * @version TemplateConfigCustomizeVO, v0.1 2018/6/19 17:36
 * 推广中心-着陆页管理(列表）
 */
public class TemplateConfigCustomizeVO extends BaseVO implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "主键id")
    private Integer id;

    /**
     * 模板类型
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "模板类型")
    private Integer tempType;

    /**
     * 模板名称
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "模板名称")
    private String tempName;

    /**
     * 页面title
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "着陆页title")
    private String tempTitle;

    /**
     * 顶部图片
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "顶部图片")
    private String topImg;

    /**
     * 底部图片
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "底部图片")
    private String bottomImg;

    /**
     * 主色
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "主色")
    private String dominantColor;

    /**
     * 副色
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "副色")
    private String secondaryColor;

    /**
     * 背景色
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "背景色")
    private String backgroundColor;

    /**
     * 按钮文字
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "按钮文字")
    private String buttonText;

    /**
     * 状态，1启用，0禁用
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "状态，1启用，0禁用")
    private Integer status;

    /**
     * 备注
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 弹层图片
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "弹层图片")
    private String layerImg;

    /**
     * 弹层名称
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "弹层名称")
    private String layerName;

    /**
     * 是否可点击跳转 1:是，0：否
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "是否可点击跳转 1:是，0：否")
    private Integer isJumt;

    /**
     * 跳转地址
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "跳转地址")
    private String jumtUrl;
    @ApiModelProperty(value = "状态，1启用，0禁用")
    private String statusStr;

    @ApiModelProperty(value = "添加时间")
    private String createTimeStr;
    @ApiModelProperty(value = "模板类型显示")
    private String tempTypeStr;

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
        this.tempName = tempName;
    }

    public String getTempTitle() {
        return tempTitle;
    }

    public void setTempTitle(String tempTitle) {
        this.tempTitle = tempTitle;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getTempTypeStr() {
        return tempTypeStr;
    }

    public void setTempTypeStr(String tempTypeStr) {
        this.tempTypeStr = tempTypeStr;
    }

    public String getTopImg() {
        return topImg;
    }

    public void setTopImg(String topImg) {
        this.topImg = topImg;
    }

    public String getBottomImg() {
        return bottomImg;
    }

    public void setBottomImg(String bottomImg) {
        this.bottomImg = bottomImg;
    }

    public String getDominantColor() {
        return dominantColor;
    }

    public void setDominantColor(String dominantColor) {
        this.dominantColor = dominantColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLayerImg() {
        return layerImg;
    }

    public void setLayerImg(String layerImg) {
        this.layerImg = layerImg;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public Integer getIsJumt() {
        return isJumt;
    }

    public void setIsJumt(Integer isJumt) {
        this.isJumt = isJumt;
    }

    public String getJumtUrl() {
        return jumtUrl;
    }

    public void setJumtUrl(String jumtUrl) {
        this.jumtUrl = jumtUrl;
    }
}
