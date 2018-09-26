/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.trade.dao.model.auto.PushMoney;

import java.util.List;

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
	 * 获取提成列表(分页)
	 *
	 * @return
	 */
	List<PushMoney> getRecordList(int limitStart, int limitEnd);

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

	/**
	 * 修改校验
	 *
	 * @return
	 */
	PushMoney getRecordById(Integer id);

	/**
	 * 修改校验
	 *
	 * @return
	 */
	void deleteRecord(List<Integer> ids);
}
