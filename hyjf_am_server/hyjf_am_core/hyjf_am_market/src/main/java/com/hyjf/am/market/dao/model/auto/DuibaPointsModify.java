package com.hyjf.am.market.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class DuibaPointsModify implements Serializable {
    private Integer id;

    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 真实姓名
     *
     * @mbggenerated
     */
    private String trueName;

    /**
     * 申请订单号
     *
     * @mbggenerated
     */
    private String modifyOrderId;

    /**
     * 调整积分数
     *
     * @mbggenerated
     */
    private Integer points;

    /**
     * 当时调整后总积分数
     *
     * @mbggenerated
     */
    private Integer total;

    /**
     * 操作类型：0调增 1调减
     *
     * @mbggenerated
     */
    private Integer pointsType;

    /**
     * 调整人用户名
     *
     * @mbggenerated
     */
    private String modifyName;

    /**
     * 审批人用户名
     *
     * @mbggenerated
     */
    private String reviewName;

    /**
     * 调整原因
     *
     * @mbggenerated
     */
    private String modifyReason;

    /**
     * 当前审批节点
     *
     * @mbggenerated
     */
    private Integer flowOrder;

    /**
     * 审核状态: 0待审核 1审核通过 2审核不通过 
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

    public String getModifyOrderId() {
        return modifyOrderId;
    }

    public void setModifyOrderId(String modifyOrderId) {
        this.modifyOrderId = modifyOrderId == null ? null : modifyOrderId.trim();
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

    public Integer getPointsType() {
        return pointsType;
    }

    public void setPointsType(Integer pointsType) {
        this.pointsType = pointsType;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName == null ? null : modifyName.trim();
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName == null ? null : reviewName.trim();
    }

    public String getModifyReason() {
        return modifyReason;
    }

    public void setModifyReason(String modifyReason) {
        this.modifyReason = modifyReason == null ? null : modifyReason.trim();
    }

    public Integer getFlowOrder() {
        return flowOrder;
    }

    public void setFlowOrder(Integer flowOrder) {
        this.flowOrder = flowOrder;
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
}