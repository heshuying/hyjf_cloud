package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;

/**
 * @author jun
 * @since 20180620
 */
public class TenderCancelRequest extends Request  {

    private BorrowTenderTmpVO borrowTenderTmpVO;

    private String userName;

    public void setBorrowTenderTmpVO(BorrowTenderTmpVO borrowTenderTmpVO) {
        this.borrowTenderTmpVO = borrowTenderTmpVO;
    }

    public BorrowTenderTmpVO getBorrowTenderTmpVO() {
        return borrowTenderTmpVO;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
