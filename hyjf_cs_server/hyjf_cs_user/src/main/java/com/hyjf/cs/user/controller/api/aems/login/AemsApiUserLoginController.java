package com.hyjf.cs.user.controller.api.aems.login;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.security.util.RSA_Hjs;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.AemsUserPostRequsettBean;
import com.hyjf.cs.user.bean.ApiResultPageBean;
import com.hyjf.cs.user.bean.ApiSkipFormBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Aems用戶自动登录
 * @author Zha Daojian
 * @date 2018/9/17 17:08
 * @param
 * @return
 **/
@Api(value = "api端-AEMS用戶自动登录",tags = "api端-AEMS用戶自动登录")
@RestController
@RequestMapping("/hyjf-api/api/aems")
public class AemsApiUserLoginController extends BaseUserController {
	/** 跳转的jsp页面 */
	private static final String SEND_JSP = "/bank/bank_send";

	@Autowired
	private LoginService loginService;
	@Autowired
	private SystemConfig systemConfig;

	@ApiOperation(value = "用戶自动登录", notes = "用戶自动登录")
	@PostMapping(value = "/thirdLoginApi.do")
    public ModelAndView thirdLoginApi(AemsUserPostRequsettBean bean){
    	// 设置接口结果页的信息（返回Url）
    	this.initCheckUtil(bean);

		// 验证
        checkPostBeanOfWeb(bean);

		ModelAndView modelAndView = new ModelAndView(SEND_JSP);
		ApiSkipFormBean apiSkipFormBean = new ApiSkipFormBean();
		String returl = CustomConstants.HOST + "/api/aems/thirdLogin.do";

		apiSkipFormBean.setAction(returl);
		apiSkipFormBean.set("bindUniqueIdScy", bean.getBindUniqueIdScy());
		apiSkipFormBean.set("pid", bean.getPid()+"");
		apiSkipFormBean.set("retUrl", bean.getRetUrl());
		apiSkipFormBean.set("timestamp", String.valueOf(bean.getTimestamp()));
		apiSkipFormBean.set("chkValue", bean.getChkValue());
		modelAndView.addObject("bankForm", apiSkipFormBean);
		return modelAndView;
    }

	/**
	 * 第三方登录
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/thirdLogin")
	public ModelAndView thirdLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute AemsUserPostRequsettBean bean){
		logger.debug("请求进来啦:【{}】", JSON.toJSONString(bean));
		// 验证
		checkPostBeanOfWeb(bean);
		// 验签
		checkSign(bean);
		//CheckUtil.check(com.hyjf.common.security.util.SignUtil.checkSignDefaultKey(bean.getChkValue(), bean.getBindUniqueIdScy(),bean.getPid(),bean.getRetUrl(), bean.getTimestamp()), MsgEnum.ERR_SIGN);
		// 解密
		int bindUniqueId = bindUniqueIdDecrypt(bean);
		// 查询Userid
		Integer userId = loginService.getUserIdByBind(bindUniqueId, bean.getPid());
		//Integer userId = bean.getUserId();
		// 登陆
		UserVO userVO = loginService.getUsersById(userId);
		loginService.loginOperationOnly(userVO,userVO.getUsername(),GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_APP);


		// 返回到hyjf的系统
		return new ModelAndView("redirect:" + systemConfig.getServerHost() + "/hyjf-web/user/pandect");
	}

	/**
	 * 用户id解密
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	private int bindUniqueIdDecrypt(AemsUserPostRequsettBean bean){
		// RAC解密
		String str = decrypt(bean.getBindUniqueIdScy());
		// 解密结果数字验证
		CheckUtil.check(Validator.isDigit(str), MsgEnum.ERR_OBJECT_DIGIT, "bindUniqueIdScy");
		// 返回
		return Integer.parseInt(str);
	}
	private String decrypt(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		try {
			return RSA_Hjs.decrypt(str,"utf-8",systemConfig.getPublickeyhjs());
		} catch (Exception e) {
			CheckUtil.check(false, MsgEnum.ERR_OBJECT_DECRYPT,"用户ID");
		}
		return null;

	}
	/**
	 * 验签
	 * @param bean
	 */
	public void checkSign(AemsUserPostRequsettBean bean) {
		CheckUtil.check(SignUtil.AEMSVerifyRequestSign(bean, "/aems//bindAems"),MsgEnum.ERR_SIGN);
	}
	/**
	 * 传入信息验证,自动登录
	 * @param bean
	 */
	public void checkPostBeanOfWeb(AemsUserPostRequsettBean bean) {
		//传入信息验证
		CheckUtil.check(Validator.isNotNull(bean.getBindUniqueIdScy()), MsgEnum.ERR_OBJECT_REQUIRED, "bindUniqueIdScy");
		CheckUtil.check(Validator.isNotNull(bean.getChkValue()), MsgEnum.ERR_OBJECT_REQUIRED, "chkValue");
		CheckUtil.check(Validator.isNotNull(bean.getPid()), MsgEnum.ERR_OBJECT_REQUIRED, "pid");
		CheckUtil.check(Validator.isNotNull(bean.getRetUrl()), MsgEnum.ERR_OBJECT_REQUIRED, "retUrl");
		CheckUtil.check(Validator.isNotNull(bean.getTimestamp()), MsgEnum.ERR_OBJECT_REQUIRED, "timestamp");
	}

	/**
	 * 错误页跳转用，初期化结果页数据（错误信息除外）
	 * @author Zha Daojian
	 * @date 2018/12/14 11:06
	 * @param userPostBean
	 * @return void
	 **/
	protected void initCheckUtil(AemsUserPostRequsettBean userPostBean) {
		// 结果页FormBean赋值
		ApiResultPageBean apiResultPageBean = new ApiResultPageBean();
		apiResultPageBean.setRetUrl(userPostBean.getRetUrl());
		apiResultPageBean.setButMes("返回");
		// 结果页FormBean传入CheckUtil
		CheckUtil.setData(apiResultPageBean);
	}
}
