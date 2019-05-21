package com.hyjf.cs.trade.controller.wechat.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.trade.bean.UserDirectRechargeBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.recharge.RechargeService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * 用户充值Controller
 * 
 * @author zhangqingqing
 *
 */
@Api(tags = "weChat端-用户充值接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/hyjf-wechat/wx/recharge")
public class WechatRechargeController extends BaseTradeController{
	
	Logger logger = LoggerFactory.getLogger(WechatRechargeController.class);

	@Autowired
	private RechargeService userRechargeService;
	@Autowired
	private AuthService authService;
	@Autowired
	SystemConfig systemConfig;


	/**
	 * 调用充值接口
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "用户充值", notes = "用户充值")
	@PostMapping("/recharge")
	public WeChatResult recharge(@RequestHeader(value = "wjtClient",required = false) String wjtClient,@RequestHeader(value = "sign") String sign, @RequestHeader(value = "userId") Integer userId, HttpServletRequest request) throws Exception {
		logger.info("wechat充值服务");
		WeChatResult result = new WeChatResult();
		String ipAddr = CustomUtil.getIpAddr(request);
		// 交易金额
		String money = request.getParameter("money");
		// 用户的手机号
		String mobile = request.getParameter("mobile");
		UserDirectRechargeBean directRechargeBean = new UserDirectRechargeBean();
		if (!this.authService.checkPaymentAuthStatus(userId)) {
			throw new CheckException(MsgEnum.ERR_AUTH_USER_PAYMENT);
		}
		// 拼装参数 调用江西银行
		String bgRetUrl = "http://CS-TRADE/hyjf-wechat/wx/recharge/bgreturn?phone="+mobile;
		directRechargeBean.setPlatform(CommonConstant.CLIENT_WECHAT);
		String host = super.getFrontHost(systemConfig,CommonConstant.CLIENT_WECHAT);
		if(StringUtils.isNotBlank(wjtClient)){
			// 如果是温金投的  则跳转到温金投那边
			host = super.getWjtFrontHost(systemConfig,wjtClient);
		}
		String forgotPwdUrl = super.getForgotPwdUrl(CommonConstant.CLIENT_WECHAT, request, systemConfig);
		if(StringUtils.isNotBlank(wjtClient)){
			// 如果是温金投的  则跳转到温金投那边
			forgotPwdUrl = super.getWjtForgotPwdUrl(wjtClient, request, systemConfig);
		}
		// 拼装参数 调用江西银行
		String retUrl = host+"/user/bank/recharge/result/failed/?sign="+sign+"&money="+money;
		String successfulUrl = host+"/user/bank/recharge/result/success/?sign="+sign+"&money="+money;
		directRechargeBean.setRetUrl(retUrl);
		directRechargeBean.setNotifyUrl(bgRetUrl);
		directRechargeBean.setSuccessfulUrl(successfulUrl);
        directRechargeBean.setForgotPwdUrl(forgotPwdUrl);
		BankCallBean bean = userRechargeService.rechargeService(directRechargeBean,userId,ipAddr,mobile,money);
		if (null == bean) {
			throw new CheckException(MsgEnum.ERR_BANK_CALL);
		}
		Map<String,Object> map = new HashMap<>();
		try {
			map = BankCallUtils.callApiMap(bean);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CheckException(MsgEnum.ERR_BANK_CALL);
		}
		result.setData(map);
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
		logger.info("[wechat页面充值异步回调开始]");
		String phone = request.getParameter("phone");
		bean.setMobile(phone);
		bean.convert();
		Integer userId = Integer.parseInt(bean.getLogUserId());
		UserVO user = this.userRechargeService.getUsers(userId);
		Map<String, String> params = new HashMap<String, String>();
		params.put("ip", bean.getUserIP());
		params.put("mobile",bean.getMobile());
		if (user!=null&&bean != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
			JSONObject msg = this.userRechargeService.handleRechargeInfo(bean, params);
			// 充值成功
			if (msg != null && "0".equals(msg.get("error"))) {
				logger.info("充值成功,手机号:[" + bean.getMobile() + "],用户ID:[" + userId + "],充值金额:[" + bean.getTxAmount() + "]");
				result.setMessage("充值成功");
				result.setStatus(true);
				return result;
			} else {
				result.setMessage("充值失败");
				result.setStatus(false);
				return result;
			}
		}
		logger.info(WechatRechargeController.class.getName(), "/bgreturn", "[wechat用户充值完成后,回调结束]");
		result.setMessage("充值失败");
		result.setStatus(false);
		return result;
	}
}
