/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AllocationEngineClient;
import com.hyjf.admin.service.AllocationEngineService;
import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.vo.admin.HjhRegionVO;

/**
 * @author libin
 * @version AllocationEngineServiceImpl.java, v0.1 2018年7月3日 下午12:52:06
 */
@Service
public class AllocationEngineServiceImpl implements  AllocationEngineService{

	@Autowired
	//专区跟配置共用AllocationEngineClient
	public AllocationEngineClient allocationEngineClient;
	
	@Override
	public List<HjhRegionVO> getHjhRegionList(AllocationEngineRuquest form) {
		List<HjhRegionVO> list = allocationEngineClient.getHjhRegionList(form);
		return list;
	}

	@Override
	public String getPlanNameByPlanNid(AllocationEngineRuquest form) {
		String planName = allocationEngineClient.getPlanNameByPlanNid(form);
		return planName;
	}

	@Override
	public int insertRecord(HjhRegionVO request) {
		int flg = allocationEngineClient.insertRecord(request);
		return flg;
	}
}
