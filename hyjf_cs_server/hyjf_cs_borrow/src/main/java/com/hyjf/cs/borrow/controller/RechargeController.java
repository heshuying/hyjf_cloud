package com.hyjf.cs.borrow.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.borrow.config.SystemConfig;
import com.hyjf.cs.borrow.service.RechargeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户充值Controller
 * 
 * @author zhangqq
 *
 */
@Controller
@RequestMapping(value = "/web/recharge")
public class RechargeController{
	
	Logger logger = LoggerFactory.getLogger(RechargeController.class);

	@Autowired
	private RechargeService userRechargeService;

	@Autowired
	SystemConfig systemConfig;


	@RequestMapping(value = "/init")
	public String init(Model model) {
		
		return "recharge/recharge";
	}
	
	/**
	 * 调用充值接口
	 * @param request
	 * @param response
	 * @param mobile
	 * @param money
	 * @return
	 */
	@RequestMapping("/page")
	public ModelAndView userAuthInves(HttpServletRequest request, HttpServletResponse response, String mobile, String money) {
		logger.info("充值服务");
		ModelAndView modelAndView = userRechargeService.userAuthInves(request,response,mobile,money);
		return modelAndView;
	}

	/**
	 *
	 * 页面充值同步
	 * @author sunss
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping("/return")
	public ModelAndView pageReturn(HttpServletRequest request, HttpServletResponse response,
								   @ModelAttribute BankCallBean bean) {
		ModelAndView modelAndView = new ModelAndView();
		String money = request.getParameter("txAmount");
		String frontParams = request.getParameter("frontParams");
		String isSuccess = request.getParameter("isSuccess");
		// 充值成功
		DecimalFormat df = new DecimalFormat("#,##0.00");
		BigDecimal feeAmt = new BigDecimal(money);
		if(StringUtils.isBlank(bean.getRetCode())&&StringUtils.isNotBlank(frontParams)){
			JSONObject jsonParm = JSONObject.parseObject(frontParams);
			if(jsonParm.containsKey("RETCODE")){
				bean.setRetCode(jsonParm.getString("RETCODE"));
			}
		}
		bean.convert();
		if (isSuccess != null && "1".equals(isSuccess)) {
			modelAndView = new ModelAndView("/bank/user/recharge/recharge_success");
			modelAndView.addObject("message", "页面充值成功");
			modelAndView.addObject("balance", df.format(feeAmt).toString());
			return modelAndView;
		}
		String retCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
		if (bean!=null&& BankCallStatusConstant.RESPCODE_SUCCESS.equals(retCode)) {
			modelAndView = new ModelAndView("/bank/user/recharge/recharge_success");
			modelAndView.addObject("message", "页面充值成功");
			modelAndView.addObject("balance", df.format(feeAmt).toString());
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("/bank/user/recharge/recharge_error");
			modelAndView.addObject("message", userRechargeService.getBankRetMsg(bean.getRetCode()));
			return modelAndView;
		}
	}

	/**
	 * 页面充值异步回调
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bgreturn")
	public BankCallResult bgreturn(HttpServletRequest request, HttpServletResponse response,
								   @ModelAttribute BankCallBean bean) {
		BankCallResult result = new BankCallResult();
		logger.info("[缴费授权异步回调开始]");
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
		logger.info(RechargeController.class.getName(), "/bgreturn", "[用户充值完成后,回调结束]");
		result.setMessage("充值失败");
		result.setStatus(false);
		return result;
	}
}
