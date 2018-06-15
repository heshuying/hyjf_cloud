/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;



import com.hyjf.am.vo.rtbbatch.BorrowApicronVo;
import com.hyjf.am.vo.rtbbatch.BorrowWithBLOBsVo;
import com.hyjf.am.vo.rtbbatch.IncreaseInterestInvestVo;
import com.hyjf.am.vo.trade.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;

import java.util.List;

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

    BorrowWithBLOBsVo getBorrow(String borrowNid);

    void updateBorrowTender(IncreaseInterestInvestVo borrowTender);

    void updateBorrowLoans(BorrowApicronVo apicron, IncreaseInterestInvestVo borrowTender) throws Exception;

    List<BorrowApicronVo> getBorrowApicronListWithRepayStatus(Integer status, Integer apiType);

    void updateBorrowApicronOfRepayStatus(Integer id, Integer status);
}
