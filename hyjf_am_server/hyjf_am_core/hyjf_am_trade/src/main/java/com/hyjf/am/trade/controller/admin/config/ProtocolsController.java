/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.config;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;
import com.hyjf.am.resquest.admin.ProtocolsRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.FddTempletCustomize;
import com.hyjf.am.trade.service.admin.ProtocolsService;
import com.hyjf.am.vo.trade.FddTempletCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
	 * @param requestT
	 * @return
	 */
	@RequestMapping("/selectfddtempletlist")
	public FddTempletCustomizeResponse selectFddTempletList(@RequestBody ProtocolsRequest requestT) {
		FddTempletCustomizeResponse response = new FddTempletCustomizeResponse();
		List<FddTempletCustomize> list = protocolsService.selectFddTempletList(requestT);
		if (!CollectionUtils.isEmpty(list)) {
			List<FddTempletCustomizeVO> voList = CommonUtils.convertBeanList(list, FddTempletCustomizeVO.class);
			response.setResultList(voList);
		}
		return response;
	}

	/**
	 * 添加协议列表
	 *
	 * @param requestT
	 * @return
	 */
	@RequestMapping("/insertaction")
	public FddTempletCustomizeResponse insertAction(@RequestBody ProtocolsRequest requestT) {
		FddTempletCustomizeResponse response = new FddTempletCustomizeResponse();
		protocolsService.insertAction(requestT);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 修改协议列表
	 *
	 * @param requestT
	 * @return
	 */
	@PostMapping("/updateaction")
	public FddTempletCustomizeResponse updateAction(@RequestBody ProtocolsRequest requestT) {
		FddTempletCustomizeResponse response = new FddTempletCustomizeResponse();
		protocolsService.updateAction(requestT);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 取得新规的模板编号
	 * @param templetId
	 * @return
	 */
	@GetMapping("/getNewTempletId/{templetId}")
	public StringResponse getNewTempletId(@PathVariable Integer templetId) {

		String tmpId = protocolsService.getNewTempletId(templetId);
		if (StringUtils.isNotBlank(tmpId)){
			return new StringResponse(tmpId);
		}
		return null;
	}
}
