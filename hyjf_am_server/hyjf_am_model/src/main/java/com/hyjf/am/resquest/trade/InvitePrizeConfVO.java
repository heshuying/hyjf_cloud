package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 19:18
 * @Description: InvitePrizeConfVO
 */
public class InvitePrizeConfVO extends BaseVO {
    private Integer id;

    private String prizeName;

    private Integer prizeQuantity;

    private Integer recommendQuantity;

    private Integer prizeReminderQuantity;

    private String prizeGroupCode;

    private Integer prizeType;

    private String couponCode;

    private BigDecimal prizeProbability;

    private String prizePicUrl;

    private Integer prizeKind;

    private Integer prizeSort;

    private Integer prizeStatus;

    private String successMessage;

    private Integer prizeApplyTime;

    private String remark;

    private Integer delFlag;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

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
        this.prizeName = prizeName;
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
        this.prizeGroupCode = prizeGroupCode;
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
        this.couponCode = couponCode;
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
        this.prizePicUrl = prizePicUrl;
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
        this.successMessage = successMessage;
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
        this.remark = remark;
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
