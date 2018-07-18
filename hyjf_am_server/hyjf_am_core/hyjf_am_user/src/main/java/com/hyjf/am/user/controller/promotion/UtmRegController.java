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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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
	@RequestMapping("/getutmreglist/{sourceId}/{type}")
	public UtmRegResponse getUtmRegList(@PathVariable Integer sourceId,
										@PathVariable String type) {
		UtmRegResponse response = new UtmRegResponse();
		List<UtmReg> list = utmRegService.getUtmRegList(sourceId, type);
		if (!CollectionUtils.isEmpty(list)) {
			List<UtmRegVO> voList = CommonUtils.convertBeanList(list, UtmRegVO.class);
			response.setResultList(voList);
			return response;
		}
		return null;
	}

	/**
	 * 查询相应的app渠道主单注册数
	 * @param list app渠道注册userid集合
	 * @return
	 */
	@RequestMapping("/getregisterattrcount")
	public UtmRegResponse getRegisterAttrCount(@RequestBody List<Integer> list) {
		UtmRegResponse response = new UtmRegResponse();
		BigDecimal registerAttrCount = utmRegService.getRegisterAttrCount(list);
		if (registerAttrCount != null) {
			response.setRegisterAttrCount(registerAttrCount);
			return response;
		}
		return null;
	}

	/**
	 * 查询相应的app渠道Ios开户数
	 * @param list
	 * @return
	 */
	@RequestMapping("/getaccountnumberios")
	public UtmRegResponse getAccountNumberIos(@RequestBody List<Integer> list) {
		UtmRegResponse response = new UtmRegResponse();
		//3 app
		Integer accountNumberIos = utmRegService.getAccountNumber(list, "3");
		if (accountNumberIos != null) {
			response.setAccountNumberIos(accountNumberIos);
			return response;
		}
		return null;
	}

	/**
	 * 查询相应的app渠道pc开户数
	 * @param list
	 * @return
	 */
	@RequestMapping("/getaccountnumberpc")
	public UtmRegResponse getAccountNumberPc(@RequestBody List<Integer> list) {
		UtmRegResponse response = new UtmRegResponse();
		//0 PC
		Integer accountNumber = utmRegService.getAccountNumber(list, "0");
		if (accountNumber != null) {
			response.setAccountNumberPc(accountNumber);
			return response;
		}
		return null;
	}

	/**
	 * 查询相应的app渠道android开户数
	 * @param list
	 * @return
	 */
	@RequestMapping("/getaccountnumberandroid")
	public UtmRegResponse getAccountNumberAndroid(@RequestBody List<Integer> list) {
		UtmRegResponse response = new UtmRegResponse();
		//2 android
		Integer accountNumber = utmRegService.getAccountNumber(list, "2");
		if (accountNumber != null) {
			response.setAccountNumberAndroid(accountNumber);
			return response;
		}
		return null;
	}

	/**
	 * 查询相应的app渠道android开户数
	 * @param list
	 * @return
	 */
	@RequestMapping("/getaccountnumberwechat")
	public UtmRegResponse getAccountNumberWechat(@RequestBody List<Integer> list) {
		UtmRegResponse response = new UtmRegResponse();
		//1 微信
		Integer accountNumber = utmRegService.getAccountNumber(list, "1");
		if (accountNumber != null) {
			response.setAccountNumberWechat(accountNumber);
			return response;
		}
		return null;
	}
}
