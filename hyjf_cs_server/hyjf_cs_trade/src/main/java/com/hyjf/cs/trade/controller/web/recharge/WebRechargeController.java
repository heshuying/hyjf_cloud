package com.hyjf.cs.trade.controller.web.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.annotation.RequestLimit;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.UserDirectRechargeBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.recharge.RechargeService;
import com.hyjf.cs.trade.vo.BankRechargeVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户充值Controller
 * 
 * @author zhangqingqing
 *
 */
@Api(value = "web端-用户充值接口",tags = "web端-用户充值接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/hyjf-web/recharge")
public class WebRechargeController extends BaseTradeController{
	
	Logger logger = LoggerFactory.getLogger(WebRechargeController.class);

	@Autowired
	private RechargeService userRechargeService;
	@Autowired
	SystemConfig systemConfig;


	/**
	 * @Description 跳转充值页面
	 * @Author pangchengchao
	 * @Version v0.1
	 * @Date
	 */
	@ApiOperation(value = "获取用户充值信息", notes = "用户充值")
	@PostMapping("/toRecharge")
	public WebResult<Object> toRecharge(@RequestHeader(value = "userId") Integer userId) {
		WebViewUserVO user=userRechargeService.getUserFromCache(userId);
		WebResult<Object> objectWebResult=userRechargeService.toRecharge(user);
		return objectWebResult;
	}
	
	/**
	 * 调用充值接口
	 * @param request
	 * @param
	 * @return
	 */
	@ApiOperation(value = "用户充值", notes = "用户充值")
	@PostMapping("/page")
	@RequestLimit(seconds=3)
	public WebResult<Object> recharge(@RequestHeader(value = "wjtClient",required = false) String wjtClient,@RequestHeader(value = "userId") int userId,
									  HttpServletRequest request,
									  @RequestBody @Valid BankRechargeVO bankRechargeVO) throws Exception {
		logger.info("web充值服务");
		WebResult<Object> result = new WebResult<Object>();
		String ipAddr = CustomUtil.getIpAddr(request);
		UserDirectRechargeBean directRechargeBean = new UserDirectRechargeBean();
		directRechargeBean.setChannel(BankCallConstant.CHANNEL_PC);
		String host = super.getFrontHost(systemConfig,String.valueOf(ClientConstants.WEB_CLIENT));
		if(StringUtils.isNotBlank(wjtClient)){
			// 如果是温金投的  则跳转到温金投那边
			host = super.getFrontHost(systemConfig,wjtClient);
		}
		String forgotPwdUrl = super.getForgotPwdUrl(CommonConstant.CLIENT_PC, request, systemConfig);
		if(StringUtils.isNotBlank(wjtClient)){
			// 如果是温金投的  则跳转到温金投那边
			forgotPwdUrl = super.getWjtForgotPwdUrl(wjtClient, request, systemConfig);
		}
		directRechargeBean.setForgotPwdUrl(forgotPwdUrl);
		// 拼装参数 调用江西银行
		String retUrl = host+"/user/rechargeError?token=1";
		String bgRetUrl = "http://CS-TRADE/hyjf-web/recharge/bgreturn" + "?phone="+bankRechargeVO.getMobile();
		String successfulUrl = host+"/user/rechargeSuccess?money="+bankRechargeVO.getMoney();
		directRechargeBean.setRetUrl(retUrl);
		directRechargeBean.setNotifyUrl(bgRetUrl);
		directRechargeBean.setSuccessfulUrl(successfulUrl);
		BankCallBean bean = userRechargeService.rechargeService(directRechargeBean,userId,ipAddr,bankRechargeVO.getMobile(),bankRechargeVO.getMoney());
		if (null == bean) {
			throw new CheckException(MsgEnum.ERR_BANK_CALL);
		}
		try {
			Map<String,Object> data =  BankCallUtils.callApiMap(bean);
			result.setData(data);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CheckException(MsgEnum.ERR_BANK_CALL);
		}
		return result;
	}


