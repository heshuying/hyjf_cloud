package com.hyjf.cs.trade.bean;



/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date  
 */
public class SynBalanceResultBean extends BaseResultBean {
    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 1L;

    /**
     *余额数据
     */
    private String bankBalance;
    
    /**
     * 账户总额数据
     */
    private String bankTotal;
    
    /**
     * 未初始化的余额数据
     */
    private String originalBankBalance;
    
    /**
     * 未初始化的账户总额数据
     */
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
