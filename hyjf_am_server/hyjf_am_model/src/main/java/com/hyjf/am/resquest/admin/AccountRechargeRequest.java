package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import java.io.Serializable;

/**
 * @Author : huanghui
 */
public class AccountRechargeRequest extends BasePage implements Serializable {

    private String startDate; // 创建时间 起始

    private String endDate; // 创建时间 结束

    private String accountIdSearch; // 电子账号
    private String isBankSearch;// 资金托管平台
    private String seqNoSearch;// 流水号
    private String clientTypeSearch; // 客户端类型

    private String usernameSearch; //
    private String userProperty;//用户属性

    private String nidSearch;// 订单号

    private String statusSearch; // 充值状态

    private String bankCodeSearch; // 银行号

    private String typeSearch;// 充值渠道

    private String getfeefromSearch;// 充值手续费收取方式,0向用户收取,1向商户收取

    private String roleIdSearch;// 充值手续费收取方式,0向用户收取,1向商户收取

    private Integer outtime;// 当前时间-充值时间，结果是秒数

    private String txDate;

    private String txTime;

    private String bankSeqNo;

    //订单编号
    private String nid;

    //用户ID
    private Integer userId;

    //订单状态
    private String status;

    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

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

    public String getGetfeefromSearch() {
        return getfeefromSearch;
    }

    public void setGetfeefromSearch(String getfeefromSearch) {
        this.getfeefromSearch = getfeefromSearch;
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
}
