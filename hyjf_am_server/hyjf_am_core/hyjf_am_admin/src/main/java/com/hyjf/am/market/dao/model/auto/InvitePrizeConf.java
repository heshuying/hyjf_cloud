package com.hyjf.am.market.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InvitePrizeConf implements Serializable {
    private Integer id;

    /**
     * 奖品名称
     *
     * @mbggenerated
     */
    private String prizeName;

    /**
     * 奖品数量
     *
     * @mbggenerated
     */
    private Integer prizeQuantity;

    /**
     * 所需推荐星数量
     *
     * @mbggenerated
     */
    private Integer recommendQuantity;

    /**
     * 奖品剩余数量
     *
     * @mbggenerated
     */
    private Integer prizeReminderQuantity;

    /**
     * 奖品分组
     *
     * @mbggenerated
     */
    private String prizeGroupCode;

    /**
     * 奖品类别 1：实物奖品，2：优惠券
     *
     * @mbggenerated
     */
    private Integer prizeType;

    private String couponCode;

    /**
     * 中奖概率
     *
     * @mbggenerated
     */
    private BigDecimal prizeProbability;

    /**
     * 实物奖品图片
     *
     * @mbggenerated
     */
    private String prizePicUrl;

    /**
     * 奖品类别  1：兑奖，2：抽奖
     *
     * @mbggenerated
     */
    private Integer prizeKind;

    /**
     * 奖品排序  
     *
     * @mbggenerated
     */
    private Integer prizeSort;

    /**
     * 奖品状态，0：启用，1：禁用
     *
     * @mbggenerated
     */
    private Integer prizeStatus;

    /**
     * 兑奖成功时的提示消息
     *
     * @mbggenerated
     */
    private String successMessage;

    /**
     * 奖品的适用时间，以月为单位
     *
     * @mbggenerated
     */
    private Integer prizeApplyTime;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 删除标识 0：未删除，1：已删除
     *
     * @mbggenerated
     */
    private Integer delFlag;

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

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName == null ? null : prizeName.trim();
    }

    public Integer getPrizeQuantity() {
        return prizeQuantity;
    }

    public void setPrizeQuantity(Integer prizeQuantity) {
        this.prizeQuantity = prizeQuantity;
    }

    public Integer getRecommendQuantity() {
        return recommendQuantity;
    }

    public void setRecommendQuantity(Integer recommendQuantity) {
        this.recommendQuantity = recommendQuantity;
    }

    public Integer getPrizeReminderQuantity() {
        return prizeReminderQuantity;
    }

    public void setPrizeReminderQuantity(Integer prizeReminderQuantity) {
        this.prizeReminderQuantity = prizeReminderQuantity;
    }

    public String getPrizeGroupCode() {
        return prizeGroupCode;
    }

    public void setPrizeGroupCode(String prizeGroupCode) {
        this.prizeGroupCode = prizeGroupCode == null ? null : prizeGroupCode.trim();
    }

    public Integer getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(Integer prizeType) {
        this.prizeType = prizeType;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode == null ? null : couponCode.trim();
    }

    public BigDecimal getPrizeProbability() {
        return prizeProbability;
    }

    public void setPrizeProbability(BigDecimal prizeProbability) {
        this.prizeProbability = prizeProbability;
    }

    public String getPrizePicUrl() {
        return prizePicUrl;
    }

    public void setPrizePicUrl(String prizePicUrl) {
        this.prizePicUrl = prizePicUrl == null ? null : prizePicUrl.trim();
    }

    public Integer getPrizeKind() {
        return prizeKind;
    }

    public void setPrizeKind(Integer prizeKind) {
        this.prizeKind = prizeKind;
    }

    public Integer getPrizeSort() {
        return prizeSort;
    }

    public void setPrizeSort(Integer prizeSort) {
        this.prizeSort = prizeSort;
    }

    public Integer getPrizeStatus() {
        return prizeStatus;
    }

    public void setPrizeStatus(Integer prizeStatus) {
        this.prizeStatus = prizeStatus;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage == null ? null : successMessage.trim();
    }

    public Integer getPrizeApplyTime() {
        return prizeApplyTime;
    }

    public void setPrizeApplyTime(Integer prizeApplyTime) {
        this.prizeApplyTime = prizeApplyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}