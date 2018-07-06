/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AllocationEngineClient;
import com.hyjf.admin.service.AllocationEngineService;
import com.hyjf.am.response.admin.HjhAllocationEngineResponse;
import com.hyjf.am.response.admin.HjhRegionResponse;
import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.vo.admin.HjhAllocationEngineVO;
import com.hyjf.am.vo.admin.HjhRegionVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

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
	public HjhRegionResponse getHjhRegionList(AllocationEngineRuquest form) {
		HjhRegionResponse list = allocationEngineClient.getHjhRegionList(form);
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

	@Override
	public HjhRegionResponse getPlanNidAjaxCheck(String planNid) {
		HjhRegionResponse response = allocationEngineClient.getPlanNidAjaxCheck(planNid);
		return response;
	}

	@Override
	public HjhRegionVO getHjhRegionVOById(String id) {
		HjhRegionVO vo = allocationEngineClient.getHjhRegionVOById(id);
		return vo;
	}

	@Override
	public int updateHjhRegionRecord(HjhRegionVO vo) {
		int flg = allocationEngineClient.updateHjhRegionRecord(vo);
		return flg;
	}

	@Override
	public HjhRegionResponse updateAllocationEngineRecord(HjhRegionVO vo) {
		HjhRegionResponse response = allocationEngineClient.updateAllocationEngineRecord(vo);
		return response;
	}

	@Override
	public List<HjhRegionVO> getHjhRegionListWithOutPage(AllocationEngineRuquest request) {
		List<HjhRegionVO> list = allocationEngineClient.getHjhRegionListWithOutPage(request);
		return list;
	}

	@Override
	public HjhAllocationEngineResponse getHjhAllocationEngineList(AllocationEngineRuquest form ) {
		HjhAllocationEngineResponse response = allocationEngineClient.getHjhAllocationEngineList(form);
		return response;
	}

	@Override
	public List<HjhAllocationEngineVO> getAllocationList(AllocationEngineRuquest form) {
		List<HjhAllocationEngineVO> list = allocationEngineClient.getAllocationList(form);
		return list;
	}

	@Override
	public HjhAllocationEngineVO getPlanConfigRecord(Integer engineId) {
		HjhAllocationEngineVO vo = allocationEngineClient.getPlanConfigRecord(engineId);
		return vo;
	}

	@Override
	public int updateHjhAllocationEngineRecord(HjhAllocationEngineVO vo) {
		int flg = allocationEngineClient.updateHjhAllocationEngineRecord(vo);
		return flg;
	}

	@Override
	public HjhAllocationEngineVO getPlanConfigRecordByParam(AllocationEngineRuquest form) {
		HjhAllocationEngineVO vo = allocationEngineClient.getPlanConfigRecordByParam(form);
		return vo;
	}

	@Override
	public boolean checkRepeat(String labelName, String planNid) {
		boolean flg = allocationEngineClient.checkRepeat(labelName,planNid);
		return flg;
	}

	@Override
	public String getPlanBorrowStyle(String planNid) {
		String borrowStyle = allocationEngineClient.getPlanBorrowStyle(planNid);
		return borrowStyle;
	}

	@Override
	public HjhRegionVO getHjhRegionRecordByPlanNid(String planNid) {
		HjhRegionVO vo = allocationEngineClient.getHjhRegionRecordByPlanNid(planNid);
		return vo;
	}

	@Override
	public int insertHjhAllocationEngineRecord(HjhAllocationEngineVO newForm) {
		int flg = allocationEngineClient.insertHjhAllocationEngineRecord(newForm);
		return flg;
	}

	@Override
	public List<HjhPlanVO> getHjhPlanByPlanNid(String planNid) {
		List<HjhPlanVO> list = allocationEngineClient.getHjhPlanByPlanNid(planNid);
		return list;
	}

	@Override
	public List<HjhRegionVO> getHjhRegioByPlanNid(String planNid) {
		List<HjhRegionVO> list = allocationEngineClient.getHjhRegioByPlanNid(planNid);
		return list;
	}
}
