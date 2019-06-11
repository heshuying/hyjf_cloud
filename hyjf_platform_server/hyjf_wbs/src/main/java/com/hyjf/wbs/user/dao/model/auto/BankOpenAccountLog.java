package com.hyjf.wbs.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BankOpenAccountLog implements Serializable {
    private Integer id;

    /**
     * 开户用户userId
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 开户用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 用户开户订单号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 用户手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 证件类型
     *
     * @mbggenerated
     */
    private String idType;

    /**
     * 身份证号码
     *
     * @mbggenerated
     */
    private String idNo;

    /**
     * 真实姓名
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 银行卡号
     *
     * @mbggenerated
     */
    private String cardNo;

    /**
     * 账户用途
     *
     * @mbggenerated
     */
    private String acctUse;

    /**
     * 前导业务授权码
     *
     * @mbggenerated
     */
    private String lastSrvAuthCode;

    /**
     * 用户ip
     *
     * @mbggenerated
     */
    private String userIp;

    /**
     * 请求方保留字段
     *
     * @mbggenerated
     */
    private String acqRes;

    /**
     * 开户平台 0PC 1微官网 2Android 3IOS 4其他
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 开户状态 0初始 1验证码发送成功 2开户中 3开户失败
     *
     * @mbggenerated
     */
    private Integer status;

    private String retCode;

    private String retMsg;

    /**
     * 创建用户id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 添加时间
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getAcctUse() {
        return acctUse;
    }

    public void setAcctUse(String acctUse) {
        this.acctUse = acctUse == null ? null : acctUse.trim();
    }

    public String getLastSrvAuthCode() {
        return lastSrvAuthCode;
    }

    public void setLastSrvAuthCode(String lastSrvAuthCode) {
        this.lastSrvAuthCode = lastSrvAuthCode == null ? null : lastSrvAuthCode.trim();
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp == null ? null : userIp.trim();
    }

    public String getAcqRes() {
        return acqRes;
    }

    public void setAcqRes(String acqRes) {
        this.acqRes = acqRes == null ? null : acqRes.trim();
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode == null ? null : retCode.trim();
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg == null ? null : retMsg.trim();
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