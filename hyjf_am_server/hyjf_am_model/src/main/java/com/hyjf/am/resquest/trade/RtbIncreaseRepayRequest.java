package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;

/**
 * @author xiasq
 * @version RtbIncreaseRepayRequest, v0.1 2018/6/19 17:34
 */
public class RtbIncreaseRepayRequest extends Request {
    // 借还款任务
	private BorrowApicronVO borrowApicronVO;
	// 投资人电子账号
	private String account;
	// 企业电子账号 红包账户
	private String companyAccount;

    public BorrowApicronVO getBorrowApicronVO() {
        return borrowApicronVO;
    }

    public void setBorrowApicronVO(BorrowApicronVO borrowApicronVO) {
        this.borrowApicronVO = borrowApicronVO;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }
}
