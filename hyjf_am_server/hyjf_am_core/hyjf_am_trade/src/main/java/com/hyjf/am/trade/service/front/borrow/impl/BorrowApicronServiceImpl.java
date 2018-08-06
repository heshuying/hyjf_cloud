/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowApicronMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.BorrowApicronExample;
import com.hyjf.am.trade.service.front.borrow.BorrowApicronService;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ${yaoy}
 * @version BorrowApicronServiceImpl, v0.1 2018/6/14 16:48
 */
@Service
public class BorrowApicronServiceImpl implements BorrowApicronService {

	@Autowired
	private BorrowApicronMapper borrowApicronMapper;

	@Override
	public List<BorrowApicron> getBorrowApicronList(Integer extraYieldRepayStatus, Integer apiType) {
		BorrowApicronExample example = new BorrowApicronExample();
		BorrowApicronExample.Criteria criteria = example.createCriteria();
		// 标的还款已完成
		criteria.andStatusEqualTo(6);
		criteria.andExtraYieldStatusEqualTo(extraYieldRepayStatus);
		criteria.andApiTypeEqualTo(apiType);
		example.setOrderByClause(" id asc ");
		List<BorrowApicron> list = this.borrowApicronMapper.selectByExample(example);
		return list;
	}

	@Override
	public int updateBorrowApicron(Integer id, Integer status, String data) {
		BorrowApicron record = new BorrowApicron();
		record.setId(id);
		record.setExtraYieldStatus(status);
		if (Validator.isNotNull(data) || status == 1) {
			record.setData(data);
		}
		if (record.getWebStatus() == null) {
			record.setWebStatus(0);
		}
		return this.borrowApicronMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateBorrowApicron(Integer id, Integer status) {
		BorrowApicron record = new BorrowApicron();
		record.setId(id);
		record.setExtraYieldStatus(status);
		if (record.getWebStatus() == null) {
			record.setWebStatus(0);
		}
		return this.borrowApicronMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<BorrowApicron> getBorrowApicronListWithRepayStatus(Integer status, Integer apiType) {
		BorrowApicronExample example = new BorrowApicronExample();
		BorrowApicronExample.Criteria criteria = example.createCriteria();
		criteria.andExtraYieldRepayStatusEqualTo(status);
		criteria.andApiTypeEqualTo(apiType);
		example.setOrderByClause(" id asc ");
		List<BorrowApicron> list = this.borrowApicronMapper.selectByExample(example);
		return list;
	}

	@Override
	public int updateBorrowApicronOfRepayStatus(Integer id, Integer status) {
		BorrowApicron record = new BorrowApicron();
		record.setId(id);
		record.setExtraYieldRepayStatus(status);
		return this.borrowApicronMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<BorrowApicron> selectBorrowApicronListByBorrowNid(String borrowNid) {
		BorrowApicronExample example = new BorrowApicronExample();
		example.createCriteria().andBorrowNidEqualTo(borrowNid).andApiTypeEqualTo(1).andStatusNotEqualTo(6);
		return this.borrowApicronMapper.selectByExample(example);
	}

	/**
	 * 根据bankSeqNo检索
	 * @auther: hesy
	 * @date: 2018/7/17
	 */
	@Override
	public BorrowApicron selectBorrowApicron(String bankSeqNo) {
		BorrowApicronExample example = new BorrowApicronExample();
		example.createCriteria().andBankSeqNoEqualTo(bankSeqNo);
		List<BorrowApicron> apicronList = this.borrowApicronMapper.selectByExample(example);
		if (apicronList != null && apicronList.size() == 1) {
			return apicronList.get(0);
		}
		return null;
	}
}
