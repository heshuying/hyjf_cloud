package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;

/**
 * @author hesy
 * @version BorrowAuthCustomizeMapper, v0.1 2018/6/27 15:49
 */
public interface BorrowAuthCustomizeMapper {
	/**
	 * 查询待授权标的数量
	 * @param params
	 * @return
	 */
	int countBorrowNeedAuthRecordTotal(Map<String, Object> params);
	/**
	 * 查询待授权标的标的列表
	 * @param params
	 * @return
	 */
	List<BorrowAuthCustomizeVO> searchBorrowNeedAuthList(Map<String, Object> params);
	
	// 查询标的已授权列表
    int countBorrowAuthedRecordTotal(Map<String, Object> params);
    // 查询标的已授权列表
    List<BorrowAuthCustomizeVO> searchBorrowAuthedList(Map<String, Object> params);
}
