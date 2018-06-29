package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowUserMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowUser;
import com.hyjf.am.trade.dao.model.auto.BorrowUserExample;
import com.hyjf.am.trade.service.BorrowUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BorrowUserServiceImpl implements BorrowUserService {

	@Autowired
	private BorrowUserMapper borrowUserMapper;

	@Override
	public BorrowUser getBorrowUserByNid(String borrowNid) {
		BorrowUserExample example = new BorrowUserExample();
		BorrowUserExample.Criteria cri = example.createCriteria();
		cri.andBorrowNidEqualTo(borrowNid);
		List<BorrowUser> list = borrowUserMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
            return null;
        }
		return list.get(0);
	}
}
