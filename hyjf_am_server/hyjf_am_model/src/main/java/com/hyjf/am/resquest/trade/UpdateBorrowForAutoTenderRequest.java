/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;

/**
 * @author liubin
 * @version UpdateCreditForAutoTenderRequest, v0.1 2018/7/5 18:12
 */
public class UpdateBorrowForAutoTenderRequest {
    private BorrowVO borrowVO;
    private HjhAccedeVO hjhAccedeVO;
    private BankCallBeanVO bankCallBeanVO;

    public UpdateBorrowForAutoTenderRequest() {
    }

    public UpdateBorrowForAutoTenderRequest(BorrowVO borrowVO, HjhAccedeVO hjhAccedeVO, BankCallBeanVO bankCallBeanVO) {
        this.borrowVO = borrowVO;
        this.hjhAccedeVO = hjhAccedeVO;
        this.bankCallBeanVO = bankCallBeanVO;
    }

    public BorrowVO getBorrowVO() {
        return borrowVO;
    }

    public void setBorrowVO(BorrowVO borrowVO) {
        this.borrowVO = borrowVO;
    }

    public HjhAccedeVO getHjhAccedeVO() {
        return hjhAccedeVO;
    }

    public void setHjhAccedeVO(HjhAccedeVO hjhAccedeVO) {
        this.hjhAccedeVO = hjhAccedeVO;
    }

    public BankCallBeanVO getBankCallBeanVO() {
        return bankCallBeanVO;
    }

    public void setBankCallBeanVO(BankCallBeanVO bankCallBeanVO) {
        this.bankCallBeanVO = bankCallBeanVO;
    }
}
