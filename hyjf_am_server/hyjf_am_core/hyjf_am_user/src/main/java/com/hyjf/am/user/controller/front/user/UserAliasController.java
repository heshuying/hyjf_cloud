/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserAliasResponse;
import com.hyjf.am.resquest.message.FindAliasesForMsgPushRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.UserAlias;
import com.hyjf.am.user.service.front.user.UserAliasService;
import com.hyjf.am.vo.user.UserAliasVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fuqiang
 * @version UserAliasController, v0.1 2018/5/8 10:50
 */
@RestController
@RequestMapping("/am-user/userAlias")
public class UserAliasController extends BaseController {

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
			userAliasVO.setMobile(mobile);
			BeanUtils.copyProperties(userAlias, userAliasVO);
		}
		logger.info("userAliasVO is :{}", userAliasVO);
		response.setResult(userAliasVO);
		return response;
	}

	/**
	 * 根据手机号查询推送别名 - 批量
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/findAliasesByMobiles")
	public UserAliasResponse findAliasesByMobiles(@RequestBody FindAliasesForMsgPushRequest request) {
		List<String> mobiles = request.getMobiles();
		logger.info("根据手机号查询推送别名 - 批量开始... mobiles is :{}", mobiles);
		UserAliasResponse response = new UserAliasResponse();
		List<UserAliasVO> userAliasVOList = userAliasService.findAliasByMobiles(mobiles);
		if(!CollectionUtils.isEmpty(userAliasVOList)){
			response.setResultList(userAliasVOList);
			response.setRtn(Response.SUCCESS);
		}
		return response;
	}

	@GetMapping("/findAliasesByUserId/{userId}")
	public UserAliasResponse findAliasesByUserId(@PathVariable Integer userId) {
		UserAliasResponse response = new UserAliasResponse();
		UserAlias userAlias = userAliasService.findAliasesByUserId(userId);
		if (null != userAlias) {
			UserAliasVO userAliasVO = new UserAliasVO();
			userAliasVO.setMobile(userAliasService.findUserByUserId(userId).getMobile());
			BeanUtils.copyProperties(userAlias, userAliasVO);
			response.setResult(userAliasVO);
		}
		return response;
	}

	@PostMapping("/updateMobileCode")
	public int updateAliases(@RequestBody UserAlias userAlias) {
		int cnt = userAliasService.updateAliases(userAlias);
		return cnt;
	}

	@PostMapping("/insertMobileCode")
	public int insertMobileCode(@RequestBody UserAlias userAlias) {
		int cnt = userAliasService.insertMobileCode(userAlias);
		return cnt;
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

	@RequestMapping("/clearMobileCode/{userId}/{sign}")
	public void clearMobileCode(@PathVariable Integer userId, @PathVariable String sign) {
		userAliasService.clearMobileCode(userId, sign);
	}
}
