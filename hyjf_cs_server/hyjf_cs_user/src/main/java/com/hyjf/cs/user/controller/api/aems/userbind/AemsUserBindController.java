package com.hyjf.cs.user.controller.api.aems.userbind;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.security.util.RSA_Hjs;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.ApiSignUtil;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.web.login.WebLoginController;
import com.hyjf.cs.user.result.BaseResultBeanFrontEnd;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.cs.user.vo.LoginRequestVO;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * api端-Aems页面授权绑定
 * @author Zha Daojian
 * @date 2018/12/13 14:38
 * @param
 * @return
 **/
@Api(value = "api端-Aems页面授权绑定",tags = "api端-Aems页面授权绑定")
@Controller
@RequestMapping("/hyjf-api/aems")
public class AemsUserBindController extends BaseUserController {
	private static final Logger logger = LoggerFactory.getLogger(AemsUserBindController.class);

	@Autowired
	SystemConfig systemConfig;
	@Autowired
	private LoginService loginService;
	@Autowired
	private WebLoginController loginController;

	@Value("${hyjf.front.host}")
	public String webHost;

    // 跳转授权页面
    public static final String JUMP_BIND_HTML = "/user/fastAuth/login";

	@InitBinder("apiUserPostBean")
	public void initBinderApiUserPostBean(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("apiUserPostBean_");
	}
	@InitBinder("loginBean")
	public void initBinderLoginBean(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("loginBean_");
	}


