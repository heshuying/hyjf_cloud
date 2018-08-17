/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import java.util.List;

import com.hyjf.am.response.admin.HjhLabelCustomizeResponse;
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
	
	/**
	 * 获取项目类型
	 *
	 * @param 
	 * @return List<BorrowProjectTypeVO>
	 */
	List<BorrowProjectTypeVO>  getBorrowProjectTypeList();
	
	/**
	 * 获取还款方式
	 *
	 * @param 
	 * @return List<BorrowStyleVO>
	 */
	List<BorrowStyleVO> getBorrowStyleList();
	
	/**
	 * 查询标签配置列表
	 *
	 * @param  request
	 * @return HjhLabelCustomizeResponse
	 */
	HjhLabelCustomizeResponse getHjhLabelList(HjhLabelRequest request);
	
	/**
	 * 查询标签配置列表ById
	 *
	 * @param  request
	 * @return List<HjhLabelCustomizeVO>
	 */
	List<HjhLabelCustomizeVO> getHjhLabelListById(HjhLabelRequest request);
	
	/**
	 * 查询标签配置列表ByLabelName
	 *
	 * @param  request
	 * @return List<HjhLabelCustomizeVO>
	 */
	List<HjhLabelCustomizeVO> getHjhLabelListByLabelName(HjhLabelRequest request);
	
	/**
	 * 插入标签配置列表ByLabelName
	 *
	 * @param  request
	 * @return 
	 */
	int insertHjhLabelRecord(HjhLabelInfoRequest hjhLabelInfoRequest);
	
	/**
	 * 更新标签配置列表ByLabelName
	 *
	 * @param  request
	 * @return 
	 */
	int updateHjhLabelRecord(HjhLabelInfoRequest hjhLabelInfoRequest);
	
	/**
	 * 更新
	 *
	 * @param  request
	 * @return 
	 */
	int updateAllocationRecord(HjhLabelInfoRequest hjhLabelInfoRequest);
}
