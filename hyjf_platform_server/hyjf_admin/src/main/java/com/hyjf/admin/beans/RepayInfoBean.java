package com.hyjf.admin.beans;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.admin.AdminRepayDelayCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author pangchengchao
 * @version DelayRepayInfoBean, v0.1 2018/7/6 15:45
 */
public class RepayInfoBean {

    private AdminRepayDelayCustomizeVO borrowRepayInfo;
    private BigDecimal balance;
    private BaseVO repayInfo;
    private List<BorrowRepaymentPlanCustomizeVO> list;
    
    
    public List<BorrowRepaymentPlanCustomizeVO> getList() {
		return list;
	}

	public void setList(List<BorrowRepaymentPlanCustomizeVO> list) {
		this.list = list;
	}

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
