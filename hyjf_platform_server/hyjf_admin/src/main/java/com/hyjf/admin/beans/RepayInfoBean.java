package com.hyjf.admin.beans;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.admin.AdminRepayDelayCustomizeVO;

import java.math.BigDecimal;

/**
 * @author pangchengchao
 * @version DelayRepayInfoBean, v0.1 2018/7/6 15:45
 */
public class RepayInfoBean {

    private AdminRepayDelayCustomizeVO borrowRepayInfo;
    private BigDecimal balance;
    private BaseVO repayInfo;

    public AdminRepayDelayCustomizeVO getBorrowRepayInfo() {
        return borrowRepayInfo;
    }

    public void setBorrowRepayInfo(AdminRepayDelayCustomizeVO borrowRepayInfo) {
        this.borrowRepayInfo = borrowRepayInfo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BaseVO getRepayInfo() {
        return repayInfo;
    }

    public void setRepayInfo(BaseVO repayInfo) {
        this.repayInfo = repayInfo;
    }
}
