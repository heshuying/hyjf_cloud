/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.repay;

import com.hyjf.am.response.trade.BorrowRecoverPlanResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan;
import com.hyjf.am.trade.service.repay.BorrowRecoverPlanService;
import com.hyjf.am.vo.trade.repay.BorrowRecoverPlanVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
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

}
