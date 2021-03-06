/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.content;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.ContentQualify;
import com.hyjf.am.config.service.ContentQualifyService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.ContentQualifyResponse;
import com.hyjf.am.resquest.admin.ContentQualifyRequest;
import com.hyjf.am.vo.config.ContentQualifyVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 公司管理-资质荣誉
 * 
 * @author fuqiang
 * @version ContentQualifyController, v0.1 2018/7/11 10:07
 */
@RestController
@RequestMapping("/am-config/content/contentqualify")
public class ContentQualifyController extends BaseConfigController {
	@Autowired
	private ContentQualifyService contentQualifyService;
	@Value("${file.domain.url}")
	private String fileUrl;

	/**
	 * 根据条件查询公司管理-资质荣誉
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/searchaction")
	public ContentQualifyResponse searchAction(@RequestBody ContentQualifyRequest request) {
		logger.info("查询公司管理-资质荣誉开始......");
		ContentQualifyResponse response = new ContentQualifyResponse();
		List<ContentQualify> list = contentQualifyService.searchAction(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<ContentQualifyVO> voList = CommonUtils.convertBeanList(list, ContentQualifyVO.class);
			voList.stream().forEach(e-> e.setImgurl(fileUrl + e.getImgurl()));
			response.setResultList(voList);
		}
		// 查询符合条件的条数
		int count = contentQualifyService.selectCount(request);
		response.setCount(count);
		return response;
	}

	/**
	 * 添加公司管理-资质荣誉
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertaction")
	public IntegerResponse insertAction(@RequestBody ContentQualifyRequest request) {
		int num = contentQualifyService.insertAction(request);
		return new IntegerResponse(num);
	}

	/**
	 * 修改公司管理-资质荣誉
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateaction")
	public IntegerResponse updateAction(@RequestBody ContentQualifyRequest request) {
		int num = contentQualifyService.updateAction(request);
		return new IntegerResponse(num);
	}

	/**
	 * 根据id查询公司管理-资质荣誉
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getrecord/{id}")
	public ContentQualifyResponse getRecord(@PathVariable Integer id) {
		ContentQualifyResponse response = new ContentQualifyResponse();
		ContentQualify contentQualify = contentQualifyService.getRecord(id);
		if (contentQualify != null) {
			ContentQualifyVO vo = new ContentQualifyVO();
			BeanUtils.copyProperties(contentQualify, vo);
			response.setResult(vo);
		}
		return response;
	}

	/**
	 * 删除公司管理-资质荣誉状态
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("delete/{id}")
	public IntegerResponse delete(@PathVariable Integer id) {
		int num = contentQualifyService.delete(id);
		return new IntegerResponse(num);
	}
}
