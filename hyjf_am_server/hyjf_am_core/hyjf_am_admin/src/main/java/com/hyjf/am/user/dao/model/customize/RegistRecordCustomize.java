/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.model.customize;

import java.io.Serializable;

/**
 * @author nxl
 * @version UserMemberListVO, v0.1 2018/6/19 17:36
 * 会员中心 ->会员管理(列表）
 */
public class RegistRecordCustomize implements Serializable {
    // 用戶id
    public String userId;
    // 用戶名
    public String userName;
    // 用戶角色
    public String mobile;
    // 推荐人名称
    public String recommendName;
    //用户属性
    private String userProperty;
    //用户类型
    private String userType;
    //注册渠道
    public String sourceName;
    // 注册平台
    public String registPlat;
    // 注册平台
    public String registPlatCode;
    // 注册时间
    public String regTime;
    // 注册ip
    public String regIP;
    // 注册渠道Id
    public String sourceId;
    // 注册渠道类型（pc,app,all,no）
    public String sourceType;

    /**
     * 获取用户id userId
     *
     * @return the userId
     */

    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId
     *            the userId to set
     */

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名 userName
     *
     * @return the userName
     */

    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName
     *            the userName to set
     */

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户角色 userRole
     *
     * @return the userRole
     */

    public String getMobile() {
        return mobile;
    }

    /**
     * 设置用户角色
     *
     */

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取推荐人 recommendName
     *
     * @return the recommendName
     */

    public String getRecommendName() {
        return recommendName;
    }

    /**
     * 设置推荐人
     *
     * @param recommendName
     *            the recommendName to set
     */

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    /**
     * registPlat
     * @return the registPlat
     */

    public String getRegistPlat() {
        return registPlat;
    }

    /**
     * @param registPlat the registPlat to set
     */

    public void setRegistPlat(String registPlat) {
        this.registPlat = registPlat;
    }

    /**
     * 获取用户注册时间 regTime
     *
     * @return the regTime
     */

    public String getRegTime() {
        return regTime;
    }

    /**
     * 设置注册时间
     *
     * @param regTime
     *            the regTime to set
     */

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getUserProperty() {
        return userProperty;
    }

    public void setUserProperty(String userProperty) {
        this.userProperty = userProperty;
    }

    public String getRegIP() {
        return regIP;
    }

    public void setRegIP(String regIP) {
        this.regIP = regIP;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getRegistPlatCode() {
        return registPlatCode;
    }

    public void setRegistPlatCode(String registPlatCode) {
        this.registPlatCode = registPlatCode;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
