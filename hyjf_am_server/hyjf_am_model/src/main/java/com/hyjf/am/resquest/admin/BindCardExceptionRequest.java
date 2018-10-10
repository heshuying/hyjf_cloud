/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.user.BankCardVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BindCardExceptionRequest, v0.1 2018/10/9 11:15
 */
@ApiModel(value = "江西银行卡异常列表请求参数")
public class BindCardExceptionRequest extends BasePage {
    @ApiModelProperty(value = "用户名")
    private String userNameSrch;
    @ApiModelProperty(value = "银行电子账号")
    private String accountIdSrch;
    @ApiModelProperty(value = "用户id,更新时用")
    private Integer userId;
    @ApiModelProperty(value = "银行电子账号,更新时用")
    private String accountId;
    @ApiModelProperty(value = "分页limit开始(后台用)")
    private int limitStart = -1;
    @ApiModelProperty(value = "分页limit结束(后台用)")
    private int limitEnd = -1;
    @ApiModelProperty(value = "后台用")
    private List<BankCardVO> bankCardVOList;

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getAccountIdSrch() {
        return accountIdSrch;
    }

    public void setAccountIdSrch(String accountIdSrch) {
        this.accountIdSrch = accountIdSrch;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public List<BankCardVO> getBankCardVOList() {
        return bankCardVOList;
    }

    public void setBankCardVOList(List<BankCardVO> bankCardVOList) {
        this.bankCardVOList = bankCardVOList;
    }
}
