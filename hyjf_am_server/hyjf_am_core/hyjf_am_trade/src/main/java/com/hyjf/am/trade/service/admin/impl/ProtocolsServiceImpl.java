/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.ProtocolsRequest;
import com.hyjf.am.trade.dao.mapper.auto.FddTempletMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.FddTempletCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.FddTemplet;
import com.hyjf.am.trade.dao.model.customize.admin.FddTempletCustomize;
import com.hyjf.am.trade.service.admin.ProtocolsService;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
		return customizeMapper.selectFddTempletList(fddTemplet);
	}

	@Override
	public void insertAction(ProtocolsRequest request) {
		FddTemplet record = new FddTemplet();
		BeanUtils.copyProperties(request, record);
		int nowTime = GetDate.getNowTime10();
		record.setCertificateTime(nowTime);//认证时间
		// 登陆信息
		record.setCreateTime(new Date());
		fddTempletMapper.insertSelective(record);
	}

	@Override
	public void updateAction(ProtocolsRequest request) {
		FddTemplet record = new FddTemplet();
		BeanUtils.copyProperties(request, record);
		int nowTime = GetDate.getNowTime10();
		record.setCertificateTime(nowTime);//认证时间
		// 登陆信息
		record.setCreateTime(new Date());
		fddTempletMapper.updateByPrimaryKeySelective(record);
	}
}
