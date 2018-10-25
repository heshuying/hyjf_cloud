/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.repay;

import com.hyjf.am.response.trade.BorrowRecoverPlanResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan;
import com.hyjf.am.trade.service.front.repay.BorrowRecoverPlanService;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 还款计划
 * @author hesy
 * @version BorrowRecoverPlanController, v0.1 2018/6/26 17:41
 */
@Api(value = "还款计划")
@RestController
@RequestMapping("/am-trade/recoverplan")
public class BorrowRecoverPlanController extends BaseController{

	@Autowired
	BorrowRecoverPlanService borrowRecoverPlanService;

	@GetMapping("/get/{id}")
	public BorrowRecoverPlanResponse selectBorrowRecoverById(@PathVariable Integer id){
		BorrowRecoverPlanResponse response = new BorrowRecoverPlanResponse();
		BorrowRecoverPlan borrowRecoverPlan=borrowRecoverPlanService.selectRecoverPlanById(id);
		if (Validator.isNotNull(borrowRecoverPlan)){
			response.setResult(CommonUtils.convertBean(borrowRecoverPlan,BorrowRecoverPlanVO.class));
		}
		return response;
	}

	@GetMapping("/getby_borrownid_period/{borrowNid}/{period}")
	public BorrowRecoverPlanResponse selectBorrowRecover(@PathVariable String borrowNid, @PathVariable Integer period){
		BorrowRecoverPlanResponse response = new BorrowRecoverPlanResponse();
		List<BorrowRecoverPlan> borrowRecoverPlan=borrowRecoverPlanService.selectRecoverPlan(borrowNid, period);
		if (Validator.isNotNull(borrowRecoverPlan)){
			response.setResultList(CommonUtils.convertBeanList(borrowRecoverPlan,BorrowRecoverPlanVO.class));
		}
		return response;
	}


	@GetMapping("/getBorrowRecoverPlanListByTenderNid/{tenderNid}")
	public BorrowRecoverPlanResponse selectBorrowRecover(@PathVariable String tenderNid){
		BorrowRecoverPlanResponse response = new BorrowRecoverPlanResponse();
		List<BorrowRecoverPlan> borrowRecoverPlan=borrowRecoverPlanService.selectRecoverPlanListByTenderNid(tenderNid);
		if (Validator.isNotNull(borrowRecoverPlan)){
			response.setResultList(CommonUtils.convertBeanList(borrowRecoverPlan,BorrowRecoverPlanVO.class));
		}
		return response;
	}

	/**
	 * 获取用户投资订单还款详情
	 *
	 * @param nid
	 * @return
	 */
	@GetMapping("/select_borrow_recover_plan_list/{nid}")
	public BorrowRecoverPlanResponse selectBorrowRecoverPlanListByNid(@PathVariable String nid){
		BorrowRecoverPlanResponse response = new BorrowRecoverPlanResponse();
		List<BorrowRecoverPlan> borrowRecoverPlanList = borrowRecoverPlanService.selectBorrowRecoverPlanListByNid(nid);
		if (!CollectionUtils.isEmpty(borrowRecoverPlanList)) {
			List<BorrowRecoverPlanVO> voList = CommonUtils.convertBeanList(borrowRecoverPlanList, BorrowRecoverPlanVO.class);
			response.setResultList(voList);
		}
		return response;
	}

}
