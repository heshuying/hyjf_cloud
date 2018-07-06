/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.hjhplan;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhAllocationEngineResponse;
import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.response.admin.HjhPlanSumResponse;
import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhPlanService;
import com.hyjf.am.vo.admin.HjhAllocationEngineVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.paginator.Paginator;
import org.springframework.util.CollectionUtils;
import io.swagger.annotations.Api;

/**
 * @author libin
 * @version AdminHjhPlanController.java, v0.1 2018年7月6日 上午10:04:37
 */
@Api(value = "计划列表")
@RestController
@RequestMapping("/am-trade/planList")
public class AdminHjhPlanController {
	
	@Autowired
	AdminHjhPlanService adminHjhPlanService;
	
	/**
	 * @Author: libin
	 * @Desc :计划列表
	 */
	@RequestMapping(value = "/getHjhPlanListByParam",method = RequestMethod.POST)
	public HjhPlanResponse getHjhPlanListByParam(@RequestBody @Valid PlanListRequest request){
		HjhPlanResponse response = new HjhPlanResponse();
		Integer count = adminHjhPlanService.countHjhPlanListTotal(request);
		// 查询列表传入分页
		Paginator paginator;
		if(request.getLimit() == 0){
			// 前台传分页
			paginator = new Paginator(request.getCurrPage(), count);
		} else {
			// 前台未传分页那默认 10
			paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
		}
		List<HjhPlanVO> list = adminHjhPlanService.selectHjhPlanList(request,paginator.getOffset(), paginator.getLimit());
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
	 * @Desc :根据参数获取 HjhPlanSumVO
	 */
    @RequestMapping(value = "/getCalcSumByParam", method = RequestMethod.POST)
    public HjhPlanSumResponse getCalcSumByParam(@RequestBody @Valid PlanListRequest request) {
    	HjhPlanSumResponse response = new HjhPlanSumResponse();
    	HjhPlanSumVO vo  = adminHjhPlanService.getCalcSumByParam(request);
    	if(vo != null){
            response.setResult(vo);
            //代表成功
            response.setRtn(Response.SUCCESS);
    	}
    	return response;
    }
	
	
	
	
	
}
