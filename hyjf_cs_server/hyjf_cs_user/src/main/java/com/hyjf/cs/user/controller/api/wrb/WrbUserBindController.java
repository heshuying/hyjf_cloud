package com.hyjf.cs.user.controller.api.wrb;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.user.bean.ApiUserPostBean;
import com.hyjf.cs.user.bean.ApiWbrUserPostBean;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.bean.LoginResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.web.login.WebLoginController;
import com.hyjf.cs.user.result.BaseResultBeanFrontEnd;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.service.wrb.UserRegisterService;
import com.hyjf.cs.user.util.RSAJSPUtil;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongzeshan
 */
@Api(value = "风车理财第三方登录绑定",tags = "api端-风车理财第三方登录绑定")
@Controller
@RequestMapping("/hyjf-wechat/api/user")
public class WrbUserBindController extends BaseUserController {

    public static final Logger logger = LoggerFactory.getLogger(WrbUserBindController.class);
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
    /** 汇盈金服调用风车理财接口通知注册绑定成功 **/
    @Value("${wrb.callback.bind.url}")
    public String HYJF_REQ_PUB_KEY_PATH;

	// 跳转失败页面
	public static final String JUMP_FAILE_HTML = "/thirdparty/thirdAuthorResult/failed";
	// 跳转授权页面
	public static final String JUMP_BIND_HTML = "/thirdparty/login";

