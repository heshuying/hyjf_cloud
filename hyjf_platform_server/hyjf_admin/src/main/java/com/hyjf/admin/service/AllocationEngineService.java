/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import java.util.List;

import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.vo.admin.HjhRegionVO;
/**
 * @author libin
 * @version AllocationEngineService.java, v0.1 2018年7月3日 上午11:50:37
 */
public interface AllocationEngineService {
	
    /**
     * 获取计划专区列表
     * @return
     */
    List<HjhRegionVO> getHjhRegionList(AllocationEngineRuquest form);

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
}
