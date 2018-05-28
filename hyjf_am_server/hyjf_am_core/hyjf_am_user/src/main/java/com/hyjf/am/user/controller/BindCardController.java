package com.hyjf.am.user.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.service.BindCardService;
import com.hyjf.am.vo.user.BankCardVO;

/**
 * 绑卡及解绑卡服务类
 * @author hesy
 */

@RestController
@RequestMapping("/am-user/card")
public class BindCardController {

	@Autowired
	private BindCardService bindCardService;

	/**
	 * 查询用户已绑定的有效卡
	 * @param userId
	 * @param cardNo
	 * @return
	 */
	@RequestMapping("/queryUserCardValid/{userId}/{cardNo}")
	public BankCardResponse queryUserCardValid(@PathVariable Integer userId, @PathVariable String cardNo) {
		BankCardResponse response = new BankCardResponse();
		BankCard card = bindCardService.queryUserCardValid(userId, cardNo);
		if (card != null) {
			BankCardVO bankCardVO = new BankCardVO();
			BeanUtils.copyProperties(card, bankCardVO);
			response.setResult(bankCardVO);
		}
		return response;
	}
	
	/**
	 * 统计用户绑定的有效银行卡个数
	 * @param userId
	 * @return
	 */
	@RequestMapping("/countUserCardValid/{userId}")
	public int countUserCardValid(@PathVariable Integer userId) {
		return bindCardService.countUserCardValid(userId);
	}
	
	/**
	 * 根据userId删除银行卡信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("/deleteUserCardByUserId/{userId}")
	public int deleteUserCardByUserId(@PathVariable Integer userId) {
		return bindCardService.deleteUserCardByUserId(userId);
	}
	
	/**
	 * 保存用户绑定的银行卡
	 * @param bankCardRequest
	 * @return
	 */
	@RequestMapping(value = "/insertUserCard", method = RequestMethod.POST)
	public int insertUserCard(@RequestBody @Valid BankCardRequest bankCardRequest) {
		BankCard bankCard = new BankCard();
		BeanUtils.copyProperties(bankCardRequest, bankCard);
		int cnt =bindCardService.insertUserCard(bankCard);
		return cnt;
	}
	
}
