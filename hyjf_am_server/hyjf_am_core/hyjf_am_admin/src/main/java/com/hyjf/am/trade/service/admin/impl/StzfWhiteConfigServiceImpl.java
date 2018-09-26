/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.impl;

import java.util.List;

import com.hyjf.am.trade.dao.mapper.customize.StzhWhiteListCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.STZHWhiteListCustomize;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.admin.STZHWhiteListRequest;
import com.hyjf.am.trade.dao.mapper.auto.StzhWhiteListMapper;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteList;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteListExample;
import com.hyjf.am.trade.service.admin.StzfWhiteConfigService;

/**
 * @author fuqiang
 * @version StzfWhiteConfigServiceImpl, v0.1 2018/7/10 15:25
 */
@Service
public class StzfWhiteConfigServiceImpl implements StzfWhiteConfigService {
	@Autowired
	private StzhWhiteListMapper stzhWhiteListMapper;
	@Autowired
	private StzhWhiteListCustomizeMapper stzhWhiteListCustomizeMapper;

	@Override
	public List<StzhWhiteList> selectSTZHWhiteList(STZHWhiteListRequest request) {
		StzhWhiteListExample example = new StzhWhiteListExample();
		if (request != null && request.getLimitStart() > 0 && request.getLimitEnd() > 0) {
			example.setLimitStart(request.getLimitStart());
			example.setLimitEnd(request.getLimitEnd());
		}
		return stzhWhiteListMapper.selectByExample(example);
	}

	@Override
	public void insertSTZHWhiteList(STZHWhiteListRequest request) {
		StzhWhiteList stzhWhiteList = new StzhWhiteList();
		BeanUtils.copyProperties(request, stzhWhiteList);
		stzhWhiteListMapper.insert(stzhWhiteList);
	}

	@Override
	public void updateSTZHWhiteList(STZHWhiteListRequest request) {
		StzhWhiteList stzhWhiteList = new StzhWhiteList();
		BeanUtils.copyProperties(request, stzhWhiteList);
		stzhWhiteListMapper.updateByPrimaryKey(stzhWhiteList);
	}

    @Override
    public STZHWhiteListCustomize selectInfo(STZHWhiteListCustomize request) {
        return stzhWhiteListCustomizeMapper.selectInfo(request);
    }

	@Override
	public StzhWhiteList selectStzfWhiteById(Integer id) {
		StzhWhiteList pushMoney = stzhWhiteListMapper.selectByPrimaryKey(id);
		return pushMoney;
	}
}
