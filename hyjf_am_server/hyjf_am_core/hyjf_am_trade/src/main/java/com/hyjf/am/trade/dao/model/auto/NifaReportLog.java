package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class NifaReportLog implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 文件包信息
     *
     * @mbggenerated
     */
    private String packageInformation;

    /**
     * 上传时间
     *
     * @mbggenerated
     */
    private Integer uploadTime;

    /**
     * 文件上传状态 0：未处理 1：成功 2：失败
     *
     * @mbggenerated
     */
    private Integer fileUploadStatus;

    /**
     * 文件解析反馈 0：未处理 1：成功 2：失败
     *
     * @mbggenerated
     */
    private Integer feedbackResult;

    /**
     * 上传文件包名
     *
     * @mbggenerated
     */
    private String uploadName;

    /**
     * 反馈文件包名
     *
     * @mbggenerated
     */
    private String feedbackName;

    /**
     * 上传文件包路径
     *
     * @mbggenerated
     */
    private String uploadPath;

    /**
     * 反馈文件包路径
     *
     * @mbggenerated
     */
    private String feedbackPath;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 最后修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPackageInformation() {
        return packageInformation;
    }

    public void setPackageInformation(String packageInformation) {
        this.packageInformation = packageInformation == null ? null : packageInformation.trim();
    }

    public Integer getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Integer uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getFileUploadStatus() {
        return fileUploadStatus;
    }

    public void setFileUploadStatus(Integer fileUploadStatus) {
        this.fileUploadStatus = fileUploadStatus;
    }

    public Integer getFeedbackResult() {
        return feedbackResult;
    }

    public void setFeedbackResult(Integer feedbackResult) {
        this.feedbackResult = feedbackResult;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName == null ? null : uploadName.trim();
    }

    public String getFeedbackName() {
        return feedbackName;
    }

    public void setFeedbackName(String feedbackName) {
        this.feedbackName = feedbackName == null ? null : feedbackName.trim();
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath == null ? null : uploadPath.trim();
    }

    public String getFeedbackPath() {
        return feedbackPath;
    }

    public void setFeedbackPath(String feedbackPath) {
        this.feedbackPath = feedbackPath == null ? null : feedbackPath.trim();
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
}