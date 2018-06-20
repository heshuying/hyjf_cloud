/**
 * Description:江西银行账户实体类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: 刘彬
 * @version: 1.0
 *           Created at: 2017年07月07日 下午2:33:39
 *           Modification History:
 *           Modified by :
 */
package com.hyjf.callcenter.beans;

import java.io.Serializable;

public class CouponBean implements Serializable  {
	//用户名
    private String userName;
    //手机号
    private String mobile;
    //优惠券Id
    private String couponUserCode;  
    //优惠券类别编号
    private String couponCode;
    //用户属性
    private String attribute;
    //优惠券适用平台
    private String couponSystem;
    //优惠券适用产品类型
    private String projectType;
    //投资项目期限条件
    private String projectExpirationType;
    //注册渠道
    private String channel;
    //优惠券类型
    private String couponType;
    //优惠券面值
    private String couponQuota; 
    //投资额度条件
    private String tenderQuota;
    //有效期
    private String endTime;
    //来源
    private String couponSource;
    //内容
    private String couponContent;
    //使用状态
    private String usedFlagView;
    //获得时间
    private String addTime;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getCouponUserCode() {
        return couponUserCode;
    }
    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }
    public String getCouponCode() {
        return couponCode;
    }
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    public String getCouponSystem() {
        return couponSystem;
    }
    public void setCouponSystem(String couponSystem) {
        this.couponSystem = couponSystem;
    }
    public String getProjectType() {
        return projectType;
    }
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
    public String getProjectExpirationType() {
        return projectExpirationType;
    }
    public void setProjectExpirationType(String projectExpirationType) {
        this.projectExpirationType = projectExpirationType;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getCouponType() {
        return couponType;
    }
    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
    public String getCouponQuota() {
        return couponQuota;
    }
    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }
    public String getTenderQuota() {
        return tenderQuota;
    }
    public void setTenderQuota(String tenderQuota) {
        this.tenderQuota = tenderQuota;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getCouponSource() {
        return couponSource;
    }
    public void setCouponSource(String couponSource) {
        this.couponSource = couponSource;
    }
    public String getCouponContent() {
        return couponContent;
    }
    public void setCouponContent(String couponContent) {
        this.couponContent = couponContent;
    }
    public String getUsedFlagView() {
        return usedFlagView;
    }
    public void setUsedFlagView(String usedFlagView) {
        this.usedFlagView = usedFlagView;
    }
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAttribute() {
        return attribute;
    }
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    
    
}
