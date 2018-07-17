/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.promotion;

import com.hyjf.am.response.market.UtmRegResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.user.service.promotion.UtmRegService;
import com.hyjf.am.vo.user.UtmRegVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version UtmRegController, v0.1 2018/7/17 9:13
 */
@RestController
@RequestMapping("/am-user/promotion/utmreg")
public class UtmRegController extends BaseController {
	@Autowired
	private UtmRegService utmRegService;

	/**
	 * 查询utm注册列表
	 * 
	 * @return
	 */
	@RequestMapping("/getutmreglist/{sourceId}")
	public UtmRegResponse getUtmRegList(@PathVariable Integer sourceId) {
		UtmRegResponse response = new UtmRegResponse();
		List<UtmReg> list = utmRegService.getUtmRegList(sourceId);
		if (!CollectionUtils.isEmpty(list)) {
			List<UtmRegVO> voList = CommonUtils.convertBeanList(list, UtmRegVO.class);
			response.setResultList(voList);
			return response;
		}
		return null;
	}
}
