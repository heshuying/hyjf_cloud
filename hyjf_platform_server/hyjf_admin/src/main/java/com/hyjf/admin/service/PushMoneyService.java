/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.PushMoneyRequestBean;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.PushMoneyRequest;

/**
 * @author fuqiang
 * @version PushMoneyService, v0.1 2018/7/10 11:00
 */
public interface PushMoneyService {
	/**
	 * 获取提成配置列表
	 *
	 * @return
	 */
	PushMoneyResponse getRecordList(PushMoneyRequest requestBean);

	/**
	 * 添加提成配置
	 *
	 * @param requestBean
	 * @return
	 */
	PushMoneyResponse insertPushMoney(PushMoneyRequestBean requestBean);

	/**
	 * 修改提成配置
	 *
	 * @param requestBean
	 * @return
	 */
	PushMoneyResponse updatePushMoney(PushMoneyRequestBean requestBean);
}
