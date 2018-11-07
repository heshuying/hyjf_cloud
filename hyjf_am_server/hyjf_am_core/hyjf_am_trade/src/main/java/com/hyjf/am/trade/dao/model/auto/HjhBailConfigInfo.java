package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhBailConfigInfo implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 机构编号
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 还款类型：month 等额本息 end 按月计息，到期还本还息 endmonth 先息后本 endday 按天计息，到期还本息 principal 等额本金
     *
     * @mbggenerated
     */
    private String borrowStyle;

    /**
     * 新增授信校验 0：未勾选 1：勾选
     *
     * @mbggenerated
     */
    private Integer isNewCredit;

    /**
     * 在贷余额授信校验 0：未勾选 1：勾选
     *
     * @mbggenerated
     */
    private Integer isLoanCredit;

    /**
     * 等额本息保证金的回滚方式 0：到期回滚 1：分期回滚 2：不回滚
     *
     * @mbggenerated
     */
    private Integer repayCapitalType;

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
     * 最后修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 删除标识：机构未配置该还款方式的设1
     *
     * @mbggenerated
     */
    private Integer delFlg;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public Integer getIsNewCredit() {
        return isNewCredit;
    }

    public void setIsNewCredit(Integer isNewCredit) {
        this.isNewCredit = isNewCredit;
    }

    public Integer getIsLoanCredit() {
        return isLoanCredit;
    }

    public void setIsLoanCredit(Integer isLoanCredit) {
        this.isLoanCredit = isLoanCredit;
    }

    public Integer getRepayCapitalType() {
        return repayCapitalType;
    }

    public void setRepayCapitalType(Integer repayCapitalType) {
        this.repayCapitalType = repayCapitalType;
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

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
}