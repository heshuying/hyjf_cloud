/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.HjhAllocationEngineResponse;
import com.hyjf.am.response.admin.HjhRegionResponse;
import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.vo.admin.HjhAllocationEngineVO;
import com.hyjf.am.vo.admin.HjhRegionVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

import java.util.List;
/**
 * @author libin
 * @version AllocationEngineService.java, v0.1 2018年7月3日 上午11:50:37
 */
public interface AllocationEngineService {
	
    /**
     * 获取计划专区列表
     * @return
     */
	HjhRegionResponse getHjhRegionList(AllocationEngineRuquest form);

    /**
     * 根据入力的计划编号查询汇计划表
     * @return
     */
    String getPlanNameByPlanNid(AllocationEngineRuquest form);
    
    /**
     * info画面入力后插入计划专区表
     * @return
     */
    int insertRecord(HjhRegionVO request);
    /**
     * 校验入力的计划编号相关
     * @return
     */
	HjhRegionResponse getPlanNidAjaxCheck(String planNid);  
	
    /**
     * 根据主键获取 HjhRegionVO
     * @return
     */
	HjhRegionVO getHjhRegionVOById(String id);
	
    /**
     * 更新计划专区表的状态
     * @return
     */
	int updateHjhRegionRecord(HjhRegionVO vo); 
    
	/**
     * 更新引擎表
     * @return
     */
	HjhRegionResponse updateAllocationEngineRecord(HjhRegionVO vo);
	
	/**
     * 获取计划专区列表
     * @return
     */
	List<HjhRegionVO>  getHjhRegionListWithOutPage(AllocationEngineRuquest request);
	
    /**
     * 获取计划引擎列表
     * @return
     */
	HjhAllocationEngineResponse getHjhAllocationEngineList(AllocationEngineRuquest form);
	
    /**
     * 获取计划引擎列表
     * @return
     */
	List<HjhAllocationEngineVO>  getAllocationList(AllocationEngineRuquest form);
	
    /**
     * 根据主键获取 HjhAllocationEngineVO
     * @return
     */
	HjhAllocationEngineVO getPlanConfigRecord(Integer engineId);
	
    /**
     * 更新计划引擎表的状态
     * @return
     */
	int updateHjhAllocationEngineRecord(HjhAllocationEngineVO vo); 
	
    /**
     * 根据参数获取 HjhAllocationEngineVO
     * @return
     */
	HjhAllocationEngineVO getPlanConfigRecordByParam(AllocationEngineRuquest form);
	
    /**
     * 根据参数获取 HjhAllocationEngineVO
     * @return
     */
	HjhAllocationEngineVO getPlanConfigRecordByPlanNidLabelName(AllocationEngineRuquest form);
	
    /**
     * 验证重复
     * 
     * @param planName
     */
	int checkRepeat(AllocationEngineRuquest form);
    
    /**
     * 先查询汇计划表获取该计划的还款方式
     * 
     * @param planNid
     */
    String getPlanBorrowStyle(String planNid);
    
    /**
     * 根据计划编号查询计划专区
     * 
     * @param planNid
     */
    HjhRegionVO getHjhRegionRecordByPlanNid(String planNid);
    
    /**
     * 计划引擎配置Info画面入力后插表
     * 
     * @param planNid
     */
    int insertHjhAllocationEngineRecord(HjhAllocationEngineVO newForm);
    
    /**
     * 通过计划编号获取计划列表
     * 
     * @param planNid
     */
    List<HjhPlanVO> getHjhPlanByPlanNid(String planNid);
    
    /**
     * 通过计划编号获取计划专区
     * 
     * @param planNid
     */
    List<HjhRegionVO> getHjhRegioByPlanNid(String planNid);
}
