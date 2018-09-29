package com.hyjf.am.trade.controller.admin.finance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BankEveResponse;
import com.hyjf.am.resquest.admin.BankEveRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.EveLogCustomize;
import com.hyjf.am.trade.service.admin.finance.BankEveService;
import com.hyjf.am.vo.admin.BankEveVO;
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
@RequestMapping("/am-trade/bankeve")
public class BankEveController extends BaseController {

	@Autowired
	private BankEveService eveService;

	/**
	 * 银行账务明细志表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectBankEveInfoList")
	public BankEveResponse selectBankEeveInfoList(@RequestBody @Valid BankEveRequest request){
		logger.info("request:" +JSONObject.toJSON(request));
		BankEveResponse response = new BankEveResponse();
		String returnCode = Response.FAIL;
		Map<String, Object> mapParam = paramSet(request);
		int count = eveService.countRecord(mapParam);
		Paginator paginator = new Paginator(request.getPaginatorPage(), count, request.getLimit());
		if (request.getLimit() == 0) {
			paginator = new Paginator(request.getPaginatorPage(), count);
		}
		List<EveLogCustomize> manageList = eveService.selectBankEveInfoList(mapParam, paginator.getOffset(), paginator.getLimit());
		if (count > 0) {
			if (!CollectionUtils.isEmpty(manageList)) {
				List<BankEveVO> vipManageVOS = CommonUtils.convertBeanList(manageList, BankEveVO.class);
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
	private Map<String, Object> paramSet(BankEveRequest request) {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		//主账号
		mapParam.put("cardnbr", request.getCardnbr());
		//系统跟踪号
		mapParam.put("seqno", request.getSeqno());
		//入交易传输时间
		mapParam.put("startDate", request.getStartDate());
		mapParam.put("endDate", request.getEndDate());
		return mapParam;
	}
}