	/**
	 * @Author: zhangqingqing
	 * @Desc :页面充值异步回调
	 * @Param: * @param request
	 * @param bean
	 * @Date: 12:40 2018/6/5
	 * @Return: BankCallResult
	 */
	@ApiOperation(value = "用户充值异步回调", notes = "用户充值")
	@ResponseBody
	@PostMapping("/bgreturn")
	public BankCallResult bgreturn(HttpServletRequest request,@RequestBody BankCallBean bean) {
		BankCallResult result = new BankCallResult();
		logger.info("[web页面充值异步回调开始]");
		String phone = request.getParameter("phone");
		bean.setMobile(phone);
		bean.convert();
		Integer userId = Integer.parseInt(bean.getLogUserId());
		UserVO user = this.userRechargeService.getUsers(userId);
		Map<String, String> params = new HashMap<String, String>();
		params.put("ip", bean.getUserIP());
		params.put("mobile",phone);
        JSONObject msg = this.userRechargeService.handleRechargeInfo(bean, params);
		if (user!=null&&bean != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
			// 充值成功
			if (msg != null && "0".equals(msg.get("error"))) {
				logger.info("web充值成功,手机号:[" + bean.getMobile() + "],用户ID:[" + userId + "],充值金额:[" + bean.getTxAmount() + "]");
				result.setMessage("充值成功");
				result.setStatus(true);
				return result;
			} else {
				result.setMessage("充值失败");
				result.setStatus(false);
				return result;
			}
		}
		logger.info(WebRechargeController.class.getName(), "/bgreturn", "[web用户充值完成后,回调结束]");
		result.setMessage("充值失败");
		result.setStatus(false);
		return result;
	}

	/**
	 * @Description web端查询充值失败原因
	 * @Author pangchengchao
	 * @Version v0.1
	 * @Date
	 */
	@ApiOperation(value = "查询充值失败原因", notes = "查询充值失败原因")
	@PostMapping("/seachFiledMess")
	@ResponseBody
	public WebResult<Object> seachUserBankRechargeErrorMessgae(@RequestBody @Valid BankRechargeVO bankRechargeVO) {
		logger.info("查询提现失败原因start,logOrdId:{}", bankRechargeVO.getLogOrdId());
		WebResult<Object> result = userRechargeService.seachUserBankRechargeErrorMessgae(bankRechargeVO.getLogOrdId());
		return result;
	}


	/**
	 * @Description web端快捷充值限额
	 * @Author pangchengchao
	 * @Version v0.1
	 * @Date
	 */
	@ApiOperation(value = "快捷充值限额", notes = "快捷充值限额")
	@PostMapping("/rechargeQuotaLimit")
	@ResponseBody
	public WebResult<Object> rechargeQuotaLimit() {
		logger.info("web端快捷充值限额开始");
		WebResult<Object> result = new WebResult<Object>();
		List<JxBankConfigVO> list = userRechargeService.getRechargeQuotaLimit();
		for (JxBankConfigVO jxBankConfigVO : list) {
			BigDecimal monthCardQuota = jxBankConfigVO.getMonthCardQuota();
			BigDecimal singleQuota = jxBankConfigVO.getSingleQuota();
			BigDecimal singleCardQuota = jxBankConfigVO.getSingleCardQuota();
			jxBankConfigVO.setSingleQuota(new BigDecimal(CommonUtils.formatBigDecimal(singleQuota.divide(new BigDecimal(10000)))));
			jxBankConfigVO.setSingleCardQuota(new BigDecimal(CommonUtils.formatBigDecimal(singleCardQuota.divide(new BigDecimal(10000)))));
			jxBankConfigVO.setMonthCardQuota(new BigDecimal(CommonUtils.formatBigDecimal(monthCardQuota.divide(new BigDecimal(10000)))));
		}
		result.setData(list);
		return result;
	}
}
