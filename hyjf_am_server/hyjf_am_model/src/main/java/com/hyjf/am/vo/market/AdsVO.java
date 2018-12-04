package com.hyjf.am.vo.market;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author xiasq
 * @version AdsVO, v0.1 2018/5/14 16:13
 */
public class AdsVO extends BaseVO {
    @ApiModelProperty(value = "id集合")
    private String ids;
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "广告名")
    private String name;
    @ApiModelProperty(value = "广告类型")
    private Integer typeId;
    @ApiModelProperty(value = "路径")
    private String url;
    @ApiModelProperty(value = "调用代码")
    private String code;
    private String content;
    @ApiModelProperty(value = " 图片路径 ")
    private String image;
    @ApiModelProperty(value = "DEFAULT '0'")
    private Integer order;
    @ApiModelProperty(value = "DEFAULT '0'")
    private Integer hits;
    @ApiModelProperty(value = "状态  0  或  1")
    private Integer status;
    @ApiModelProperty(value = "分享url")
    private String shareUrl;
    @ApiModelProperty(value = "分享图片url")
    private String shareImage;
    @ApiModelProperty(value = "是否在首页特色banner位置显示，0为不显示，1显示")
    private Integer isIndex;
    @ApiModelProperty(value = "是否已结束(只针对活动banner有效0:否,1:是)")
    private Integer isEnd;
    @ApiModelProperty(value = "分享标题")
    private String shareTitle;
    @ApiModelProperty(value = "活动列表图")
    private String activitiImage;
    @ApiModelProperty(value = "活动描述")
    private String activitiDesc;
    @ApiModelProperty(value = "分享内容")
    private String shareContent;
    @ApiModelProperty(value = "客户端类型 0为pc广告  1为手机广告")
    private Integer clientType;
    @ApiModelProperty(value = "是否限制新手 1：限制 2：不限制")
    private Integer newUserShow;
    @ApiModelProperty(value = "拆分状态 1：android广告管理 2：ios广告管理 3: 微信广告管理")
    private Integer platformType;

    private String startTime;
    private String endTime;
    private Integer createUserId;
    private Integer updateUserId;
    private Date createTime;
    private Date updateTime;
    private String timeStart;
    private String timeEnd;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
    }

    public Integer getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(Integer isIndex) {
        this.isIndex = isIndex;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getActivitiImage() {
        return activitiImage;
    }

    public void setActivitiImage(String activitiImage) {
        this.activitiImage = activitiImage;
    }

    public String getActivitiDesc() {
        return activitiDesc;
    }

    public void setActivitiDesc(String activitiDesc) {
        this.activitiDesc = activitiDesc;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getNewUserShow() {
        return newUserShow;
    }

    public void setNewUserShow(Integer newUserShow) {
        this.newUserShow = newUserShow;
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

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }
}
