package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.trade.dao.model.auto.BorrowUser;
import com.hyjf.am.trade.dao.model.auto.BorrowUserExample;
import com.hyjf.am.trade.service.front.borrow.BorrowUserService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 借款人公司信息
 * @Author zhangyk upd by liushouyi
 */
@Service
public class BorrowUserServiceImpl extends BaseServiceImpl implements BorrowUserService {

	/**
	 * 根据借款编号获取借款人公司信息
	 *
	 * @param borrowNid
	 * @return
	 */
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
