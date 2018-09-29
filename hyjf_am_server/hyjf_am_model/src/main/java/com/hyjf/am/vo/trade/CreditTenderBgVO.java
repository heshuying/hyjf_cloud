package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;

import java.io.Serializable;

/**
 * @Description 债转异步操作VO
 * @Author sunss
 * @Date 2018/7/6 15:37
 */
public class CreditTenderBgVO extends BaseVO implements Serializable {

    private CreditTenderLogVO creditTenderLog;
    private BorrowCreditVO borrowCredit;
    private CreditTenderVO creditTender;
    private AccountVO assignAccountNew;
    private AccountListVO assignAccountList;
    private AccountVO sellerAccountNew;
    private AccountListVO sellerAccountList;
    private BorrowRecoverVO borrowRecover;
    private BorrowRecoverPlanVO borrowRecoverPlan;
    private CreditRepayVO creditRepayVO;
    // 取得债权出让人的用户电子账户号
    private BankOpenAccountVO sellerBankAccount ;

    public BorrowCreditVO getBorrowCredit() {
        return borrowCredit;
    }

    public void setBorrowCredit(BorrowCreditVO borrowCredit) {
        this.borrowCredit = borrowCredit;
    }

    public CreditTenderVO getCreditTender() {
        return creditTender;
    }

    public void setCreditTender(CreditTenderVO creditTender) {
        this.creditTender = creditTender;
    }

    public AccountVO getAssignAccountNew() {
        return assignAccountNew;
    }

    public void setAssignAccountNew(AccountVO assignAccountNew) {
        this.assignAccountNew = assignAccountNew;
    }

    public AccountListVO getAssignAccountList() {
        return assignAccountList;
    }

    public void setAssignAccountList(AccountListVO assignAccountList) {
        this.assignAccountList = assignAccountList;
    }

    public AccountVO getSellerAccountNew() {
        return sellerAccountNew;
    }

    public void setSellerAccountNew(AccountVO sellerAccountNew) {
        this.sellerAccountNew = sellerAccountNew;
    }

    public AccountListVO getSellerAccountList() {
        return sellerAccountList;
    }

    public void setSellerAccountList(AccountListVO sellerAccountList) {
        this.sellerAccountList = sellerAccountList;
    }

    public CreditTenderLogVO getCreditTenderLog() {
        return creditTenderLog;
    }

    public void setCreditTenderLog(CreditTenderLogVO creditTenderLog) {
        this.creditTenderLog = creditTenderLog;
    }

    public BorrowRecoverVO getBorrowRecover() {
        return borrowRecover;
    }

    public void setBorrowRecover(BorrowRecoverVO borrowRecover) {
        this.borrowRecover = borrowRecover;
    }

    public BorrowRecoverPlanVO getBorrowRecoverPlan() {
        return borrowRecoverPlan;
    }

    public void setBorrowRecoverPlan(BorrowRecoverPlanVO borrowRecoverPlan) {
        this.borrowRecoverPlan = borrowRecoverPlan;
    }

    public CreditRepayVO getCreditRepayVO() {
        return creditRepayVO;
    }

    public void setCreditRepayVO(CreditRepayVO creditRepayVO) {
        this.creditRepayVO = creditRepayVO;
    }

    public BankOpenAccountVO getSellerBankAccount() {
        return sellerBankAccount;
    }

    public void setSellerBankAccount(BankOpenAccountVO sellerBankAccount) {
        this.sellerBankAccount = sellerBankAccount;
    }
}