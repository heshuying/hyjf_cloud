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
import com.hyjf.cs.user.bean.BaseMapBean;
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
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusController, v0.1 2018/6/11 14:20
 */
@Api(value = "app端-用户授权自动投资自动债转接口",tags = "app端-用户授权自动投资自动债转接口")
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
    public AutoPlusResultBean getUserAuthCreditUrl(@RequestHeader(value = "userId") Integer userId, @RequestParam String srvAuthCode,
                                                   @RequestParam String code) {
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
        autoPlusService.appAuthInvesCheck(srvAuthCode,code,checkResult,userId);
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
            e.printStackTrace();
            throw new CheckException(BaseResultBeanFrontEnd.FAIL,"调用银行接口失败！");
        }
        result.setStatus("000");
        return result;
    }

    /**
     * 用户授权自动债转同步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动债转同步回调")
    @GetMapping(value = "/userAuthCreditReturn")
    public ModelAndView userAuthCreditReturn(@ModelAttribute BankCallBean bean) {
        String errorPath = systemConfig.getAppServerHost()+ CommonConstant.JUMP_HTML_ERROR_PATH;
        String successPath = systemConfig.getAppServerHost()+"/user/setting/authorization/result/success";
        ModelAndView modelAndView = new ModelAndView();
        bean.convert();
        // 返回失败
        if (bean.getRetCode()!=null&&!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            modelAndView = new ModelAndView("/jumpHTML");
            BaseMapBean baseMapBean=new BaseMapBean();
            baseMapBean.set("userAutoType","1");
            baseMapBean.set("message","用户授权自动债转失败,失败原因：" + autoPlusService.getBankRetMsg(bean.getRetCode()));
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.FAIL);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "请联系客服");
            baseMapBean.setCallBackAction(errorPath);
            modelAndView.addObject("callBackForm", baseMapBean);
            return modelAndView;
        }
        Integer userId = Integer.parseInt(bean.getLogUserId());
        HjhUserAuthVO hjhUserAuth = this.autoPlusService.getHjhUserAuth(userId);
        // 判断用户授权自动投资是否已成功
        if (hjhUserAuth!=null&&hjhUserAuth.getAutoCreditStatus()==1) {
            modelAndView = new ModelAndView("/jumpHTML");
            BaseMapBean baseMapBean=new BaseMapBean();
            baseMapBean.set("userAutoType","1");
            baseMapBean.set("autoInvesStatus",hjhUserAuth.getAutoInvesStatus()+"");
            baseMapBean.set("autoCreditStatus",hjhUserAuth.getAutoCreditStatus()+"");
            baseMapBean.set("message","用户授权自动债转成功");
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, BaseResultBeanFrontEnd.SUCCESS_MSG);
            baseMapBean.setCallBackAction(successPath);
            modelAndView.addObject("callBackForm", baseMapBean);
            return modelAndView;
        }
        //投资人签约状态查询
        BankCallBean retBean=autoPlusService.getUserAuthQUery(userId,BankCallConstant.QUERY_TYPE_2);
        try {
            if (retBean != null && "1".equals(retBean.getState())) {
                modelAndView = new ModelAndView("/jumpHTML");
                BaseMapBean baseMapBean=new BaseMapBean();
                baseMapBean.set("userAutoType","1");
                Integer autoInvesStatus = 0;
                if(hjhUserAuth!=null){
                    autoInvesStatus = hjhUserAuth.getAutoInvesStatus();
                }
                baseMapBean.set("autoInvesStatus",autoInvesStatus +"");
                baseMapBean.set("autoCreditStatus",1+"");
                baseMapBean.set("message","用户授权自动债转成功");
                baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
                baseMapBean.set(CustomConstants.APP_STATUS_DESC, BaseResultBeanFrontEnd.SUCCESS_MSG);
                baseMapBean.setCallBackAction(successPath);
                modelAndView.addObject("callBackForm", baseMapBean);
                return modelAndView;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView = new ModelAndView("/jumpHTML");
        BaseMapBean baseMapBean=new BaseMapBean();
        baseMapBean.set("message","");
        baseMapBean.set("userAutoType","1");
        baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.FAIL);
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, "请联系客服");
        baseMapBean.setCallBackAction(errorPath);
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }

    /**
     * 用户授权自动债转异步回调
     *
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动债转异步回调", notes = "用户授权自动债转异步回调")
    @PostMapping("/userAuthCreditBgreturn")
    public String userCreditAuthInvesBgreturn(BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean, BankCallConstant.QUERY_TYPE_2);
        return result;
    }

    /**
     * 用户授权自动投资
     *
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "用户授权自动投资", notes = "用户授权自动投资")
    @PostMapping(value = "/userAuthInves")
    public AppResult userAuthInves(@RequestHeader(value = "userId") Integer userId,@RequestHeader(value = "token") String token,@RequestHeader(value = "sign") String sign,HttpServletRequest request) {
        AppResult<Object> result = new AppResult<>();
        result.setStatusInfo(BaseResult.SUCCESS,BaseResult.SUCCESS_DESC);
        String srvAuthCode = request.getParameter("srvAuthCode");
        String code = request.getParameter("code");
        JSONObject checkResult = checkParam(request);
        autoPlusService.appAuthInvesCheck(srvAuthCode,code,checkResult,userId);
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
     * 用户授权自动投资同步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动投资同步回调",notes = "用户授权自动投资同步回调")
    @GetMapping(value = "/userAuthInvesReturn")
    public ModelAndView userAuthInvesReturn(@RequestBody BankCallBean bean) {
        String errorPath = systemConfig.getAppServerHost()+ CommonConstant.JUMP_HTML_ERROR_PATH;
        ModelAndView modelAndView = new ModelAndView();
        bean.convert();
        // 返回失败
        if (bean.getRetCode()!=null&&!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            modelAndView = new ModelAndView("/jumpHTML");
            BaseMapBean baseMapBean=new BaseMapBean();
            baseMapBean.set("userAutoType","0");
            baseMapBean.set(CustomConstants.APP_STATUS_DESC,"用户授权自动投资失败,失败原因：" + autoPlusService.getBankRetMsg(bean.getRetCode()));
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.FAIL);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "请联系客服");
            baseMapBean.setCallBackAction(errorPath);
            modelAndView.addObject("callBackForm", baseMapBean);
            return modelAndView;
        }
        String logUserId = bean.getLogUserId();
        if(StringUtils.isNotBlank(logUserId)){
            Integer userId = Integer.parseInt(logUserId);
            HjhUserAuthVO hjhUserAuth = this.autoPlusService.getHjhUserAuth(userId);
            // 判断用户授权自动投资是否已成功
            if (hjhUserAuth!=null&&hjhUserAuth.getAutoInvesStatus()==1) {
                modelAndView = new ModelAndView("/jumpHTML");
                BaseMapBean baseMapBean=new BaseMapBean();
                baseMapBean.set("message","用户授权自动投资成功");
                baseMapBean.set("userAutoType","0");
                baseMapBean.set("autoInvesStatus",hjhUserAuth.getAutoInvesStatus()+"");
                baseMapBean.set("autoCreditStatus",hjhUserAuth.getAutoCreditStatus()+"");
                baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
                baseMapBean.set(CustomConstants.APP_STATUS_DESC, BaseResultBeanFrontEnd.SUCCESS_MSG);
                baseMapBean.setCallBackAction(systemConfig.getAppServerHost()+"/user/setting/authorization/result/success");
                modelAndView.addObject("callBackForm", baseMapBean);
                return modelAndView;
            }
            //投资人签约状态查询
            BankCallBean retBean = autoPlusService.getUserAuthQUery(userId, BankCallConstant.QUERY_TYPE_1);
            try {
                if (retBean != null && "1".equals(retBean.getState())) {
                    modelAndView = new ModelAndView("/jumpHTML");
                    Integer autoCreditStatus = 0;
                    if (hjhUserAuth != null){
                        logger.info("hjhUserAuth为空");
                        autoCreditStatus = 0;
                    }
                    BaseMapBean baseMapBean = new BaseMapBean();
                    baseMapBean.set("autoInvesStatus", 1 + "");
                    baseMapBean.set("autoCreditStatus", autoCreditStatus + "");
                    baseMapBean.set("message", "用户授权自动投资成功");
                    baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
                    baseMapBean.set(CustomConstants.APP_STATUS_DESC, BaseResultBeanFrontEnd.SUCCESS_MSG);
                    baseMapBean.setCallBackAction(errorPath);
                    modelAndView.addObject("callBackForm", baseMapBean);
                    return modelAndView;
                }
            } catch (Exception e) {
                logger.error("更新签约状态和日志表异常...", e);
            }
            modelAndView = new ModelAndView("/jumpHTML");
            BaseMapBean baseMapBean=new BaseMapBean();
            baseMapBean.set(CustomConstants.APP_STATUS_DESC,"");
            baseMapBean.set("userAutoType","0");
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.FAIL);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "请联系客服");
            baseMapBean.setCallBackAction(errorPath);
            modelAndView.addObject("callBackForm", baseMapBean);
        }
        return modelAndView;
    }


    /**
     * 用户授权自动投资异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动投资异步回调", notes = "用户授权自动投资异步回调")
    @ResponseBody
    @PostMapping(value = "/userAuthInvesBgreturn")
    public String userAuthInvesBgreturn(BankCallBean bean) {

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
    public AppResult<Object> searchFiledMess(@RequestBody Map<String,String> param) {
        logger.info("调用银行失败原因start,logOrdId:{}", param);
        AppResult<Object> result = new AppResult<Object>();
        String retMsg = autoPlusService.getFailedMess(param.get("logOrdId"));
        Map<String,String> map = new HashedMap();
        map.put("error",retMsg);
        result.setData(map);
        return result;
    }

}
