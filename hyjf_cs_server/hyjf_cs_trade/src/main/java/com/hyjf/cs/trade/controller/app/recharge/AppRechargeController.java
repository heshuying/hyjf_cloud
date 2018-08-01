package com.hyjf.cs.trade.controller.app.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.RechargeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户充值Controller
 * 
 * @author zhangqingqing
 *
 */
@Api(tags = "app端用户充值接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/hyjf-app/recharge")
public class AppRechargeController extends BaseTradeController{
	
	Logger logger = LoggerFactory.getLogger(AppRechargeController.class);

	@Autowired
	private RechargeService userRechargeService;

	@Autowired
	SystemConfig systemConfig;


	
	/**
	 * 调用充值接口
	 * @param request
	 * @param
	 * @param mobile
	 * @param money
	 * @return
	 */
	@ApiOperation(value = "用户充值", notes = "用户充值")
	@PostMapping("/page")
	public ModelAndView recharge(@RequestHeader(value = "token") String token,HttpServletRequest request, String mobile, String money) throws Exception {
		logger.info("app充值服务");
		String ipAddr = CustomUtil.getIpAddr(request);
		BankCallBean bean = userRechargeService.rechargeService(token,ipAddr,mobile,money);
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView = BankCallUtils.callApi(bean);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ReturnMessageException(MsgEnum.ERR_BANK_CALL);
		}
		return modelAndView;
	}

	/**
	 * @Author: zhangqingqing
	 * @Desc :页面充值同步
	 * @Param: * @param request
	 * @param bean
	 * @Date: 12:40 2018/6/5
	 * @Return: ModelAndView
	 */
	@ApiOperation(value = "用户充值同步回调", notes = "用户充值")
	@PostMapping("/return")
	public ModelAndView pageReturn(HttpServletRequest request, BankCallBean bean) {
		logger.info("[app页面充值同步回调开始]");
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
	public BankCallResult bgreturn(HttpServletRequest request,BankCallBean bean) {
		BankCallResult result = new BankCallResult();
		logger.info("[app页面充值异步回调开始]");
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
		logger.info(AppRechargeController.class.getName(), "/bgreturn", "[用户充值完成后,回调结束]");
		result.setMessage("充值失败");
		result.setStatus(false);
		return result;
	}
}
