/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.content;

import java.util.List;

import com.hyjf.am.response.config.LinkResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.Link;
import com.hyjf.am.config.service.ContentPartnerService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.resquest.admin.ContentPartnerRequest;
import com.hyjf.am.vo.config.LinkVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author fuqiang
 * @version ContentPartnerController, v0.1 2018/7/12 10:36
 */
@RestController
@RequestMapping("/am-config/content/contentenpartner")
public class ContentPartnerController extends BaseConfigController {
	@Autowired
	private ContentPartnerService contentPartnerService;

	/**
	 * 根据条件查询公司管理-合作伙伴
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/searchaction")
	public LinkResponse searchAction(@RequestBody ContentPartnerRequest request) {
		logger.info("查询公司管理-合作伙伴开始......");
		LinkResponse response = new LinkResponse();
		List<Link> list = contentPartnerService.searchAction(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<LinkVO> voList = CommonUtils.convertBeanList(list, LinkVO.class);
			response.setResultList(voList);
		}
		return response;
	}

	/**
	 * 添加公司管理-合作伙伴
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertaction")
	public LinkResponse insertAction(@RequestBody ContentPartnerRequest request) {
		LinkResponse response = new LinkResponse();
		contentPartnerService.insertAction(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 修改公司管理-合作伙伴
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateaction")
	public LinkResponse updateAction(@RequestBody ContentPartnerRequest request) {
		LinkResponse response = new LinkResponse();
		contentPartnerService.updateAction(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 根据id查询公司管理-合作伙伴
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/getrecord/{id}")
	public LinkResponse getRecord(@PathVariable Integer id) {
		LinkResponse response = new LinkResponse();
		Link link = contentPartnerService.getRecord(id);
		if (link != null) {
			LinkVO vo = new LinkVO();
			BeanUtils.copyProperties(link, vo);
		}
		return response;
	}
}
