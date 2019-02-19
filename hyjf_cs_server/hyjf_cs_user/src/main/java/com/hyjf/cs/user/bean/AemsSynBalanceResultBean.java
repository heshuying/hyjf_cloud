package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 此处为类说明
 * @author hsy
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年8月16日
 * @see 下午3:04:43
 */
public class AemsSynBalanceResultBean extends BaseResultBean {
    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "余额数据")
    private String bankBalance;

    @ApiModelProperty(value = "账户总额数据")
    private String bankTotal;

    @ApiModelProperty(value = "未初始化的余额数据")
    private String originalBankBalance;

    @ApiModelProperty(value = "未初始化的账户总额数据")
    private String originalBankTotal;

    public String getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(String bankBalance) {
        this.bankBalance = bankBalance;
    }

    public String getBankTotal() {
        return bankTotal;
    }

    public void setBankTotal(String bankTotal) {
        this.bankTotal = bankTotal;
    }

    public String getOriginalBankBalance() {
        return originalBankBalance;
    }

    public void setOriginalBankBalance(String originalBankBalance) {
        this.originalBankBalance = originalBankBalance;
    }

    public String getOriginalBankTotal() {
        return originalBankTotal;
    }

    public void setOriginalBankTotal(String originalBankTotal) {
        this.originalBankTotal = originalBankTotal;
    }

    

    
}
