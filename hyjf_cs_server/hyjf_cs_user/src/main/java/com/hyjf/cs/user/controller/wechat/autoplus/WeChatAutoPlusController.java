/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.autoplus;

import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.bean.AutoPlusResultBean;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.cs.user.util.ResultEnum;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author zhangqingqing
 * @version AutoPlusController, v0.1 2018/6/11 14:37
 */

@Api(tags = "weChat端-用户授权自动投资债转接口")
@Controller
@RequestMapping("/hyjf-wechat/wx/user/autoplus")
public class WeChatAutoPlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatAutoPlusController.class);

    @Autowired
    AutoPlusService autoPlusService;


    /**
     *
     * 发送授权短信验证码
     * 请求地址: /wx/user/autoplus/sendcode.page
     * 需要参数: 授权类型userAutoType(0 自动投标授权 1 自动债转授权) mobile
     * @author sunss
     * @param
     * @param userAutoType
     * @param mobile
     * @return
     */
    @ApiOperation(value = "授权发送短信验证码", notes = "授权发送短信验证码")
    @PostMapping(value = "/sendcode.do")
    @ResponseBody
    public BaseResultBean sendSmsCode(@RequestHeader(value = "userId") Integer userId, @RequestParam String userAutoType, String mobile) {
        logger.info("发送授权短信验证码 接口,手机号为：【" + mobile + "】,授权类型为【" + userAutoType + "】,userid为：【" + userId + "】");
        String returnRequest = "/user/autoplus/sendcode";
        AutoPlusResultBean result = new AutoPlusResultBean(returnRequest);
        CheckUtil.check(userId!=null,MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = autoPlusService.getUsersById(userId);
        if (StringUtils.isBlank(mobile)) {
            mobile = user.getMobile();
        }
        String srvTxCode = autoPlusService.checkSmsParam(user,userAutoType);
        // 请求银行接口
        BankCallBean bankBean = null;
        try {
            bankBean = autoPlusService.callSendCode(userId,mobile,srvTxCode, ClientConstants.CHANNEL_WEI,null);
        } catch (Exception e) {
            logger.error("请求验证码接口发生异常", e);
            throw new CheckException(MsgEnum.ERR_BANK_CALL);
        }
        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
            logger.error("请求验证码接口发生异常");
            throw new CheckException(MsgEnum.ERR_BANK_CALL);
        }else {
            result.setSrvAuthCode(bankBean.getSrvAuthCode());
            result.setStatus("000");
            result.setStatusDesc("短信发送成功！");
        }
        return result;
    }

    /**
     * 用户自动债转授权
     * @param userId
     * @param sign
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "用户自动债转授权", notes = "用户自动债转授权")
    @PostMapping(value = "userAuthCredit.page")
    public ModelAndView userAuthCredit(@RequestHeader(value = "userId") Integer userId,@RequestHeader(value = "sign") String sign,HttpServletRequest request, HttpServletResponse response){
        String srvAuthCode = request.getParameter("srvAuthCode");
        String code = request.getParameter("code");
        // 判断是否授权过
        HjhUserAuthVO hjhUserAuth = autoPlusService.getHjhUserAuth(userId);
        if (StringUtils.isBlank(code) || StringUtils.isBlank(srvAuthCode)) {
            return getErrorModelAndView(ResultEnum.PARAM, sign, "1", hjhUserAuth);
        }
        UserVO users = autoPlusService.getUsersById(userId);
        if (users.getBankOpenAccount() == 0) {
            // 未开户
            return getErrorModelAndView(ResultEnum.USER_ERROR_200, sign, "1", hjhUserAuth);
        }
        // 判断用户是否设置过交易密码
        if (users.getIsSetPassword() == 0) {
            // 未设置交易密码
            return getErrorModelAndView(ResultEnum.USER_ERROR_201, sign, "1", hjhUserAuth);
        }
        if (hjhUserAuth != null && hjhUserAuth.getAutoCreditStatus().intValue()==1) {
            ModelAndView mv = getErrorModelAndView(ResultEnum.USER_ERROR_203, sign,"1", hjhUserAuth);
            if (hjhUserAuth.getAutoCreditStatus() == 1) {
                return mv;
            }
        }
        // 组装发往江西银行参数
        BankCallBean bean = autoPlusService.weChatGetCommonBankCallBean(users, 2, srvAuthCode, code, sign);
        // 插入日志
        this.autoPlusService.insertUserAuthLog(users, bean, 1, BankCallConstant.QUERY_TYPE_2);
        try {
            ModelAndView modelAndView = BankCallUtils.callApi(bean);
            return modelAndView;
        } catch (Exception e) {
            return getErrorModelAndView(ResultEnum.USER_ERROR_205, sign, "1", hjhUserAuth);
        }
    }

    /**
     * 用户授权自动债转同步回调
     * @param userId
     * @param sign
     * @param bean
     * @param request
     * @return
     */
    @ApiOperation(value = "用户授权自动债转同步回调")
    @PostMapping(value = "/userAuthCreditReturn")
    public ModelAndView userAuthCreditReturn(@RequestHeader(value = "userId") Integer userId,@RequestHeader(value = "sign") String sign,@ModelAttribute BankCallBean bean, HttpServletRequest request) {
        bean.convert();
        HjhUserAuthVO hjhUserAuth = autoPlusService.getHjhUserAuth(userId);
        // 返回失败
        if (bean.getRetCode() != null && !BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            return getErrorModelAndView(ResultEnum.USER_ERROR_204, sign,"1", hjhUserAuth);
        }
        // 投资人签约状态查询
        BankCallBean retBean = autoPlusService.getUserAuthQUery(userId, BankCallConstant.QUERY_TYPE_2);
        if(hjhUserAuth==null){
            hjhUserAuth = new HjhUserAuthVO();
        }
        hjhUserAuth.setAutoCreditStatus(1);
        if ("1".equals(retBean.getState())) {
            return getSuccessModelAndView(sign, "1", hjhUserAuth);
        }
        return getErrorModelAndView(ResultEnum.USER_ERROR_205, sign, "1", hjhUserAuth);
    }

    /**
     * 用户授权自动债转异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动债转异步回调", notes = "用户授权自动债转异步回调")
    @PostMapping(value = "/userAuthCreditBgreturn")
    public String userCreditAuthInvesBgreturn(BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean,BankCallConstant.QUERY_TYPE_2);
        return result;
    }

    /**
     * 自动投资授权接口
     * @param userId
     * @param sign
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "自动投资授权接口")
    @PostMapping(value = "/userAuthInves.page")
    public ModelAndView userAuthInves(@RequestHeader(value = "userId") Integer userId,@RequestHeader(value = "sign") String sign,HttpServletRequest request, HttpServletResponse response) {
        String srvAuthCode = request.getParameter("srvAuthCode");
        String code = request.getParameter("code");
        logger.info("自动投资授权接口,srvAuthCode为：【" + srvAuthCode + "】,code为【" + code + "】,userid为：【" + userId + "】");
        // 判断是否授权过
        HjhUserAuthVO hjhUserAuth = autoPlusService.getHjhUserAuth(userId);
        if (StringUtils.isBlank(code) || StringUtils.isBlank(srvAuthCode)) {
            return getErrorModelAndView(ResultEnum.PARAM, sign, "0",hjhUserAuth);
        }
        UserVO users = autoPlusService.getUsersById(userId);
        if (users.getBankOpenAccount() == 0) {
            // 未开户
            return getErrorModelAndView(ResultEnum.USER_ERROR_200, sign,"0", hjhUserAuth);
        }

        // 判断用户是否设置过交易密码
        if (users.getIsSetPassword() == 0) {
            // 未设置交易密码
            return getErrorModelAndView(ResultEnum.USER_ERROR_201, sign,"0", hjhUserAuth);
        }

        // 判断是否授权过
        if (hjhUserAuth != null && hjhUserAuth.getAutoInvesStatus().intValue()==1) {
            ModelAndView mv = getErrorModelAndView(ResultEnum.USER_ERROR_201, sign,"0", hjhUserAuth);
            if (hjhUserAuth.getAutoInvesStatus() == 1) {
                return mv;
            }
        }
        // 组装发往江西银行参数
        BankCallBean bean = autoPlusService.weChatGetCommonBankCallBean(users, 1, srvAuthCode, code, sign);
        // 插入日志
        this.autoPlusService.insertUserAuthLog(users, bean, 1, BankCallConstant.QUERY_TYPE_1);
        try {
            ModelAndView modelAndView = BankCallUtils.callApi(bean);
            return modelAndView;
        } catch (Exception e) {
            return getErrorModelAndView(ResultEnum.USER_ERROR_204, sign,"0", hjhUserAuth);
        }
    }

    /**
     * 用户授权自动投资同步回调
     * @param bean
     * @param userId
     * @param sign
     * @param request
     * @return
     */
    @ApiOperation(value = "用户授权自动投资同步回调")
    @PostMapping(value = "/userAuthInvesReturn")
    public ModelAndView userAuthInvesReturn(@ModelAttribute BankCallBean bean,@RequestHeader(value = "userId") Integer userId,@RequestHeader(value = "sign") String sign, HttpServletRequest request) {
        bean.convert();
        HjhUserAuthVO hjhUserAuth = autoPlusService.getHjhUserAuth(userId);
        // 返回失败
        if (bean.getRetCode() != null && !BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            return getErrorModelAndView(ResultEnum.USER_ERROR_204, sign, "0", hjhUserAuth);
        }
        // 投资人签约状态查询
        BankCallBean retBean = autoPlusService.getUserAuthQUery(userId, BankCallConstant.QUERY_TYPE_1);

        if ("1".equals(retBean.getState())) {
            if(hjhUserAuth==null){
                hjhUserAuth = new HjhUserAuthVO();
            }
            hjhUserAuth.setAutoInvesStatus(1);
            return getSuccessModelAndView(sign, "0", hjhUserAuth);
        }
        return getErrorModelAndView(ResultEnum.USER_ERROR_204, sign, "0", hjhUserAuth);
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动投资异步回调
     * @Param: * @param bean
     * @Date: 16:37 2018/5/30
     * @Return: String
     */
    @ApiOperation(value = "用户授权自动投资异步回调", notes = "用户授权自动投资异步回调")
    @ResponseBody
    @PostMapping(value = "/userAuthInvesBgreturn", produces = "application/json; charset=utf-8")
    public String userAuthInvesBgreturn(@RequestBody @Valid BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean,BankCallConstant.QUERY_TYPE_1);
        return result;
    }


    /**
     * 组装跳转错误页面MV
     * @param param
     * @param sign
     * @param type
     * @param hjhUserAuth
     * @return
     */
    private ModelAndView getErrorModelAndView(ResultEnum param, String sign, String type, HjhUserAuthVO hjhUserAuth) {
        ModelAndView modelAndView = new ModelAndView("/jumpHTML");
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, param.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, param.getStatusDesc());
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.set("autoInvesStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoInvesStatus()==null?"0":hjhUserAuth.getAutoInvesStatus()+ "");
        baseMapBean.set("autoCreditStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoCreditStatus()==null?"0":hjhUserAuth.getAutoCreditStatus() + "");
        baseMapBean.set("userAutoType", type);
        baseMapBean.setCallBackAction(CustomConstants.HOST + CommonConstant.JUMP_HTML_ERROR_PATH);
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }

    /**
     * 组装跳转成功页面MV
     * @param sign
     * @param type
     * @param hjhUserAuth
     * @return
     */
    private ModelAndView getSuccessModelAndView(String sign, String type, HjhUserAuthVO hjhUserAuth) {
        ModelAndView modelAndView = new ModelAndView("/jumpHTML");
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.SUCCESS.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, ResultEnum.SUCCESS.getStatusDesc());
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.set("autoInvesStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoInvesStatus()==null?"0":hjhUserAuth.getAutoInvesStatus()+ "");
        baseMapBean.set("autoCreditStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoCreditStatus()==null?"0":hjhUserAuth.getAutoCreditStatus() + "");
        baseMapBean.set("userAutoType", type);

        baseMapBean.setCallBackAction(CustomConstants.HOST + CommonConstant.JUMP_HTML_SUCCESS_PATH);
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }

}
