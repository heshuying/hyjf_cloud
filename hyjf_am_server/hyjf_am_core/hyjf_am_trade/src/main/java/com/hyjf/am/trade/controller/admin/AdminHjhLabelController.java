/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.trade.service.admin.AdminHjhLabelService;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
}
