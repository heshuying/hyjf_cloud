package com.hyjf.admin.client;


import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.am.resquest.admin.BorrowCreditAmRequest;
import com.hyjf.am.vo.admin.BorrowCreditInfoSumVO;
import com.hyjf.am.vo.admin.BorrowCreditInfoVO;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.admin.BorrowCreditVO;

import java.util.List;

public interface AmBorrowCreditClient {


     List<BorrowCreditVO> getBorrowCreditList(BorrowCreditAmRequest request);

     Integer getBorrowCreditCount(BorrowCreditAmRequest request);

     BorrowCreditSumVO getBorrwoCreditTotalSum(BorrowCreditAmRequest request);

     Integer countBorrowCreditInfo(BorrowCreditAmRequest request);

     List<BorrowCreditInfoVO> searchBorrowCreditInfoList(BorrowCreditAmRequest request);

     BorrowCreditInfoSumVO sumBorrowCreditInfoData(BorrowCreditAmRequest request);
}
