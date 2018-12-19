package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

public class HjhUserAuthLogVO extends BaseVO implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户id
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
     * 订单号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 订单状态 1：完成  2：未完成
     *
     * @mbggenerated
     */
    private Integer orderStatus;

    /**
     * 签约类型 0全部开通 1:自动出借签约 2:预约取现 3:无密消费 4:自动债转授权 5:缴费授权 6:还款授权 11:自动出借授权、自动债转授权 12:自动出借授权、缴费授权 13:自动债转授权、缴费授权 14:自动出借授权、自动债转授权、缴费授权
     *
     * @mbggenerated
     */
    private Integer authType;

    /**
     * 签约操作平台 0:pc  1:微信  2:安卓  3:IOS  4:其他
     *
     * @mbggenerated
     */
    private Integer operateEsb;

    /**
     * 签约授权时间
     *
     * @mbggenerated
     */
    private Integer authCreateTime;

    /**
     * 删除标识 0: 未删除    1:已删除
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建者
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 更新者
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

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public Integer getOperateEsb() {
        return operateEsb;
    }

    public void setOperateEsb(Integer operateEsb) {
        this.operateEsb = operateEsb;
    }

    public Integer getAuthCreateTime() {
        return authCreateTime;
    }

    public void setAuthCreateTime(Integer authCreateTime) {
        this.authCreateTime = authCreateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}