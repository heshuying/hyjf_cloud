/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.content;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.ContentEnvironment;
import com.hyjf.am.config.service.ContentEnvironmentService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.admin.ContentEnvironmentResponse;
import com.hyjf.am.resquest.admin.ContentEnvironmentRequest;
import com.hyjf.am.vo.config.ContentEnvironmentVO;
import com.hyjf.am.vo.config.ContentQualifyVO;
import com.hyjf.common.util.CommonUtils;

/**
 * 内容管理-办公环境
 * 
 * @author fuqiang
 * @version ContentEnvironmentController, v0.1 2018/7/11 14:46
 */
@RestController
@RequestMapping("/am-config/content/contentenvironment")
public class ContentEnvironmentController extends BaseConfigController {
	@Autowired
	private ContentEnvironmentService contentEnvironmentService;

	/**
	 * 根据条件查询内容管理-办公环境
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/searchaction")
	public ContentEnvironmentResponse searchAction(@RequestBody ContentEnvironmentRequest request) {
		logger.info("查询内容管理-办公环境开始......");
		ContentEnvironmentResponse response = new ContentEnvironmentResponse();
		List<ContentEnvironment> list = contentEnvironmentService.searchAction(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<ContentEnvironmentVO> voList = CommonUtils.convertBeanList(list, ContentEnvironmentVO.class);
			response.setResultList(voList);
		}
		return response;
	}

	/**
	 * 添加内容管理-办公环境
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertaction")
	public ContentEnvironmentResponse insertAction(@RequestBody ContentEnvironmentRequest request) {
		ContentEnvironmentResponse response = new ContentEnvironmentResponse();
		contentEnvironmentService.insertAction(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 修改内容管理-办公环境
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateaction")
	public ContentEnvironmentResponse updateAction(@RequestBody ContentEnvironmentRequest request) {
		ContentEnvironmentResponse response = new ContentEnvironmentResponse();
		contentEnvironmentService.updateAction(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 根据id查询内容管理-办公环境
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/getrecord/{id}")
	public ContentEnvironmentResponse getRecord(@PathVariable Integer id) {
		ContentEnvironmentResponse response = new ContentEnvironmentResponse();
		ContentEnvironment contentEnvironment = contentEnvironmentService.getRecord(id);
		if (contentEnvironment != null) {
			ContentQualifyVO vo = new ContentQualifyVO();
			BeanUtils.copyProperties(contentEnvironment, vo);
		}
		return response;
	}
}
