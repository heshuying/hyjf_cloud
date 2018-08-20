package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * 充值管理
 * @Author : huanghui
 */
public class AccountRechargeRequestBean extends BasePage {

    @ApiModelProperty(value = "充值起始时间")
    private String startDate;

    @ApiModelProperty(value = "充值结束时间")
    private String endDate;

    @ApiModelProperty(value = "电子账号")
    private String accountIdSearch;

    @ApiModelProperty(value = "资金托管平台")
    private String isBankSearch;

    @ApiModelProperty(value = "流水号")
    private String seqNoSearch;

    @ApiModelProperty(value = "客户端类型")
    private String clientTypeSearch;

    @ApiModelProperty(value = "用户名")
    private String usernameSearch;

    @ApiModelProperty(value = "用户属性")
    private String userProperty;

    @ApiModelProperty(value = "订单号")
    private String nidSearch;

    @ApiModelProperty(value = "充值状态")
    private String statusSearch;

    @ApiModelProperty(value = "银行号")
    private String bankCodeSearch;

    @ApiModelProperty(value = "充值渠道")
    private String typeSearch;

    @ApiModelProperty(value = "充值手续费收取方式:0,向用户收取.1,向商户收取")
    private String getfeefromSearch;

    @ApiModelProperty(value = "用户角色:1,投资人.2,借款人.3,垫付机构.")
    private String roleIdSearch;



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

}
