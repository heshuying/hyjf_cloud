/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import java.util.List;

import com.hyjf.am.resquest.admin.HjhLabelInfoRequest;
import com.hyjf.am.resquest.admin.HjhLabelRequest;
import com.hyjf.am.vo.admin.HjhLabelCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

/**
 * @author libin
 * @version HjhLabelService.java, v0.1 2018年6月30日 上午9:17:41
 */
public interface HjhLabelService {
	// 项目类型
	List<BorrowProjectTypeVO>  getBorrowProjectTypeList();
	
	// 还款方式
	List<BorrowStyleVO> getBorrowStyleList();
	
	// 标签配置列表
	List<HjhLabelCustomizeVO> getHjhLabelList(HjhLabelRequest request);
	
	// 标签配置列表
	List<HjhLabelCustomizeVO> getHjhLabelListById(HjhLabelRequest request);
	
	// 标签配置列表
	List<HjhLabelCustomizeVO> getHjhLabelListByLabelName(HjhLabelRequest request);
	
	// 插入标签配置列表
	void insertHjhLabelRecord(HjhLabelInfoRequest hjhLabelInfoRequest);
	
	// 更新标签配置列表
	int updateHjhLabelRecord(HjhLabelInfoRequest hjhLabelInfoRequest);
	
	// 更新分配引擎表
	int updateAllocationRecord(HjhLabelInfoRequest hjhLabelInfoRequest);
}
