/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import java.util.List;

import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.vo.admin.HjhRegionVO;

/**
 * @author libin
 * @version AdminAllocationEngineService.java, v0.1 2018年7月3日 下午1:34:25
 */
public interface AdminAllocationEngineService {
   /**
	 * 计划专区条数
	 * @return
	*/
   Integer countHjhRegionRecordTotal(AllocationEngineRuquest request);
   /**
	 * 计划专区列表
	 * @return
	*/
   List<HjhRegionVO> selectHjhRegionList(AllocationEngineRuquest request, int limitStart, int limitEnd);
   /**
	 * 查询汇计划表
	 * @return
	*/
   String selectPlanNameByPlanNid(AllocationEngineRuquest request);
   
   /**
	 * 插入计划专区表
	 * @return
	*/
   int insertRecord(HjhRegionVO request);
}
