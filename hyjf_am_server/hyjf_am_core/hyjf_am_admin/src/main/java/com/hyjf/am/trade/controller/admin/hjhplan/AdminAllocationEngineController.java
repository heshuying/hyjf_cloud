/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.hjhplan;

import com.alibaba.druid.util.StringUtils;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.admin.HjhAllocationEngineResponse;
import com.hyjf.am.response.admin.HjhRegionResponse;
import com.hyjf.am.response.user.HjhPlanResponse;
import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.auto.HjhRegion;
import com.hyjf.am.trade.service.admin.hjhplan.AdminAllocationEngineService;
import com.hyjf.am.vo.admin.HjhAllocationEngineVO;
import com.hyjf.am.vo.admin.HjhRegionVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author libin
 * @version AdminAllocationEngineController.java, v0.1 2018年7月3日 下午1:31:17
 */
@Api(value = "标的分配引擎-计划专区列表",tags = "标的分配引擎-计划专区列表")
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
			response.setMessage("根据输入智投编号未查到相关智投名称");
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
	public IntegerResponse insertRecord(@RequestBody HjhRegionVO request) {
		int flg = adminAllocationEngineService.insertRecord(request);
		return new IntegerResponse(flg);
	}
	
    /**
     * AJAX校验
     */
    @RequestMapping("/getPlanNidAjaxCheck/{planNid}")
    public HjhRegionResponse getPlanNidAjaxCheck(@PathVariable String planNid){
    	HjhRegionResponse response = new HjhRegionResponse();
		//计划编号未入力
		if (StringUtils.isEmpty(planNid) && "".equals(planNid) ) {
			response.setMessage("错误:" +"未传入智投编号");
			return response;
		}
		int errorFlag = this.checkInputPlanNidSrch(planNid);
		if (errorFlag == 1) {
			response.setMessage("错误:" +"智投编号：" + planNid +"不存在于智投服务列表中");
			return response;
		} else if (errorFlag == 2) {
			response.setMessage("错误:" +"智投编号：" + planNid + "已存在于智投专区表");
			return response;
		} else if (errorFlag == 0) {
			response.setMessage("");
			return response;
		}
		return response;
    }
    
	/**
	 * 校验入力的planNid
	 * 
	 * @param
	 * @return
	 * @author Administrator
	 */
	public int checkInputPlanNidSrch(String planNid) {
		List<HjhPlan> planList = adminAllocationEngineService.getHjhPlanByPlanNid(planNid);
		if (planList == null || planList.size() == 0) {
			// 该计划编号"不存在"与汇计划表中
			return 1;
		}		
		List<HjhRegion> planList1 = adminAllocationEngineService.getHjhRegionByPlanNid(planNid);		
		if (planList1.size() >= 1) {//查出多条就是重复
			// 该计划编号已存在于计划专区表
			return 2;
		}		
		return 0;		
	} 
	
    /**
     * 根据id获取HjhRegionVO
     */
    @RequestMapping("/getHjhRegionVOById/{id}")
    public HjhRegionResponse getHjhRegionVOById(@PathVariable String id){
    	HjhRegionResponse response = new HjhRegionResponse();
    	HjhRegionVO vo  = adminAllocationEngineService.selectHjhRegionVOById(id);
    	if(vo != null){
            response.setResult(vo);
            //代表成功
            response.setRtn(Response.SUCCESS);
    	}
    	return response;
    }
    
	/**
	 * @Author: libin
	 * @Desc :更新计划专区表
	 */
    @RequestMapping("/updateHjhRegionRecord")
    public IntegerResponse updateHjhRegionRecord(@RequestBody HjhRegionVO request) {
    	int flg = adminAllocationEngineService.updateHjhRegionRecord(request);
    	return new IntegerResponse(flg);
    }
    
	/**
	 * @Author: libin
	 * @Desc :更新引擎表
	 */
    @RequestMapping("/updateAllocationEngineRecord")
    public HjhRegionResponse updateAllocationEngineRecord(@RequestBody HjhRegionVO vo) {
    	HjhRegionResponse response = new HjhRegionResponse();
    	int flg = adminAllocationEngineService.updateAllocationEngineRecord(vo.getPlanNid(),vo.getConfigStatus());
    	if(flg > 0){
            //代表成功
            response.setMessage(Response.SUCCESS_MSG);
    	}
    	return response;
    }	

	/**
	 * @Author: libin
	 * @Desc :计划专区列表
	 */
	@RequestMapping(value = "/getHjhRegionListWithOutPage",method = RequestMethod.POST)
	public HjhRegionResponse getHjhRegionListWithOutPage(@RequestBody @Valid AllocationEngineRuquest request){
		HjhRegionResponse response = new HjhRegionResponse();
		List<HjhRegionVO> list = adminAllocationEngineService.selectHjhRegionListWithOutPage(request);
        if(list.size() > 0){
            if (!CollectionUtils.isEmpty(list)) {
                response.setResultList(list);
                response.setRtn(Response.SUCCESS);
            }
        }
		return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :计划引擎列表
	 */
	@RequestMapping(value = "/getHjhAllocationEngineList",method = RequestMethod.POST)
	public HjhAllocationEngineResponse getHjhAllocationEngineList(@RequestBody @Valid AllocationEngineRuquest request){
		HjhAllocationEngineResponse response = new HjhAllocationEngineResponse();
		// 未带分页参数查询
		List<HjhAllocationEngineVO> list = adminAllocationEngineService.selectHjhAllocationEngineList(request.getPlanNidSrch());
		Integer count = list.size();
		// 查询列表传入分页
		Paginator paginator;
		if(request.getLimit() == 0){
			// 前台传分页
			paginator = new Paginator(request.getCurrPage(), count);
		} else {
			// 前台未传分页那默认 10
			paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
		}
		// 带分页查询
		List<HjhAllocationEngineVO> list1 = adminAllocationEngineService.selectHjhAllocationEngineListByPage(request,paginator.getOffset(), paginator.getLimit());
        if(count > 0){
            if (!CollectionUtils.isEmpty(list1)) {
                response.setResultList(list1);
                response.setCount(count);
                //代表成功
                response.setRtn(Response.SUCCESS);
            }
        }
		return response;
	}  
	
	/**
	 * @Author: libin
	 * @Desc :计划引擎列表
	 */
	@RequestMapping(value = "/getAllocationList",method = RequestMethod.POST)
	public HjhAllocationEngineResponse getAllocationList(@RequestBody @Valid AllocationEngineRuquest request){
		HjhAllocationEngineResponse response = new HjhAllocationEngineResponse();
		// 未带分页参数查询
		List<HjhAllocationEngineVO> list = adminAllocationEngineService.selectHjhAllocationEngineList(request.getPlanNidSrch());
        if(list.size() > 0){
            if (!CollectionUtils.isEmpty(list)) {
                response.setResultList(list);
                response.setCount(list.size());
                //代表成功
                response.setRtn(Response.SUCCESS);
            }
        }
		return response;
	}

    /**
     * 根据id获取HjhAllocationEngineVO
     */
    @RequestMapping("/getPlanConfigRecord/{engineId}")
    public HjhAllocationEngineResponse getPlanConfigRecord(@PathVariable Integer engineId){
    	HjhAllocationEngineResponse response = new HjhAllocationEngineResponse();
    	HjhAllocationEngineVO vo  = adminAllocationEngineService.selectPlanConfigRecord(engineId);
    	if(vo != null){
            response.setResult(vo);
            //代表成功
            response.setRtn(Response.SUCCESS);
    	}
    	return response;
    }
	
	/**
	 * @Author: libin
	 * @Desc :更新计划引擎表
	 */
    @RequestMapping("/updateHjhAllocationEngineRecord")
    public IntegerResponse updateHjhAllocationEngineRecord(@RequestBody HjhAllocationEngineVO request) {
    	int flg = adminAllocationEngineService.updateHjhAllocationEngineRecord(request);
    	return new IntegerResponse(flg);
    }
    
	/**
	 * @Author: libin
	 * @Desc :根据参数获取 HjhAllocationEngineVO
	 */
    @RequestMapping(value = "/getPlanConfigRecordByParam", method = RequestMethod.POST)
    public HjhAllocationEngineResponse getPlanConfigRecordByParam(@RequestBody @Valid AllocationEngineRuquest request) {
    	HjhAllocationEngineResponse response = new HjhAllocationEngineResponse();
       	HjhAllocationEngineVO vo  = adminAllocationEngineService.selectPlanConfigRecordByParam(request.getPlanNid(),request.getLabelId());
    	if(vo != null){
            response.setResult(vo);
            //代表成功
            response.setRtn(Response.SUCCESS);
    	}
    	return response;
    }
    
	/**
	 * @Author: libin
	 * @Desc :根据参数获取 HjhAllocationEngineVO
	 */
    @RequestMapping(value = "/getPlanConfigRecordByPlanNidLabelName", method = RequestMethod.POST)
    public HjhAllocationEngineResponse getPlanConfigRecordByPlanNidLabelName(@RequestBody @Valid AllocationEngineRuquest request) {
    	HjhAllocationEngineResponse response = new HjhAllocationEngineResponse();
       	HjhAllocationEngineVO vo  = adminAllocationEngineService.getPlanConfigRecordByPlanNidLabelName(request.getPlanNid(),request.getLabelName());
    	if(vo != null){
            response.setResult(vo);
            //代表成功
            response.setRtn(Response.SUCCESS);
    	}
    	return response;
    }
    

	/**
	 * @Author: libin
	 * @Desc :
	 */
    @RequestMapping(value = "/checkRepeat", method = RequestMethod.POST)
    public HjhAllocationEngineResponse checkRepeat(@RequestBody @Valid AllocationEngineRuquest form) {
    	HjhAllocationEngineResponse response = new HjhAllocationEngineResponse();
    	int Flag = adminAllocationEngineService.checkRepeat(form);
        response.setFlag(Flag);
        //代表成功
        response.setRtn(Response.SUCCESS);
    	return response;
    }
    
	/**
	 * @Author: libin
	 * @Desc :
	 */
    @RequestMapping(value = "/getPlanBorrowStyle/{planNid}", method = RequestMethod.GET)
    public StringResponse getPlanBorrowStyle(@PathVariable String planNid) {
    	String borrowStyle = adminAllocationEngineService.getPlanBorrowStyle(planNid);
    	return new StringResponse(borrowStyle);
    } 
    
	/**
	 * @Author: libin
	 * @Desc :根据参数获取
	 */
    @RequestMapping(value = "/getHjhRegionRecordByPlanNid/{planNid}")
    public HjhRegionResponse getHjhRegionRecordByPlanNid(@PathVariable String planNid) {
    	HjhRegionResponse response = new HjhRegionResponse();
    	HjhRegionVO vo  = adminAllocationEngineService.getHjhRegionRecordByPlanNid(planNid);
    	if(vo != null){
            response.setResult(vo);
            //代表成功
            response.setRtn(Response.SUCCESS);
    	}
    	return response;
    }  
    
	/**
	 * @Author: libin
	 * @Desc :插入计划引擎表
	 */
	@RequestMapping("/insertHjhAllocationEngineRecord")
	public IntegerResponse insertHjhAllocationEngineRecord(@RequestBody HjhAllocationEngineVO request) {
		int flg = adminAllocationEngineService.insertHjhAllocationEngineRecord(request);
		return new IntegerResponse(flg);
	}
	
	/**
	 * @Author: libin
	 * @Desc :根据参数获取
	 */
    @RequestMapping(value = "/selectHjhPlanByPlanNid/{planNid}")
    public HjhPlanResponse selectHjhPlanByPlanNid(@PathVariable String planNid) {
    	HjhPlanResponse response = new HjhPlanResponse();
    	List<HjhPlanVO> list = adminAllocationEngineService.selectHjhPlanByPlanNid(planNid);
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
	 * @Desc :根据参数获取
	 */
    @RequestMapping(value = "/getHjhRegioByPlanNid/{planNid}")
    public HjhRegionResponse getHjhRegioByPlanNid(@PathVariable String planNid) {
    	HjhRegionResponse response = new HjhRegionResponse();
    	List<HjhRegionVO> list = adminAllocationEngineService.selectHjhRegioByPlanNid(planNid);
        if(list.size() > 0){
            if (!CollectionUtils.isEmpty(list)) {
                response.setResultList(list);
                //代表成功
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }
    
}
