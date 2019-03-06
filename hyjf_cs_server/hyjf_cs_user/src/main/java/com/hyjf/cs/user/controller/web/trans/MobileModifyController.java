/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.trans;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.result.MobileModifyResultBean;
import com.hyjf.cs.user.service.trans.MobileModifyService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version MobileModifyController, v0.1 2018/6/14 16:46
 */
@Api(value = "web端-修改手机号",tags = "web端-修改手机号")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user")
public class MobileModifyController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(MobileModifyController.class);
    @Autowired
    MobileModifyService mobileModifyService;

    /**
     * 用户手机号码修改(未开户)
     * @auther: hesy
     * @date: 2018/6/20
     */
    @ApiOperation(value = "手机号码修改（未开户）", notes = "手机号码修改（未开户）")
    @ApiImplicitParam(name = "paraMap",value = "{newMobile: string,smsCode: string}", dataType = "Map")
    @PostMapping(value = "/mobileModify", produces = "application/json; charset=utf-8")
    public WebResult<WebViewUserVO> mobileModify(@RequestHeader(value = "userId") int userId,
                                                 @RequestBody Map<String, String> paraMap) {
        logger.info("用户手机号码修改, paraMap :{}",paraMap);
        WebResult<WebViewUserVO> result = new WebResult<WebViewUserVO>();

        WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY+userId, WebViewUserVO.class);
        boolean checkRet = mobileModifyService.checkForMobileModify(paraMap.get("newMobile"), paraMap.get("smsCode"));
        if(checkRet) {
            UserVO userVO = new UserVO();
            userVO.setUserId(user.getUserId());
            userVO.setMobile(paraMap.get("newMobile"));
            mobileModifyService.updateUserByUserId(userVO);

            WebViewUserVO webUser = mobileModifyService.getWebViewUserByUserId(user.getUserId(),BankCallConstant.CHANNEL_PC);
            if (null != webUser) {
                webUser = mobileModifyService.updateUserToCache(webUser);
                result.setData(webUser);
            }
        }



        return result;
    }
    
    /**
     * 用户手机号码修改（已开户）
     * @auther: hesy
     * @date: 2018/6/20
     */
    @ApiOperation(value = "手机号码修改（已开户）", notes = "手机号码修改（已开户）")
    @ApiImplicitParam(name = "paraMap",value = "{newMobile:string,smsCode:string,srvAuthCode:string}", dataType = "Map")
    @PostMapping(value = "/mobileModifyOpened", produces = "application/json; charset=utf-8")
    public WebResult<WebViewUserVO> mobileModifyOpened(@RequestHeader(value = "userId") int userId, @RequestBody Map<String, String> paraMap) {
        logger.info("用户手机号码修改, paraMap :{}",paraMap);
        WebResult<WebViewUserVO> result = new WebResult<WebViewUserVO>();

        WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY+userId, WebViewUserVO.class);
        
        boolean checkRet = mobileModifyService.checkForMobileModifyOpened(paraMap.get("newMobile"), paraMap.get("smsCode"), paraMap.get("srvAuthCode"));
        if(checkRet) {
        	
            BankCallBean bankBean = null;
            try {
				bankBean = mobileModifyService.callMobileModify(user.getUserId(), paraMap.get("newMobile"), paraMap.get("smsCode"), paraMap.get("srvAuthCode"));
			} catch (Exception e) {
                result.setStatus(WebResult.ERROR);
                result.setStatusDesc(WebResult.ERROR_DESC);
                logger.error("请求手机号码修改接口失败", e);
            }
            
            if (bankBean == null) {
    			result.setStatus(WebResult.FAIL);
	            result.setStatusDesc("修改手机号失败");
	            logger.error("请求手机号码修改接口失败");
	            return result;
    		}
            
    		if (!BankCallConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode())) {
    			result.setStatus(WebResult.FAIL);
	            result.setStatusDesc("请求手机号码修改接口失败: " + bankBean.getRetCode());
	            logger.error("请求手机号码修改接口失败: " + bankBean.getRetCode());
	            return result;
    		}
    		
    		 UserVO userVO = new UserVO();
             userVO.setUserId(user.getUserId());
             userVO.setMobile(paraMap.get("newMobile"));
             mobileModifyService.updateUserByUserId(userVO);

            WebViewUserVO webUser = mobileModifyService.getWebViewUserByUserId(user.getUserId(),BankCallConstant.CHANNEL_PC);
            if (null != webUser) {
                webUser = mobileModifyService.updateUserToCache(webUser);
                result.setData(webUser);
            }
        }

        return result;
    }

    /**
     * 用户手机号修改基础信息获取
     * @auther: hesy
     * @date: 2018/6/20
     */
    @ApiOperation(value = "用户手机号修改基础信息获取", notes = "用户手机号修改基础信息获取")
    @PostMapping("/mobileModifyInit")
    public MobileModifyResultBean mobileModifyInit(@RequestHeader(value = "userId") int userId) {
        WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY+userId, WebViewUserVO.class);
        MobileModifyResultBean resultBean = mobileModifyService.queryForMobileModify(user.getUserId());

        return resultBean;
    }

    /**
     * @author: zhangqingqing
     * @desc :判断是否开户
     * @param: * @param token
     * @date: 9:36 2018/6/15
     */
    @ApiOperation(value = "判断是否开户", notes = "判断是否开户")
    @PostMapping(value = "/checkOpenAccount", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> initMobile(@RequestHeader(value = "userId") Integer userId) {
        logger.info("web端获取开户未开户接口");
        WebResult<Map<String,Object>> result = new WebResult<Map<String,Object>>();
        Map<String,Object> resultMap = new HashMap<>();
        UserVO user = mobileModifyService.getUsersById(userId);
        Integer accountFlag = user.getBankOpenAccount();
        resultMap.put("bankOpenAccount", accountFlag);
        String mobile = user.getMobile();
        String hideMobile = "";
        if (StringUtils.isNotBlank(mobile)) {
            hideMobile = mobile.substring(0,mobile.length()-(mobile.substring(3)).length())+"****"+mobile.substring(7);
        }
        resultMap.put("mobile", mobile);
        resultMap.put("hideMobile", hideMobile);
        result.setData(resultMap);
        return result;
    }

    /**
     * 用户修改手机号发送短信验证码
     * @auther: hesy
     * @date: 2018/6/20
     */
    @ApiOperation(value = "用户修改手机号发送短信验证码", notes = "用户修改手机号发送短信验证码")
    @ApiImplicitParam(name = "param",value = "{mobile: string}", dataType = "Map")
    @PostMapping(value = "/mobileModifySendCode", produces = "application/json; charset=utf-8")
    public WebResult<Object> mobileModifySendCode(@RequestHeader(value = "userId") int userId, @RequestBody Map<String,String> param) {
        logger.info("Web端用户修改手机号发送短信验证码, mobile :{}，cardNo:{}", param);
        WebResult<Object> result = new WebResult<Object>();

        WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY+userId, WebViewUserVO.class);
        CheckUtil.check(null!=param && StringUtils.isNotBlank(param.get("mobile")), MsgEnum.ERR_OBJECT_BLANK,"手机号");//手机号未填写
        CheckUtil.check(mobileModifyService.checkIsOpen(user.getUserId()),MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        // 请求银行绑卡接口
        BankCallBean bankBean = null;
        try {
            bankBean = mobileModifyService.callSendCode(user.getUserId(),param.get("mobile"), BankCallMethodConstant.TXCODE_MOBILE_MODIFY_PLUS, ClientConstants.CHANNEL_PC,null);
        } catch (Exception e) {
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
            logger.error("请求验证码接口发生异常", e);
        }
        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
            logger.error("请求验证码接口失败");
        }else {
            result.setData(bankBean.getSrvAuthCode());
        }
        return result;
    }

}
