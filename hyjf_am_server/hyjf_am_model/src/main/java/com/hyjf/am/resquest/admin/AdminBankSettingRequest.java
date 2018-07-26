/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.paginator.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author dangzw
 * @version AdminBankSettingRequest, v0.1 2018/7/24 22:38
 */
public class AdminBankSettingRequest extends BasePage {

    /**
     * 前台时间接收
     */
    private String ids;

    private String startCreate;

    private String endCreate;

    public String getStartCreate() {
        return startCreate;
    }

    public void setStartCreate(String startCreate) {
        this.startCreate = startCreate;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getEndCreate() {
        return endCreate;
    }

    public void setEndCreate(String endCreate) {
        this.endCreate = endCreate;
    }

    private Integer id;

    private Integer bankId;

    private String bankName;

    private String payAllianceCode;

    private String bankCode;

    private String bankIcon;

    private String bankLogo;

    private Integer quickPayment;

    private BigDecimal singleQuota;

    private BigDecimal singleCardQuota;

    private BigDecimal feeWithdraw;

    private Integer sortId;

    private String remark;

    private Integer createUser;

    private Integer createTime;

    private Integer updateUser;

    private Integer updateTime;

    private Integer delFlg;

    private BigDecimal monthCardQuota;

    private JxBankConfigVO jxBankConfig;

    private static final long serialVersionUID = 1L;

    public JxBankConfigVO getJxBankConfig() {
        return jxBankConfig;
    }

    public void setJxBankConfig(JxBankConfigVO jxBankConfig) {
        this.jxBankConfig = jxBankConfig;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getPayAllianceCode() {
        return payAllianceCode;
    }

    public void setPayAllianceCode(String payAllianceCode) {
        this.payAllianceCode = payAllianceCode == null ? null : payAllianceCode.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBankIcon() {
        return bankIcon;
    }

    public void setBankIcon(String bankIcon) {
        this.bankIcon = bankIcon == null ? null : bankIcon.trim();
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo == null ? null : bankLogo.trim();
    }

    public Integer getQuickPayment() {
        return quickPayment;
    }

    public void setQuickPayment(Integer quickPayment) {
        this.quickPayment = quickPayment;
    }

    public BigDecimal getSingleQuota() {
        return singleQuota;
    }

    public void setSingleQuota(BigDecimal singleQuota) {
        this.singleQuota = singleQuota;
    }

    public BigDecimal getSingleCardQuota() {
        return singleCardQuota;
    }

    public void setSingleCardQuota(BigDecimal singleCardQuota) {
        this.singleCardQuota = singleCardQuota;
    }

    public BigDecimal getFeeWithdraw() {
        return feeWithdraw;
    }

    public void setFeeWithdraw(BigDecimal feeWithdraw) {
        this.feeWithdraw = feeWithdraw;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public BigDecimal getMonthCardQuota() {
        return monthCardQuota;
    }

    public void setMonthCardQuota(BigDecimal monthCardQuota) {
        this.monthCardQuota = monthCardQuota;
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

    public com.hyjf.common.paginator.Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(com.hyjf.common.paginator.Paginator paginator) {
        this.paginator = paginator;
    }

}
