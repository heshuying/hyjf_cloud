/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;
import com.hyjf.am.resquest.admin.ProtocolsRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.admin.FddTempletCustomize;
import com.hyjf.am.trade.service.admin.ProtocolsService;
import com.hyjf.am.vo.trade.FddTempletCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version ProtocolsController, v0.1 2018/7/10 18:06
 */
@RestController
@RequestMapping("/am-trade/protocol")
public class ProtocolsController extends BaseController {
	@Autowired
	private ProtocolsService protocolsService;

	/**
	 * 获取协议列表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectfddtempletlist")
	public FddTempletCustomizeResponse selectFddTempletList(ProtocolsRequest request) {
		FddTempletCustomizeResponse response = new FddTempletCustomizeResponse();
		List<FddTempletCustomize> list = protocolsService.selectFddTempletList(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<FddTempletCustomizeVO> voList = CommonUtils.convertBeanList(list, FddTempletCustomizeVO.class);
			response.setResultList(voList);
		}
		return response;
	}

	/**
	 * 添加协议列表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertaction")
	public FddTempletCustomizeResponse insertAction(ProtocolsRequest request) {
		FddTempletCustomizeResponse response = new FddTempletCustomizeResponse();
		protocolsService.insertAction(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 修改协议列表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateaction")
	public FddTempletCustomizeResponse updateAction(ProtocolsRequest request) {
		FddTempletCustomizeResponse response = new FddTempletCustomizeResponse();
		protocolsService.updateAction(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}
}
