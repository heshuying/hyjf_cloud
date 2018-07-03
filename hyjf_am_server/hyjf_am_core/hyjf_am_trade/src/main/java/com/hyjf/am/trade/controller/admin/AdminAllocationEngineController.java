/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.util.StringUtils;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhRegionResponse;
import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.trade.service.admin.AdminAllocationEngineService;
import com.hyjf.am.vo.admin.HjhRegionVO;
import com.hyjf.common.paginator.Paginator;

import io.swagger.annotations.Api;

/**
 * @author libin
 * @version AdminAllocationEngineController.java, v0.1 2018年7月3日 下午1:31:17
 */
@Api(value = "标的分配引擎-计划专区列表")
@RestController
@RequestMapping("/am-trade/allocation")
public class AdminAllocationEngineController {
	
	@Autowired
	AdminAllocationEngineService adminAllocationEngineService;

	/**
	 * @Author: libin
	 * @Desc :计划专区列表
	 */
	@RequestMapping(value = "/selectHjhRegionList",method = RequestMethod.POST)
	public HjhRegionResponse selectHjhRegionList(@RequestBody @Valid AllocationEngineRuquest request){
		HjhRegionResponse response = new HjhRegionResponse();
		Integer count = adminAllocationEngineService.countHjhRegionRecordTotal(request);
		// 查询列表传入分页
		Paginator paginator;
		if(request.getLimit() == 0){
			// 前台传分页
			paginator = new Paginator(request.getCurrPage(), count);
		} else {
			// 前台未传分页那默认 10
			paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
		}
		List<HjhRegionVO> list = adminAllocationEngineService.selectHjhRegionList(request,paginator.getOffset(), paginator.getLimit());
        if(count > 0){
            if (!CollectionUtils.isEmpty(list)) {
                response.setResultList(list);
                response.setCount(count);
                //代表成功
                response.setRtn(Response.SUCCESS);
            }
        }
		return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :查询汇计划表
	 */
	@RequestMapping(value = "/selectPlanNameByPlanNid",method = RequestMethod.POST)
	public HjhRegionResponse selectPlanNameByPlanNid(@RequestBody @Valid AllocationEngineRuquest request){
		HjhRegionResponse response = new HjhRegionResponse();
		String planName = adminAllocationEngineService.selectPlanNameByPlanNid(request);
		if(StringUtils.isEmpty(planName)){
			response.setMessage("根据输入计划编号未查到相关计划名称");
		} else{
			response.setPlanName(planName);
		}
		return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :插入计划专区表
	 */
	@RequestMapping("/insertRecord")
	public int insertRecord(@RequestBody HjhRegionVO request) {
		int flg = adminAllocationEngineService.insertRecord(request);
		return flg;
	}
}
