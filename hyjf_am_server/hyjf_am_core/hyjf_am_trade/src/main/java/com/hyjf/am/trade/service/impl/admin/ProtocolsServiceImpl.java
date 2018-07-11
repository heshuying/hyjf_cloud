/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.admin.ProtocolsRequest;
import com.hyjf.am.trade.dao.mapper.auto.FddTempletMapper;
import com.hyjf.am.trade.dao.model.customize.admin.FddTempletCustomize;
import com.hyjf.am.trade.service.admin.ProtocolsService;

/**
 * @author fuqiang
 * @version ProtocolsServiceImpl, v0.1 2018/7/10 18:15
 */
@Service
public class ProtocolsServiceImpl implements ProtocolsService {
	@Autowired
	private FddTempletMapper fddTempletMapper;

	@Override
	public List<FddTempletCustomize> selectFddTempletList(ProtocolsRequest request) {
		return null;// todo
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
