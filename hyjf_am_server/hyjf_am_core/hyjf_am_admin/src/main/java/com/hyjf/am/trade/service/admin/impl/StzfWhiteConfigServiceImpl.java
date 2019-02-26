/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.STZHWhiteListRequest;
import com.hyjf.am.resquest.config.STZHWhiteListRequestBean;
import com.hyjf.am.trade.dao.mapper.auto.StzhWhiteListMapper;
import com.hyjf.am.trade.dao.mapper.customize.StzhWhiteListCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteList;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteListExample;
import com.hyjf.am.trade.dao.model.customize.STZHWhiteListCustomize;
import com.hyjf.am.trade.service.admin.StzfWhiteConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public List<StzhWhiteList> selectSTZHWhiteList(STZHWhiteListRequestBean request, int limitStart, int limitEnd) {
		StzhWhiteListExample example = new StzhWhiteListExample();
		if (limitStart != -1) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
		return stzhWhiteListMapper.selectByExample(example);
	}

	@Override
	public void insertSTZHWhiteList(STZHWhiteListRequestBean request) {
		StzhWhiteList stzhWhiteList = new StzhWhiteList();
		BeanUtils.copyProperties(request, stzhWhiteList);
		stzhWhiteList.setDelFlag(0);
		stzhWhiteList.setInstCode(request.getInstcode());
		stzhWhiteList.setUpdateUserId(Integer.parseInt(request.getUpdateuser()));
		stzhWhiteList.setCreateUserId(Integer.valueOf(request.getCreateuser()));
		stzhWhiteListMapper.insert(stzhWhiteList);
	}

	@Override
	public void updateSTZHWhiteList(STZHWhiteListRequestBean request) {
		StzhWhiteList stzhWhiteList = new StzhWhiteList();
		BeanUtils.copyProperties(request, stzhWhiteList);
		if (request.getDelFlag() == null) {
			stzhWhiteList.setDelFlag(0);
		}
		stzhWhiteList.setInstCode(request.getInstcode());
		stzhWhiteList.setUpdateUserId(Integer.parseInt(request.getUpdateuser()));
		stzhWhiteList.setCreateUserId(Integer.valueOf(request.getCreateuser()));
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

	@Override
	public int countSTZFHWhiteList(STZHWhiteListRequestBean request) {
		StzhWhiteListExample example = new StzhWhiteListExample();
		int count = stzhWhiteListMapper.countByExample(example);
		return count;
	}
}
