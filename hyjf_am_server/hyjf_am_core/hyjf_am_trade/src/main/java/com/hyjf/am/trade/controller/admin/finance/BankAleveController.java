package com.hyjf.am.trade.controller.admin.finance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BankAleveResponse;
import com.hyjf.am.resquest.admin.BankAleveRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.AleveLogCustomize;
import com.hyjf.am.trade.service.admin.finance.BankAleveService;
import com.hyjf.am.vo.admin.BankAleveVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zha Daojian
 * @version BankAleveController, v0.1 2018/6/29 13:55
 */
@RestController
@RequestMapping("/am-trade/bankaleve")
public class BankAleveController extends BaseController {

	@Autowired
	private BankAleveService aleveService;

	/**
	* @author Zha Daojian
	* @date 2018/8/16 11:38
	* @param request
	* @return com.hyjf.am.response.trade.BankAleveResponse
	**/
	@RequestMapping(value = "/selectBankAleveInfoList")
	public BankAleveResponse selectBankAleveInfoList(@RequestBody @Valid BankAleveRequest request){
		logger.info("request:" +JSONObject.toJSON(request));
		BankAleveResponse response = new BankAleveResponse();
		String returnCode = Response.FAIL;
		Map<String, Object> mapParam = paramSet(request);
		int count = aleveService.countRecord(mapParam);
		Paginator paginator = new Paginator(request.getPaginatorPage(), count, request.getLimit());
		if (request.getLimit() == 0) {
			paginator = new Paginator(request.getPaginatorPage(), count);
		}
		List<AleveLogCustomize> manageList = aleveService.selectBankAleveInfoList(mapParam, paginator.getOffset(), paginator.getLimit());
		if (count > 0) {
			if (!CollectionUtils.isEmpty(manageList)) {
				List<BankAleveVO> vipManageVOS = CommonUtils.convertBeanList(manageList, BankAleveVO.class);
				response.setResultList(vipManageVOS);
				response.setCount(count);
				returnCode = Response.SUCCESS;
			}
		}
		response.setRtn(returnCode);
		return response;
	}

	/**
	 * 查询条件设置
	 *
	 * @param request
	 * @return
	 */
	private Map<String, Object> paramSet(BankAleveRequest request) {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		//自动同步用生成订单id
		mapParam.put("orderId", request.getOrderId());
		//userid
		mapParam.put("userId", request.getUserId());
		//入账日期起止
		mapParam.put("startValdate", request.getStartValdate());
		mapParam.put("endValdate", request.getEndValdate());
		//交易日期起止
		mapParam.put("startInpdate", request.getStartInpdate());
		mapParam.put("endInpdate", request.getEndInpdate());
		//自然日期起止
		mapParam.put("startReldate", request.getStartReldate());
		mapParam.put("endReldate", request.getEndReldate());
		return mapParam;
	}
}
