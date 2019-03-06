package com.hyjf.cs.trade.controller.app.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.DES;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.trade.bean.UserDirectRechargeBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.recharge.RechargeService;
import com.hyjf.cs.trade.vo.AppRechargeVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户充值Controller
 * 
 * @author
 *
 */
@Api(tags = "app端-用户充值接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/hyjf-app")
public class AppRechargeController extends BaseTradeController{
	
	Logger logger = LoggerFactory.getLogger(AppRechargeController.class);

	@Autowired
	private RechargeService userRechargeService;
	@Autowired
	private AuthService authService;
	@Autowired
	SystemConfig systemConfig;

	/**
	 *
	 * app获取快捷充值地址的数据接口 需要将请求参数拼接到地址上并带回
	 *
	 * @author pcc
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/user/bank/recharge/getRechargeUrl")
	@ApiOperation(value = "获取用户充值信息", notes = "获取用户充值信息")
	public JSONObject getRechargeUrl(AppRechargeVO vo, @RequestHeader(value = "key") String key) {
		JSONObject object=new JSONObject();
		object.put("request","/user/bank/recharge/getRechargeUrl");
		/** 充值接口 */
		String RECHARGE_URL = super.getFrontHost(systemConfig,vo.getPlatform()) + "/public/formsubmit?requestType="+CommonConstant.APP_BANK_REQUEST_TYPE_RECHARGE;
		String mobile = "";

		// 校验数据并拼接回传地址
		if (Validator.isNull(vo.getMoney())) {
			object.put("status",CustomConstants.APP_STATUS_FAIL);
			object.put("statusDesc","请求参数非法");
		} else {// 拼接充值地址并返回
			mobile = strEncode(vo.getMobile());
			StringBuffer sb = new StringBuffer(RECHARGE_URL);
			sb.append("&money=").append(vo.getMoney()).append("&isMencry=").append("2").append("&mobile=").append(mobile);
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
	 * @return
	 */
	@ApiOperation(value = "用户充值", notes = "用户充值")
	@ResponseBody
	@PostMapping("/bank/user/userDirectRecharge/recharge")
	public AppResult<Object> recharge(@RequestHeader(value = "userId") Integer userId, @RequestHeader(value = "key") String key,
									  @RequestHeader(value = "sign") String sign,HttpServletRequest request) throws Exception {
		logger.info("app充值服务");

		AppResult<Object> result = new AppResult<Object>();
		String mobile = request.getParameter("mobile");// 手机号
		String money = request.getParameter("money");// 交易金额
		String isMencry = request.getParameter("isMencry");// 版本标识
		String platform = request.getParameter("platform");// 平台
		logger.info("解密前的手机号["+mobile+"],充值金额:[" + money + "]");
		if(!"1".equals(isMencry)){
			if (Validator.isNull(key)) {
				throw new CheckException(MsgEnum.ERR_PARAM_NUM);
			}
			// 解密
			mobile = DES.decodeValue(key, mobile);
		}

		logger.info("充值手机号为["+mobile+"],充值金额:[" + money + "]");
		WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY + userId, WebViewUserVO.class);
		UserVO userVO=userRechargeService.getUserByUserId(user.getUserId());
		if(null==userVO){
			throw new CheckException(MsgEnum.ERR_USER_NOT_LOGIN);
		}

		if(0==userVO.getIsSetPassword()){
			throw new CheckException(MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
		}
		if(userVO.getBankOpenAccount()==0){
			throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
		}
		if (!this.authService.checkPaymentAuthStatus(userId)) {
			throw new CheckException(MsgEnum.ERR_AUTH_USER_PAYMENT);
		}
		logger.info("user is :{}", JSONObject.toJSONString(user));
		String ipAddr = CustomUtil.getIpAddr(request);
		UserDirectRechargeBean directRechargeBean = new UserDirectRechargeBean();
		// 拼装参数 调用江西银行
		String retUrl = super.getFrontHost(systemConfig,platform)+"/user/bank/recharge/result/failed";
		String bgRetUrl = "http://CS-TRADE/hyjf-app/bank/user/userDirectRecharge/bgreturn?phone="+mobile;
        bgRetUrl=splicingParam(bgRetUrl,request);
        if(null==money||money.isEmpty()){
        	money="0";
		}
		BigDecimal recharge = new BigDecimal(money);
		recharge = recharge.setScale(2, BigDecimal.ROUND_HALF_UP);
		String successfulUrl = super.getFrontHost(systemConfig,platform)+"/user/bank/recharge/result/success?money="+ CommonUtils.formatAmount(recharge);
		retUrl += "?token=1&sign=" +sign;
		successfulUrl += "&token=1&sign=" +sign;
		directRechargeBean.setRetUrl(retUrl);
		directRechargeBean.setNotifyUrl(bgRetUrl);
		directRechargeBean.setSuccessfulUrl(successfulUrl);
		directRechargeBean.setChannel(BankCallConstant.CHANNEL_APP);
		directRechargeBean.setPlatform(platform);
		directRechargeBean.setForgotPwdUrl(super.getForgotPwdUrl(platform,request,systemConfig));
		BankCallBean bean = userRechargeService.rechargeService(directRechargeBean,userId,ipAddr,mobile,money);
		if (null == bean) {
			throw new CheckException(MsgEnum.ERR_BANK_CALL);
		}
		try {
			Map<String,Object> data =  BankCallUtils.callApiMap(bean);
			result.setData(data);
		} catch (Exception e) {
			logger.info("app端充值失败");
			logger.error(e.getMessage());
			throw new CheckException(MsgEnum.ERR_BANK_CALL);
		}

		result.setStatus("000");
		return result;
	}

    private String splicingParam(String bgRetUrl, HttpServletRequest request) {
	    String sign=request.getParameter("sign");
        String token=request.getParameter("token");
        StringBuffer sb = new StringBuffer(bgRetUrl);

        if(bgRetUrl.indexOf("?")!=-1){
            sb.append("&sign=").append(sign).append("&token=").append(token);
        }else{
            sb.append("?sign=").append(sign).append("&token=").append(token);
        }
	    return sb.toString();
    }




    /**
	 * 页面充值异步回调
	 * @param request
	 * @param bean
	 * @return
	 */
	@ApiIgnore
	@ResponseBody
	@PostMapping("/bank/user/userDirectRecharge/bgreturn")
	public BankCallResult bgreturn(HttpServletRequest request,@RequestBody BankCallBean bean) {
		BankCallResult result = new BankCallResult();
		logger.info("[app页面充值异步回调开始]");
		String phone = request.getParameter("phone");
		String userId = bean.getLogUserId();
		bean.setMobile(phone);
		bean.convert();
		UserVO user = this.userRechargeService.getUsers(Integer.parseInt(userId));
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
