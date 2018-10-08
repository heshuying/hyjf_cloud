package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author lisheng
 * @version EmailRecipientRequest, v0.1 2018/10/8 11:23
 */

public class EmailRecipientRequest extends BasePage implements Serializable {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "业务名称")
    private String businessName;
    @ApiModelProperty(value = "状态 1有效,2:无效")
    private Integer status;
    @ApiModelProperty(value = "创建时间-开始")
    private String timeStartCreateSrch;
    @ApiModelProperty(value = "创建时间-结束")
    private String timeEndCreateSrch;
    @ApiModelProperty(value = "更新时间-开始")
    private String timeStartUpdateSrch;
    @ApiModelProperty(value = "更新时间-结束")
    private String timeEndUpdateSrch;
    @ApiModelProperty(value = "更新人")
    private String updateName;
    @ApiModelProperty(value = "联系人邮箱")
    private String email;
    @ApiModelProperty(value = "时间点 1:每个工作日 2:每天  3:每月第一个工作日")
    private Integer timePoint;
    @ApiModelProperty(value = "邮件发送具体时间")
    private String sendTime;
    @ApiModelProperty(value = "创建人")
    private String createName;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTimeStartCreateSrch() {
        return timeStartCreateSrch;
    }

    public void setTimeStartCreateSrch(String timeStartCreateSrch) {
        this.timeStartCreateSrch = timeStartCreateSrch;
    }

    public String getTimeEndCreateSrch() {
        return timeEndCreateSrch;
    }

    public void setTimeEndCreateSrch(String timeEndCreateSrch) {
        this.timeEndCreateSrch = timeEndCreateSrch;
    }

    public String getTimeStartUpdateSrch() {
        return timeStartUpdateSrch;
    }

    public void setTimeStartUpdateSrch(String timeStartUpdateSrch) {
        this.timeStartUpdateSrch = timeStartUpdateSrch;
    }

    public String getTimeEndUpdateSrch() {
        return timeEndUpdateSrch;
    }

    public void setTimeEndUpdateSrch(String timeEndUpdateSrch) {
        this.timeEndUpdateSrch = timeEndUpdateSrch;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Integer timePoint) {
        this.timePoint = timePoint;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
