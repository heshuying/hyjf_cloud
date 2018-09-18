/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.PushMoneyRequestBean;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.PushMoneyRequest;

import java.util.List;

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

	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 *
	 * @param id
	 * @return
	 */
    PushMoneyResponse getInfoAction(Integer id);

	/**
	 * 删除配置信息
	 *
	 * @param ids
	 * @return
	 */
    PushMoneyResponse deleteRecord(List<Integer> ids);
}
