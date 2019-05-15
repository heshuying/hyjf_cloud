package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @Author : huanghui
 */
public class AccountRechargeRequest extends BasePage implements Serializable {

    /**
     * 创建时间 起始
     */
    private String startDate;

    /**
     * 创建时间 结束
     */
    private String endDate;

    /**
     * 电子账号
     */
    private String accountIdSearch;

    /**
     * 资金托管平台
     */
    private String isBankSearch;

    /**
     * 流水号
     */
    private String seqNoSearch;

    /**
     * 客户端类型
     */
    private String clientTypeSearch;

    /**
     * 用户名
     */
    private String usernameSearch;

    /**
     * 用户属性
     */
    private String userProperty;

    /**
     * 订单号
     */
    private String nidSearch;

    /**
     * 充值状态
     */
    private String statusSearch;

    /**
     * 银行号
     */
    private String bankCodeSearch;

    /**
     * 充值渠道
     */
    private String typeSearch;

    /**
     * 用户角色
     */
    private String roleIdSearch;

    /**
     * 当前时间-充值时间，结果是秒数
     */
    private Integer outtime;

    private String txDate;

    private String txTime;

    private String bankSeqNo;

    /**
     * 标的NID
     */
    private String nid;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    //机构编码(温金投机构）
    private String instCode;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAccountIdSearch() {
        return accountIdSearch;
    }

    public void setAccountIdSearch(String accountIdSearch) {
        this.accountIdSearch = accountIdSearch;
    }

    public String getIsBankSearch() {
        return isBankSearch;
    }

    public void setIsBankSearch(String isBankSearch) {
        this.isBankSearch = isBankSearch;
    }

    public String getSeqNoSearch() {
        return seqNoSearch;
    }

    public void setSeqNoSearch(String seqNoSearch) {
        this.seqNoSearch = seqNoSearch;
    }

    public String getClientTypeSearch() {
        return clientTypeSearch;
    }

    public void setClientTypeSearch(String clientTypeSearch) {
        this.clientTypeSearch = clientTypeSearch;
    }

    public String getUsernameSearch() {
        return usernameSearch;
    }

    public void setUsernameSearch(String usernameSearch) {
        this.usernameSearch = usernameSearch;
    }

    public String getUserProperty() {
        return userProperty;
    }

    public void setUserProperty(String userProperty) {
        this.userProperty = userProperty;
    }

    public String getNidSearch() {
        return nidSearch;
    }

    public void setNidSearch(String nidSearch) {
        this.nidSearch = nidSearch;
    }

    public String getStatusSearch() {
        return statusSearch;
    }

    public void setStatusSearch(String statusSearch) {
        this.statusSearch = statusSearch;
    }

    public String getBankCodeSearch() {
        return bankCodeSearch;
    }

    public void setBankCodeSearch(String bankCodeSearch) {
        this.bankCodeSearch = bankCodeSearch;
    }

    public String getTypeSearch() {
        return typeSearch;
    }

    public void setTypeSearch(String typeSearch) {
        this.typeSearch = typeSearch;
    }

    public String getRoleIdSearch() {
        return roleIdSearch;
    }

    public void setRoleIdSearch(String roleIdSearch) {
        this.roleIdSearch = roleIdSearch;
    }

    public Integer getOuttime() {
        return outtime;
    }

    public void setOuttime(Integer outtime) {
        this.outtime = outtime;
    }

    public String getTxDate() {
        return txDate;
    }

    public void setTxDate(String txDate) {
        this.txDate = txDate;
    }

    public String getTxTime() {
        return txTime;
    }

    public void setTxTime(String txTime) {
        this.txTime = txTime;
    }

    public String getBankSeqNo() {
        return bankSeqNo;
    }

    public void setBankSeqNo(String bankSeqNo) {
        this.bankSeqNo = bankSeqNo;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }
}
