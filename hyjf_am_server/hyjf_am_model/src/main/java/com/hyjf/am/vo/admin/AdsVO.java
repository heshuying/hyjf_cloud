package com.hyjf.am.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author：yinhui
 * @Date: 2018/7/19  17:42
 */
public class AdsVO extends BaseVO implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "广告位")
    private String typeName;

    @ApiModelProperty(value = "广告名称")
    private String name;

    @ApiModelProperty(value = "广告类型")
    private Integer typeId;

    private String url;

    private String code;

    private String content;

    @ApiModelProperty(value = "广告图片")
    private String image;

    @ApiModelProperty(value = "排序")
    private Integer order;

    private Integer hits;

    @ApiModelProperty(value = "广告状态 1-启用,0-关闭")
    private Integer status;

    private String shareUrl;

    @ApiModelProperty(value = "分享图片")
    private String shareImage;

    @ApiModelProperty(value = "是否在首页特色banner位置显示，0为不显示，1显示")
    private Integer isIndex;

    private String startTime;

    private String endTime;

    @ApiModelProperty(value = "是否已结束(只针对活动banner有效0:否,1:是)")
    private Integer isEnd;

    private String shareTitle;

    private String activitiImage;

    private String activitiDesc;

    private String shareContent;

    private Integer clientType;

    private Integer newUserShow;

    private Integer createUserId;

    private Integer updateUserId;

    @ApiModelProperty(value = "添加时间")
    private String createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

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
        this.name = name == null ? null : name.trim();
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
        this.url = url == null ? null : url.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
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
        this.shareUrl = shareUrl == null ? null : shareUrl.trim();
    }

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage == null ? null : shareImage.trim();
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
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
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
        this.shareTitle = shareTitle == null ? null : shareTitle.trim();
    }

    public String getActivitiImage() {
        return activitiImage;
    }

    public void setActivitiImage(String activitiImage) {
        this.activitiImage = activitiImage == null ? null : activitiImage.trim();
    }

    public String getActivitiDesc() {
        return activitiDesc;
    }

    public void setActivitiDesc(String activitiDesc) {
        this.activitiDesc = activitiDesc == null ? null : activitiDesc.trim();
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent == null ? null : shareContent.trim();
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

    public String getCreateTime() {
        return createTime;
    }

    //
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Object createTime) {
        String dateString = null;
        if(createTime instanceof java.util.Date){

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateString = formatter.format(createTime);
        }else{
            dateString =(String) createTime;
        }
        this.createTime = dateString;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
