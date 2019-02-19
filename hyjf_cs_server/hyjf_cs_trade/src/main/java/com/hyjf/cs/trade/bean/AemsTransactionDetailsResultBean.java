package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Aems 交易明细查询
 * @Author : Zhadaojian
 */
public class AemsTransactionDetailsResultBean extends BaseBean implements Serializable {

    public static final Integer PAGE_STRAT = 0;
    public static final Integer PAGE_MAXSIZE = 100;

    @ApiModelProperty(value = "交易开始时间(必传) 暂定 2017-10-01(不带时分秒)")
    public String startDate;

    @ApiModelProperty(value = "交易结束时间(必传) 暂定2017-10-07")
    public String endDate;

    @ApiModelProperty(value = "手机号(必传) 校验手机号会否合法")
    public String phone;

    @ApiModelProperty(value = "电子账号(必传) 通过手机号验重")
    private String accountId;

    @ApiModelProperty(value = "订单号(选传)")
    private String nid;

    @ApiModelProperty(value = "用户名(通过必传手机号查询")
    private String username;

    @ApiModelProperty(value = "资金托管平台(默认) 默认为1 江西银行")
    private String isBank;

    @ApiModelProperty(value = "交易状态(选传) 0失败，1成功 2:冲正是否是数字验证")
    private String tradeStatus;

    @ApiModelProperty(value = "收支类型(选传) 1收入2支出3冻结4解冻")
    private String typeSearch;

    @ApiModelProperty(value = "交易类型ID(选传) 选几个值固定给宽策略 是否是数字验证")
    private String tradeTypeSearch;

    @ApiModelProperty(value = "当前时间戳（10位）")
    private Long timestamp;

    @ApiModelProperty(value = "第三方接口机构编号")
    private String instCode;

    @ApiModelProperty(value = "检索开始行(必传默认0)")
    private Integer limitStart = PAGE_STRAT;

    @ApiModelProperty(value = "检索步长(必传默认100)")
    private Integer limitEnd = PAGE_MAXSIZE;

    public static Integer getPageStrat() {
        return PAGE_STRAT;
    }

    public static Integer getPageMaxsize() {
        return PAGE_MAXSIZE;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsBank() {
        return isBank;
    }

    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTypeSearch() {
        return typeSearch;
    }

    public void setTypeSearch(String typeSearch) {
        this.typeSearch = typeSearch;
    }

    public String getTradeTypeSearch() {
        return tradeTypeSearch;
    }

    public void setTradeTypeSearch(String tradeTypeSearch) {
        this.tradeTypeSearch = tradeTypeSearch;
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getInstCode() {
        return instCode;
    }

    @Override
    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    @Override
    public Integer getLimitStart() {
        return limitStart;
    }

    @Override
    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    @Override
    public Integer getLimitEnd() {
        return limitEnd;
    }

    @Override
    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }
}
