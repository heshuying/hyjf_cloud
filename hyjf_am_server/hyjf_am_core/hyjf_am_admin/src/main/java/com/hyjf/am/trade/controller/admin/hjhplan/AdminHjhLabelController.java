/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.hjhplan;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhLabelCustomizeResponse;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.resquest.admin.HjhLabelInfoRequest;
import com.hyjf.am.resquest.admin.HjhLabelRequest;
import com.hyjf.am.trade.dao.model.customize.AdminHjhLabelCustomize;
import com.hyjf.am.trade.service.admin.hjhplan.AdminAllocationEngineService;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhLabelService;
import com.hyjf.am.vo.admin.HjhAllocationEngineVO;
import com.hyjf.am.vo.admin.HjhLabelCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author libin
 * @version AdminHjhLabelController.java, v0.1 2018年6月30日 上午10:41:29
 */
@Api(value = "标签配置列表")
@RestController
@RequestMapping("/am-trade/hjhLabel")
public class AdminHjhLabelController {
	
	@Autowired
	AdminHjhLabelService adminHjhLabelService;
	
	@Autowired
	AdminAllocationEngineService adminAllocationEngineService;
	
	/**
	 * @Author: libin
	 * @Desc :查询还款方式
	 */
	@ApiOperation(value = "查询还款方式")
	@GetMapping("/selectBorrowStyleList")
	public BorrowStyleResponse selectBorrowStyleList(){
		BorrowStyleResponse response = new BorrowStyleResponse();
		String returnCode = "00";//代表成功
	    List<BorrowStyleVO> borrowStyleList = adminHjhLabelService.selectBorrowStyleList();
	    if (null!=borrowStyleList && borrowStyleList.size()>0) {
	    	 response.setResultList(borrowStyleList); 
	    	 response.setRtn(returnCode);
	    }
	    return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :查询产品类型
	 */
	@ApiOperation(value = "查询产品类型")
	@GetMapping("/selectBorrowProjectByBorrowCd")
	public BorrowProjectTypeResponse selectBorrowProjectByBorrow(){
		BorrowProjectTypeResponse response = new BorrowProjectTypeResponse();
		String returnCode = "00";//代表成功
		List<BorrowProjectTypeVO> borrowProjectList = adminHjhLabelService.selectBorrowProjectByBorrow();
	    if (null!=borrowProjectList && borrowProjectList.size()>0) {
	    	 response.setResultList(borrowProjectList); 
	    	 response.setRtn(returnCode);
	    }
	    return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :查询标签配置列表
	 */
	@RequestMapping(value = "/selectHjhLabelList",method = RequestMethod.POST)
	public HjhLabelCustomizeResponse selectHjhLabelList(@RequestBody @Valid HjhLabelRequest request){
		HjhLabelCustomizeResponse response = new HjhLabelCustomizeResponse();
		Integer count = adminHjhLabelService.countRecordTotal(request);
		Paginator paginator;
		if(request.getLimit() == 0){
			// 前台传分页
			paginator = new Paginator(request.getCurrPage(), count);
		} else {
			// 前台未传分页那默认10
			paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
		}
		List<AdminHjhLabelCustomize> list = adminHjhLabelService.selectHjhLabelList(request,paginator.getOffset(), paginator.getLimit());
        if(count > 0){
            if (!CollectionUtils.isEmpty(list)) {
            	for(AdminHjhLabelCustomize vo:list){
            		List<HjhAllocationEngineVO> alist = adminAllocationEngineService.selectHjhAllocationEngineListByLabelId(vo.getId());
            		//如果通过标签表中的标签编号去查询引擎表发现可以在引擎表查到记录，则说明此标签已经在引擎中使用
            		if(alist.size() > 0){
            			vo.setLabelisUsed("1");//已使用
            		} else {
            			vo.setLabelisUsed("0");//未使用
            		}
            	}
                response.setResultList(CommonUtils.convertBeanList(list,HjhLabelCustomizeVO.class));
                response.setCount(count);
                response.setRtn(Response.SUCCESS);//代表成功
            }
        }
        return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :查询标签配置列表
	 */
	@RequestMapping(value = "/selectHjhLabelListById",method = RequestMethod.POST)
	public HjhLabelCustomizeResponse selectHjhLabelListById(@RequestBody @Valid HjhLabelRequest request){
		HjhLabelCustomizeResponse response = new HjhLabelCustomizeResponse();
		List<HjhLabelCustomizeVO> list = adminHjhLabelService.selectHjhLabelListById(request);
		if(list.size()> 0){
            if (!CollectionUtils.isEmpty(list)) {
                response.setResultList(list);
                response.setRtn(Response.SUCCESS);//代表成功
            }
		}
		return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :查询标签配置列表
	 */
	@RequestMapping(value = "/selectHjhLabelListLabelName",method = RequestMethod.POST)
	public HjhLabelCustomizeResponse selectHjhLabelListLabelName(@RequestBody @Valid HjhLabelRequest request){
		HjhLabelCustomizeResponse response = new HjhLabelCustomizeResponse();
		List<HjhLabelCustomizeVO> list = adminHjhLabelService.selectHjhLabelListLabelName(request);
		if(list.size()> 0){
            if (!CollectionUtils.isEmpty(list)) {
                response.setResultList(list);
                response.setRtn(Response.SUCCESS);//代表成功
            }
		}	
		return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :插入标签配置列表
	 */
	@RequestMapping("/insertHjhLabelRecord")
	public IntegerResponse insertHjhLabelRecord(@RequestBody HjhLabelInfoRequest request) {
		int update = adminHjhLabelService.insertHjhLabelRecord(request);
		return new IntegerResponse(update);
	}
	
	/**
	 * @Author: libin
	 * @Desc :更新标签配置列表
	 */
	 @RequestMapping("/updateHjhLabelRecord")
	 public IntegerResponse updateHjhLabelRecord(@RequestBody HjhLabelInfoRequest request) {
		 int update = adminHjhLabelService.updateHjhLabelRecord(request);
		 return new IntegerResponse(update);
	 }
	 
	/**
	 * @Author: libin
	 * @Desc :更新标签配置列表
	 */
	 @RequestMapping("/updateAllocationRecord")
	 public IntegerResponse updateAllocationRecord(@RequestBody HjhLabelInfoRequest request) {
		 int update = adminHjhLabelService.updateAllocationRecord(request);
		 return new IntegerResponse(update);
	 } 
	 
	/**
	 * @Author: libin
	 * @Desc :更新标签配置列表ByIdAndLabelState
	 */
	 @RequestMapping("/updateHjhLabelRecordByIdAndLabelState")
	 public IntegerResponse updateHjhLabelRecordByIdAndLabelState(@RequestBody HjhLabelInfoRequest request) {
		 int update = adminHjhLabelService.updateHjhLabelRecordByIdAndLabelState(request);
		 return new IntegerResponse(update);
	 }
}
