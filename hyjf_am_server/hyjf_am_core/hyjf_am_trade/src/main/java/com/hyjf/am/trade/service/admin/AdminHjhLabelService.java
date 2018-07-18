/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import java.util.List;

import com.hyjf.am.resquest.admin.HjhLabelInfoRequest;
import com.hyjf.am.resquest.admin.HjhLabelRequest;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.vo.admin.HjhLabelCustomizeVO;
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
   /**
	 * 集合总数
	 * @return
	*/
   Integer countRecordTotal(HjhLabelRequest request);
   /**
	 * 标签配置列表
	 * @return
	*/
   List<HjhLabelCustomizeVO> selectHjhLabelList(HjhLabelRequest request, int limitStart, int limitEnd);
   
   /**
	 * 标签配置列表
	 * @return
	*/
   List<HjhLabelCustomizeVO> selectHjhLabelListById(HjhLabelRequest request);  
   
   /**
	 * 标签配置列表
	 * @return
	*/
   List<HjhLabelCustomizeVO> selectHjhLabelListLabelName(HjhLabelRequest request);
   
	/**
    * 插入标签配置列表
    *
    * @param request
    */
   void insertHjhLabelRecord(HjhLabelInfoRequest request);
   
	/**
    * 更新标签配置列表
    *
    * @param request
    */
   int updateHjhLabelRecord(HjhLabelInfoRequest request);
   
	/**
    * 更新标签配置列表
    *
    * @param request
    */
   int updateAllocationRecord(HjhLabelInfoRequest request);

   /**
    * 获取最优标签
    */
	HjhLabel getBestLabel(Borrow borrow , BorrowInfo borrowInfo, HjhPlanAsset hjhPlanAsset);
}
