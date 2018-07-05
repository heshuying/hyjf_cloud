/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import java.util.List;
import com.hyjf.am.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.AllocationEngineClient;
import com.hyjf.am.response.admin.HjhAllocationEngineResponse;
import com.hyjf.am.response.admin.HjhRegionResponse;
import com.hyjf.am.response.user.HjhPlanResponse;
import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.vo.admin.HjhAllocationEngineVO;
import com.hyjf.am.vo.admin.HjhRegionVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

/**
 * @author libin
 * @version AllocationEngineClientImpl.java, v0.1 2018年7月3日 下午12:57:32
 */
@Service
public class AllocationEngineClientImpl implements AllocationEngineClient{
	@Autowired
	private RestTemplate restTemplate;
	
    /**
     * 查询计划专区列表
     * @param request
     * @return
     */
	@Override
	public HjhRegionResponse getHjhRegionList(AllocationEngineRuquest form) {
		HjhRegionResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/selectHjhRegionList", form, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }			
		return null;
	}

	@Override
	public String getPlanNameByPlanNid(AllocationEngineRuquest form) {
		HjhRegionResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/selectPlanNameByPlanNid", form, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getPlanName();
        }	
		return null;
	}

	@Override
	public int insertRecord(HjhRegionVO request) {
		String url = "http://AM-TRADE/am-trade/allocation/insertRecord";
		Integer Flag = restTemplate.postForEntity(url,request,Integer.class).getBody();
		return Flag;
	}

	@Override
	public HjhRegionResponse getPlanNidAjaxCheck(String planNid) {
		HjhRegionResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/allocation/getPlanNidAjaxCheck/"+planNid, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }	
		return null;
	}

	@Override
	public HjhRegionVO getHjhRegionVOById(String id) {
		HjhRegionResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/allocation/getHjhRegionVOById/"+id, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }	
		return null;
	}

	@Override
	public int updateHjhRegionRecord(HjhRegionVO vo) {
		String url = "http://AM-TRADE/am-trade/allocation/updateHjhRegionRecord";
		Integer Flag = restTemplate.postForEntity(url,vo,Integer.class).getBody();
		return Flag;
	}

	@Override
	public HjhRegionResponse updateAllocationEngineRecord(HjhRegionVO vo) {
		HjhRegionResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/updateAllocationEngineRecord" , vo, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
	}

	@Override
	public List<HjhRegionVO> getHjhRegionListWithOutPage(AllocationEngineRuquest request) {
		HjhRegionResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/getHjhRegionListWithOutPage", request, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
		return null;
	}

	@Override
	public HjhAllocationEngineResponse getHjhAllocationEngineList(AllocationEngineRuquest request) {
		HjhAllocationEngineResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/getHjhAllocationEngineList", request, HjhAllocationEngineResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
		return null;
	}

	@Override
	public List<HjhAllocationEngineVO> getAllocationList(AllocationEngineRuquest form) {
		HjhAllocationEngineResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/getAllocationList", form, HjhAllocationEngineResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
		return null;	
	}

	@Override
	public HjhAllocationEngineVO getPlanConfigRecord(Integer engineId) {
		HjhAllocationEngineResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/allocation/getPlanConfigRecord/"+ engineId, HjhAllocationEngineResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
		return null;
	}

	@Override
	public int updateHjhAllocationEngineRecord(HjhAllocationEngineVO vo) {
		String url = "http://AM-TRADE/am-trade/allocation/updateHjhAllocationEngineRecord";
		Integer Flag = restTemplate.postForEntity(url,vo,Integer.class).getBody();
		return Flag;
	}

	@Override
	public HjhAllocationEngineVO getPlanConfigRecordByParam(AllocationEngineRuquest form) {
		HjhAllocationEngineResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/getPlanConfigRecordByParam", form,HjhAllocationEngineResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
		return null;
	}

	@Override
	public boolean checkRepeat(String labelName, String planNid) {
		String url = "http://AM-TRADE/am-trade/allocation/checkRepeat/" + labelName + "/" + planNid;
		boolean Flag = restTemplate.getForEntity(url,Boolean.class).getBody();
		return Flag;
	}

	@Override
	public String getPlanBorrowStyle(String planNid) {
		String url = "http://AM-TRADE/am-trade/allocation/getPlanBorrowStyle/" + planNid;
		String borrowStyle = restTemplate.getForEntity(url,String.class).getBody();
		return borrowStyle;
	}

	@Override
	public HjhRegionVO getHjhRegionRecordByPlanNid(String planNid) {
		HjhRegionResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/allocation/getHjhRegionRecordByPlanNid/"+planNid, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }	
		return null;
	}

	@Override
	public int insertHjhAllocationEngineRecord(HjhAllocationEngineVO request) {
		String url = "http://AM-TRADE/am-trade/allocation/insertHjhAllocationEngineRecord";
		Integer Flag = restTemplate.postForEntity(url,request,Integer.class).getBody();
		return Flag;
	}

	@Override
	public List<HjhPlanVO> getHjhPlanByPlanNid(String planNid) {
        HjhPlanResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/allocation/selectHjhPlanByPlanNid/" + planNid , HjhPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
		return null;
	}

	@Override
	public List<HjhRegionVO> getHjhRegioByPlanNid(String planNid) {
		HjhRegionResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/allocation/getHjhRegioByPlanNid/"+planNid, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
		return null;
	}
}
