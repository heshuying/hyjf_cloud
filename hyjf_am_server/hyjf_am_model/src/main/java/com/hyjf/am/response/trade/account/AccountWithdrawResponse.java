package com.hyjf.am.response.trade.account;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;

/**
 * @author pangchengchao
 * @version AccountwithdrawResponse, v0.1 2018/6/13 10:47
 */
public class AccountWithdrawResponse extends Response<AccountWithdrawVO> {
    private Integer userBorrowTenderCounte;

    public Integer getUserBorrowTenderCounte() {
        return userBorrowTenderCounte;
    }

    public void setUserBorrowTenderCounte(Integer userBorrowTenderCounte) {
        this.userBorrowTenderCounte = userBorrowTenderCounte;
    }
}
