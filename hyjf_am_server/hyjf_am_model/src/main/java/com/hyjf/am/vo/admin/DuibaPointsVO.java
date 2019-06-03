/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsVO, v0.1 2019/5/29 10:45
 */
public class DuibaPointsVO implements Serializable {
    private Integer id;

    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 真实姓名
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "真实姓名")
    private String trueName;

    /**
     * 当前操作积分数
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "当前操作积分数")
    private Integer points;

    /**
     * 当前操作后总积分数
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "当前操作后总积分数")
    private Integer total;

    /**
     * 类型 0:获取 1:使用
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "类型 0:获取 1:使用")
    private Integer type;

    /**
     * 转换实义的类型
     */
    @ApiModelProperty(value = "转换实义的类型")
    private String typeStr;

    /**
     * 积分业务名称: 0出借 1商品兑换 2订单取消
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "积分业务名称: 0出借 1商品兑换 2订单取消")
    private Integer businessName;

    /**
     * 转换实义的积分业务名称
     */
    @ApiModelProperty(value = "转换实义的积分业务名称")
    private String businessNameStr;

    /**
     * 兑吧订单号
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "兑吧订单号")
    private String duibaOrderId;

    /**
     * 汇盈订单号
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "汇盈订单号")
    private String hyOrderId;

    /**
     * 备注
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private String createBy;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private String updateBy;

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

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBusinessName() {
        return businessName;
    }

    public void setBusinessName(Integer businessName) {
        this.businessName = businessName;
    }

    public String getDuibaOrderId() {
        return duibaOrderId;
    }

    public void setDuibaOrderId(String duibaOrderId) {
        this.duibaOrderId = duibaOrderId == null ? null : duibaOrderId.trim();
    }

    public String getHyOrderId() {
        return hyOrderId;
    }

    public void setHyOrderId(String hyOrderId) {
        this.hyOrderId = hyOrderId == null ? null : hyOrderId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getBusinessNameStr() {
        return businessNameStr;
    }

    public void setBusinessNameStr(String businessNameStr) {
        this.businessNameStr = businessNameStr;
    }
}
