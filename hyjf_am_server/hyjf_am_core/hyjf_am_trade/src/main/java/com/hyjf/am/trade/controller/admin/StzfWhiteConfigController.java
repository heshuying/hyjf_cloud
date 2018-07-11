/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.resquest.admin.STZHWhiteListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteList;
import com.hyjf.am.trade.service.admin.StzfWhiteConfigService;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author fuqiang
 * @version StzfWhiteConfigController, v0.1 2018/7/10 15:19
 */
@RestController
@RequestMapping("/am-trade/stzfwhiteconfig")
public class StzfWhiteConfigController extends BaseController {
	@Autowired
	private StzfWhiteConfigService stzfWhiteConfigService;

	/**
	 * 获取受托支付白名单列表
	 *
	 * @return
	 */
	@RequestMapping("/selectSTZHWhiteList")
	public STZHWhiteListResponse selectSTZHWhiteList(@RequestBody STZHWhiteListRequest request) {
		STZHWhiteListResponse response = new STZHWhiteListResponse();
		List<StzhWhiteList> list = stzfWhiteConfigService.selectSTZHWhiteList(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<STZHWhiteListVO> voList = CommonUtils.convertBeanList(list, STZHWhiteListVO.class);
			response.setResultList(voList);
		}
		return response;
	}

	/**
	 * 添加受托支付白名单
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertSTZHWhiteList")
	public STZHWhiteListResponse insertSTZHWhiteList(@RequestBody STZHWhiteListRequest request) {
		STZHWhiteListResponse response = new STZHWhiteListResponse();
		stzfWhiteConfigService.insertSTZHWhiteList(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 修改受托支付白名单
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateSTZHWhiteList")
	public STZHWhiteListResponse updateSTZHWhiteList(@RequestBody STZHWhiteListRequest request) {
		STZHWhiteListResponse response = new STZHWhiteListResponse();
		stzfWhiteConfigService.updateSTZHWhiteList(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}
}
