/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: SubCommissionRequest, v0.1 2018/7/10 10:43
 */
@ApiModel(value = "平台账户分佣请求参数")
public class SubCommissionRequest extends BasePage implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1251849043086260227L;

    @ApiModelProperty(value = "转入用户名(检索用)")
    private String receiveUserNameSrch;

    @ApiModelProperty(value = "转入姓名(发起分佣)")
    private String truename;

    @ApiModelProperty(value = "订单号(检索用)")
    private String orderIdSrch;

    @ApiModelProperty(value = "转账状态(检索用)")
    private String tradeStatusSrch;

    @ApiModelProperty(value = "添加时间开始(检索用)")
    private String timeStartSrch;

    @ApiModelProperty(value = "添加时间结束(检索用)")
    private String timeEndSrch;

    @ApiModelProperty(value = "转出方用户电子账户号(发起分佣)")
    private String accountId;

    @ApiModelProperty(value = "收款方用户ID(发起分佣)")
    private Integer receiveUserId;

    @ApiModelProperty(value = "收款方用户名(发起分佣)")
    private String receiveUserName;

    @ApiModelProperty(value = "收款方用户电子账户号(发起分佣)")
    private String receiveAccountId;

    @ApiModelProperty(value = "分佣金额(发起分佣)")
    private String txAmount;

    @ApiModelProperty(value = "交易密码(发起分佣)")
    private String password;

    @ApiModelProperty(value = "账户余额(发起分佣)")
    private String balance;

    @ApiModelProperty(value = "备注说明(发起分佣)")
    private String remark;

    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    // 请求银行的bean
    private BankCallBeanVO requestBean;

    // 银行返回的bean
    private BankCallBeanVO resultBean;

    // 调用银行接口成功
    private boolean callBankSuccess;

    // 银行返回的错误代码对应的错误信息
    private String errorMsg;

    // 网站收支  账户分佣时用
    private AccountWebListVO accountWebList;

    // 登录者信息 账户分佣时用
    private AdminSystemVO adminSystemVO;

    public String getReceiveUserNameSrch() {
        return receiveUserNameSrch;
    }

    public void setReceiveUserNameSrch(String receiveUserNameSrch) {
        this.receiveUserNameSrch = receiveUserNameSrch;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getOrderIdSrch() {
        return orderIdSrch;
    }

    public void setOrderIdSrch(String orderIdSrch) {
        this.orderIdSrch = orderIdSrch;
    }

    public String getTradeStatusSrch() {
        return tradeStatusSrch;
    }

    public void setTradeStatusSrch(String tradeStatusSrch) {
        this.tradeStatusSrch = tradeStatusSrch;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Integer receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getReceiveAccountId() {
        return receiveAccountId;
    }

    public void setReceiveAccountId(String receiveAccountId) {
        this.receiveAccountId = receiveAccountId;
    }

    public String getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public BankCallBeanVO getRequestBean() {
        return requestBean;
    }

    public void setRequestBean(BankCallBeanVO requestBean) {
        this.requestBean = requestBean;
    }

    public BankCallBeanVO getResultBean() {
        return resultBean;
    }

    public void setResultBean(BankCallBeanVO resultBean) {
        this.resultBean = resultBean;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isCallBankSuccess() {
        return callBankSuccess;
    }

    public void setCallBankSuccess(boolean callBankSuccess) {
        this.callBankSuccess = callBankSuccess;
    }

    public AccountWebListVO getAccountWebList() {
        return accountWebList;
    }

    public void setAccountWebList(AccountWebListVO accountWebList) {
        this.accountWebList = accountWebList;
    }

    public AdminSystemVO getAdminSystemVO() {
        return adminSystemVO;
    }

    public void setAdminSystemVO(AdminSystemVO adminSystemVO) {
        this.adminSystemVO = adminSystemVO;
    }
}
