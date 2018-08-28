/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.autotender;

import com.hyjf.am.resquest.api.AutoTenderComboRequest;

/**
 * @author libin
 * @version ApiAutoTenderService.java, v0.1 2018年8月27日 上午10:09:10
 */
public interface ApiAutoTenderService {
	/**
	 * 调用汇付天下接口前操作
	 * 
	 * @param borrowId
	 *            借款id
	 * @param userId
	 *            用户id
	 * @param account
	 *            投资金额
	 * @param ip
	 *            投资人ip
	 * @param couponGrantId 
	 * @param tenderUserName 
	 * @return 操作是否成功
	 * @throws Exception 
	 */
	Integer updateTenderLog(AutoTenderComboRequest autoTenderComboRequest);

}
