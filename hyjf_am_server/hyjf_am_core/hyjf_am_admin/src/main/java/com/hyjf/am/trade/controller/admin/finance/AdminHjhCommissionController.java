/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhCommissionResponse;
import com.hyjf.am.response.admin.OADepartmentResponse;
import com.hyjf.am.response.admin.TenderCommissionResponse;
import com.hyjf.am.resquest.admin.CommissionComboRequest;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;
import com.hyjf.am.trade.service.admin.finance.AdminHjhCommissionService;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.am.vo.trade.hjh.HjhCommissionCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
/**
 * @author libin
 * @version AdminHjhCommissionController.java, v0.1 2018年8月7日 下午4:42:18   
 */
@Api(value = "汇计划提成列表")
@RestController
@RequestMapping("/am-trade/hjhCommission")
public class AdminHjhCommissionController {
	
	@Autowired
	AdminHjhCommissionService adminHjhCommissionService;
	
	private static final Logger logger = LoggerFactory.getLogger(AdminHjhCommissionController.class);
	
    /**
     * 类名
     */
    private static final String THIS_CLASS = AdminHjhCommissionController.class.getName();
	
	/**
	 * @Author: libin
	 * @Desc :汇计划提成列表     
	 */
	@RequestMapping(value = "/selectHjhCommissionList",method = RequestMethod.POST)
	public HjhCommissionResponse selectHjhCommissionList(@RequestBody @Valid HjhCommissionRequest request){
		HjhCommissionResponse response = new HjhCommissionResponse();
		Integer count = adminHjhCommissionService.countTotal(request);
		// 查询列表传入分页
		Paginator paginator;
		if(request.getLimit() == 0){
			// 前台传分页
			paginator = new Paginator(request.getCurrPage(), count);
		} else {
			// 前台未传分页那默认 10
			paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
		}
		List<HjhCommissionCustomizeVO> list = adminHjhCommissionService.selectHjhCommissionList(request,paginator.getOffset(), paginator.getLimit());
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
     * 查询金额总计 
     * @param id
     * @return
     */
	@RequestMapping(value = "/selecthjhCommissionTotal",method = RequestMethod.POST)
	public HjhCommissionResponse selecthjhCommissionTotal(@RequestBody @Valid HjhCommissionRequest request){
		HjhCommissionResponse response = new HjhCommissionResponse();
		Integer count = adminHjhCommissionService.countTotal(request);
		// 查询列表传入分页
		Paginator paginator;
		if(request.getLimit() == 0){
			// 前台传分页
			paginator = new Paginator(request.getCurrPage(), count);
		} else {
			// 前台未传分页那默认 10
			paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
		}
		Map<String , Object> totalMap = this.adminHjhCommissionService.queryPushMoneyTotle(request,paginator.getOffset(), paginator.getLimit());
		if(count > 0){
            if (totalMap != null) {
            	response.setTotalMap(totalMap);	
/*            	response.setTenderTotal(new BigDecimal(totalMap.get("tenderTotle")));
            	response.setCommissionTotal(new BigDecimal(totalMap.get("commissionTotle"))); */  
                /*response.setCount(count);*/
                //代表成功
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;	
	}
	
   /**
	 * 查询汇计划提成是否已经发放
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryTenderCommissionByPrimaryKey/{ids}")
	public TenderCommissionResponse queryTenderCommissionByPrimaryKey(@PathVariable int ids) {
		TenderCommissionResponse response = new TenderCommissionResponse();
		TenderCommissionVO vo = adminHjhCommissionService.queryTenderCommissionByPrimaryKey(ids);
		if(vo != null){
			response.setResult(vo);
		}
		return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :获取部门列表    
	 */
	@RequestMapping(value = "/getCrmDepartmentList",method = RequestMethod.POST)
	public OADepartmentResponse getCrmDepartmentList(@RequestBody @Valid HjhCommissionRequest request){
		OADepartmentResponse response = new OADepartmentResponse();
		List<OADepartmentCustomizeVO> list = adminHjhCommissionService.getCrmDepartmentList(request);
		if(list.size() > 0){
			response.setResultList(list);
		}
		return response;
	}
	
   /**
	 * 提成发放方式
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryCrmCuttype/{userId}")
	public TenderCommissionResponse queryCrmCuttype(@PathVariable Integer userId) {
		TenderCommissionResponse response = new TenderCommissionResponse();
		Integer type = adminHjhCommissionService.queryCrmCuttype(userId);
		response.setType(type);
		return response;
	}
	
   /**
	 * 发提成
	 * @param id
	 * @return
	 */
	@PostMapping(value = "/updateTenderCommissionRecord")
	public Integer updateTenderCommissionRecord(@RequestBody CommissionComboRequest request){
		int flg = 0;
		try {
			// 发提成处理
			flg = adminHjhCommissionService.updateTenderCommissionRecord(request);
		} catch (Exception e) {
			logger.error(THIS_CLASS, "/updateTenderCommissionRecord", e);
        }
		return flg;
	}
}
