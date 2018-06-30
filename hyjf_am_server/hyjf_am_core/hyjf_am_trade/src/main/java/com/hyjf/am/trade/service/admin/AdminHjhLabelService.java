/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import java.util.List;

import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

/**
 * @author Albert
 * @version AdminHjhLabelService.java, v0.1 2018年6月30日 上午10:44:28
 */
public interface AdminHjhLabelService {
   /**
	 * 还款方式
	 * @return
	 */
	List<BorrowStyleVO> selectBorrowStyleList();
   /**
	 * 项目类型
	 * @return
	 */
	List<BorrowProjectTypeVO> selectBorrowProjectByBorrow();
}
