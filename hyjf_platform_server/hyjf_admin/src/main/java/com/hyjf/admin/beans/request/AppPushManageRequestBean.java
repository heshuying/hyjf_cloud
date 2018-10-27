package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author : huanghui
 */
public class AppPushManageRequestBean extends BasePage {

    @ApiModelProperty(value = "当前ID")
    private Integer id;

    @ApiModelProperty(value = "标题名称")
    private String title;

    @ApiModelProperty(value = "跳转类型:0:原生;1:H5;")
    private Integer jumpType;

    @ApiModelProperty(value = "跳转内容:原生,0;H5 URL,1;H5自定义:2;")
    private Integer jumpContent;

    @ApiModelProperty(value = "跳转URL")
    private String jumpUrl;

    @ApiModelProperty(value = "排序")
    private Integer orderId;

    @ApiModelProperty(value = "0:禁用;1:启用.")
    private Integer status;

    @ApiModelProperty(value = "开始时间")
    private Date timeStart;

    @ApiModelProperty(value = "结束时间")
    private Date timeEnd;

    @ApiModelProperty(value = "缩略图")
    private String thumb;

    @ApiModelProperty(value = "内容")
    private String content;

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
        this.title = title;
    }

    public Integer getJumpType() {
        return jumpType;
    }

    public void setJumpType(Integer jumpType) {
        this.jumpType = jumpType;
    }

    public Integer getJumpContent() {
        return jumpContent;
    }

    public void setJumpContent(Integer jumpContent) {
        this.jumpContent = jumpContent;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
