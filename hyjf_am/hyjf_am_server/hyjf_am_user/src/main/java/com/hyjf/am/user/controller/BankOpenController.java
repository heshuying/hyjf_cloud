package com.hyjf.am.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountExample;
import com.hyjf.am.user.dao.model.auto.UsersInfo;
import com.hyjf.am.user.service.BankOpenService;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/am-user/bankopen")
public class BankOpenController {
	
	private static Logger logger = LoggerFactory.getLogger(BankOpenController.class);
	

	@Autowired
	private BankOpenService bankOpenService;

	/**
	 * 插入开户日志表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateUserAccountLog")
	public int updateUserAccountLog(@RequestBody @Valid BankOpenRequest request) {
		logger.info("updateUserAccountLog...param is :{}", JSONObject.toJSONString(request));
		
		Integer userId = request.getUserId();
		String username = request.getUsername();
		String mobile = request.getMobile();
		String orderId = request.getOrderId();
		String channel = request.getChannel();
		String trueName = request.getTrueName();
		String idNo = request.getIdNo();
		String cardNO = request.getCardNo();
		
		boolean result = this.bankOpenService.updateUserAccountLog(userId, username, mobile, orderId, channel, trueName,idNo,cardNO);
        
		return result?1:0;
	}

	/**
	 * 更新开户日志表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateUserAccountLogStatus")
	public int updateUserAccountLogStatus(@RequestBody @Valid BankOpenRequest request) {
		logger.info("updateUserAccountLogStatus...param is :{}", JSONObject.toJSONString(request));
		
		Integer userId = request.getUserId();
		String orderId = request.getOrderId();
		Integer status = request.getStatus();
		
		this.bankOpenService.updateUserAccountLog(userId, orderId, status);
        
		return 1;
	}

	/**
	 * 更新开户日志表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateUserAccount")
	public int updateUserAccount(@RequestBody @Valid BankOpenRequest request) {
		logger.info("updateUserAccount...param is :{}", JSONObject.toJSONString(request));
		
		Integer userId = request.getUserId();
		String orderId = request.getOrderId();
		String accountId = request.getAccountId();
		Integer bankAccountEsb = request.getBankAccountEsb();
		String trueName = request.getTrueName();
		String idNo = request.getIdNo();
		String mobile = request.getMobile();
		
		boolean result = this.bankOpenService.updateUserAccount(userId, trueName, orderId, accountId, idNo, bankAccountEsb, mobile);
        
		return result?1:0;
	}

	/**
	 * 更新开户日志表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateCardNoToBank")
	public int updateCardNoToBank(@RequestBody @Valid BankOpenRequest request) {
		logger.info("updateCardNoToBank...param is :{}", JSONObject.toJSONString(request));
		
		Integer userId = request.getUserId();
		String orderId = request.getOrderId();
		String accountId = request.getAccountId();
		Integer bankAccountEsb = request.getBankAccountEsb();
		String trueName = request.getTrueName();
		String idNo = request.getIdNo();
		String mobile = request.getMobile();
		
		// TODO:保留，业务继续。。
//		boolean result = this.bankOpenService.updateCardNoToBank(userId, trueName, orderId, accountId, idNo, bankAccountEsb, mobile);
        
		return 1;
	}

	@RequestMapping("/findByCardId/{cardId}")
	public UserInfoResponse findByCardId(@PathVariable String cardId) {
		UserInfoResponse response = new UserInfoResponse();
		UsersInfo usersInfo = bankOpenService.findUserInfoByCradId(cardId);
		if (usersInfo != null) {
			UserInfoVO userInfoVO = new UserInfoVO();
			BeanUtils.copyProperties(usersInfo, userInfoVO);
			response.setResult(userInfoVO);
		}
		return response;
	}

	@RequestMapping("/selectByExample")
	public BankOpenAccountResponse selectByExample(BankOpenAccountExample example) {
		BankOpenAccountResponse response = new BankOpenAccountResponse();
		BankOpenAccount bankOpenAccount = bankOpenService.selectByExample(example);
		if(bankOpenAccount != null){
			BankOpenAccountVO bankOpenAccountVO = new BankOpenAccountVO();
			BeanUtils.copyProperties(bankOpenAccount, bankOpenAccountVO);
			response.setResult(bankOpenAccountVO);
		}
		return response;
	}
	
}
