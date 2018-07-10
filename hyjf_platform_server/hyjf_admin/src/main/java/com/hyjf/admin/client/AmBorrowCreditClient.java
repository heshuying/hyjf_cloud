package com.hyjf.admin.client;


import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.am.resquest.admin.BorrowCreditAmRequest;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.admin.BorrowCreditVO;

import java.util.List;

public interface AmBorrowCreditClient {


    public List<BorrowCreditVO> getBorrowCreditList(BorrowCreditAmRequest request);

    public Integer getBorrowCreditCount(BorrowCreditAmRequest request);

    public BorrowCreditSumVO getBorrwoCreditTotalSum(BorrowCreditAmRequest request);
}
