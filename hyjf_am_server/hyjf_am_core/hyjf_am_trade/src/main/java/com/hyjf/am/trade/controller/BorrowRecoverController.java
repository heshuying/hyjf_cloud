/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.RecentPaymentListCustomizeResponse;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.web.RecentPaymentListCustomize;
import com.hyjf.am.trade.service.BorrowRecoverService;
import com.hyjf.am.trade.service.BorrowService;
import com.hyjf.am.trade.service.UserService;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.ProjectCompanyDetailVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WebProjectPersonDetailVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.user.RecentPaymentListCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


	@PostMapping("updateBorrowRecover")
	public boolean updateBorrowRecover(@RequestBody BorrowRecoverVO borrowRecover) {
		int count=borrowRecoverService.updateBorrowRecover(CommonUtils.convertBean(borrowRecover,BorrowRecover.class));
		if (count>0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 根据tenderNid和bidNid查询
	 * @param tenderNid
	 * @param bidNid
	 * @return
	 */
	@GetMapping("/getBorrowRecoverByTenderNidBidNid/{tenderNid}/{bidNid}")
	public BorrowRecoverResponse getBorrowRecoverByTenderNidBidNid(@PathVariable String tenderNid,@PathVariable String bidNid){
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		BorrowRecover borrowRecover=borrowRecoverService.getBorrowRecoverByTenderNidBidNid(tenderNid,bidNid);
		if (Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverVO.class));
		}
		return response;
	}

	/**
	 * 根据tenderNid查询
	 * @param tenderNid
	 * @return
	 */
	@GetMapping("/getBorrowRecoverByTenderNid/{tenderNid}")
	public BorrowRecoverResponse getBorrowRecoverByTenderNid(@PathVariable String tenderNid){
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		BorrowRecover borrowRecover=borrowRecoverService.getBorrowRecoverByTenderNid(tenderNid);
		if (Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverVO.class));
		}
		return response;
	}

	/**
	 * 获取borrow_recover_plan更新每次还款时间
	 * @param bidNid
	 * @param creditTenderNid
	 * @param periodNow
	 * @return
	 */
	@GetMapping("/getPlanByBidTidPeriod/{bidNid}/{creditTenderNid}/{periodNow}")
	public BorrowRecoverPlanResponse getPlanByBidTidPeriod(@PathVariable String bidNid, @PathVariable String creditTenderNid, @PathVariable Integer periodNow){
		BorrowRecoverPlanResponse response = new BorrowRecoverPlanResponse();
		BorrowRecoverPlan borrowRecover=borrowRecoverService.getPlanByBidTidPeriod(bidNid,creditTenderNid,periodNow);
		if (Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverPlanVO.class));
		}
		return response;
	}


}
