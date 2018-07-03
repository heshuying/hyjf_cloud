/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import java.util.List;

import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.vo.admin.HjhRegionVO;

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
    List<HjhRegionVO> getHjhRegionList(AllocationEngineRuquest form);

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
}
