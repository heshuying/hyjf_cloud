package com.hyjf.am.user.controller.front.account;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.service.front.account.BankCardService;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 绑卡及解绑卡服务类
 * @author hesy
 */

@RestController
@RequestMapping("/am-user/bankCard")
public class BankCardController extends BaseController {

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
	/**
	 * 查询用户已绑定的有效卡
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getBankCard/{userId}")
	public BankCardResponse getBankCard(@PathVariable Integer userId) {
		BankCardResponse response = new BankCardResponse();
		BankCard card = bankCardService.getBankCard(userId);
		if (card != null) {
			response.setResult(CommonUtils.convertBean(card, BankCardVO.class));
		}
		return response;
	}

	/**
	 * 根据主键查询银行卡信息
	 * @auth sunpeikai
	 * @param id 主键id
	 * @return
	 */
	@GetMapping(value = "/getBankCardById/{id}")
	public BankCardResponse getBankCardById(@PathVariable Integer id){
		BankCardResponse response = new BankCardResponse();
		BankCard card = bankCardService.getBankCardById(id);
		if (card != null) {
			response.setResult(CommonUtils.convertBean(card, BankCardVO.class));
			response.setRtn(Response.SUCCESS);
		}
		return response;
	}

	/**
	 * 根据用户id和银行卡号查询银行卡信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@PostMapping(value = "/selectBankCardByUserIdAndCardNo")
	public BankCardResponse selectBankCardByUserIdAndCardNo(@RequestBody BankCardRequest request){
		BankCardResponse response = new BankCardResponse();
		BankCard bankCard = bankCardService.selectBankCardByUserIdAndCardNo(request);
		if(bankCard != null){
			BankCardVO bankCardVO = CommonUtils.convertBean(bankCard,BankCardVO.class);
			response.setResult(bankCardVO);
			response.setRtn(Response.SUCCESS);
		}
		return response;
	}

	/**
	 * 更新银行卡信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@PostMapping(value = "/updateBankCard")
	public IntegerResponse updateBankCard(@RequestBody BankCardVO bankCardVO){
		logger.info("updateBankCardVO:" + JSONObject.toJSONString(bankCardVO));
		IntegerResponse response = new IntegerResponse();
		int count = bankCardService.updateBankCard(bankCardVO);
		response.setResultInt(count);
		response.setRtn(Response.SUCCESS);
		return response;
	}
}
