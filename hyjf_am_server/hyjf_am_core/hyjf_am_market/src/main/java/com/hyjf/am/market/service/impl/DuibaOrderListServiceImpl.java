package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.customize.market.DuibaOrdersCustomizeMapper;
import com.hyjf.am.market.service.DuibaOrderListService;
import com.hyjf.am.vo.admin.DuibaOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version AdsImpl, v0.1 2018/4/19 15:42
 */
@Service
public class DuibaOrderListServiceImpl implements DuibaOrderListService {

	@Autowired
	private DuibaOrdersCustomizeMapper duibaOrdersCustomizeMapper;

	@Override
	public Integer updateOneOrderByPrimaryKey(DuibaOrderVO duibaOrderVO){
		return duibaOrdersCustomizeMapper.updateOneOrderByPrimaryKey(duibaOrderVO);
	}

	@Override
	public DuibaOrderVO selectOrderByOrderId(String duibaOrderId){
		return duibaOrdersCustomizeMapper.selectOrderByOrderId(duibaOrderId);
	}

}