    @InitBinder("apiUserPostBean")
    public void initBinderApiUserPostBean(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("apiUserPostBean_");
    }
    @InitBinder("loginBean")
    public void initBinderLoginBean(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("loginBean_");
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
				Integer userid = userRegisterService.selectByUserId(Integer.valueOf(apiUserPostBean.getWrb_user_id()), String.valueOf(WrbCommonDateUtil.FCLC_INSTCODE));
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
					}
					String loginsign = SecretUtil.createToken(userid, users.getUsername(), accountId);
					//登录成功之后风车理财的特殊标记，供后续出借使用
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
                    if (userVO != null) {
                        String hyjfMobile = userVO.getMobile();
                        if (hyjfMobile != null) {
                            mobile = hyjfMobile;
                            readonly = "readonly";
                        }
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
     * 授权操作
     * @param request
     * @param response
     * @param apiUserPostBean
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "bind", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public JSONObject bind(HttpServletRequest request, HttpServletResponse response,
                           @ModelAttribute ApiWbrUserPostBean apiUserPostBean) throws Exception{
        // 返回对象
        JSONObject jsonObj = new JSONObject();
        // 第三方用户ID
        String bindUniqueId = apiUserPostBean.getWrb_user_id();
        if (StringUtils.isBlank(bindUniqueId)) {
            jsonObj = new JSONObject();
            jsonObj.put("status", BaseResultBeanFrontEnd.FAIL);
            jsonObj.put("statusCode", BaseResultBeanFrontEnd.FAIL);
            jsonObj.put("statusDesc", "授权失败，第三方用户ID为空");
            return jsonObj;
        }
        logger.info("bindUniqueId is :{}", bindUniqueId);
        //用户Id
        Integer userId = null;
        //用户名
        String userName = null;


        // 用户接受协议验证
        if(!apiUserPostBean.getReadAgreement()){
            jsonObj = new JSONObject();
            jsonObj.put("status", BaseResultBeanFrontEnd.FAIL);
            jsonObj.put("statusCode", BaseResultBeanFrontEnd.FAIL);
            jsonObj.put("statusDesc", "授权失败，请仔细阅读并同意《汇盈金服授权协议》");
            return jsonObj;
        }
        // 用户手机号码验证
        if(!StringUtils.isNotBlank(apiUserPostBean.getLoginBean_loginUserName())){
            jsonObj = new JSONObject();
            jsonObj.put("status", BaseResultBeanFrontEnd.FAIL);
            jsonObj.put("statusCode", BaseResultBeanFrontEnd.FAIL);
            jsonObj.put("statusDesc", "授权失败，请输入正确的手机号码");
            return jsonObj;
        }


        //根据登陆账户名取得用户ID和用户名
        UserVO users = loginService.getUser(apiUserPostBean.getLoginBean_loginUserName());
        logger.info("users is :{}", users);
        // 未获取的验证在下面登陆时 验证
        if (users != null) {
            //用户Id
            userId = users.getUserId();
            logger.info("userId is :{}", userId);
            //用户名
            userName = users.getUsername();

            // 第三方用户已授权验证
            Integer userid = loginService.getUserIdByBind(Integer.parseInt(bindUniqueId), WrbCommonDateUtil.FCLC_INSTCODE);
            logger.info("userid is:{}", userid);
            if(userid != null&&userid!=0){
                JSONObject jsonResult = new JSONObject();
                jsonResult.put("status", BaseResultBeanFrontEnd.FAIL);
                jsonResult.put("statusCode", BaseResultBeanFrontEnd.FAIL);
                jsonResult.put("statusDesc", "该用户已绑定汇盈金服，不能重复绑定");

                jsonResult.put("hyjfUserName",userName );
                jsonResult.put("userId",users.getUserId() );
                return jsonResult;
            }

            // 汇盈金服账号已绑定其他用户验证
            String fcUserId = loginService.getBindUniqueIdByUserId(userId, WrbCommonDateUtil.FCLC_INSTCODE);
            logger.info("fcUserId is:{}", fcUserId);
            if(StringUtils.isNotBlank(fcUserId)){
                jsonObj = new JSONObject();
                jsonObj.put("status", BaseResultBeanFrontEnd.FAIL);
                jsonObj.put("statusCode", BaseResultBeanFrontEnd.FAIL);
                jsonObj.put("statusDesc", "该汇盈账号以绑定其他用户，不能重复绑定");
                jsonObj.put("hyjfUserName",userName );
                jsonObj.put("userId",users.getUserId() );
                return jsonObj;
            }
        }else{
            jsonObj = new JSONObject();
            jsonObj.put("status", BaseResultBeanFrontEnd.FAIL);
            jsonObj.put("statusCode", BaseResultBeanFrontEnd.FAIL);
            jsonObj.put("statusDesc", "请先注册汇盈金服账号！");
            return jsonObj;
        }
        // 登陆
        LoginResultBean baseResultBean = this.login(request, apiUserPostBean.getLoginBean_loginUserName(),apiUserPostBean.getLoginBean_loginPassword(),"2");
        if (!baseResultBean.getStatus().equals("000")) {
            // 登陆失败，返回失败信息
            jsonObj.put("status", baseResultBean.getStatus());
            jsonObj.put("statusCode", BaseResultBeanFrontEnd.FAIL);
            jsonObj.put("statusDesc", baseResultBean.getStatusDesc());
            return jsonObj;
        }
        String sign = baseResultBean.getSign();
        //登录成功之后风车理财的特殊标记，供后续出借使用
        CookieUtils.addCookie(request, response, CustomConstants.TENDER_FROM_TAG,
                CustomConstants.WRB_CHANNEL_CODE);
        // 授权
        Boolean result = loginService.bindThirdUser(userId, Integer.valueOf(bindUniqueId), WrbCommonDateUtil.FCLC_INSTCODE);
        if(!result){
            jsonObj = new JSONObject();
            jsonObj.put("status", BaseResultBeanFrontEnd.FAIL);
            jsonObj.put("statusCode", BaseResultBeanFrontEnd.FAIL);
            jsonObj.put("statusDesc", "授权失败，请联系汇盈金服客服。");
            return jsonObj;
        }

        // 返回第三方页面
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("status", BaseResultBeanFrontEnd.SUCCESS);
        jsonResult.put("statusCode", BaseResultBeanFrontEnd.SUCCESS);
        jsonResult.put("statusDesc", "授权成功");
        if (StringUtils.isNoneBlank(apiUserPostBean.getTarget_url())) {
            jsonResult.put("retUrl", apiUserPostBean.getTarget_url());
        }else {
            jsonResult.put("retUrl", wechatHost+"?sign=" + sign);
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
        Date date = users.getRegTime();

        params.put("reg_time", simpleDateFormat.format(date));
        WrbParseParamUtil.wrbCallback(HYJF_REQ_PUB_KEY_PATH, params);

        return jsonResult;
    }

    /**
     * 登录接口
     *
     * @param request
     * @param userName
     * @param password
     * @param env
     * @return
     */
    @ApiOperation(value = "用户登录接口", notes = "用户登录接口")
    @ResponseBody
    @PostMapping(value = "/doLogin")
    public LoginResultBean login(HttpServletRequest request, @RequestParam String userName, @RequestParam String password,
                                @RequestParam(value = "env", defaultValue = "") String env) {
        LoginResultBean result = new LoginResultBean();
        CheckUtil.check(null != userName && null != password, MsgEnum.STATUS_CE000001);
        //密码解密
        password = RSAJSPUtil.rsaToPassword(password);
        // weChat 只支持手机号登录
        if (!CommonUtils.isMobile(userName)) {
            throw new ReturnMessageException(MsgEnum.ERR_USER_LOGIN);
        }
        //判断用户输入的密码错误次数---开始
        UserVO user = loginService.getUser(userName);
        Map<String, String> errorInfo=loginService.insertErrorPassword(userName,password,BankCallConstant.CHANNEL_WEI,user);
        if (!errorInfo.isEmpty()){
            logger.error("weChat端登录失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(errorInfo.get("info"));
            return result;
        }
        //判断用户输入的密码错误次数---结束
        WebViewUserVO userVO = loginService.login(userName, password, com.hyjf.cs.user.util.GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_WEI,user);
        if (userVO != null) {
            logger.info("weChat端登录成功, userId is :{}", userVO.getUserId());
            // 登录成功后,将用户ID返回给前端
            result.setUserId(String.valueOf(userVO.getUserId()));

            BankOpenAccountVO account = loginService.getBankOpenAccount(userVO.getUserId());
            String accountId = null;
            if(account!=null&&StringUtils.isNoneBlank(account.getAccount())){
                accountId = account.getAccount();
            }

            String sign = SecretUtil.createToken(userVO.getUserId(), userVO.getUsername(), accountId);
            // 登录完成返回值
            result.setEnum(ResultEnum.SUCCESS);
            result.setSign(sign);
            if (StringUtils.isNotBlank(env)) {
                //登录成功之后风车理财的特殊标记，供后续出借使用
                RedisUtils.del("loginFrom" + userVO.getUserId());
                RedisUtils.set("loginFrom" + userVO.getUserId(), env, 1800);
            }
            // 登录完成返回值
            result.setStatus(ResultEnum.SUCCESS.getStatus());
            result.setStatusDesc("登录成功");
            result.setSign(userVO.getToken());
        } else {
            logger.error("weChat端登录失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_USER_LOGIN.getMsg());
        }
        return result;
    }

}
