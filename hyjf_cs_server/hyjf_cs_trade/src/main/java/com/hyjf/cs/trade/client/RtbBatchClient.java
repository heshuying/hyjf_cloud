/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.rtbbatch.BorrowApicronVo;
import com.hyjf.am.vo.rtbbatch.BorrowWithBLOBsVo;
import com.hyjf.am.vo.rtbbatch.IncreaseInterestInvestVo;
import com.hyjf.am.vo.trade.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;

import java.util.List;
import java.util.Map;

/**
 * @author ${yaoy}
 * @version RtbBatchClient, v0.1 2018/6/13 16:11
 */
public interface RtbBatchClient {
    List<BorrowApicronVo> getBorrowApicronList(Integer status, Integer apiType);

    List<IncreaseInterestInvestVo> getBorrowTenderList(String borrowNid);

    int updateBorrowApicron(Integer id, Integer status, String data);

    int updateBorrowApicron2(Integer id, Integer status);

    AccountVO getAccountByUserId(Integer borrowUserId);

    BankOpenAccountVO getBankOpenAccount(Integer borrowUserId);

    BorrowWithBLOBsVo getBorrow(String borrowNid);

    int updateBorrowTender(IncreaseInterestInvestVo borrowTender);

    List<Map<String, String>> updateBorrowLoans(BorrowApicronVo apicron, IncreaseInterestInvestVo borrowTender) throws Exception;

    List<BorrowApicronVo> getBorrowApicronListWithRepayStatus(Integer status, Integer apiType);

    int updateBorrowApicronOfRepayStatus(Integer id, Integer status);
}
