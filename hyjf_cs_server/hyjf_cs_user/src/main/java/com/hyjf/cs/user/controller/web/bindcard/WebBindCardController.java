package com.hyjf.cs.user.controller.web.bindcard;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.controller.BaseUserController;
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
@Api(value = "web端-用户解绑卡接口",description = "web端-用户解绑卡接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/card")
public class WebBindCardController extends BaseUserController {
	private static final Logger logger = LoggerFactory.getLogger(WebBindCardController.class);

	@Autowired
	BindCardService bindCardService;
	
	@ApiOperation(value = "用户绑卡发送短信验证码", notes = "用户绑卡发送短信验证码")
	@PostMapping(value = "/bindCardSendCode", produces = "application/json; charset=utf-8")
	public WebResult<Object> bindCardSendCode(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid BindCardVO bindCardVO) {
		logger.info("绑卡发送验证码开始, mobile :{}，cardNo:{}", bindCardVO.getMobile(), bindCardVO.getCardNo());
		WebResult<Object> result = new WebResult<Object>();
		
		WebViewUserVO user = bindCardService.getUsersByToken(token);
        
        bindCardService.checkParamSendcode(user.getUserId(), bindCardVO.getMobile(), bindCardVO.getCardNo());
        // 请求银行绑卡接口
        BankCallBean bankBean = null;
		try {
			bankBean = bindCardService.callSendCode(user.getUserId(),bindCardVO.getMobile(), BankCallMethodConstant.TXCODE_CARD_BIND_PLUS, ClientConstants.CHANNEL_PC,bindCardVO.getCardNo());
		} catch (Exception e) {
			result.setStatus(ApiResult.ERROR);
			result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
			logger.error("请求绑卡验证码接口发生异常", e);
		}

		if (bankBean == null) {
			result.setStatus(ApiResult.FAIL);
			result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
			logger.error("请求绑卡验证码接口失败");
		}

        if(!BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()) && !"JX900651".equals(bankBean.getRetCode())) {
			result.setStatus(ApiResult.FAIL);
			result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
			logger.error("请求绑卡验证码接口失败");
        }else {
			result.setData(bankBean.getSrvAuthCode());
		}
		return result;
	}
	
	@ApiOperation(value = "用户绑卡", notes = "用户绑卡")
	@PostMapping(value = "/bindCard", produces = "application/json; charset=utf-8")
	public WebResult<Object> bindCard(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid BindCardVO bindCardVO, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("绑卡开始, bindCardVO :{}", JSONObject.toJSONString(bindCardVO));
		WebResult<Object> result = new WebResult<Object>();
		
		WebViewUserVO user = RedisUtils.getObj(RedisConstants.USER_TOKEN_REDIS+token, WebViewUserVO.class);
        String userIp = GetCilentIP.getIpAddr(request);
        
        bindCardService.checkParamBindCard(bindCardVO, user.getUserId());
        
        // 请求银行绑卡接口
        BankCallBean bankBean = null;
		try {
			bankBean = bindCardService.callBankBindCard(bindCardVO, user.getUserId(), userIp);
		} catch (Exception e) {
			result.setStatus(ApiResult.FAIL);
			result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
			logger.error("请求绑卡接口发生异常", e);
		}

		if(bankBean!=null && "CE999042".equals(bankBean.getRetCode())){
			result.setStatus(ApiResult.FAIL);
			result.setStatusDesc("短信验证码错误");
			logger.error("短信验证码错误");
		}

        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
        	result.setStatus(ApiResult.FAIL);
			result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
			logger.error("请求绑卡接口失败");
        }
        
        // 绑卡请求后业务处理
        try {
			bindCardService.updateAfterBindCard(bankBean);
		} catch (ParseException e) {
			result.setStatus(ApiResult.FAIL);
			result.setStatusDesc(MsgEnum.ERR_CARD_SAVE.getMsg());
			logger.error("绑卡后处理异常", e);
		}
        
		return result;
	}
	

}

	