/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.autoplus;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.user.bean.AutoPlusResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusController, v0.1 2018/6/11 14:20
 */
@Api(value = "app端-用户授权自动出借自动债转接口",tags = "app端-用户授权自动出借自动债转接口")
@RestController
@RequestMapping("/hyjf-app/bank/user/autoplus")
public class APPAutoPlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(APPAutoPlusController.class);
    @Autowired
    AutoPlusService autoPlusService;

    @Autowired
    SystemConfig systemConfig;

    @ApiOperation(value = "授权发送短信验证码", notes = "授权发送短信验证码")
    @PostMapping(value = "/sendcode")
    public AutoPlusResultBean autoPlusSendCode(@RequestHeader(value = "userId") Integer userId, @RequestParam String userAutoType,
                                               @RequestParam(value = "mobile", required = false) String mobile) {
        logger.info("app端授权发送短信验证码, mobile :{}", mobile);
        String returnRequest = "/bank/user/autoplus/sendcode";
        AutoPlusResultBean result = new AutoPlusResultBean(returnRequest);
        CheckUtil.check(userId != null, MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = autoPlusService.getUsersById(userId);
        String srvTxCode = autoPlusService.checkSmsParam(user, userAutoType);
        // 银行接口调用需要的业务交易代码
        if (StringUtils.isBlank(mobile)) {
            mobile = user.getMobile();
        }
        // 请求银行接口
        BankCallBean bankBean = null;
        try {
            bankBean = autoPlusService.callSendCode(userId, user.getMobile(), srvTxCode, ClientConstants.CHANNEL_APP, null);
        } catch (Exception e) {
            logger.error("请求验证码接口发生异常", e);
            throw new CheckException(MsgEnum.ERR_BANK_CALL);
        }
        if (bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
            logger.error("请求验证码接口发生异常");
            throw new CheckException(MsgEnum.ERROR_SMS_SEND);
        }
        if (StringUtils.isBlank(bankBean.getSrvAuthCode())) {
            logger.error("短信验证码发送失败！");
            throw new CheckException(MsgEnum.ERROR_SMS_SEND);
        }
        // 业务授权码
        String srvAuthCode = bankBean.getSrvAuthCode();
        result.setSrvAuthCode(srvAuthCode);
        result.setStatus(CustomConstants.APP_STATUS_SUCCESS);
        result.setStatusDesc(CustomConstants.APP_STATUS_DESC_SUCCESS);
        return result;
    }

    /**
     * 自动投标授权url获取
     *
     * @return
     */
    @ApiOperation(value = "自动投标授权url获取", notes = "自动投标授权url获取")
    @PostMapping(value = "/getUserAuthInvesUrl")
    @ResponseBody
    public AutoPlusResultBean getUserAuthInvesUrl(@RequestHeader(value = "userId") Integer userId, @RequestParam String srvAuthCode, @RequestParam String code) {
        String returnRequest = "/bank/user/autoplus/getUserAuthInvesUrl";
        AutoPlusResultBean result = new AutoPlusResultBean(returnRequest);
        if (StringUtils.isBlank(code) || StringUtils.isBlank(srvAuthCode)) {
            result.setStatusDesc("验证码或前导业务码不能为空");
            return result;
        }
        HjhUserAuthVO userAuth = autoPlusService.getHjhUserAuth(userId);
        if (userAuth != null && userAuth.getAutoInvesStatus() == 1) {
            throw new CheckException(MsgEnum.ERR_AUTHORIZE_REPEAT);
        }
        String url = systemConfig.getAppServerHost()+"/public/formsubmit?requestType=" + CommonConstant.APP_BANK_REQUEST_TYPE_AUTHINVES+"&code=" + code + "&srvAuthCode=" + srvAuthCode;
        result.setAuthUrl(url);
        result.setStatus(CustomConstants.APP_STATUS_SUCCESS);
        result.setStatusDesc(CustomConstants.APP_STATUS_DESC_SUCCESS);
        return result;
    }

    /**
     * 自动债转授权url获取
     *
     * @return
     */
    @ApiOperation(value = "自动债转授权url获取", notes = "自动债转授权url获取")
    @PostMapping("/getUserAuthCreditUrl")
    @ResponseBody
    public AutoPlusResultBean getUserAuthCreditUrl(@RequestHeader(value = "userId")Integer userId, @RequestParam  @ApiParam("前导业务码") String srvAuthCode,
                                                   @RequestParam @ApiParam("验证码") String code) {
        String returnRequest = "/bank/user/autoplus/getUserAuthCreditUrl";
        AutoPlusResultBean result = new AutoPlusResultBean(returnRequest);
        if (StringUtils.isBlank(code) || StringUtils.isBlank(srvAuthCode)) {
            result.setStatusDesc("验证码或前导业务码不能为空");
            return result;
        }
        HjhUserAuthVO userAuth = autoPlusService.getHjhUserAuth(userId);
        if (userAuth != null && userAuth.getAutoCreditStatus() == 1) {
            result.setUserAutoStatus(1);
            result.setStatusDesc("自动投标已授权");
            return result;
        }
        String url = systemConfig.getAppServerHost()+"/public/formsubmit?requestType=" + CommonConstant.APP_BANK_REQUEST_TYPE_AUTHCREDIT+"&code=" + code
                + "&srvAuthCode=" + srvAuthCode;
        result.setAuthUrl(url);
        result.setStatus(CustomConstants.APP_STATUS_SUCCESS);
        result.setStatusDesc(CustomConstants.APP_STATUS_DESC_SUCCESS);
        return result;
    }

    /**
     * 用户授权自动债转
     * @param userId
     * @param token
     * @param sign
     * @param request
     * @return
     */
    @ApiOperation(value = "用户授权自动债转")
    @PostMapping(value = "/userAuthCredit")
    public AppResult userAuthCredit(@RequestHeader(value = "userId") Integer userId, @RequestHeader(value = "token") String token, @RequestHeader(value = "sign") String sign, HttpServletRequest request) {
        AppResult<Object> result = new AppResult<>();
        result.setStatusInfo(BaseResult.SUCCESS,BaseResult.SUCCESS_DESC);
        String srvAuthCode = request.getParameter("srvAuthCode");
        String code = request.getParameter("code");
        JSONObject checkResult = checkParam(request);
        autoPlusService.appAuthInvesCheck(srvAuthCode,code,checkResult,userId,BankCallConstant.QUERY_TYPE_2);

        UserVO user = autoPlusService.getUsersById(userId);
        // 组装发往江西银行参数
        BankCallBean bean = autoPlusService.appGetCommonBankCallBean(user,2,srvAuthCode,code,sign,token);
        // 插入日志
        this.autoPlusService.insertUserAuthLog(user, bean,2,BankCallConstant.QUERY_TYPE_2);
        // 跳转到江西银行画面
        try {
            Map<String, Object> map = BankCallUtils.callApiMap(bean);
            result.setData(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CheckException(BaseResultBeanFrontEnd.FAIL,"调用银行接口失败！");
        }
        result.setStatus("000");
        return result;
    }

    /**
     * 用户授权自动债转异步回调
     *
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动债转异步回调", notes = "用户授权自动债转异步回调")
    @PostMapping("/userAuthCreditBgreturn")
    public String userCreditAuthInvesBgreturn(@RequestBody BankCallBean bean) {
        logger.info("授权异步回调");
        String result = autoPlusService.userBgreturn(bean, BankCallConstant.QUERY_TYPE_2);
        return result;
    }

    /**
     * 用户授权自动出借
     *
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "用户授权自动出借", notes = "用户授权自动出借")
    @PostMapping(value = "/userAuthInves")
    public AppResult userAuthInves(@RequestHeader(value = "userId") Integer userId,@RequestHeader(value = "token") String token,@RequestHeader(value = "sign") String sign,HttpServletRequest request) {
        AppResult<Object> result = new AppResult<>();
        result.setStatusInfo(BaseResult.SUCCESS,BaseResult.SUCCESS_DESC);
        String srvAuthCode = request.getParameter("srvAuthCode");
        String code = request.getParameter("code");
        JSONObject checkResult = checkParam(request);
        autoPlusService.appAuthInvesCheck(srvAuthCode,code,checkResult,userId, BankCallConstant.QUERY_TYPE_1);
        UserVO user = autoPlusService.getUsersById(userId);
        // 组装发往江西银行参数
        BankCallBean bean = autoPlusService.appGetCommonBankCallBean(user, 1, srvAuthCode, code, sign, token);
        // 插入日志
        this.autoPlusService.insertUserAuthLog(user, bean, 2, BankCallConstant.QUERY_TYPE_1);
        // 跳转到汇付天下画面
        try {
            Map<String, Object> map = BankCallUtils.callApiMap(bean);
            result.setData(map);
        } catch (Exception e) {
            throw new CheckException(BaseResultBeanFrontEnd.FAIL,"调用银行接口失败！");
        }
        result.setStatus("000");
        return result;
    }

    /**
     * 用户授权自动出借异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动出借异步回调", notes = "用户授权自动出借异步回调")
    @PostMapping(value = "/userAuthInvesBgreturn")
    public String userAuthInvesBgreturn(@RequestBody BankCallBean bean) {
        logger.info("授权异步回调");
        String result = autoPlusService.userBgreturn(bean,BankCallConstant.QUERY_TYPE_1);
        return result;
    }

    /**
     * 检查参数的正确性
     * @param request
     * @return
     */
    private JSONObject checkParam(HttpServletRequest request) {
        // 平台
        String platform = request.getParameter("platform");
        // token
        String token = request.getParameter("token");
        // 唯一标识
        String sign = request.getParameter("sign");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");

        // 检查参数正确性  || Validator.isNull(netStatus)
        if (Validator.isNull(platform) || Validator.isNull(token) || Validator.isNull(sign) || Validator.isNull(randomString)
                || Validator.isNull(order)) {
            return jsonMessage("请求参数非法", "1");
        }
        // 取得加密用的Key
        String key = SecretUtil.getKey(sign);
        if (Validator.isNull(key)) {
            return jsonMessage("请求参数非法", "1");
        }
        Integer userId = SecretUtil.getUserId(sign);
        if (userId == null) {
            return jsonMessage("用户信息不存在", "1");
        }
        return null;
    }

    /**
     * @Description 调用银行失败原因
     * @Author
     */
    @ApiOperation(value = "调用银行失败原因", notes = "查询调用银行失败原因")
    @PostMapping("/searchFiledMess")
    @ApiImplicitParam(name = "param",value = "{logOrdId:String}",dataType = "Map")
    @ResponseBody
    public AppResult<Object> searchFiledMess(@RequestParam("logOrdId")@ApiParam("订单号") String logOrdId) {
        logger.info("调用银行失败原因start,logOrdId:{}",logOrdId);
        AppResult<Object> result = new AppResult<Object>();
        Map<String,String> map = new HashedMap();
        map.put("isSetPassword","0");
        String retMsg = autoPlusService.getFailedMess(logOrdId);
        if(retMsg.equals("00000000")){
            map.put("isSetPassword","1");
        }else {
            map.put("error",retMsg);
        }
        result.setData(map);
        result.setStatus("000");
        return result;
    }

}
