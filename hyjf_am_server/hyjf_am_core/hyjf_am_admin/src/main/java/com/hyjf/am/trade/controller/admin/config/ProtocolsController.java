/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.config;

import java.util.List;

import com.hyjf.am.response.StringResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;
import com.hyjf.am.resquest.admin.ProtocolsRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.FddTempletCustomize;
import com.hyjf.am.trade.service.admin.ProtocolsService;
import com.hyjf.am.vo.trade.FddTempletCustomizeVO;
import com.hyjf.common.util.CommonUtils;

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

	/**
	 * 取得新规的模板编号
	 * @param protocolType
	 * @return
	 */
	@GetMapping("/getNewTempletId/{protocolType}")
	public StringResponse getNewTempletId(@PathVariable Integer protocolType) {
		String tmpId = protocolsService.getNewTempletId(protocolType);
		if (StringUtils.isNotBlank(tmpId)){
			return new StringResponse(tmpId);
		}
		return null;
	}

    /**
     * 协议管理-画面迁移
     *
     * @param id
     * @return
     */
    @GetMapping("/getRecordInfoById/{id}")
    public FddTempletCustomizeResponse getRecordInfoById(@PathVariable Integer id) {
        FddTempletCustomizeResponse response = new FddTempletCustomizeResponse();
        FddTempletCustomize fddTemplet = protocolsService.getRecordInfoById(id);
        if (fddTemplet != null) {
            FddTempletCustomizeVO voList = CommonUtils.convertBean(fddTemplet, FddTempletCustomizeVO.class);
            response.setResult(voList);
        }
        return response;
    }
}
