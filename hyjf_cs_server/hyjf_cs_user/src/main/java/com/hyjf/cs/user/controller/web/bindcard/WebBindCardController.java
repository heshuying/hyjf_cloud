package com.hyjf.cs.user.controller.web.bindcard;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.BankCardUtil;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.BindCardVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * web端用户解绑卡接口
 * @author hesy
 */
@Api(value = "web端-用户解绑卡接口",tags = "web端-用户解绑卡接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user/card")
public class WebBindCardController extends BaseUserController {
	private static final Logger logger = LoggerFactory.getLogger(WebBindCardController.class);

	@Autowired
	SystemConfig systemConfig;
	@Autowired
	BindCardService bindCardService;

	/**
	 *  我的银行卡页面数据
	 */
	@ApiOperation(value = "我的银行卡页面数据", notes = "我的银行卡页面数据")
	@PostMapping(value = "/mycard", produces = "application/json; charset=utf-8")
	public WebResult<Object> myCardInit(@RequestHeader(value = "userId") int userId) {
		WebResult<Object> result = new WebResult<Object>();
		Map<String,Object> resultMap = new HashMap<>();
		WebViewUserVO user = bindCardService.getUserFromCache(userId);

		if(user == null){
			result.setStatus(WebResult.ERROR);
			result.setStatusDesc("用户未登录");
			return result;
		}
		if(!user.isBankOpenAccount()){
			result.setStatus(WebResult.ERROR);
			result.setStatusDesc("用户未开户");
			return result;
		}

		BankCardVO bankCardVO = bindCardService.queryUserCardValid(String.valueOf(user.getUserId()),null);
		if(bankCardVO == null){
//			result.setStatus(WebResult.ERROR);
			result.setStatusDesc("用户未绑卡");
			//未绑卡
			resultMap.put("bindType", 0);
			result.setData(resultMap);
			return result;
		}

		JxBankConfigVO bankConfigVO = bindCardService.getBankConfigById(bankCardVO.getBankId());
		//已绑卡
		resultMap.put("bindType", 1);
		if(bankConfigVO != null){
            resultMap.put("bankicon", systemConfig.getServerHost() +bankConfigVO.getBankLogo());
            resultMap.put("bankname", bankConfigVO.getBankName());
        }else{
            resultMap.put("bankicon", systemConfig.getServerHost() + "/data/upfiles/filetemp/image/bank_log.png");
            resultMap.put("bankname", StringUtils.isBlank(bankCardVO.getBank())?"":bankCardVO.getBank());
        }
		resultMap.put("bankcard", BankCardUtil.getCardNo(bankCardVO.getCardNo()));
		resultMap.put("bankcardNotEncrypt", bankCardVO.getCardNo());
		resultMap.put("cardId", bankCardVO.getId());

		result.setData(resultMap);
		return result;
	}

	/**
	 *  用户绑卡发送短信验证码
	 * @param bindCardVO
	 * @return
	 */
	@ApiOperation(value = "用户绑卡发送短信验证码", notes = "用户绑卡发送短信验证码")
	@PostMapping(value = "/bindCardSendCode", produces = "application/json; charset=utf-8")
	public WebResult<Object> bindCardSendCode(@RequestHeader(value = "userId") int userId, @RequestBody @Valid BindCardVO bindCardVO) {
		logger.info("绑卡发送验证码开始, mobile :{}，cardNo:{}", bindCardVO.getMobile(), bindCardVO.getCardNo());
		WebResult<Object> result = new WebResult<Object>();
		result.setData("");

        bindCardService.checkParamSendcode(userId, bindCardVO.getMobile(), bindCardVO.getCardNo());
        // 请求银行绑卡接口
        BankCallBean bankBean = null;
		try {
			bankBean = bindCardService.callSendCode(userId,bindCardVO.getMobile(), BankCallMethodConstant.TXCODE_CARD_BIND_PLUS, ClientConstants.CHANNEL_PC,bindCardVO.getCardNo());
		} catch (Exception e) {
			result.setStatus(ApiResult.ERROR);
			result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
			logger.error("请求绑卡验证码接口发生异常", e);
			return result;
		}

		if (bankBean == null) {
			result.setStatus(ApiResult.FAIL);
			result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
			logger.error("请求绑卡验证码接口失败");
			return result;
		}

        if(!BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()) && !"JX900651".equals(bankBean.getRetCode())) {
			result.setStatus(ApiResult.FAIL);
			result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
			logger.error("请求绑卡验证码接口失败");
			return result;
        }else {
			result.setData(bankBean.getSrvAuthCode());
		}
		return result;
	}

	/**
	 * 用户绑卡
	 * @param bindCardVO
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "用户绑卡", notes = "用户绑卡")
	@PostMapping(value = "/bindCard", produces = "application/json; charset=utf-8")
	public WebResult<Object> bindCard(@RequestHeader(value = "userId") int userId, @RequestBody @Valid BindCardVO bindCardVO, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("绑卡开始, bindCardVO :{}", JSONObject.toJSONString(bindCardVO));
		WebResult<Object> result = new WebResult<Object>();
		
		WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY+userId, WebViewUserVO.class);
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
			return result;
		}

		if(bankBean!=null && "CE999042".equals(bankBean.getRetCode())){
			result.setStatus(ApiResult.FAIL);
			result.setStatusDesc("短信验证码错误");
			logger.error("短信验证码错误");
			return result;
		}

        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
        	result.setStatus(ApiResult.FAIL);
			result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
			logger.error("请求绑卡接口失败");
			return result;
        }
        
        // 绑卡请求后业务处理
		try {
//			BankCallBean bankBean = new BankCallBean();
//			bankBean.setAccountId("6212461910000080264");
//			bankBean.setLogUserId("5274");
//			LogAcqResBean resBean = new LogAcqResBean();
//			bankBean.setLogAcqResBean(resBean);
//			resBean.setCardNo("6225880142489757");
//			resBean.setMobile("18911288535");
			bindCardService.updateAfterBindCard(bankBean);
		} catch (Exception e) {
			result.setStatus(ApiResult.FAIL);
			result.setStatusDesc(MsgEnum.ERR_CARD_SAVE.getMsg());
			logger.error("绑卡后处理异常", e);
			return result;
		}
        
		return result;
	}
	

}

	