/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.auto.HjhRegion;
import com.hyjf.am.vo.admin.HjhAllocationEngineVO;
import com.hyjf.am.vo.admin.HjhRegionVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

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
   
   List<HjhPlan> getHjhPlanByPlanNid(String planNid);
   
   List<HjhRegion> getHjhRegionByPlanNid(String planNid);
   
   HjhRegionVO selectHjhRegionVOById(String id);
   
   /**
	 * 更新计划专区表
	 * @return
	*/
   int updateHjhRegionRecord(HjhRegionVO request);
   
   /**
	 * 更新引擎表
	 * @return
	*/
   int updateAllocationEngineRecord(String planNid, Integer configStatus);
   
   /**
	 * 计划专区列表
	 * @return
	*/
   List<HjhRegionVO> selectHjhRegionListWithOutPage(AllocationEngineRuquest request);
   
   /**
	 * 计划专区引擎列表
	 * @return
	*/ 
   List<HjhAllocationEngineVO> selectHjhAllocationEngineList(String planNidSrch);
   
   /**
	 * 计划引擎列表
	 * @return
	*/
   List<HjhAllocationEngineVO> selectHjhAllocationEngineListByPage(AllocationEngineRuquest request, int limitStart, int limitEnd);
   
   HjhAllocationEngineVO selectPlanConfigRecord(Integer engineId);
   
   
   /**
	 * 更新计划专区表
	 * @return
	*/
   int updateHjhAllocationEngineRecord(HjhAllocationEngineVO request);
   
   
   HjhAllocationEngineVO selectPlanConfigRecordByParam(String planNid,String labelId); 
   
   boolean checkRepeat(String labelName,String planNid);
   
   String getPlanBorrowStyle(String planNid);
   
   HjhRegionVO  getHjhRegionRecordByPlanNid(String planNid);
   
   /**
	 * 插入计划引擎表
	 * @return
	*/
   int insertHjhAllocationEngineRecord(HjhAllocationEngineVO request);
   
   List<HjhPlanVO> selectHjhPlanByPlanNid(String planNid);
   
   List<HjhRegionVO> selectHjhRegioByPlanNid(String planNid);
}
