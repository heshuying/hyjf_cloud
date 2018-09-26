package com.hyjf.cs.trade.service.repay.impl;

import com.hyjf.am.vo.trade.borrow.BatchCenterCustomizeVO;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.bean.BatchCenterCustomize;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.repay.BatchHjhBorrowRepayApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BatchHjhBorrowRepayApiServiceImpl extends BaseServiceImpl implements BatchHjhBorrowRepayApiService {

	@Autowired
	private AmTradeClient borrowClient;

	/**
	 * countBatchCenter
	 */
	@Override
	public Long countBatchCenter(BatchCenterCustomize batchCenterCustomize) {
		return borrowClient.countBatchCenter(batchCenterCustomize);
	}

	/**
	 * selectBatchCenterList
	 */
	@Override
	public List<BatchCenterCustomizeVO> selectBatchCenterList(BatchCenterCustomize batchCenterCustomize) {
		return borrowClient.selectBatchCenterList(batchCenterCustomize);
	}

	/**
	 * searchRepayProjectDetail
	 */
	@Override
	public String getborrowIdByProductId(String productId,String instCode)  {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId",productId);
		params.put("instCode",instCode);
		return borrowClient.getborrowIdByProductId(params);
	}
}
