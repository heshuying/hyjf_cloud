/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.config;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 电销数据推送记录VO
 *
 * @author liuyang
 * @version ElectricitySalesDataPushListVO, v0.1 2019/5/31 9:30
 */
public class ElectricitySalesDataPushListVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 7403675983586325189L;

    /**
     * ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 所属客组ID
     *
     * @mbggenerated
     */
    private Integer groupId;

    /**
     * 所属客组名称
     *
     * @mbggenerated
     */
    private String groupName;

    /**
     * 用户客户账号
     *
     * @mbggenerated
     */
    private String bankAccount;

    /**
     * 客户角色1投资人2借款人3担保机构
     *
     * @mbggenerated
     */
    private Integer roleId;

    /**
     * 客户姓名
     *
     * @mbggenerated
     */
    private String trueName;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 性别:0未知,1男,2女
     *
     * @mbggenerated
     */
    private Integer sex;

    /**
     * 年龄
     *
     * @mbggenerated
     */
    private Integer age;

    /**
     * 生日
     *
     * @mbggenerated
     */
    private String birthday;

    /**
     * 注册时间
     *
     * @mbggenerated
     */
    private Date regTime;

    /**
     * PC渠道编号
     *
     * @mbggenerated
     */
    private Integer pcSourceId;

    /**
     * PC渠道来源
     *
     * @mbggenerated
     */
    private String pcSourceName;

    /**
     * APP渠道编号
     *
     * @mbggenerated
     */
    private Integer appSourceId;

    /**
     * APP渠道来源
     *
     * @mbggenerated
     */
    private String appSourceName;

    /**
     * 充值金额
     *
     * @mbggenerated
     */
    private BigDecimal rechargeMoney;

    /**
     * 充值时间
     *
     * @mbggenerated
     */
    private Date rechargeTime;

    /**
     * 是否是渠道:固定0:非渠道
     *
     * @mbggenerated
     */
    private Integer channel;

    /**
     * 上传方式:0:系统自动上传,1:手动上传
     *
     * @mbggenerated
     */
    private Integer uploadType;

    /**
     * 启用状态 0:初始 1:成功 2:失败
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

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
        this.userName = userName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public Integer getPcSourceId() {
        return pcSourceId;
    }

    public void setPcSourceId(Integer pcSourceId) {
        this.pcSourceId = pcSourceId;
    }

    public String getPcSourceName() {
        return pcSourceName;
    }

    public void setPcSourceName(String pcSourceName) {
        this.pcSourceName = pcSourceName;
    }

    public Integer getAppSourceId() {
        return appSourceId;
    }

    public void setAppSourceId(Integer appSourceId) {
        this.appSourceId = appSourceId;
    }

    public String getAppSourceName() {
        return appSourceName;
    }

    public void setAppSourceName(String appSourceName) {
        this.appSourceName = appSourceName;
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

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getUploadType() {
        return uploadType;
    }

    public void setUploadType(Integer uploadType) {
        this.uploadType = uploadType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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
