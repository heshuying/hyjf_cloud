/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.trade.dao.mapper.auto.PushMoneyMapper;
import com.hyjf.am.trade.dao.model.auto.PushMoney;
import com.hyjf.am.trade.dao.model.auto.PushMoneyExample;
import com.hyjf.am.trade.service.admin.PushMoneyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
		return pushMoneyMapper.selectByExampleWithBLOBs(new PushMoneyExample());
	}

	@Override
	public List<PushMoney> getRecordList(int limitStart, int limitEnd) {
		PushMoneyExample example = new PushMoneyExample();
		if (limitStart != -1) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
		return pushMoneyMapper.selectByExampleWithBLOBs(example);
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

    @Override
    public PushMoney getRecordById(Integer id) {
		PushMoneyExample example = new PushMoneyExample();
		example.createCriteria().andIdEqualTo(id);
		List<PushMoney> pushMonies = pushMoneyMapper.selectByExampleWithBLOBs(example);
		if (pushMonies.size() == 0){
			return null;
		}
		return pushMonies.get(0);
    }

	@Override
	public void deleteRecord (List<Integer> ids) {
		for (Integer id : ids) {
			pushMoneyMapper.deleteByPrimaryKey(id);
		}
	}
}
