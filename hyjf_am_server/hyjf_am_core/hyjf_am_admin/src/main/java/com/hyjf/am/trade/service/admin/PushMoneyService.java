/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import java.util.List;

import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.trade.dao.model.auto.PushMoney;

/**
 * @author fuqiang
 * @version PushMoneyService, v0.1 2018/7/10 19:21
 */
public interface PushMoneyService {
	/**
	 * 获取提成列表
	 *
	 * @return
	 */
	List<PushMoney> getRecordList();

	/**
	 * 添加提成配置
	 *
	 * @param request
	 */
	void insertPushMoney(PushMoneyRequest request);

	/**
	 * 添加提成配置
	 *
	 * @param request
	 */
	void updatePushMoney(PushMoneyRequest request);
}
