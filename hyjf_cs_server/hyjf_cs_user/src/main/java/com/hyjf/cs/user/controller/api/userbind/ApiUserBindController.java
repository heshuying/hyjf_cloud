package com.hyjf.cs.user.controller.api.userbind;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.security.util.RSA_Hjs;
import com.hyjf.common.security.util.SignUtil;
import com.hyjf.common.util.ApiSignUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.ApiResultPageBean;
import com.hyjf.cs.user.bean.ApiUserPostBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.web.login.WebLoginController;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.vo.LoginRequestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author dongzeshan
 */
@Api(value = "风车理财第三方登录",tags = "api端-风车理财第三方登录")
@RestController
@RequestMapping("/api/user")
public class ApiUserBindController extends BaseUserController {
	@Autowired
	SystemConfig systemConfig;
    @Autowired
    private LoginService loginService;
	@Autowired
	private WebLoginController loginController;
    /** 纳觅财富机构编号 */
    public static final String NMCF_PID = "11000002";
    
	/**
	 * 授权按钮
	 * @param
	 * @return
	 * @throws Exception 
	 */
    @ApiOperation(value = "风车理财第三方登录", notes = "风车理财第三方登录")
    @PostMapping(value = "/bind", produces = "application/json; charset=utf-8")
    public JSONObject bind(HttpServletRequest request, HttpServletResponse response, 
    		@RequestBody ApiUserPostBean apiUserPostBean) throws Exception{
    	// 设置接口结果页的信息（返回Url）
    	this.initCheckUtil(apiUserPostBean);
    	// 返回对象
    	JSONObject jsonObj = new JSONObject();
    	
    	// 验证
    	this.checkPostBeanOfWeb(apiUserPostBean);
		// 验签
    	this.checkSign(apiUserPostBean);
		// 解密
		int bindUniqueId = this.decrypt(apiUserPostBean);
		logger.info("bindUniqueId is :{}", bindUniqueId);
		//用户Id
		Integer userId = null;
		//用户名
		String userName = null;
		
		
        // 用户接受协议验证
			CheckUtil.check(!apiUserPostBean.getReadAgreement(),MsgEnum.ERR_BIND);
		// 用户手机号码验证
			CheckUtil.check(!StringUtils.isNotBlank(apiUserPostBean.getLoginUserName()),MsgEnum.ERR_OBJECT_REQUIRED, "手机号码");

		
    	
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
	        Integer userid = loginService.getUserIdByBind(bindUniqueId, apiUserPostBean.getPid());
	        logger.info("userid is:{}", userid);
	        if(userid != null){
                // 纳觅财富
                if (NMCF_PID.equals(apiUserPostBean.getPid())) {
                    JSONObject jsonResult = new JSONObject();
                    jsonResult.put("status", true);
                    jsonResult.put("statusCode", "0");
                    jsonResult.put("statusDesc", "该用户已绑定汇盈金服，不能重复绑定");

                    jsonResult.put("bindUniqueIdScy", apiUserPostBean.getBindUniqueIdScy());
                    jsonResult.put("hyjfUserName",userName );
                    jsonResult.put("userId",users.getUserId() );
                    Long timestamp = System.currentTimeMillis();
                    jsonResult.put("timestamp",timestamp);
                    jsonResult.put("chkValue",ApiSignUtil.encryptByRSA(apiUserPostBean.getPid()+timestamp+""));
					String str = jsonResult.toJSONString();
					jsonResult.put("retUrl", apiUserPostBean.getRetUrl() + "?datajson=" + URLEncoder.encode(str, "UTF-8"));
                    return jsonResult;
                }
	        	jsonObj = new JSONObject();
	            jsonObj.put("status", false);
	            jsonObj.put("error", "重复绑定");
	            return jsonObj;
	        }
	        
	        // 汇盈金服账号已绑定其他用户验证
	        String binduniqueid = loginService.getBindUniqueIdByUserId(userId, apiUserPostBean.getPid());
	        if(binduniqueid != null){
	        	jsonObj = new JSONObject();
	            jsonObj.put("status", false);
	            jsonObj.put("error", "该用户未绑定");
	            return jsonObj;
	        }
		}
		 LoginRequestVO user=new LoginRequestVO();
		 user.setUsername(apiUserPostBean.getLoginUserName());
		 user.setPassword(apiUserPostBean.getLoginPassword());
		// 登陆
		 WebResult<WebViewUserVO> login = loginController.login( user,request);
        if (!"000".equals(login.getStatus())) {
            // 登陆失败，返回失败信息
            return jsonObj;
        }

        // 授权
        Boolean result = loginService.bindThirdUser(userId, bindUniqueId, apiUserPostBean.getPid());
        if(!result){
        	jsonObj = new JSONObject();
            jsonObj.put("status", false);
            jsonObj.put("error", "授权失败，请联系汇盈金服客服。");
            return jsonObj;
        }
        
        // 返回第三方页面
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("status", true);
        jsonResult.put("statusCode", "0");
        jsonResult.put("statusDesc", "授权成功"); 
        jsonResult.put("retUrl", apiUserPostBean.getRetUrl());
        jsonResult.put("bindUniqueIdScy", apiUserPostBean.getBindUniqueIdScy());
        jsonResult.put("hyjfUserName",userName ); 
        jsonResult.put("userId",users.getUserId() ); 
        Long timestamp = System.currentTimeMillis();
        jsonResult.put("timestamp",timestamp); 
        jsonResult.put("chkValue",ApiSignUtil.encryptByRSA(apiUserPostBean.getPid()+timestamp+"")); 
        logger.info("chkValue:"+ApiSignUtil.encryptByRSA(apiUserPostBean.getPid()+timestamp+""));
		
		return jsonResult;
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
}
