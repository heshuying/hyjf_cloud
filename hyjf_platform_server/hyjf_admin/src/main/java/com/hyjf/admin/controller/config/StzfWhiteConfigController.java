/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.request.STZHWhiteListRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.StzfWhiteConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fuqiang
 * @version StzfWhiteConfigController, v0.1 2018/7/9 17:54
 */
@Api(tags = "配置中心-受托支付白名单")
@RestController
@RequestMapping("/hyjf-admin/stzfwhiteconfig")
public class StzfWhiteConfigController extends BaseController {

	@Autowired
	private StzfWhiteConfigService stzfWhiteConfigService;

	@ApiOperation(value = "受托支付白名单列表显示", notes = "受托支付白名单列表显示")
	@PostMapping("/selectSTZHWhiteList")
	public AdminResult<ListResult<STZHWhiteListVO>> selectSTZHWhiteList(
			@RequestBody STZHWhiteListRequestBean requestBean) {
		STZHWhiteListResponse response = stzfWhiteConfigService.selectSTZHWhiteList(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "添加受托支付白名单", notes = "添加受托支付白名单")
	@PostMapping("/insertAction")
	public AdminResult add(HttpServletRequest request, @RequestBody STZHWhiteListRequestBean requestBean) {
		AdminSystemVO adminSystemVO = (AdminSystemVO) request.getSession().getAttribute(USER);
		requestBean.setCreateuser(adminSystemVO.getId());
		requestBean.setUpdateuser(adminSystemVO.getId());
		if (requestBean.getInstname() != null) {
			// 机构编号
			requestBean.setInstcode(requestBean.getInstname());
			HjhInstConfigVO hjhInstConfigVO = stzfWhiteConfigService.selectHjhInstConfig(requestBean.getInstcode());
			if (hjhInstConfigVO != null) {
				requestBean.setInstname(hjhInstConfigVO.getInstName());
			}
		}
		STZHWhiteListResponse response = stzfWhiteConfigService.insertSTZHWhiteList(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "修改受托支付白名单", notes = "修改受托支付白名单")
	@PostMapping("/updateAction")
	public AdminResult updateAction(HttpServletRequest request, @RequestBody STZHWhiteListRequestBean requestBean) {
		AdminSystemVO adminSystemVO = (AdminSystemVO) request.getSession().getAttribute(USER);
		requestBean.setCreateuser(adminSystemVO.getId());
		requestBean.setUpdateuser(adminSystemVO.getId());
		if (requestBean.getInstname() != null) {
			// 机构编号
			requestBean.setInstcode(requestBean.getInstname());
			HjhInstConfigVO hjhInstConfigVO = stzfWhiteConfigService.selectHjhInstConfig(requestBean.getInstcode());
			if (hjhInstConfigVO != null) {
				requestBean.setInstname(hjhInstConfigVO.getInstName());
			}
		}
		STZHWhiteListResponse response = stzfWhiteConfigService.updateSTZHWhiteList(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
}