	/**
	 * Aems页面授权绑定 - 汇盈金服开放平台接口_投资端_v1.3.2
	 * @author Zha Daojian
	 * @date 2018/12/14 11:07
	 * @param request, response, userPostBean
	 * @return org.springframework.web.servlet.ModelAndView
	 **/
	@ApiOperation(value = "页面授权绑定api-跳转登陆授权页面",notes = "页面授权绑定api-跳转登陆授权页面")
	@PostMapping(value = "/bindAems")
	public ModelAndView bindApi(HttpServletRequest request, HttpServletResponse response,@RequestBody AemsUserPostRequsettBean userPostBean){
		// 设置接口结果页的信息（返回Url）
		this.initCheckUtil(userPostBean);
		ModelAndView modelAndView = new ModelAndView("wrb/wrb_result");
        BaseMapBean baseMapBean=new BaseMapBean();
        // 验证
        this.checkPostBeanOfWeb(userPostBean);
        logger.info("验签开始....");
        // 验签
        this.checkSign(userPostBean);
        logger.info("解密开始....apiUserPostBean is : {}", JSONObject.toJSONString(userPostBean));
        // 解密
        int bindUniqueId = Integer.parseInt(userPostBean.getBindUniqueIdScy());
        logger.info("解密结果....bindUniqueId is : {}", bindUniqueId);
        baseMapBean.set("instcode",String.valueOf(userPostBean.getPid()));
        Integer userId = loginService.getUserIdByBind(bindUniqueId, userPostBean.getPid());
        logger.info("用户ID："+userId);
        // 回调url（h5错误页面）
        if(userId == null||userId==0){
            // 跳转登陆授权画面
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "用户授权！");
            baseMapBean.setCallBackAction(webHost+JUMP_BIND_HTML);
        }else{
			// 登陆
			UserVO userVO = loginService.getUsersById(userId);
			loginService.loginOperationOnly(userVO,userVO.getUsername(),GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_APP);

            //重复绑定
            CheckUtil.check(false,MsgEnum.ERR_BIND_REPEAT);
        }
        String idCard = userPostBean.getIdCard();
        String phone = userPostBean.getMobile();
        String mobile = userPostBean.getMobile()==null?"":phone;
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
        baseMapBean.set("mobile", mobile==null?"":mobile);
        baseMapBean.set("readonly", readonly);
        baseMapBean.setAll(objectToMap(userPostBean));
        modelAndView.addObject("callBackForm", baseMapBean);
		return modelAndView;
	}

	/**
	 * 授权按钮
	 * @param
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "第三方登录", notes = "第三方登录")
	@ResponseBody
	@PostMapping(value = "/bind")
	public JSONObject bind(HttpServletRequest request, HttpServletResponse response,
                           @RequestBody ApiUserPostBean apiUserPostBean) throws Exception{
		// 返回对象
		JSONObject jsonObj = new JSONObject();
		logger.info(JSON.toJSONString(apiUserPostBean));
		// 第三方用户ID
		Integer bindUniqueId = this.decrypt(apiUserPostBean);
		logger.info("bindUniqueId is :{}", bindUniqueId);
		//用户Id
		Integer userId = null;
		//用户名
		String userName = null;

		if(!StringUtils.isNotBlank(String.valueOf(bindUniqueId))){
			jsonObj = new JSONObject();
			jsonObj.put("status", "99");
			jsonObj.put("statusCode", "99");
			jsonObj.put("statusDesc", "授权失败，第三方用户id为空");
			return jsonObj;
		}
		// 用户接受协议验证
		if(!apiUserPostBean.getReadAgreement()){
			jsonObj = new JSONObject();
			jsonObj.put("status", "99");
			jsonObj.put("statusCode", "99");
			jsonObj.put("statusDesc", "授权失败，请仔细阅读并同意《汇盈金服授权协议》");
			return jsonObj;
		}
		// 用户手机号码验证
		if(!StringUtils.isNotBlank(apiUserPostBean.getLoginUserName())){
			jsonObj = new JSONObject();
			jsonObj.put("status", "99");
			jsonObj.put("statusCode", "99");
			jsonObj.put("statusDesc", "授权失败，请输入正确的手机号码");
			return jsonObj;
		}

		//根据登陆账户名取得用户ID和用户名
		UserVO users = loginService.getUser(apiUserPostBean.getLoginUserName());
		logger.info("users is :{}", users);
		// 未获取的验证在下面登陆时 验证
		if (users != null) {
			//用户Id
			userId = users.getUserId();
			logger.info("userId is :{}", userId);
			//用户名
			userName = users.getUsername();

			// 第三方用户已授权验证
			Integer userid = loginService.getUserIdByBind(Integer.valueOf(bindUniqueId),apiUserPostBean.getPid());
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
			String fcUserId = loginService.getBindUniqueIdByUserId(userId, apiUserPostBean.getPid());
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
		user.setUsername(apiUserPostBean.getLoginUserName());
		user.setPassword(apiUserPostBean.getLoginPassword());
		// 登陆
		WebResult<WebViewUserVO> login = loginController.login( user,request);
		if (!"000".equals(login.getStatus())) {
			// 登陆失败，返回失败信息
			jsonObj.put("status", login.getStatus());
			jsonObj.put("statusCode", login.getStatus());
			jsonObj.put("statusDesc", login.getStatusDesc());
			return jsonObj;
		}
		// 授权
		Boolean result = loginService.bindThirdUser(userId,Integer.valueOf(bindUniqueId), apiUserPostBean.getPid());
		if(!result){
			jsonObj = new JSONObject();
			jsonObj.put("status", "99");
			jsonObj.put("statusCode", "99");
			jsonObj.put("statusDesc", "授权失败，请联系汇盈金服客服。");
			return jsonObj;
		}
        // 返回第三方页面
		jsonObj.put("status", "000");
		jsonObj.put("statusCode", "0");
		jsonObj.put("statusDesc", "授权成功");
		jsonObj.put("retUrl", apiUserPostBean.getRetUrl());
		jsonObj.put("bindUniqueIdScy", apiUserPostBean.getBindUniqueIdScy());
		jsonObj.put("userId",users.getUserId() );
		jsonObj.put("mobile",apiUserPostBean.getLoginUserName() );
		jsonObj.put("username",userName );
		jsonObj.put("token",login.getData().getToken() );
		jsonObj.put("roleId",login.getData().getRoleId() );
		jsonObj.put("iconUrl",login.getData().getIconUrl() );
		jsonObj.put("hyjfUserName",userName );
        Long timestamp = System.currentTimeMillis();
		jsonObj.put("timestamp",timestamp);
		jsonObj.put("chkValue",ApiSignUtil.encryptByRSA(apiUserPostBean.getPid()+timestamp+""));
        logger.info("chkValue:"+ApiSignUtil.encryptByRSA(apiUserPostBean.getPid()+timestamp+""));
    	
		return jsonObj;
	}
	/**
	 * 错误页跳转用，初期化结果页数据（错误信息除外）
	 * @author Zha Daojian
	 * @date 2018/12/14 11:06checkPostBeanOfWeb
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
	/**
	 * 传入信息验证,AEMS自动登录、绑定
	 * @param bean
	 */
	public void checkPostBeanOfWeb(AemsUserPostRequsettBean bean) {
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
	public void checkSign(AemsUserPostRequsettBean bean) {
		CheckUtil.check(SignUtil.AEMSVerifyRequestSign(bean, "/aems//bindAems"),MsgEnum.ERR_SIGN);
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

    public static Map<String, String> objectToMap(Object obj){
        if(obj == null){
            return null;
        }

        Map<String, String> map = new HashMap<String, String>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), String.valueOf(field.get(obj)));
            }catch (Exception e){
                // logger.error("转换异常！");
				logger.error(e.getMessage());
            }
        }

        return map;
    }
}
