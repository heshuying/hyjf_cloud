/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.hjhplan;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhPlanDetailResponse;
import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.response.admin.HjhPlanSumResponse;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhPlanService;
import com.hyjf.am.vo.trade.hjh.HjhPlanDetailVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author libin
 * @version AdminHjhPlanController.java, v0.1 2018年7月6日 上午10:04:37
 */
@Api(value = "计划列表",tags ="计划列表")
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
    
	/**
	 * @Author: libin
	 * @Desc :根据参数获取 HjhPlanDetailVO
	 */
    @RequestMapping(value = "/getHjhPlanDetailByPlanNid", method = RequestMethod.POST)
    public HjhPlanDetailResponse getHjhPlanDetailByPlanNid(@RequestBody @Valid PlanListRequest request) {
    	HjhPlanDetailResponse response = new HjhPlanDetailResponse();
    	List<HjhPlanDetailVO> list = adminHjhPlanService.selectHjhPlanDetailByPlanNid(request);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
            //代表成功
            response.setRtn(Response.SUCCESS);
        }
    	return response;
    }
    
	/**
	 * @Author: libin
	 * @Desc :AJAX
	 */
	@RequestMapping(value = "/getPlanNameAjaxCheck",method = RequestMethod.POST)
	public HjhPlanResponse getPlanNameAjaxCheck(@RequestBody @Valid PlanListRequest request){
		HjhPlanResponse response = new HjhPlanResponse();
		String planName = request.getPlanNameSrch();
		if (StringUtils.isEmpty(planName)) {
			response.setMessage("未传入计划名称！");
			return response;
		}
		int debtPlanCount = this.adminHjhPlanService.isDebtPlanNameExist(planName);
		if (debtPlanCount != 0) {
			response.setMessage("计划名称不能重复！");
			return response;
		}
		return response;
	}
	
	
	/**
	 * @Author: libin
	 * @Desc :AJAX
	 */
	@RequestMapping(value = "/getPlanNidAjaxCheck",method = RequestMethod.POST)
	public HjhPlanResponse getPlanNidAjaxCheck(@RequestBody @Valid PlanListRequest request){
		HjhPlanResponse response = new HjhPlanResponse();
		String planNid = request.getPlanNidSrch();
		if (StringUtils.isEmpty(planNid)) {
			response.setMessage("未传入计划编号！");
			return response;
		}
		int debtPlanCount = this.adminHjhPlanService.isDebtPlanNidExist(planNid);
		if (debtPlanCount != 0) {
			response.setMessage("计划编号不能重复！");
			return response;
		}
		return response;
	}
	
	
	/**
	 * @Author: libin
	 * @Desc :修改计划状态
	 */
	@RequestMapping(value = "/updatePlanStatusByPlanNid",method = RequestMethod.POST)
	public HjhPlanResponse updatePlanStatusByPlanNid(@RequestBody @Valid PlanListRequest request){
		HjhPlanResponse response = new HjhPlanResponse();
		int flg = adminHjhPlanService.updatePlanStatusByPlanNid(request);
	  	if(flg > 0){
            //代表成功
            response.setMessage(Response.SUCCESS_MSG);
    	}
		return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :修改计划显示状态
	 */
	@RequestMapping(value = "/updatePlanDisplayByPlanNid",method = RequestMethod.POST)
	public HjhPlanResponse updatePlanDisplayByPlanNid(@RequestBody @Valid PlanListRequest request){
		HjhPlanResponse response = new HjhPlanResponse();
		int flg = adminHjhPlanService.updatePlanDisplayByPlanNid(request);
	  	if(flg > 0){
            //代表成功
            response.setMessage(Response.SUCCESS_MSG);
    	}
		return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :
	 */
    @GetMapping(value = "/isExistsRecord/{planNid}")
    public BooleanResponse isExistsRecord(@PathVariable String planNid) {
    	boolean Flag = adminHjhPlanService.isExistsRecord(planNid);
    	return new BooleanResponse(Flag);
    }
	
	/**
	 * @Author: libin
	 * @Desc :
	 */
    @GetMapping(value = "/countByPlanName/{planName}")
    public IntegerResponse countByPlanName(@PathVariable String planName) {
    	int Flag = adminHjhPlanService.countByPlanName(planName);
    	return new IntegerResponse(Flag);
    }
    
	/**
	 * @Author: libin
	 * @Desc :
	 */
    @GetMapping(value = "/isLockPeriodExist/{lockPeriod}/{borrowStyle}/{isMonth}")
    public IntegerResponse isLockPeriodExist(@PathVariable String lockPeriod, @PathVariable String borrowStyle, @PathVariable String isMonth) {
    	int Flag = adminHjhPlanService.isLockPeriodExist(lockPeriod,borrowStyle,isMonth);
    	return new IntegerResponse(Flag);
    }
    
	/**
	 * @throws Exception 
	 * @Author: libin
	 * @Desc :更新计划表
	 */
	@RequestMapping("/updateRecord")
	public IntegerResponse updateRecord(@RequestBody PlanListRequest form) throws Exception {
		int flg = adminHjhPlanService.updateRecord(form);
		return new IntegerResponse(flg);
	}
    
	/**
	 * @throws Exception 
	 * @Author: libin
	 * @Desc :更新计划表
	 */
	@RequestMapping("/insertRecord")
	public IntegerResponse insertRecord(@RequestBody PlanListRequest form) throws Exception {
		int flg = adminHjhPlanService.insertRecord(form);
		return new IntegerResponse(flg);
	}
	
	/**
	 * @Author: libin
	 * @Desc :计划列表无分页
	 */
	@RequestMapping(value = "/getHjhPlanListByParamWithoutPage",method = RequestMethod.POST)
	public HjhPlanResponse getHjhPlanListByParamWithoutPage(@RequestBody @Valid PlanListRequest request){
		HjhPlanResponse response = new HjhPlanResponse();
		List<HjhPlanVO> list = adminHjhPlanService.selectHjhPlanListWithoutPage(request);
        if(!CollectionUtils.isEmpty(list)&&list.size() > 0){
			response.setResultList(list);
			//代表成功
			response.setRtn(Response.SUCCESS);
        }
		return response;
	}
    
}
