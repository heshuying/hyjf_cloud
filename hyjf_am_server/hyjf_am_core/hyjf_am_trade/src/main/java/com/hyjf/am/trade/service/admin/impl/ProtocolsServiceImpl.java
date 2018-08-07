/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.ProtocolsRequest;
import com.hyjf.am.trade.dao.mapper.auto.FddTempletMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.FddTempletCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.admin.FddTempletCustomize;
import com.hyjf.am.trade.service.admin.ProtocolsService;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version ProtocolsServiceImpl, v0.1 2018/7/10 18:15
 */
@Service
public class ProtocolsServiceImpl implements ProtocolsService {
	@Autowired
	private FddTempletMapper fddTempletMapper;
	@Autowired
	private FddTempletCustomizeMapper customizeMapper;

	@Override
	public List<FddTempletCustomize> selectFddTempletList(ProtocolsRequest request) {
		FddTempletCustomize fddTemplet = new FddTempletCustomize();
		if (request.getLimitStart() != -1) {
			fddTemplet.setLimitStart(request.getLimitStart());
			fddTemplet.setLimitEnd(request.getLimitEnd());
		}
		List<FddTempletCustomize> list = customizeMapper.selectFddTempletList(fddTemplet);
		Map<String, String> protocolType = CacheUtil.getParamNameMap("PROTOCOL_TYPE");
		for (FddTempletCustomize customize : list) {
			//todo
		}
		return null;
	}

	@Override
	public void insertAction(ProtocolsRequest request) {
		// todo
	}

	@Override
	public void updateAction(ProtocolsRequest request) {
		// todo
	}
}
