package com.hyjf.am.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author：yinhui
 * @Date: 2018/8/8  15:43
 */
public class ProtocolVersionVO  extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "协议id")
    private String protocolId;

    @ApiModelProperty(value = "协议版本号")
    private String versionNumber;

    @ApiModelProperty(value = "协议文件名称")
    private String protocolName;

    @ApiModelProperty(value = "协议路径")
    private String protocolUrl;

    @ApiModelProperty(value = "前台展示名称")
    private String displayName;

    @ApiModelProperty(value = "是否启用(0.版本不启用1.版本启用)")
    private Integer displayFlag;

    private Integer createUser;

    private Date createTime;

    private Integer updateUser;

    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remarks;

    //修改人
    private String userName;

    //协议模板日志列显示时间
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getProtocolUrl() {
        return protocolUrl;
    }

    public void setProtocolUrl(String protocolUrl) {
        this.protocolUrl = protocolUrl;
    }

    public Integer getDisplayFlag() {
        return displayFlag;
    }

    public void setDisplayFlag(Integer displayFlag) {
        this.displayFlag = displayFlag;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
