package com.hyjf.cs.trade.service.batch;

import com.hyjf.cs.common.service.BaseService;

/**
 * 投资撤销异常service
 * @author jijun
 * @since 20180625
 */
public interface BankTenderCancelExceptionService extends BaseService{

	void handle();

}
