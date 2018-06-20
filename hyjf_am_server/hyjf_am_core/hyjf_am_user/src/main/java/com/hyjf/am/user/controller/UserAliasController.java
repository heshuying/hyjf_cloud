/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.hyjf.am.response.user.UserAliasResponse;
import com.hyjf.am.user.dao.model.auto.UserAlias;
import com.hyjf.am.user.service.UserAliasService;
import com.hyjf.am.vo.user.UserAliasVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fuqiang
 * @version UserAliasController, v0.1 2018/5/8 10:50
 */
@RestController
@RequestMapping("/am-user/userAlias")
public class UserAliasController {

	private static Logger logger = LoggerFactory.getLogger(UserAliasController.class);

	@Autowired
	private UserAliasService userAliasService;

	/**
	 * 根据手机号查询推送别名
	 *
	 * @param mobile
	 * @return
	 */
	@RequestMapping("/findAliasByMobile/{mobile}")
	public UserAliasResponse findAliasByMobile(@PathVariable String mobile) {
		logger.info("根据手机号查询推送别名开始... mobile is :{}", mobile);
		UserAliasResponse response = new UserAliasResponse();
		UserAliasVO userAliasVO = null;
		UserAlias userAlias = userAliasService.findAliasByMobile(mobile);
		if (userAlias != null) {
			userAliasVO = new UserAliasVO();
			BeanUtils.copyProperties(userAlias, userAliasVO);
		}
		logger.info("userAliasVO is :{}", userAliasVO);
		response.setResult(userAliasVO);
		return response;
	}

	/**
	 * 根据手机号查询推送别名 - 批量
	 *
	 * @param mobiles
	 * @return
	 */
	@RequestMapping("/findAliasesByMobiles")
	public List<UserAliasResponse> findAliasesByMobiles(List<String> mobiles) {
		logger.info("根据手机号查询推送别名 - 批量开始... mobiles is :{}", mobiles);
		List<UserAliasResponse> list = new ArrayList<>();
		List<UserAliasVO> userAliasVOList = userAliasService.findAliasByMobiles(mobiles);
		for (UserAliasVO userAliasVO : userAliasVOList) {
			UserAliasResponse response = new UserAliasResponse();
			response.setResult(userAliasVO);
			list.add(response);
		}
		return list;
	}

	/**
	 * 根据设备类型统计用户人数
	 *
	 * @param clientAndroid
	 * @return
	 */
	@RequestMapping("/countAliasByClient/{clientAndroid}")
	public UserAliasResponse countAliasByClient(@PathVariable String clientAndroid) {
		logger.info("根据设备类型统计用户人数开始... clientAndroid is :{}", clientAndroid);
		UserAliasResponse response = new UserAliasResponse();
		Integer count = userAliasService.countAliasByClient(clientAndroid);
		response.setCount(count);
		return response;
	}
}
