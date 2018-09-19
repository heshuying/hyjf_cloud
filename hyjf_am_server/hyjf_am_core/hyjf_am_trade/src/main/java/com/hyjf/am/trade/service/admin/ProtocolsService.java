/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.ProtocolsRequest;
import com.hyjf.am.trade.dao.model.customize.FddTempletCustomize;

import java.util.List;

/**
 * @author fuqiang
 * @version ProtocolsService, v0.1 2018/7/11 9:05
 */
public interface ProtocolsService {
	/**
	 * 获取协议列表
	 *
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	List<FddTempletCustomize> selectFddTempletList(int limitStart, int limitEnd);

	/**
	 * 添加协议列表
	 *
	 * @param request
	 */
	void insertAction(ProtocolsRequest request);

	/**
	 * 插入协议列表
	 *
	 * @param request
	 */
	void updateAction(ProtocolsRequest request);

	/**
	 * 取得新规的模板编号
	 * @param protocolType
	 * @return
	 */
    String getNewTempletId(Integer protocolType);

	/**
	 * 协议管理-画面迁移
	 *
	 * @param id
	 * @return
	 */
    FddTempletCustomize getRecordInfoById(Integer id);
}
