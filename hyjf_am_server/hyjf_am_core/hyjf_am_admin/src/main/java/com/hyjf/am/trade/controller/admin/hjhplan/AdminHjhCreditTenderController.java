/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.hjhplan;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhCreditTenderResponse;
import com.hyjf.am.response.admin.HjhCreditTenderSumResponse;
import com.hyjf.am.response.trade.HjhDebtCreditTenderResponse;
import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhCreditTenderService;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderSumVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;

import io.swagger.annotations.Api;

/**
 * @author libin
 * @version AdminHjhCreditTenderController.java, v0.1 2018年7月11日 下午3:13:22
 */
@Api(value = "汇计划承接记录列表")
@RestController
@RequestMapping("/am-trade/hjhcredittender")
public class AdminHjhCreditTenderController {
	
	@Autowired
	AdminHjhCreditTenderService adminHjhCreditTenderService;
	
	/**
	 * @Author: libin
	 * @Desc :汇计划承接记录列表
	 */
	@RequestMapping(value = "/getHjhCreditTenderListByParam",method = RequestMethod.POST)
	public HjhCreditTenderResponse getHjhPlanListByParam(@RequestBody @Valid HjhCreditTenderRequest request){
		HjhCreditTenderResponse response = new HjhCreditTenderResponse();
		Integer count = adminHjhCreditTenderService.countHjhCreditTenderTotal(request);
		// 查询列表传入分页
		Paginator paginator;
		if(request.getLimit() == 0){
			// 前台传分页
			paginator = new Paginator(request.getCurrPage(), count);
		} else {
			// 前台未传分页那默认 10
			paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
		}
		List<HjhCreditTenderCustomizeVO> list = adminHjhCreditTenderService.selectHjhCreditTenderList(request,paginator.getOffset(), paginator.getLimit());
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
	 * @Desc :汇计划承接记录列表不分页
	 */
	@RequestMapping(value = "/getHjhCreditTenderListByParamWithOutPage",method = RequestMethod.POST)
	public HjhCreditTenderResponse getHjhCreditTenderListByParamWithOutPage(@RequestBody @Valid HjhCreditTenderRequest request){
		HjhCreditTenderResponse response = new HjhCreditTenderResponse();
		List<HjhCreditTenderCustomizeVO> list = adminHjhCreditTenderService.getHjhCreditTenderListByParamWithOutPage(request);
        if(list.size() > 0){
            if (!CollectionUtils.isEmpty(list)) {
                response.setResultList(list);
                //代表成功
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :汇计划承接记录详情
	 */
	@RequestMapping(value = "/selectHjhCreditTenderRecord",method = RequestMethod.POST)
	public HjhDebtCreditTenderResponse selectHjhCreditTenderRecord(@RequestBody @Valid HjhCreditTenderRequest request){
		HjhDebtCreditTenderResponse response = new HjhDebtCreditTenderResponse();
		HjhDebtCreditTenderVO vo = adminHjhCreditTenderService.selectHjhCreditTenderRecord(request);
        if(vo != null){
        	response.setResult(vo);
            //代表成功
            response.setRtn(Response.SUCCESS);
        }
        return response;
	}
	
	/**
	* @Author: libin
	* @Desc :根据参数获取 HjhCreditTenderSumVO
	*/
    @RequestMapping(value = "/getHjhCreditTenderCalcSumByParam", method = RequestMethod.POST)
    public HjhCreditTenderSumResponse getHjhCreditTenderCalcSumByParam(@RequestBody @Valid HjhCreditTenderRequest request) {
    	HjhCreditTenderSumResponse response = new HjhCreditTenderSumResponse();
    	HjhCreditTenderSumVO vo = adminHjhCreditTenderService.getHjhCreditTenderCalcSumByParam(request);
    	if(vo != null){
            response.setResult(vo);
            //代表成功
            response.setRtn(Response.SUCCESS);
    	}
    	return response;
    }
	
}
