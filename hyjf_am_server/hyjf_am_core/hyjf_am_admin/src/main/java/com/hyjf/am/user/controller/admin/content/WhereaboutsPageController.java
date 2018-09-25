/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.content;


import com.hyjf.am.response.StringResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.am.resquest.admin.WhereaboutsPageRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.WhereaboutsPageConfig;
import com.hyjf.am.user.service.admin.content.WhereaboutsPageService;
import com.hyjf.am.vo.config.WhereaboutsPageVo;

import io.swagger.annotations.Api;

/**
 * @author tanyy
 * @version WhereaboutsPageController, v0.1 2018/7/20 10:35
 */
@Api(value = "移动端着陆页管理")
@RestController
@RequestMapping("/am-admin/content/whereaboutspage")
public class WhereaboutsPageController extends BaseController {
	@Autowired
	private WhereaboutsPageService whereaboutsPageService;

	/**
	 * 根据条件查询移动端着陆页管理
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/searchaction")
	public WhereaboutsPageResponse searchAction(@RequestBody WhereaboutsPageRequest request) {
		logger.info("查询移动端着陆页管理......");
		WhereaboutsPageResponse response = whereaboutsPageService.searchAction(request);
		return response;
	}

	/**
	 * 添加着移动端着陆页管理
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insert")
	public WhereaboutsPageResponse insertAction(@RequestBody WhereaboutsPageRequest request) {
		WhereaboutsPageResponse response = new WhereaboutsPageResponse();
		whereaboutsPageService.insertAction(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 修改移动端着陆页管理
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	public WhereaboutsPageResponse updateAction(@RequestBody WhereaboutsPageRequest request) {
		WhereaboutsPageResponse response = new WhereaboutsPageResponse();
		whereaboutsPageService.updateAction(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}
	/**
	 * 修改状态
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatestatus")
	public WhereaboutsPageResponse updateStatus(@RequestBody WhereaboutsPageRequest request) {
		WhereaboutsPageResponse response = new WhereaboutsPageResponse();
		whereaboutsPageService.statusAction(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}
	/**
	 * 根据id查询移动端着陆页管理
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/getrecord/{id}")
	public WhereaboutsPageResponse getRecord(@PathVariable Integer id) {
		WhereaboutsPageResponse response = new WhereaboutsPageResponse();
		WhereaboutsPageConfig whereaboutsPageConfig = whereaboutsPageService.getRecord(id);
		if (whereaboutsPageConfig != null) {
			WhereaboutsPageVo vo = new WhereaboutsPageVo();
			BeanUtils.copyProperties(whereaboutsPageConfig, vo);
		}
		return response;
	}

	/**
	 * 删除移动端着陆页管理---逻辑删除
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public WhereaboutsPageResponse delete(@PathVariable Integer id) {
		WhereaboutsPageResponse response = new WhereaboutsPageResponse();
		whereaboutsPageService.deleteById(id);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 检查渠道
	 *
	 * @param utmId
	 * @return
	 */
	@ApiOperation(value = "检查渠道", notes = "检查渠道")
	@GetMapping("/checkutmid/{utmId}")
	public StringResponse checkUtmId(@PathVariable Integer utmId) {
		StringResponse msg = this.whereaboutsPageService.checkUtmId(utmId);
		return msg;
	}
	/**
	 * 检查推广人
	 *
	 * @param referrer
	 * @return
	 */
	@ApiOperation(value = "检查推广人", notes = "检查推广人")
	@GetMapping("/checkreferrer/{referrer}")
	public StringResponse checkReferrer(@PathVariable String referrer) {
		StringResponse msg = this.whereaboutsPageService.checkReferrer(referrer);
		return msg;
	}
	/**
	 * 修改详情
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/getwhereaboutspageconfig")
	public WhereaboutsPageResponse getWhereaboutsPageConfigById(@RequestBody WhereaboutsPageRequest request) {
		WhereaboutsPageResponse response = whereaboutsPageService.getWhereaboutsPageConfigById(request);
		return response;
	}


}
