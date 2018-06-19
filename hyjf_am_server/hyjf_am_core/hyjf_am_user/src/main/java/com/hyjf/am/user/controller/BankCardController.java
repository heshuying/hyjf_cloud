package com.hyjf.am.user.controller;

import javax.validation.Valid;

import com.hyjf.am.resquest.user.BankSmsLogRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.BankCardLog;
import com.hyjf.am.user.service.BankCardService;
import com.hyjf.am.user.service.BindCardService;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.CommonUtils;

/**
 * 绑卡及解绑卡服务类
 * @author hesy
 */

@RestController
@RequestMapping("/am-user/bankCard")
public class BankCardController {

	@Autowired
	private BankCardService bankCardService;

	/**
	 * 查询用户已绑定的有效卡
	 * @param userId
	 * @param cardNo
	 * @return
	 */
	@RequestMapping("/getBankCard/{userId}/{bankId}")
	public BankCardResponse getBankCard(@PathVariable Integer userId, @PathVariable String bankId) {
		BankCardResponse response = new BankCardResponse();
		BankCard card = bankCardService.getBankCard(userId, bankId);
		if (card != null) {
			response.setResult(CommonUtils.convertBean(card, BankCardVO.class));
		}
		return response;
	}
	

}
