package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.am.vo.trade.borrow.BatchBorrowTenderCustomizeVO;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.BatchBankInvestService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jijun
 * @date 20180623
 */
@Service
public class BatchBankInvestServiceImpl extends BaseTradeServiceImpl implements BatchBankInvestService {
    private static final Logger logger = LoggerFactory.getLogger(BatchBankInvestServiceImpl.class);

    @Autowired
    private AmTradeClient amTradeClient;//银行提现掉单
    
	@Override
	public void insertAuthCode() {
		List<BatchBorrowTenderCustomizeVO> list=this.amTradeClient.queryAuthCodeBorrowTenderList();
		if (CollectionUtils.isNotEmpty(list)){
			logger.info("BatchBankInvestServiceImpl.run.start...");
			amTradeClient.insertAuthCode(list);
			logger.info("BatchBankInvestServiceImpl.run.end...");
		}
	}
	/**
	 * 自动放款复审任务开始
	 * @return
	 */
	@Override
	public void hjhautoreview() {
		logger.info("自动放款复审任务BatchBankInvestServiceImpl.run.start...");
		this.amTradeClient.hjhautoreview();
		logger.info("自动放款复审任务BatchBankInvestServiceImpl.run.end...");
	}
}
