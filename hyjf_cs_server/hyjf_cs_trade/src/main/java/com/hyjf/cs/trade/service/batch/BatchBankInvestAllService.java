package com.hyjf.cs.trade.service.batch;

import com.hyjf.cs.trade.service.BaseTradeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 出借全部掉单异常处理
 * @author jijun
 * @version 20180623
 */
public interface BatchBankInvestAllService extends BaseTradeService {

	void updateTender();

	/**
	 * 投资全部掉单异常处理
	 */
	public void recharge();
   
	
}
