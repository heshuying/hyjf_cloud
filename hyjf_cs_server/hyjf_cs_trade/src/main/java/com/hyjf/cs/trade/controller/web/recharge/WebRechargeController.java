package com.hyjf.cs.trade.controller.web.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.RechargeService;
import com.hyjf.cs.trade.vo.BankRechargeVO;
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
import javax.validation.Valid;
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
@Api(value = "web端用户充值接口",tags = "web端用户充值接口")
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
	public WebResult<Object> toRecharge(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
		WebViewUserVO user=userRechargeService.getUsersByToken(token);
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
	public WebResult<Object> recharge(@RequestHeader(value = "token") String token,HttpServletRequest request,@RequestBody @Valid BankRechargeVO bankRechargeVO) throws Exception {
		logger.info("web充值服务");
		WebResult<Object> result = new WebResult<Object>();
		String ipAddr = CustomUtil.getIpAddr(request);
		BankCallBean bean = userRechargeService.rechargeService(token,ipAddr,bankRechargeVO.getMobile(),bankRechargeVO.getMoney());
		ModelAndView modelAndView = new ModelAndView();
		try {
			Map<String,Object> data =  BankCallUtils.callApiMap(bean);
			result.setData(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ReturnMessageException(MsgEnum.ERR_BANK_CALL);
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
	public BankCallResult bgreturn(HttpServletRequest request,BankCallBean bean) {
		BankCallResult result = new BankCallResult();
		logger.info("[web页面充值异步回调开始]");
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
		logger.info(WebRechargeController.class.getName(), "/bgreturn", "[用户充值完成后,回调结束]");
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
	@ApiOperation(value = "web端查询充值失败原因", notes = "web端查询充值失败原因")
	@PostMapping("/seachFiledMess")
	@ResponseBody
	public WebResult<Object> seachUserBankRechargeErrorMessgae(@RequestParam("logOrdId") String logOrdId) {
		logger.info("查询提现失败原因start,logOrdId:{}", logOrdId);
		WebResult<Object> result = userRechargeService.seachUserBankRechargeErrorMessgae(logOrdId);
		return result;
	}
}
