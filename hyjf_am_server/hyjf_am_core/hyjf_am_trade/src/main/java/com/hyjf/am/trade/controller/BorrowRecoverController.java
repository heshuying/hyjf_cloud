/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.BorrowRecoverResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.service.BorrowRecoverService;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jijun
 * @date 20180630
 */

@Api(value = "借款信息")
@RestController
@RequestMapping("/am-trade/borrowRecover")
public class BorrowRecoverController extends BaseController{

	@Autowired
	BorrowRecoverService borrowRecoverService;

	@GetMapping("/selectBorrowRecoverByTenderNid/{tenderAgreementID}")
	public BorrowRecoverResponse selectBorrowRecoverByTenderNid(@PathVariable String tenderAgreementID){
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		BorrowRecover borrowRecover=borrowRecoverService.selectBorrowRecoverByTenderNid(tenderAgreementID);
		if (Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverVO.class));
		}
		return response;
	}

	@GetMapping("/select_by_id/{id}")
	public BorrowRecoverResponse selectBorrowRecoverById(@PathVariable Integer id){
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		BorrowRecover borrowRecover=borrowRecoverService.selectBorrowRecoverById(id);
		if (Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverVO.class));
		}
		return response;
	}

	@RequestMapping("/select_by_borrownid/{borrowNid}")
	public BorrowRecoverResponse selectByBorrowNid(String borrowNid) {
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		List<BorrowRecover> recoverList=borrowRecoverService.selectByBorrowNid(borrowNid);
		if (recoverList != null){
			List<BorrowRecoverVO> resultList = CommonUtils.convertBeanList(recoverList,BorrowRecoverVO.class);
			response.setResultList(resultList);
		}
		return response;
	}

	@GetMapping("/select_by_nid/{nid}")
	public BorrowRecoverResponse selectBorrowRecoverByNid(@PathVariable String nid){
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		BorrowRecover borrowRecover=borrowRecoverService.selectBorrowRecoverByNid(nid);
		if (Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverVO.class));
		}
		return response;
	}


	@PostMapping("updateBorrowRecover")
	public boolean updateBorrowRecover(@RequestBody BorrowRecoverVO borrowRecover) {
		int count=borrowRecoverService.updateBorrowRecover(CommonUtils.convertBean(borrowRecover,BorrowRecover.class));
		if (count>0){
			return true;
		}else{
			return false;
		}
	}

}
