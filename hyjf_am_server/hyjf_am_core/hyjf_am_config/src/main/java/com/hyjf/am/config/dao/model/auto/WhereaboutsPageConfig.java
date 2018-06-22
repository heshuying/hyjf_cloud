package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class WhereaboutsPageConfig implements Serializable {
    private Integer id;

    private String title;

    private Integer utmId;

    private Integer referrer;

    private String topButton;

    private String jumpPath;

    private Boolean bottomButtonStatus;

    private String bottomButton;

    private String downloadPath;

    private String describe;

    private Integer statusOn;

    private Boolean deleteStatus;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String remark;

    private Integer style;

    private static final long serialVersionUID = 1L;

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
        this.title = title == null ? null : title.trim();
    }

    public Integer getUtmId() {
        return utmId;
    }

    public void setUtmId(Integer utmId) {
        this.utmId = utmId;
    }

    public Integer getReferrer() {
        return referrer;
    }

    public void setReferrer(Integer referrer) {
        this.referrer = referrer;
    }

    public String getTopButton() {
        return topButton;
    }

    public void setTopButton(String topButton) {
        this.topButton = topButton == null ? null : topButton.trim();
    }

    public String getJumpPath() {
        return jumpPath;
    }

    public void setJumpPath(String jumpPath) {
        this.jumpPath = jumpPath == null ? null : jumpPath.trim();
    }

    public Boolean getBottomButtonStatus() {
        return bottomButtonStatus;
    }

    public void setBottomButtonStatus(Boolean bottomButtonStatus) {
        this.bottomButtonStatus = bottomButtonStatus;
    }

    public String getBottomButton() {
        return bottomButton;
    }

    public void setBottomButton(String bottomButton) {
        this.bottomButton = bottomButton == null ? null : bottomButton.trim();
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath == null ? null : downloadPath.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public Integer getStatusOn() {
        return statusOn;
    }

    public void setStatusOn(Integer statusOn) {
        this.statusOn = statusOn;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }
}