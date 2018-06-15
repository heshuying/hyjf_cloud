/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.vo.trade.IncreaseInterestInvestVo;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVo;
import com.hyjf.am.vo.trade.borrow.BorrowWithBLOBsVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.trade.client.RtbBatchClient;
import com.hyjf.cs.trade.service.RtbLoansBatchService;


/**
 * @author ${yaoy}
 * @version RtbLoansBatchServiceImpl, v0.1 2018/6/12 15:52
 */
@Service
public class RtbLoansBatchServiceImpl implements RtbLoansBatchService {

    @Autowired
    RtbBatchClient rtbBatchClient;

    @Override
    public List<BorrowApicronVo> getBorrowApicronList(Integer status, Integer apiType) {
        return rtbBatchClient.getBorrowApicronList(status,apiType);
    }

    @Override
    public List<IncreaseInterestInvestVo> getBorrowTenderList(String borrowNid) {
        return rtbBatchClient.getBorrowTenderList(borrowNid);
    }

    @Override
    public void updateBorrowApicron(Integer id, Integer status, String data) {
        rtbBatchClient.updateBorrowApicron(id,status,data);
    }

    @Override
    public Integer updateBorrowApicron2(Integer id, Integer status) {
        return rtbBatchClient.updateBorrowApicron2(id,status);
    }

    @Override
    public AccountVO getAccountByUserId(Integer borrowUserId) {
        AccountVO accountVO =rtbBatchClient.getAccountByUserId(borrowUserId);
        return accountVO;
    }

    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer borrowUserId) {
        return rtbBatchClient.getBankOpenAccount(borrowUserId);
    }

    @Override
    public BorrowWithBLOBsVO getBorrow(String borrowNid) {
        return rtbBatchClient.getBorrow(borrowNid);
    }

    @Override
    public void updateBorrowTender(IncreaseInterestInvestVo borrowTender) {
        rtbBatchClient.updateBorrowTender(borrowTender);
    }

    @Override
    public void updateBorrowLoans(BorrowApicronVo apicron, IncreaseInterestInvestVo borrowTender) throws Exception {
        rtbBatchClient.updateBorrowLoans(apicron,borrowTender);
    }

    @Override
    public List<BorrowApicronVo> getBorrowApicronListWithRepayStatus(Integer status, Integer apiType) {
        return rtbBatchClient.getBorrowApicronListWithRepayStatus(status,apiType);
    }

    @Override
    public void updateBorrowApicronOfRepayStatus(Integer id, Integer status) {
        rtbBatchClient.updateBorrowApicronOfRepayStatus(id,status);
    }
}
