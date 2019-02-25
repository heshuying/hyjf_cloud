package com.hyjf.am.vo.admin.coupon;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CertCouponRecoverVO extends BaseVO implements Serializable {


    private Integer id;

    /**
     * 投资订单编号
     *
     * @mbggenerated
     */
    private String tenderId;

    /**
     * 转账订单编号，每次转账时生成
     *
     * @mbggenerated
     */
    private String transferId;

    /**
     * 还款状态，0：未还款，1：已还款
     *
     * @mbggenerated
     */
    private Integer recoverStatus;

    /**
     * 收益领取状态，1：未回款，2：未领取，3：转账中,4：转账失败，5：已领取，6：已过期
     *
     * @mbggenerated
     */
    private Integer receivedFlg;

    /**
     * 分期还款的期数，未分期的默认为：1
     *
     * @mbggenerated
     */
    private Integer recoverPeriod;

    /**
     * 转账时间，手动领取收益时，调用汇付接口之前，需更新的时间
     *
     * @mbggenerated
     */
    private Integer transferTime;

    /**
     * 应还款时间，放款时生成
     *
     * @mbggenerated
     */
    private Integer recoverTime;

    /**
     * 已还款时间，还款时生成，体验金的场合为用户的手动领取时间
     *
     * @mbggenerated
     */
    private Integer recoverYestime;

    /**
     * 相关联的真实本金投资的还款时间，用于体验金收益过期判断
     *
     * @mbggenerated
     */
    private Integer mainRecoverYestime;

    /**
     * 应还利息,放款时生成
     *
     * @mbggenerated
     */
    private BigDecimal recoverInterest;

    /**
     * 已还利息
     *
     * @mbggenerated
     */
    private BigDecimal recoverInterestYes;

    /**
     * 应还本息
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccount;

    /**
     * 已还本息
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccountYes;

    /**
     * 应还本金
     *
     * @mbggenerated
     */
    private BigDecimal recoverCapital;

    /**
     * 已还本金
     *
     * @mbggenerated
     */
    private BigDecimal recoverCapitalYes;

    /**
     * 分期付款的场合，1：还款期，0：非还款期
     *
     * @mbggenerated
     */
    private Integer currentRecoverFlg;

    /**
     * 还款类别  1：直投类，2：汇添金
     *
     * @mbggenerated
     */
    private Integer recoverType;

    /**
     * 通知用户标识，0：未通知，1：已通知
     *
     * @mbggenerated
     */
    private Integer noticeFlg;

    /**
     * 收益过期时间
     *
     * @mbggenerated
     */
    private Integer expTime;

    /**
     * 删除标识  0：未删除，1：已删除
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

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId == null ? null : tenderId.trim();
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId == null ? null : transferId.trim();
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public Integer getReceivedFlg() {
        return receivedFlg;
    }

    public void setReceivedFlg(Integer receivedFlg) {
        this.receivedFlg = receivedFlg;
    }

    public Integer getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(Integer recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public Integer getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Integer transferTime) {
        this.transferTime = transferTime;
    }

    public Integer getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Integer recoverTime) {
        this.recoverTime = recoverTime;
    }

    public Integer getRecoverYestime() {
        return recoverYestime;
    }

    public void setRecoverYestime(Integer recoverYestime) {
        this.recoverYestime = recoverYestime;
    }

    public Integer getMainRecoverYestime() {
        return mainRecoverYestime;
    }

    public void setMainRecoverYestime(Integer mainRecoverYestime) {
        this.mainRecoverYestime = mainRecoverYestime;
    }

    public BigDecimal getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(BigDecimal recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public BigDecimal getRecoverInterestYes() {
        return recoverInterestYes;
    }

    public void setRecoverInterestYes(BigDecimal recoverInterestYes) {
        this.recoverInterestYes = recoverInterestYes;
    }

    public BigDecimal getRecoverAccount() {
        return recoverAccount;
    }

    public void setRecoverAccount(BigDecimal recoverAccount) {
        this.recoverAccount = recoverAccount;
    }

    public BigDecimal getRecoverAccountYes() {
        return recoverAccountYes;
    }

    public void setRecoverAccountYes(BigDecimal recoverAccountYes) {
        this.recoverAccountYes = recoverAccountYes;
    }

    public BigDecimal getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(BigDecimal recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public BigDecimal getRecoverCapitalYes() {
        return recoverCapitalYes;
    }

    public void setRecoverCapitalYes(BigDecimal recoverCapitalYes) {
        this.recoverCapitalYes = recoverCapitalYes;
    }

    public Integer getCurrentRecoverFlg() {
        return currentRecoverFlg;
    }

    public void setCurrentRecoverFlg(Integer currentRecoverFlg) {
        this.currentRecoverFlg = currentRecoverFlg;
    }

    public Integer getRecoverType() {
        return recoverType;
    }

    public void setRecoverType(Integer recoverType) {
        this.recoverType = recoverType;
    }

    public Integer getNoticeFlg() {
        return noticeFlg;
    }

    public void setNoticeFlg(Integer noticeFlg) {
        this.noticeFlg = noticeFlg;
    }

    public Integer getExpTime() {
        return expTime;
    }

    public void setExpTime(Integer expTime) {
        this.expTime = expTime;
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
