/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import java.util.List;

import com.hyjf.am.response.admin.HjhAllocationEngineResponse;
import com.hyjf.am.response.admin.HjhRegionResponse;
import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.vo.admin.HjhAllocationEngineVO;
import com.hyjf.am.vo.admin.HjhRegionVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

/**
 * @author libin
 * @version AllocationEngineClient.java, v0.1 2018年7月3日 下午12:54:49
 */
public interface AllocationEngineClient {
	
    /**
     * 查询计划专区列表
     * @param request
     * @return
     */
	HjhRegionResponse getHjhRegionList(AllocationEngineRuquest form);

    /**
     * 查询汇计划表
     * @param request
     * @return
     */
    String getPlanNameByPlanNid(AllocationEngineRuquest form);
    
    /**
     * 插入计划专区表
     * @param request
     * @return
     */
    int insertRecord(HjhRegionVO request);
    
    /**
     * AJAX
     * @param request
     * @return
     */
    HjhRegionResponse getPlanNidAjaxCheck(String planNid);
    
    /**
     * 根据主键获取 HjhRegionVO
     * @param request
     * @return
     */
    HjhRegionVO getHjhRegionVOById(String id);
    
    /**
     * 更新计划专区表的状态
     * @param request
     * @return
     */
    int updateHjhRegionRecord(HjhRegionVO vo); 
    
    /**
     * 更新引擎表
     * @param request
     * @return
     */
    HjhRegionResponse updateAllocationEngineRecord(HjhRegionVO vo); 
    
    /**
     * 查询计划专区列表
     * @param request
     * @return
     */
    List<HjhRegionVO> getHjhRegionListWithOutPage(AllocationEngineRuquest request);
    
    /**
     * 根据计划专区列表传入计划编号查询引擎列表
     * @param request
     * @return
     */
    HjhAllocationEngineResponse getHjhAllocationEngineList(AllocationEngineRuquest form);
    
    /**
     * 根据计划专区列表传入计划编号查询引擎列表
     * @param request
     * @return
     */
    List<HjhAllocationEngineVO> getAllocationList(AllocationEngineRuquest form); 
    
    /**
     * 根据主键获取 HjhRegionVO
     * @param request
     * @return
     */
    HjhAllocationEngineVO getPlanConfigRecord(Integer engineId);
    
    /**
     * 更新计划引擎表的状态
     * @param request
     * @return
     */
    int updateHjhAllocationEngineRecord(HjhAllocationEngineVO vo); 
    
    /**
     * 根据参数获取 HjhRegionVO
     * @param request
     * @return
     */
    HjhAllocationEngineVO getPlanConfigRecordByParam(AllocationEngineRuquest form);
    
    /**
     * 验证重复
     * @param planName
     */
    boolean checkRepeat(String labelName,String planNid);
    
    /** 获取还款方式
     * @param planName
     */
    String getPlanBorrowStyle(String planNid);
    
    /** 获取计划专区
     * @param planName
     */
    HjhRegionVO getHjhRegionRecordByPlanNid(String planNid);
    
    /**
	 * 计划引擎配置Info画面入力后插表
	 * 
	 * @param planNid
	 */
    int insertHjhAllocationEngineRecord(HjhAllocationEngineVO request);
    
    /**
	 * 通过计划编号获取计划列表
	 * 
	 * @param planNid
	 */
    List<HjhPlanVO> getHjhPlanByPlanNid(String planNid);
    
    /**
	 * 通过计划编号获取计划专区列表
	 * 
	 * @param planNid
	 */
    List<HjhRegionVO> getHjhRegioByPlanNid(String planNid);
}
