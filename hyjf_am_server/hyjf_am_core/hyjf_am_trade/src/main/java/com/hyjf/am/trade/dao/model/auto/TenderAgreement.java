package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class TenderAgreement implements Serializable {
    private Integer id;

    /**
     * 投资用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 投资用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 标的编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 投资类型:0:原始投资,1:承接债权,2:加入计划,3:计划承接
     *
     * @mbggenerated
     */
    private Integer tenderType;

    /**
     * 投资订单号或承接订单号
     *
     * @mbggenerated
     */
    private String tenderNid;

    /**
     * 合同状态 0:初始,1:生成成功,2,签署成功,3,下载成功
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 合同编号
     *
     * @mbggenerated
     */
    private String contractNumber;

    /**
     * 合同名称
     *
     * @mbggenerated
     */
    private String contractName;

    /**
     * 模板编号
     *
     * @mbggenerated
     */
    private String templetId;

    /**
     * 合同生成时间
     *
     * @mbggenerated
     */
    private Integer contractCreateTime;

    /**
     * 合同签署时间
     *
     * @mbggenerated
     */
    private Integer contractSignTime;

    /**
     * 合同下载地址
     *
     * @mbggenerated
     */
    private String downloadUrl;

    /**
     * 合同查看地址
     *
     * @mbggenerated
     */
    private String viewpdfUrl;

    /**
     * 脱敏图片存放地址
     *
     * @mbggenerated
     */
    private String imgUrl;

    /**
     * 本地pdf文件路径
     *
     * @mbggenerated
     */
    private String pdfUrl;

    /**
     * 创建用户id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建用户名
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private String updateUserName;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getTenderType() {
        return tenderType;
    }

    public void setTenderType(Integer tenderType) {
        this.tenderType = tenderType;
    }

    public String getTenderNid() {
        return tenderNid;
    }

    public void setTenderNid(String tenderNid) {
        this.tenderNid = tenderNid == null ? null : tenderNid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber == null ? null : contractNumber.trim();
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public String getTempletId() {
        return templetId;
    }

    public void setTempletId(String templetId) {
        this.templetId = templetId == null ? null : templetId.trim();
    }

    public Integer getContractCreateTime() {
        return contractCreateTime;
    }

    public void setContractCreateTime(Integer contractCreateTime) {
        this.contractCreateTime = contractCreateTime;
    }

    public Integer getContractSignTime() {
        return contractSignTime;
    }

    public void setContractSignTime(Integer contractSignTime) {
        this.contractSignTime = contractSignTime;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    public String getViewpdfUrl() {
        return viewpdfUrl;
    }

    public void setViewpdfUrl(String viewpdfUrl) {
        this.viewpdfUrl = viewpdfUrl == null ? null : viewpdfUrl.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl == null ? null : pdfUrl.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
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