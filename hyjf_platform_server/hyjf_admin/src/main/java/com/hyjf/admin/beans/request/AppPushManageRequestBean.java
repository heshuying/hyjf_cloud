package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author : huanghui
 */
public class AppPushManageRequestBean extends BasePage implements Serializable {

    @ApiModelProperty(value = "查询ID")
    private String ids;

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

    @ApiModelProperty(value = "开始时间 - 检索用")
    private String timeStartDiy;

    @ApiModelProperty(value = "结束时间 - 检索用")
    private String timeEndDiy;

    @ApiModelProperty(value = "缩略图")
    private String thumb;

    @ApiModelProperty(value = "内容")
    private String content;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
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

    public String getTimeStartDiy() {
        return timeStartDiy;
    }

    public void setTimeStartDiy(String timeStartDiy) {
        this.timeStartDiy = timeStartDiy;
    }

    public String getTimeEndDiy() {
        return timeEndDiy;
    }

    public void setTimeEndDiy(String timeEndDiy) {
        this.timeEndDiy = timeEndDiy;
    }
}
