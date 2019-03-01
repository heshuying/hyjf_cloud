package com.hyjf.cs.trade.service.batch;

import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @author jijun
 * @version 20180623
 */
public interface BatchBankInvestService extends BaseTradeService {

	void insertAuthCode();
	/**
	 * 自动放款复审任务开始
	 * @return
	 */
	void hjhautoreview();
}
