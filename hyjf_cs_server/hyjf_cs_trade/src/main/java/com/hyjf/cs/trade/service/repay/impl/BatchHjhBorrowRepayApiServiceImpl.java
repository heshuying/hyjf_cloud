package com.hyjf.cs.trade.service.repay.impl;

import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.bean.BatchCenterCustomize;
import com.hyjf.cs.trade.client.BorrowClient;
import com.hyjf.cs.trade.service.repay.BatchHjhBorrowRepayApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BatchHjhBorrowRepayApiServiceImpl extends BaseServiceImpl implements BatchHjhBorrowRepayApiService {

	@Autowired
	private BorrowClient borrowClient;

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
	public List<BatchCenterCustomize> selectBatchCenterList(BatchCenterCustomize batchCenterCustomize) {
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
