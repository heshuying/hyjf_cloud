package com.hyjf.cs.trade.service.repay;

import com.hyjf.am.vo.trade.borrow.BatchCenterCustomizeVO;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.trade.bean.BatchCenterCustomize;

import java.util.List;

public interface BatchHjhBorrowRepayApiService extends BaseService {

	/**
	 *
	 * 获取批次放款列表数量
	 * @author pcc
	 * @param batchCenterCustomize
	 * @return
	 */
	Integer countBatchCenter(BatchCenterCustomize batchCenterCustomize);
	/**
	 *
	 * 获取批次放款列表
	 * @author pcc
	 * @param batchCenterCustomize
	 * @return
	 */
	List<BatchCenterCustomizeVO> selectBatchCenterList(BatchCenterCustomize batchCenterCustomize);
	/**
	 * 根据资产编号查询标的号
	 * @param productId
	 * @param channel 
	 * @return
	 */
	String getborrowIdByProductId(String productId, String channel);
}
