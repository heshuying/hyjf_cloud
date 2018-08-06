/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.borrow;

import com.hyjf.am.response.trade.BorrowConfigResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowConfig;
import com.hyjf.am.trade.service.front.borrow.BorrowConfigService;
import com.hyjf.am.vo.trade.borrow.BorrowConfigVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标的配置
 * @author hesy
 * @version BorrowConfigServiceImpl, v0.1 2018/7/4 13:52
 * */
@RestController
@RequestMapping("/am-trade/borrowconfig")
public class BorrowConfigController extends BaseController {

	@Autowired
	BorrowConfigService borrowConfigService;

	@RequestMapping("/get_by_code/{code}")
	public BorrowConfigResponse getByCode(@PathVariable String code){
		BorrowConfigResponse response = new BorrowConfigResponse();
		BorrowConfig borrowConfig=borrowConfigService.selectBorrowConfigByCode(code);
		if (Validator.isNotNull(borrowConfig)){
			response.setResult(CommonUtils.convertBean(borrowConfig,BorrowConfigVO.class));
		}
		return response;
	}

}
