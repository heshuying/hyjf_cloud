/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.trade.dao.mapper.auto.PushMoneyMapper;
import com.hyjf.am.trade.dao.model.auto.PushMoney;
import com.hyjf.am.trade.dao.model.auto.PushMoneyExample;
import com.hyjf.am.trade.service.admin.PushMoneyService;

/**
 * @author fuqiang
 * @version PushMoneyServiceImpl, v0.1 2018/7/10 19:22
 */
@Service
public class PushMoneyServiceImpl implements PushMoneyService {
	@Autowired
	private PushMoneyMapper pushMoneyMapper;

	@Override
	public List<PushMoney> getRecordList() {
		return pushMoneyMapper.selectByExample(new PushMoneyExample());
	}

	@Override
	public void insertPushMoney(PushMoneyRequest request) {
		PushMoney pushMoney = new PushMoney();
		BeanUtils.copyProperties(request, pushMoney);
		pushMoneyMapper.insert(pushMoney);
	}

	@Override
	public void updatePushMoney(PushMoneyRequest request) {
		PushMoney pushMoney = new PushMoney();
		BeanUtils.copyProperties(request, pushMoney);
		pushMoneyMapper.updateByPrimaryKey(pushMoney);
	}
}
