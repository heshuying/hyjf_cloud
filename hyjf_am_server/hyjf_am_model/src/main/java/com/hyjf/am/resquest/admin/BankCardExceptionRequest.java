/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: BankCardExceptionRequest, v0.1 2018/8/14 14:32
 */
@ApiModel(value = "银行卡异常列表请求参数")
public class BankCardExceptionRequest extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id，更新银行卡用")
    private String userId;
    @ApiModelProperty(value = "用户名(检索用)")
    private String usernameSearch;
    @ApiModelProperty(value = "所属银行代码(检索用)")
    private String bankSearch;
    @ApiModelProperty(value = "银行账号(检索用)")
    private String accountSearch;
    @ApiModelProperty(value = "默认提现卡(检索用)")
    private String isdefaultSearch;
    @ApiModelProperty(value = "银行卡属性(检索用)")
    private String bankShuxingSearch;
    @ApiModelProperty(value = "查询添加时间开始日期(检索用)")
    private String startDateSearch;
    @ApiModelProperty(value = "查询添加时间结束日期(检索用)")
    private String endDateSearch;
    @ApiModelProperty(value = "分页limit开始(后台用)")
    private int limitStart = -1;
    @ApiModelProperty(value = "分页limit结束(后台用)")
    private int limitEnd = -1;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsernameSearch() {
        return usernameSearch;
    }

    public void setUsernameSearch(String usernameSearch) {
        this.usernameSearch = usernameSearch;
    }

    public String getBankSearch() {
        return bankSearch;
    }

    public void setBankSearch(String bankSearch) {
        this.bankSearch = bankSearch;
    }

    public String getAccountSearch() {
        return accountSearch;
    }

    public void setAccountSearch(String accountSearch) {
        this.accountSearch = accountSearch;
    }

    public String getIsdefaultSearch() {
        return isdefaultSearch;
    }

    public void setIsdefaultSearch(String isdefaultSearch) {
        this.isdefaultSearch = isdefaultSearch;
    }

    public String getBankShuxingSearch() {
        return bankShuxingSearch;
    }

    public void setBankShuxingSearch(String bankShuxingSearch) {
        this.bankShuxingSearch = bankShuxingSearch;
    }

    public String getStartDateSearch() {
        return startDateSearch;
    }

    public void setStartDateSearch(String startDateSearch) {
        this.startDateSearch = startDateSearch;
    }

    public String getEndDateSearch() {
        return endDateSearch;
    }

    public void setEndDateSearch(String endDateSearch) {
        this.endDateSearch = endDateSearch;
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
}
