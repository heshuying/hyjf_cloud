/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;


import java.util.List;

import com.hyjf.am.vo.trade.IncreaseInterestInvestVo;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVo;
import com.hyjf.am.vo.trade.borrow.BorrowWithBLOBsVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;

/**
 * @author ${yaoy}
 * @version RtbLoansBatchService, v0.1 2018/6/12 16:02
 */
public interface RtbLoansBatchService {

    List<BorrowApicronVo> getBorrowApicronList(Integer status, Integer apiType);

    List<IncreaseInterestInvestVo> getBorrowTenderList(String borrowNid);

    void updateBorrowApicron(Integer id, Integer status, String data);

    Integer updateBorrowApicron2(Integer id, Integer status);

    AccountVO getAccountByUserId(Integer borrowUserId);

    BankOpenAccountVO getBankOpenAccount(Integer borrowUserId);

    BorrowWithBLOBsVO getBorrow(String borrowNid);

    void updateBorrowTender(IncreaseInterestInvestVo borrowTender);

    void updateBorrowLoans(BorrowApicronVo apicron, IncreaseInterestInvestVo borrowTender) throws Exception;

    List<BorrowApicronVo> getBorrowApicronListWithRepayStatus(Integer status, Integer apiType);

    void updateBorrowApicronOfRepayStatus(Integer id, Integer status);
}
