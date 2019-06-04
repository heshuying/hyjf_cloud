/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.bean.electricitysalesdatapush;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 电销数据上送Bean
 *
 * @author liuyang
 * @version ElectricitySalesDataPushBean, v0.1 2019/6/3 16:34
 */
public class ElectricitySalesDataPushBean implements Serializable {
    private static final long serialVersionUID = -1890145004710591015L;
    /**
     * 客户姓名
     */
    private String trueName;

    /**
     * 手机号
     */
    private String mobile;


    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * PC渠道来源
     */
    private String pcSourceName;

    /**
     * APP渠道来源
     */
    private String appSourceName;


    /**
     * 生日
     */
    private String birthday;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 充值金额
     */
    private BigDecimal rechargeMoney;

    /**
     * 充值时间
     */
    private Date rechargeTime;


    /**
     * 是否是渠道:固定0:非渠道
     */
    private String channel;


    /**
     * 坐席姓名
     */
    private String ownerUserName;


    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPcSourceName() {
        return pcSourceName;
    }

    public void setPcSourceName(String pcSourceName) {
        this.pcSourceName = pcSourceName;
    }

    public String getAppSourceName() {
        return appSourceName;
    }

    public void setAppSourceName(String appSourceName) {
        this.appSourceName = appSourceName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
