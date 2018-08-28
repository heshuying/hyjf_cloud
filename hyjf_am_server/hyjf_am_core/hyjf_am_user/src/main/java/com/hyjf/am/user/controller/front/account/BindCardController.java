package com.hyjf.am.user.controller.front.account;

import javax.validation.Valid;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.user.BankCardUpdateRequest;
import com.hyjf.am.user.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankSmsLogRequest;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.BankCardLog;
import com.hyjf.am.user.service.front.account.BindCardService;
import com.hyjf.am.vo.user.BankCardVO;

/**
 * 绑卡及解绑卡服务类
 * @author hesy
 */
@RestController
@RequestMapping("/am-user/card")
public class BindCardController extends BaseController {

	@Autowired
	private BindCardService bindCardService;

	/**
	 * 查询用户已绑定的有效卡
	 * @auther: hesy
	 * @date: 2018/6/20
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
	 * @auther: hesy
	 * @date: 2018/6/20
	 */
	@RequestMapping("/countUserCardValid/{userId}")
	public int countUserCardValid(@PathVariable Integer userId) {
		return bindCardService.countUserCardValid(userId);
	}

	/**
	 * 根据userId删除银行卡信息
	 * @auther: hesy
	 * @date: 2018/6/20
	 */
	@RequestMapping("/deleteUserCardByUserId/{userId}")
	public int deleteUserCardByUserId(@PathVariable Integer userId) {
		return bindCardService.deleteUserCardByUserId(userId);
	}
	
	/**
	 * 根据cardNo删除银行卡
	 * @auther: hesy
	 * @date: 2018/6/20
	 */
	@RequestMapping("/deleteUserCardByCardNo/{cardNo}")
	public int deleteUserCardByCardNo(@PathVariable String cardNo) {
		return bindCardService.deleteUserCardByCardNo(cardNo);
	}
	
	/**
	 * 保存用户绑定的银行卡
	 * @auther: hesy
	 * @date: 2018/6/20
	 */
	@RequestMapping(value = "/insertUserCard", method = RequestMethod.POST)
	public int insertUserCard(@RequestBody @Valid BankCardRequest bankCardRequest) {
		BankCard bankCard = new BankCard();
		BeanUtils.copyProperties(bankCardRequest, bankCard);
		int cnt =bindCardService.insertUserCard(bankCard);
		return cnt;
	}
	
	/**
	 * 更新用户绑定的银行卡
	 * @auther: hesy
	 * @date: 2018/6/20
	 */
	@RequestMapping(value = "/updateUserCard", method = RequestMethod.POST)
	public int updateUserCard(@RequestBody @Valid BankCardRequest bankCardRequest) {
		BankCard bankCard = new BankCard();
		BeanUtils.copyProperties(bankCardRequest, bankCard);
		int cnt = bindCardService.updateUserCard(bankCard);
		return cnt;
	}
	
	/**
	 * 插入绑卡日志
	 * @auther: hesy
	 * @date: 2018/6/20
	 */
	@RequestMapping(value = "/insertBindCardLog", method = RequestMethod.POST)
	public int insertBindCardLog(@RequestBody @Valid BankCardLogRequest bankCardLogRequest) {
		BankCardLog bankCardLog = new BankCardLog();
		BeanUtils.copyProperties(bankCardLogRequest, bankCardLog);
		int cnt = bindCardService.insertBindCardLog(bankCardLog);
		return cnt;
	}

	/**
	 * 更新绑卡授权记录
	 * @auther: hesy
	 * @date: 2018/6/20
	 */
	@RequestMapping(value = "/updateBankSmsLog", method = RequestMethod.POST)
	public boolean updateBankSmsLog(@RequestBody BankSmsLogRequest request) {
		if(null == request) {
			return false;
		}
		int userId = request.getUserId();
		String srvTxCode = request.getSrvTxCode();
		String srvAuthCode = request.getSrvAuthCode();
		String smsSeq = request.getSmsSeq();

		return bindCardService.updateBankSmsLog(userId, srvTxCode, srvAuthCode,smsSeq);
	}

	/**
	 * 查询用户的授权码
	 * @auther: hesy
	 * @date: 2018/6/20
	 */
	@RequestMapping(value = "/selectBankSmsLog", method = RequestMethod.POST)
	public String selectBankSmsLog(@RequestBody BankSmsLogRequest request) {
		if(null == request) {
			return null;
		}
		int userId = request.getUserId();
		String srvTxCode = request.getSrvTxCode();
		String srvAuthCode = request.getSrvAuthCode();

		return bindCardService.selectBankSmsLog(userId, srvTxCode, srvAuthCode);
	}

	/**
	 * 用户删除银行卡后调用方法
	 * @auther: hesy
	 * @date: 2018/7/19
	 */
	@RequestMapping(value = "/update_after_deletecard", method = RequestMethod.POST)
	public Response<Boolean> updateAfterDeleteCard(@RequestBody BankCardUpdateRequest requestBean) {
		if(null == requestBean) {
			return new Response<>(Response.FAIL, "请求参数错误", false);
		}

		try {
			boolean result = bindCardService.updateAfterDeleteCard(requestBean);
			if(!result){
				return new Response<>(Response.FAIL, "银行卡更新失败", false);
			}
		} catch (Exception e) {
			logger.error("银行卡更新异常", e);
			return new Response<>(Response.ERROR, "银行卡更新异常", false);
		}

		return new Response<>(Response.SUCCESS, Response.SUCCESS_MSG, true);
	}
}
