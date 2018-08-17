/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author tanyy
 * @version ContentLandingPageRequestBean, v0.1 2018/7/16 14:23
 */
public class ContentLandingPageRequestBean extends BaseRequest {

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "着落页名称")
    private String pageName;
    @ApiModelProperty(value = "渠道")
    private String channelName;
    @ApiModelProperty(value = "地址")
    private String pageUrl;
    @ApiModelProperty(value = "上传图片-二维码")
    private String codeUrl;
    @ApiModelProperty(value = "备注")
    private String remark;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
    /**
     * 前台时间接收
     */
    private String startTime;

    private String endTime;

    private String pageNameSrch;
    private String channelNameSrch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getPageNameSrch() {
        return pageNameSrch;
    }

    public void setPageNameSrch(String pageNameSrch) {
        this.pageNameSrch = pageNameSrch;
    }

    public String getChannelNameSrch() {
        return channelNameSrch;
    }

    public void setChannelNameSrch(String channelNameSrch) {
        this.channelNameSrch = channelNameSrch;
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
