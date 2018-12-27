package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * @author lisheng
 * @version NaMiMarketingVO, v0.1 2018/12/26 14:58
 */

public class NaMiMarketingVO extends BaseVO {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6455442854066228801L;

    private Integer id;

    /**
     * 账户户名 检索条件
     */
    private String username;
    /**
     * 姓名 检索条件
     */
    private String truename;

    /**
     * 邀请人账户名  检索条件
     */
    private String refferName;
    /**
     * 注册日期时间  检索条件
     */
    private String regTime;
    /**
     * 投资订单号
     */
    private String tenderNo;
    /**
     * 单笔投资金额
     */
    private BigDecimal tenderAmount;
    /**
     * 单笔投资期限
     */
    private String term;
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 产品编号
     */
    private String productNo;
    /**
     * 单笔当月产生的业绩
     */
    private BigDecimal returnPerformance;
    /**
     * 单笔返现金额
     */
    private BigDecimal returnAmount;
    /**
     * 部门名
     */
    private String debtName;
    /**
     * 部门id
     */
    private Integer debtId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getRefferName() {
        return refferName;
    }

    public void setRefferName(String refferName) {
        this.refferName = refferName;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getTenderNo() {
        return tenderNo;
    }

    public void setTenderNo(String tenderNo) {
        this.tenderNo = tenderNo;
    }

    public BigDecimal getTenderAmount() {
        return tenderAmount;
    }

    public void setTenderAmount(BigDecimal tenderAmount) {
        this.tenderAmount = tenderAmount;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public BigDecimal getReturnPerformance() {
        return returnPerformance;
    }

    public void setReturnPerformance(BigDecimal returnPerformance) {
        this.returnPerformance = returnPerformance;
    }

    public BigDecimal getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getDebtName() {
        return debtName;
    }

    public void setDebtName(String debtName) {
        this.debtName = debtName;
    }

    public Integer getDebtId() {
        return debtId;
    }

    public void setDebtId(Integer debtId) {
        this.debtId = debtId;
    }

}
