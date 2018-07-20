package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.trade.BankConfigVO;

import java.util.Date;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/11.
 */
public class AdminFeeConfigRequest extends BasePage {
    private Integer id;

    private String bankCode;

    private String name;

    private String personalCredit;

    private String enterpriseCredit;

    private String quickPayment;

    private String directTakeout;

    private String directTakeoutPercent;

    private String quickTakeout;

    private String quickTakeoutPercent;

    private String normalTakeout;

    private String remark;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private List<BankConfigVO> BankConfigList;

    public List<BankConfigVO> getBankConfigList() {
        return BankConfigList;
    }

    public void setBankConfigList(List<BankConfigVO> bankConfigList) {
        BankConfigList = bankConfigList;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalCredit() {
        return personalCredit;
    }

    public void setPersonalCredit(String personalCredit) {
        this.personalCredit = personalCredit;
    }

    public String getEnterpriseCredit() {
        return enterpriseCredit;
    }

    public void setEnterpriseCredit(String enterpriseCredit) {
        this.enterpriseCredit = enterpriseCredit;
    }

    public String getQuickPayment() {
        return quickPayment;
    }

    public void setQuickPayment(String quickPayment) {
        this.quickPayment = quickPayment;
    }

    public String getDirectTakeout() {
        return directTakeout;
    }

    public void setDirectTakeout(String directTakeout) {
        this.directTakeout = directTakeout;
    }

    public String getDirectTakeoutPercent() {
        return directTakeoutPercent;
    }

    public void setDirectTakeoutPercent(String directTakeoutPercent) {
        this.directTakeoutPercent = directTakeoutPercent;
    }

    public String getQuickTakeout() {
        return quickTakeout;
    }

    public void setQuickTakeout(String quickTakeout) {
        this.quickTakeout = quickTakeout;
    }

    public String getQuickTakeoutPercent() {
        return quickTakeoutPercent;
    }

    public void setQuickTakeoutPercent(String quickTakeoutPercent) {
        this.quickTakeoutPercent = quickTakeoutPercent;
    }

    public String getNormalTakeout() {
        return normalTakeout;
    }

    public void setNormalTakeout(String normalTakeout) {
        this.normalTakeout = normalTakeout;
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
    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private com.hyjf.common.paginator.Paginator paginator;

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }
}
