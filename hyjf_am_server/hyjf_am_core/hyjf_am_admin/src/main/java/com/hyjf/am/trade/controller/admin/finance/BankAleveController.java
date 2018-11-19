package com.hyjf.am.trade.controller.admin.finance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BankAleveResponse;
import com.hyjf.am.resquest.admin.BankAleveRequest;
import com.hyjf.am.trade.dao.model.customize.AleveLogCustomize;
import com.hyjf.am.trade.service.admin.finance.BankAleveService;
import com.hyjf.am.user.controller.BaseController;
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

@RestController
@RequestMapping("/am-trade/bankaleve")
public class BankAleveController extends BaseController {

	@Autowired
	private BankAleveService aleveService;

	/**
	 * 银行账务明细志表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectBankAleveInfoList")
	public BankAleveResponse selectBankAleveInfoList(@RequestBody @Valid BankAleveRequest request){
		logger.info("request:" +JSONObject.toJSON(request));
		BankAleveResponse response = new BankAleveResponse();
		String returnCode = Response.FAIL;
		Map<String, Object> mapParam = paramSet(request);
		int count = aleveService.countRecord(mapParam);
		if(request.getCurrPage()>0){
			Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
			mapParam.put("limitStart", paginator.getOffset());
			mapParam.put("limitEnd", paginator.getLimit());
		}
		List<AleveLogCustomize> manageList = aleveService.selectBankAleveInfoList(mapParam);
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


	@RequestMapping(value = "/selectBankAleveInfoCount")
	public IntegerResponse selectBankAleveInfoCount(@RequestBody @Valid BankAleveRequest request){
		logger.info("request:" +JSONObject.toJSON(request));
		IntegerResponse response = new IntegerResponse();
		Map<String, Object> mapParam = paramSet(request);
		int count = aleveService.countRecord(mapParam);
		response.setResultInt(count);
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
		//电子账号
		mapParam.put("cardnbr", request.getCardnbr());
		//系统跟踪号
		mapParam.put("seqno", request.getSeqno());
		//交易类型
		mapParam.put("transtype", request.getTranstype());
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
