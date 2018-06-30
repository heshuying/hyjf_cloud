/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import java.util.List;

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
}
