package com.hyjf.cs.user.controller.web.bindcard;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.user.constants.BindCardError;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.BindCardVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;

/**
 * web端用户解绑卡接口
 * @author hesy
 */
@Api(value = "web端用户解绑卡接口")
@RestController
@RequestMapping("/web/card")
public class WebBindCardController {
	private static final Logger logger = LoggerFactory.getLogger(WebBindCardController.class);

	@Autowired
	BindCardService bindCardService;
	
	@ApiOperation(value = "用户绑卡发送短信验证码", notes = "用户绑卡发送短信验证码")
	@PostMapping(value = "/bindCardSendCode", produces = "application/json; charset=utf-8")
	public ApiResult<Object> bindCardSendCode(@RequestHeader(value = "token", required = true) String token, @RequestParam String mobile, @RequestParam String cardNo) {
		logger.info("绑卡发送验证码开始, mobile :{}，cardNo:{}", mobile, cardNo);
		ApiResult<Object> result = new ApiResult<Object>();
		
		WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        
        bindCardService.checkParamSendcode(user.getUserId(), mobile, cardNo);
        // 请求银行绑卡接口
        BankCallBean bankBean = null;
		try {
			bankBean = bindCardService.callSendCode(user.getUserId(),mobile, BankCallMethodConstant.TXCODE_CARD_BIND_PLUS, ClientConstants.CHANNEL_PC,cardNo);
		} catch (Exception e) {
			result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc(BindCardError.BANK_CALL_ERROR.getMessage());
			logger.error("请求绑卡验证码接口发生异常", e);
		}
        
        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
        	result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc(BindCardError.BANK_CALL_ERROR.getMessage());
			logger.error("请求绑卡验证码接口失败");
        }else {
			result.setResult(bankBean.getSrvAuthCode());
		}
		return result;
	}
	
	@ApiOperation(value = "用户绑卡", notes = "用户绑卡")
	@PostMapping(value = "/bindCard", produces = "application/json; charset=utf-8")
	public ApiResult<Object> bindCard(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid BindCardVO bindCardVO, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("绑卡开始, bindCardVO :{}", JSONObject.toJSONString(bindCardVO));
		ApiResult<Object> result = new ApiResult<Object>();
		
		WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        String userIp = GetCilentIP.getIpAddr(request);
        
        bindCardService.checkParamBindCard(bindCardVO, user.getUserId());
        
        // 请求银行绑卡接口
        BankCallBean bankBean = null;
		try {
			bankBean = bindCardService.callBankBindCard(bindCardVO, user.getUserId(), userIp);
		} catch (Exception e) {
			result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc(BindCardError.BANK_CALL_ERROR.getMessage());
			logger.error("请求绑卡接口发生异常", e);
		}
        
        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
        	result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc(BindCardError.BANK_CALL_ERROR.getMessage());
			logger.error("请求绑卡接口失败");
        }
        
        // 绑卡请求后业务处理
        try {
			bindCardService.updateAfterBindCard(bankBean);
		} catch (ParseException e) {
			result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc(BindCardError.CARD_SAVE_ERROR.getMessage());
			logger.error("绑卡后处理异常", e);
		}
        
		return result;
	}
	
	@ApiOperation(value = "用户解绑卡", notes = "用户解绑卡")
	@PostMapping(value = "/unBindCard", produces = "application/json; charset=utf-8")
	public ApiResult<Object> unBindCard(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid BindCardVO bindCardVO, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("解绑卡开始, bindCardVO :{}", JSONObject.toJSONString(bindCardVO));
		ApiResult<Object> result = new ApiResult<Object>();
		
		WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        
        bindCardService.checkParamUnBindCard(bindCardVO, user.getUserId());
        
        // 请求银行绑卡接口
        BankCallBean bankBean = null;
		try {
			bankBean = bindCardService.callBankUnBindCard(bindCardVO, user.getUserId());
		} catch (Exception e) {
			result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc(BindCardError.BANK_CALL_ERROR.getMessage());
			logger.error("请求解绑卡接口发生异常", e);
		}
        
        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
        	result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc(BindCardError.BANK_CALL_ERROR.getMessage());
			logger.error("请求解绑卡接口失败");
        }
        
        // 绑卡请求后业务处理
        try {
			bindCardService.updateAfterUnBindCard(bankBean);
		} catch (Exception e) {
			result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc(BindCardError.CARD_SAVE_ERROR.getMessage());
			logger.error("解绑卡后处理异常", e);
		}
        
		return result;
	}
	
	

}

	