/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.content;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/am-user/content/whereaboutspage")
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


}
