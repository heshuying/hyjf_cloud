package com.hyjf.cs.user.controller.api.userbind;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.security.util.RSA_Hjs;
import com.hyjf.common.security.util.SignUtil;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.web.login.WebLoginController;
import com.hyjf.cs.user.result.BaseResultBeanFrontEnd;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.service.wrb.UserRegisterService;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.cs.user.vo.LoginRequestVO;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongzeshan
 */
@Api(value = "风车理财第三方登录",tags = "api端-风车理财第三方登录")
@Controller
@RequestMapping("/hyjf-wechat/api/user")
public class ApiUserBindController extends BaseUserController {
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	private LoginService loginService;
	@Autowired
	private WebLoginController loginController;
	@Autowired
	private UserRegisterService userRegisterService;
	@Autowired
	BindCardService bindCardService;

	@Value("${hyjf.front.wei.host}")
	public String wechatHost;

	//风车理财机构编号
	public static final Integer FCLC_INSTCODE = 11000003;

	// 跳转失败页面
	public static final String JUMP_FAILE_HTML = "/thirdparty/thirdAuthorResult/failed";
	// 跳转授权页面
	public static final String JUMP_BIND_HTML = "/thirdparty/login";


	/**
	 * 页面授权绑定 - 汇盈金服开放平台接口_投资端_v1.3.2
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@ApiOperation(value = "页面授权绑定api-跳转登陆授权页面",notes = "页面授权绑定api-跳转登陆授权页面")
	@GetMapping(value = "/bindApi.do")
	public ModelAndView bindApi(HttpServletRequest request, HttpServletResponse response, ApiUserPostBean apiUserPostBean){
		// 设置接口结果页的信息（返回Url）
		this.initCheckUtil(apiUserPostBean);
		//TODO:用户登录授权页面
		ModelAndView result = new ModelAndView("api/fast-authorize-login");
		// 验证
		//this.checkPostBeanOfWeb(apiUserPostBean);
		logger.info("验签开始....");
		// 验签
		//this.checkSign(apiUserPostBean);
		logger.info("解密开始....apiUserPostBean is : {}", JSONObject.toJSONString(apiUserPostBean));
		// 解密
		//int bindUniqueId = this.decrypt(apiUserPostBean);
		//logger.info("解密结果....bindUniqueId is : {}", bindUniqueId);
		result.addObject("instcode",apiUserPostBean.getPid());
		//Integer userId = loginService.getUserIdByBind(bindUniqueId, apiUserPostBean.getPid());
		Integer userId = apiUserPostBean.getUserId();
		if(userId == null){
			// 跳转登陆授权画面
			result.addObject("apiForm",new BeanMap(apiUserPostBean));
			//modelAndView.addObject("apiForm",new BeanMap(apiUserPostBean));
		}else{
			// 登陆
			WebViewUserVO webUser = loginService.getWebViewUserByUserId(userId);
			loginService.setToken(webUser);
			//WebUtils.sessionLogin(request, response, webUser);

			//重复绑定
			CheckUtil.check(false,MsgEnum.ERR_BIND_REPEAT);
		}
		String idCard = apiUserPostBean.getIdCard();
		String phone = apiUserPostBean.getMobile();
		String mobile = apiUserPostBean.getMobile()==null?"":phone;
		String readonly = "";
		if (!StringUtils.isEmpty(idCard)) {
			UserVO userVO = loginService.getUserByIdCard(idCard);
			String hyjfMobile = userVO.getMobile();
			if(hyjfMobile != null){
				mobile = hyjfMobile;
				readonly = "readonly";
			}
		}else {
			if (!StringUtils.isEmpty(phone)) {
				readonly = "readonly";
			}
		}
		result.addObject("mobile", mobile);
		result.addObject("readonly", readonly);

		return result;
	}


	/**
	 * 跳转登陆授权页面
	 * @return
	 */
	@RequestMapping("bind_api")
	public ModelAndView bindApi(HttpServletRequest request, HttpServletResponse response, @RequestParam String param,
								@RequestParam(value = "sign", required = false) String sign){
		logger.info("风车理财登陆授权, param is :{}, sign is :{}", param, sign);
		Map<String, String> paramMap = WrbParseParamUtil.parseParam(param);
		ModelAndView modelAndView = new ModelAndView("wrb/wrb_result");

		// 回调url（h5错误页面）
		BaseMapBean baseMapBean=new BaseMapBean();
		ApiUserPostBean apiUserPostBean = null;
		try {
			apiUserPostBean = WrbParseParamUtil.mapToBean(paramMap, ApiUserPostBean.class);
			// 验证
			//apiCommonService.checkPostBeanOfWeb(apiUserPostBean);
			if(!(StringUtils.isNotBlank(apiUserPostBean.getFrom()) && "wrb".equals(apiUserPostBean.getFrom()))){
				baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.FAIL);
				baseMapBean.set(CustomConstants.APP_STATUS_DESC, "用户授权平台异常！");
				baseMapBean.setCallBackAction(CustomConstants.WECHAT_HOST+JUMP_FAILE_HTML);
				modelAndView.addObject("callBackForm", baseMapBean);
				return modelAndView;
			}
			if (StringUtils.isNotBlank(apiUserPostBean.getWrb_user_id())) {
				modelAndView.addObject("instcode", WrbCommonDateUtil.FCLC_INSTCODE);
				baseMapBean.set("instcode", WrbCommonDateUtil.FCLC_INSTCODE+"");
				// 已授权验证
				Integer userid = userRegisterService.selectByUserId(Integer.valueOf(apiUserPostBean.getWrb_user_id()), "11000003");
				logger.info("apiUserPostBean is :{}", apiUserPostBean);
				if(userid == null||userid==0){
					// 跳转登陆授权画面
					baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
					baseMapBean.set(CustomConstants.APP_STATUS_DESC, "用户授权！");
					baseMapBean.setCallBackAction(wechatHost+JUMP_BIND_HTML);
					baseMapBean.setAll(paramMap);
				}else{
					UserVO users = bindCardService.getUsersById(userid);
					BankOpenAccountVO account = bindCardService.getBankOpenAccount(userid);
					String accountId = null;
					if(account!=null&&StringUtils.isNoneBlank(account.getAccount())){
						accountId = account.getAccount();
						/*********** 登录时自动同步线下充值记录 start ***********/
						if (users.getBankOpenAccount() == 1) {
							CommonSoaUtils.synBalance(users.getUserId());
						}
						/*********** 登录时自动同步线下充值记录 end ***********/
					}
					String loginsign = SecretUtil.createToken(userid, users.getUsername(), accountId);
					//登录成功之后风车理财的特殊标记，供后续投资使用
					CookieUtils.addCookie(request, response, CustomConstants.TENDER_FROM_TAG,
							CustomConstants.WRB_CHANNEL_CODE);
					RedisUtils.del("loginFrom"+userid);
					RedisUtils.set("loginFrom"+userid, "2", 1800);
					baseMapBean.setCallBackAction(wechatHost);
					baseMapBean.set("sign", loginsign);
				}
				String idCard = apiUserPostBean.getId_no();
				String phone = apiUserPostBean.getMobile();
				String mobile = apiUserPostBean.getMobile()==null?"":phone;
				String readonly = "";
				if (!StringUtils.isEmpty(idCard)) {
					UserVO userVO = loginService.getUserByIdCard(idCard);
					String hyjfMobile = userVO.getMobile();
					if(hyjfMobile != null){
						mobile = hyjfMobile;
						readonly = "readonly";
					}
				}else {
					if (!StringUtils.isEmpty(phone)) {
						readonly = "readonly";
					}
				}
				baseMapBean.set("mobile", mobile);
				baseMapBean.set("readonly", readonly);
			}else{
				baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.FAIL);
				baseMapBean.set(CustomConstants.APP_STATUS_DESC, "用户授权参数参数异常！");
				baseMapBean.setCallBackAction(CustomConstants.WECHAT_HOST+JUMP_FAILE_HTML);
			}
		} catch (Exception e) {
			logger.error("参数解析失败, paramMap is :"+ paramMap, e);
			baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.FAIL);
			baseMapBean.set(CustomConstants.APP_STATUS_DESC, "用户授权参数解析失败！");
			baseMapBean.setCallBackAction(CustomConstants.WECHAT_HOST+JUMP_FAILE_HTML);

		}
		modelAndView.addObject("callBackForm", baseMapBean);
		return modelAndView;
	}
	/**
	 * 授权按钮
	 * @param
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "风车理财第三方登录", notes = "风车理财第三方登录")
	@ResponseBody
	@PostMapping(value = "/bind")
	public JSONObject bind(HttpServletRequest request, HttpServletResponse response,
						   @ModelAttribute("apiUserPostBean") ApiUserPostBean apiUserPostBean, @ModelAttribute ("loginBean") ApiLoginBean apiLoginBean) throws Exception{
		// 返回对象
		JSONObject jsonObj = new JSONObject();
		logger.info(JSON.toJSONString(apiUserPostBean));
		logger.info(JSON.toJSONString(apiLoginBean));
		// 第三方用户ID
		String bindUniqueId = apiUserPostBean.getWrb_user_id();
		logger.info("bindUniqueId is :{}", bindUniqueId);
		//用户Id
		Integer userId = null;
		//用户名
		String userName = null;

		if(!StringUtils.isNotBlank(bindUniqueId)){
			jsonObj = new JSONObject();
			jsonObj.put("status", "99");
			jsonObj.put("statusCode", "99");
			jsonObj.put("statusDesc", "授权失败，风车理财用户id为空");
			return jsonObj;
		}
		// 用户接受协议验证
		if(!apiLoginBean.getReadAgreement()){
			jsonObj = new JSONObject();
			jsonObj.put("status", "99");
			jsonObj.put("statusCode", "99");
			jsonObj.put("statusDesc", "授权失败，请仔细阅读并同意《汇盈金服授权协议》");
			return jsonObj;
		}
		// 用户手机号码验证
		if(!StringUtils.isNotBlank(apiLoginBean.getLoginBean_loginUserName())){
			jsonObj = new JSONObject();
			jsonObj.put("status", "99");
			jsonObj.put("statusCode", "99");
			jsonObj.put("statusDesc", "授权失败，请输入正确的手机号码");
			return jsonObj;
		}

		//根据登陆账户名取得用户ID和用户名
		UserVO users = loginService.getUser(apiLoginBean.getLoginBean_loginUserName());
		logger.info("users is :{}", users);
		// 未获取的验证在下面登陆时 验证
		if (users != null) {
			//用户Id
			userId = users.getUserId();
			logger.info("userId is :{}", userId);
			//用户名
			userName = users.getUsername();

			// 第三方用户已授权验证
			Integer userid = loginService.getUserIdByBind(Integer.valueOf(bindUniqueId),FCLC_INSTCODE);
			logger.info("userid is:{}", userid);
			if(userid != null&&userid!=0){
				JSONObject jsonResult = new JSONObject();
				jsonResult.put("status", "99");
				jsonResult.put("statusCode","99");
				jsonResult.put("statusDesc", "该用户已绑定汇盈金服，不能重复绑定");
				jsonResult.put("userId",users.getUserId() );
				jsonResult.put("hyjfUserName",userName);
				return jsonResult;
			}
			// 汇盈金服账号已绑定其他用户验证
			String fcUserId = loginService.getBindUniqueIdByUserId(userId, FCLC_INSTCODE);
			if(fcUserId != null){
				jsonObj = new JSONObject();
				jsonObj.put("status", "99");
				jsonObj.put("statusCode", "99");
				jsonObj.put("statusDesc", "该汇盈账号以绑定其他用户，不能重复绑定");

				jsonObj.put("hyjfUserName",userName );
				jsonObj.put("userId",users.getUserId() );
				return jsonObj;
			}
		}else {
			jsonObj = new JSONObject();
			jsonObj.put("status", "99");
			jsonObj.put("statusCode", "99");
			jsonObj.put("statusDesc", "请先注册汇盈金服账号！");
			return jsonObj;
		}
		LoginRequestVO user=new LoginRequestVO();
		user.setUsername(apiLoginBean.getLoginBean_loginUserName());
		user.setPassword(apiLoginBean.getLoginBean_loginPassword());
		// 登陆
		WebResult<WebViewUserVO> login = loginController.login( user,request);
		if (!"000".equals(login.getStatus())) {
			// 登陆失败，返回失败信息
			jsonObj.put("status", login.getStatus());
			jsonObj.put("statusCode", login.getStatus());
			jsonObj.put("statusDesc", login.getStatusDesc());
			return jsonObj;
		}
		BankOpenAccountVO account = loginService.getBankOpenAccount(userId);
		String accountId = null;
		if(account!=null&&StringUtils.isNoneBlank(account.getAccount())){
			accountId = account.getAccount();
			/*********** 登录时自动同步线下充值记录 start ***********/
			if (users.getBankOpenAccount() == 1) {
				CommonSoaUtils.synBalance(users.getUserId());
			}
			/*********** 登录时自动同步线下充值记录 end ***********/
		}
		String sign = SecretUtil.createToken(userId, users.getUsername(), accountId);
		//登录成功之后风车理财的特殊标记，供后续投资使用
		CookieUtils.addCookie(request, response, "wrb","wrb");
		// 授权
		Boolean result = loginService.bindThirdUser(userId,Integer.valueOf(bindUniqueId), FCLC_INSTCODE);
		if(!result){
			jsonObj = new JSONObject();
			jsonObj.put("status", "99");
			jsonObj.put("statusCode", "99");
			jsonObj.put("statusDesc", "授权失败，请联系汇盈金服客服。");
			return jsonObj;
		}
		// 返回第三方页面
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("status", "000");
		jsonResult.put("statusCode", "000");
		jsonResult.put("statusDesc", "授权成功");
		if (StringUtils.isNoneBlank(apiUserPostBean.getTarget_url())) {
			jsonResult.put("retUrl", apiUserPostBean.getTarget_url());
		}else {
			jsonResult.put("retUrl", systemConfig.getServerHost()+"?sign=" + sign);
		}
		jsonResult.put("hyjfUserName",userName );
		jsonResult.put("userId",users.getUserId() );
		jsonResult.put("sign",sign );

		//回调风车理财绑定用户
		Map<String, String> params = new HashMap<>();
		params.put("from", "hyjf");
		params.put("wrb_user_id", apiUserPostBean.getWrb_user_id());
		params.put("pf_user_id", users.getUserId()+"");
		params.put("pf_user_name", users.getUsername());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		params.put("reg_time", simpleDateFormat.format(users.getRegTime()));
		WrbParseParamUtil.wrbCallback(systemConfig.getWrncallbackbindurl(), params);
    	
		return jsonObj;
	}
	/**
	 * 错误页跳转用，初期化结果页数据（错误信息除外）
	 * @param
	 * @param
	 * @param
	 * @return
	 * @author liubin
	 */
	protected void initCheckUtil(ApiUserPostBean apiUserPostBean) {
		// 结果页FormBean赋值
		ApiResultPageBean apiResultPageBean = new ApiResultPageBean();
		apiResultPageBean.setRetUrl(apiUserPostBean.getRetUrl());
		apiResultPageBean.setButMes("返回");
		// 结果页FormBean传入CheckUtil
		CheckUtil.setData(apiResultPageBean);
	}
	/**
	 * 传入信息验证,汇晶社自动登录、绑定
	 * @param bean
	 */
	public void checkPostBeanOfWeb(ApiUserPostBean bean) {
		//传入信息验证
		CheckUtil.check(Validator.isNotNull(bean.getBindUniqueIdScy()),MsgEnum.ERR_OBJECT_REQUIRED, "bindUniqueIdScy");
		CheckUtil.check(Validator.isNotNull(bean.getChkValue()),MsgEnum.ERR_OBJECT_REQUIRED, "chkValue");
		CheckUtil.check(Validator.isNotNull(bean.getPid()), MsgEnum.ERR_OBJECT_REQUIRED, "pid");
		CheckUtil.check(Validator.isNotNull(bean.getRetUrl()), MsgEnum.ERR_OBJECT_REQUIRED, "retUrl");
		CheckUtil.check(Validator.isNotNull(bean.getTimestamp()), MsgEnum.ERR_OBJECT_REQUIRED, "timestamp");
		CheckUtil.check(Validator.isNotNull(bean.getChkValue()), MsgEnum.ERR_OBJECT_REQUIRED, "chkValue");
	}
	/**
	 * 验签
	 * @param bean
	 */
	public void checkSign(ApiUserPostBean bean) {
		CheckUtil.check(SignUtil.checkSignDefaultKey(systemConfig.getAopAccesskey(),bean.getChkValue(), bean.getBindUniqueIdScy(),bean.getPid(),bean.getRetUrl(), bean.getTimestamp()),MsgEnum.ERR_SIGN);
	}
	/**
	 * 解密
	 * @param bean
	 * @return
	 */
	public int decrypt(ApiUserPostBean bean) {
		// RAC解密
		String str = decrypt(bean.getBindUniqueIdScy());
		// 解密结果数字验证
		CheckUtil.check(Validator.isDigit(str),MsgEnum.ERR_OBJECT_DIGIT, "bindUniqueIdScy");
		// 返回
		return Integer.valueOf(str);
	}
	public String decrypt(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		try {
			return RSA_Hjs.decrypt(str,"utf-8",systemConfig.getPublickeyhjs());
		} catch (Exception e) {
			CheckUtil.check(false, MsgEnum.ERR_SYSTEM_UNKNOWN);
		}
		return null;

	}
	/**
	 * 登陆
	 * @param request
	 * @param
	 * @param userName 用户名
	 * @param password 密码
	 * @return LoginResultBean
	 */

	public LoginResultBean doLogin( HttpServletRequest request,String userName , String password) {

		logger.info("请求登录接口,手机号为：【"+userName+"】");
		LoginResultBean result = new LoginResultBean();
		if(StringUtils.isBlank(userName)||StringUtils.isBlank(password)){
			result.setEnum(ResultEnum.PARAM);
			return result;
		}
		//密码解密
		password = RSAJSPUtil.rsaToPassword(password);

		Map<String, String> user = loginService.updateLoginInAction(userName, password, CustomUtil.getIpAddr(request));
		String stt = user.get("stt");
		switch (stt) {
			case "-1":
				result.setEnum(ResultEnum.ERROR_001);
				break;
			case "-2":
				result.setEnum(ResultEnum.ERROR_002);
				break;
			case "-3":
				result.setEnum(ResultEnum.ERROR_003);
				break;
			case "-4":
				result.setEnum(ResultEnum.ERROR_043);
				break;
			case "-5":
				result.setEnum(ResultEnum.ERR_PASSWORD_ERROR_TOO_MANEY);
				break;
			default:
				// 登录完成返回值
				result.setEnum(ResultEnum.SUCCESS);
				result.setSign(user.get("sign"));
				int  userId=Integer.valueOf(user.get("userId"));
				RedisUtils.del("loginFrom"+userId);
				RedisUtils.set("loginFrom"+userId, "2", 1800);
				break;
		}

		return result;
	}
}
