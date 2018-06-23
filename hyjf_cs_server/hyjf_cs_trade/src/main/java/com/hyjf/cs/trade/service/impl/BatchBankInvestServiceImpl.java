package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.vo.trade.borrow.BatchBorrowTenderCustomizeVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.cs.trade.client.BankWithdrawClient;
import com.hyjf.cs.trade.client.BatchBankInvestClient;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.BatchBankInvestService;

import java.util.List;

/**
 * @author jijun
 * @date 20180623
 */
@Service
public class BatchBankInvestServiceImpl extends BaseTradeServiceImpl implements BatchBankInvestService {
    private static final Logger logger = LoggerFactory.getLogger(BatchBankInvestServiceImpl.class);

    @Autowired
    private BatchBankInvestClient batchBankInvestClient;//银行提现掉单
    
	@Override
	public void handle() {
		List<BatchBorrowTenderCustomizeVO> list=this.batchBankInvestClient.queryAuthCodeBorrowTenderList();
		if (CollectionUtils.isNotEmpty(list)){
			logger.info("BatchBankInvestServiceImpl.run.start...");
			batchBankInvestClient.insertAuthCode(list);
			logger.info("BatchBankInvestServiceImpl.run.end...");
		}
	}
}
