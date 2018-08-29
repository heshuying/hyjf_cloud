/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author tanyy
 * @version WhereaboutsPageRequestBean, v0.1 2018/7/20 14:23
 */
public class WhereaboutsPageRequestBean extends BaseRequest {

    //活动页id
    @ApiModelProperty(value = "活动页id")
    private Integer id;
    //页面title
    @ApiModelProperty(value = "页面title")
    private String title;
    //平台
    @ApiModelProperty(value = "平台")
    private String sourceName;
    //渠道Id
    @ApiModelProperty(value = "渠道Id")
    private String utmId;
    //渠道名称
    @ApiModelProperty(value = "渠道名称")
    private String utmSource;
    //推荐人id
    @ApiModelProperty(value = "推荐人id")
    private String referrer;
    //样式：通用模板/大转盘
    @ApiModelProperty(value = "选择样式：通用模板/大转盘")
    private Integer style;

    //地址
    @ApiModelProperty(value = "地址")
    private String jumpPath;
    //描述
    @ApiModelProperty(value = "描述")
    private String describe;
    //开启状态
    @ApiModelProperty(value = "开启状态")
    private Integer statusOn;

    private String downloadPath;

    private String remark;

    private Integer delFlag;

    private String createUserId;

    private String updateUserId;

    private Date createTime;

    private Date updateTime;
    /**
     * 检索条件渠道名称
     */
    @ApiModelProperty(value = "检索条件渠道名称")
    private String utmName;
    /**
     * 检索条件推荐人名称
     */
    @ApiModelProperty(value = "推荐人用户名")
    private String referrerName;
    /**
     * 检索条件页面title
     */
    @ApiModelProperty(value = "检索条件页面title")
    private String titleName;

    /**
     * 检索条件 时间开始
     */
    @ApiModelProperty(value = "检索条件 时间开始")
    private String timeStartSrch;

    /**
     * 检索条件 时间结束
     */
    @ApiModelProperty(value = "检索条件 时间结束")
    private String timeEndSrch;
    /**
     * 图片
     */
    private String imageJson1;

    /**
     * 图片
     */
    private String topButton;


    private Integer bottomButtonStatus;

    private String bottomButton;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    public String getUtmName() {
        return utmName;
    }

    public void setUtmName(String utmName) {
        this.utmName = utmName;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getUtmId() {
        return utmId;
    }

    public void setUtmId(String utmId) {
        this.utmId = utmId;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public String getJumpPath() {
        return jumpPath;
    }

    public void setJumpPath(String jumpPath) {
        this.jumpPath = jumpPath;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getStatusOn() {
        return statusOn;
    }

    public void setStatusOn(Integer statusOn) {
        this.statusOn = statusOn;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public String getImageJson1() {
        return imageJson1;
    }

    public void setImageJson1(String imageJson1) {
        this.imageJson1 = imageJson1;
    }

    public String getTopButton() {
        return topButton;
    }

    public void setTopButton(String topButton) {
        this.topButton = topButton;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        this.bottomButton = bottomButton;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
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
