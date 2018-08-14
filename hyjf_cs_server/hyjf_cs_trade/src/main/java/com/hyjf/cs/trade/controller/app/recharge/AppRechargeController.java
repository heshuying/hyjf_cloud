package com.hyjf.cs.trade.controller.app.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.recharge.RechargeService;
import com.hyjf.cs.trade.vo.AppRechargeVO;
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
 * @author
 *
 */
@Api(tags = "app端用户充值接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/hyjf-app")
public class AppRechargeController extends BaseTradeController{
	
	Logger logger = LoggerFactory.getLogger(AppRechargeController.class);

	@Autowired
	private RechargeService userRechargeService;

	@Autowired
	SystemConfig systemConfig;

	/**
	 *
	 * app获取快捷充值地址的数据接口 需要将请求参数拼接到地址上并带回
	 *
	 * @author renxingchen
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/bank/recharge/getRechargeUrl")
	@ApiOperation(value = "获取用户充值信息", notes = "获取用户充值信息")
	public JSONObject getRechargeUrl(AppRechargeVO vo) {
		JSONObject object=new JSONObject();
		object.put("request","/user/bank/recharge/getRechargeUrl");
		/** 充值接口 */
		String RECHARGE_URL = super.getFrontHost(systemConfig,vo.getPlatform()) + "/bank/user/userDirectRecharge/recharge?";
		String mobile = "";
		String token = "";
		String order = "";
		// 校验数据并拼接回传地址
		if (Validator.isNull(vo.getMoney())) {
			object.put("status",CustomConstants.APP_STATUS_FAIL);
			object.put("statusDesc","请求参数非法");
		} else {// 拼接充值地址并返回
			mobile = strEncode(vo.getMobile());
			token = strEncode(vo.getToken());
			order = strEncode(vo.getOrder());
			StringBuffer sb = new StringBuffer(RECHARGE_URL);
			sb.append("version=").append(vo.getVersion()).append(CustomConstants.APP_PARM_AND).append("netStatus=").append(vo.getNetStatus()).append(CustomConstants.APP_PARM_AND).append("platform=")
					.append(vo.getPlatform()).append(CustomConstants.APP_PARM_AND).append("token=").append(token).append(CustomConstants.APP_PARM_AND).append("sign=")
					.append(vo.getSign()).append(CustomConstants.APP_PARM_AND).append("randomString=").append(vo.getRandomString()).append(CustomConstants.APP_PARM_AND).append("order=")
					.append(order).append(CustomConstants.APP_PARM_AND).append("platform=").append(strEncode(vo.getPlatform())).append(CustomConstants.APP_PARM_AND)
					.append("money=").append(vo.getMoney()).append(CustomConstants.APP_PARM_AND).append("cardNo=").append(vo.getCardNo()).append(CustomConstants.APP_PARM_AND).append("code=")
					.append(vo.getCode()).append("&isMencry=").append("2").append("&mobile=").append(mobile);

			object.put("rechargeUrl",sb.toString());
			logger.info("请求充值接口url："+sb.toString());
			object.put("status",CustomConstants.APP_STATUS_SUCCESS);
			object.put("statusDesc",CustomConstants.APP_STATUS_DESC_SUCCESS);
		}
		return object;
	}
	
	/**
	 * 调用充值接口
	 * @param request
	 * @param
	 * @param mobile
	 * @param money
	 * @return
	 */
	@ApiOperation(value = "用户充值", notes = "用户充值")
	@PostMapping("/bank/user/userDirectRecharge/recharge")
	public ModelAndView recharge(@RequestHeader(value = "userId") Integer userId,HttpServletRequest request, String mobile, String money) throws Exception {
		logger.info("app充值服务");
		String ipAddr = CustomUtil.getIpAddr(request);
		BankCallBean bean = userRechargeService.rechargeService(userId,ipAddr,mobile,money);
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
	 * 页面充值异步回调
	 * @param request
	 * @param bean
	 * @return
	 */
	@ApiOperation(value = "用户充值异步回调", notes = "用户充值")
	@ResponseBody
	@PostMapping("/bank/user/userDirectRecharge/bgreturn")
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
