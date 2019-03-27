package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class ProtocolTemplate implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 协议id
     *
     * @mbggenerated
     */
    private String protocolId;

    /**
     * 协议模板名称
     *
     * @mbggenerated
     */
    private String protocolName;

    /**
     * 前台显示名称
     *
     * @mbggenerated
     */
    private String displayName;

    /**
     * 协议类型
     *
     * @mbggenerated
     */
    private String protocolType;

    /**
     * 协议版本号(当前协议使用的版本号)
     *
     * @mbggenerated
     */
    private String versionNumber;

    /**
     * 状态(0.协议不显示1.协议显示)
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 协议路径(当前协议显示路径)
     *
     * @mbggenerated
     */
    private String protocolUrl;

    /**
     * pdf图片路径地址
     *
     * @mbggenerated
     */
    private String imgUrl;

    /**
     * 备注(当前协议)
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * 是否在帮助中心显示（0不显示，1显示）
     *
     * @mbggenerated
     */
    private Byte isShow;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    private static final long serialVersionUID = 1L;

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
        this.protocolId = protocolId == null ? null : protocolId.trim();
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName == null ? null : protocolName.trim();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : displayName.trim();
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType == null ? null : protocolType.trim();
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber == null ? null : versionNumber.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProtocolUrl() {
        return protocolUrl;
    }

    public void setProtocolUrl(String protocolUrl) {
        this.protocolUrl = protocolUrl == null ? null : protocolUrl.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Byte getIsShow() {
        return isShow;
    }

    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
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
}