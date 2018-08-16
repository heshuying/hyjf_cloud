/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.trade;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.PushMoney;
import com.hyjf.am.trade.service.admin.PushMoneyService;
import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version PushMoneyController, v0.1 2018/7/10 19:20
 */
@RestController
@RequestMapping("/am-trade/pushmoney")
public class PushMoneyController extends BaseController {
	@Autowired
	private PushMoneyService pushMoneyService;

	/**
	 * 获取提成列表
	 *
	 * @return
	 */
	@RequestMapping("/getrecordlist")
	public PushMoneyResponse getRecordList() {
		PushMoneyResponse response = new PushMoneyResponse();
		List<PushMoney> list = pushMoneyService.getRecordList();
		if (!CollectionUtils.isEmpty(list)) {
			List<PushMoneyVO> voList = CommonUtils.convertBeanList(list, PushMoneyVO.class);
			response.setResultList(voList);
		}
		return response;
	}

	/**
	 * 添加提成配置
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertpushmoney")
	public PushMoneyResponse insertPushMoney(@RequestBody PushMoneyRequest request) {
		PushMoneyResponse response = new PushMoneyResponse();
		pushMoneyService.insertPushMoney(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 修改提成配置
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatepushmoney")
	public PushMoneyResponse updatePushMoney(@RequestBody PushMoneyRequest request) {
		PushMoneyResponse response = new PushMoneyResponse();
		List<PushMoney> list = pushMoneyService.getRecordList();
		pushMoneyService.updatePushMoney(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

}
