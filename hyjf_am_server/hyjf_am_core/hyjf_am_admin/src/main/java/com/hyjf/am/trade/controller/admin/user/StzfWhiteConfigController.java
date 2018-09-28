/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.user;

import java.util.List;

import com.hyjf.am.trade.dao.model.customize.STZHWhiteListCustomize;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/am-admin/stzfwhiteconfig")
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

	/**
	 * 获取用户/机构信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUserByUserName")
	public STZHWhiteListResponse getUserByUserName(@RequestBody STZHWhiteListRequest request) {
		STZHWhiteListResponse response = new STZHWhiteListResponse();
		STZHWhiteListCustomize stzhWhiteListCustomize = new STZHWhiteListCustomize();
		BeanUtils.copyProperties(request,stzhWhiteListCustomize);
		STZHWhiteListCustomize whiteListCustomize = stzfWhiteConfigService.selectInfo(stzhWhiteListCustomize);
		if (whiteListCustomize != null) {
			STZHWhiteListVO stzhWhiteListVO = new STZHWhiteListVO();
			BeanUtils.copyProperties(whiteListCustomize,stzhWhiteListVO);
			response.setResult(stzhWhiteListVO);
		}
		return response;
	}

	/**
	 * 根据id查询受托支付白名单详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/selectSTZHWhiteById/{id}")
	public STZHWhiteListResponse selectSTZHWhiteById(@PathVariable Integer id) {
		STZHWhiteListResponse response = new STZHWhiteListResponse();
		StzhWhiteList stzhWhiteList = stzfWhiteConfigService.selectStzfWhiteById(id);
		if (stzhWhiteList != null) {
			STZHWhiteListVO stzhWhiteListVO = new STZHWhiteListVO();
			BeanUtils.copyProperties(stzhWhiteList,stzhWhiteListVO);
//			stzhWhiteListVO.setApprovalTime(GetDate.times10toStrYYYYMMDD(Integer.parseInt(stzhWhiteList.getApprovalTime())));
			response.setResult(stzhWhiteListVO);
		}
		return response;
	}

}
