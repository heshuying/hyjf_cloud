/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.ProtocolsRequestBean;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author fuqiang
 * @version ProtocolsService, v0.1 2018/7/10 16:54
 */
public interface ProtocolsService {
	/**
	 * 获取协议列表
	 *
	 * @param request
	 * @return
	 */
	FddTempletCustomizeResponse selectFddTempletList(ProtocolsRequestBean request);

	/**
	 * 添加协议列表
	 *
	 * @param requestBean
	 * @return
	 */
	FddTempletCustomizeResponse insertAction(ProtocolsRequestBean requestBean);

	/**
	 * 修改协议列表
	 *
	 * @param requestBean
	 * @return
	 */
	FddTempletCustomizeResponse updateAction(ProtocolsRequestBean requestBean);

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
	FddTempletCustomizeResponse getRecordInfo(Integer id);

	/**
	 * 协议管理-获取协议类型下拉列表
	 *
	 * @param protocolType
	 * @return
	 */
	List<ParamNameVO> getParamNameList(String protocolType);
}
