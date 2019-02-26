/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.aems.mergeauth;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.common.util.ApiSignUtil;
import com.hyjf.cs.user.bean.AemsMergeAuthPagePlusRequestBean;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.aems.auth.AemsAuthService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * AEMS系统:多合一授权
 *
 * @author liuyang
 * @version AemsMergeAuthPagePlusController, v0.1 2018/12/13 14:22
 */
@Api(value = "AEMS系统:多合一授权", tags = "AEMS系统-用户多合一授权")
@RestController
@RequestMapping("/hyjf-api/aems/mergeauth")
public class AemsMergeAuthPagePlusController extends BaseUserController {

    @Autowired
    SystemConfig systemConfig;
    @Autowired
    private AemsAuthService authService;

    /**
     * 外部服务接口:缴费授权 @RequestMapping
     */
    public static final String REQUEST_MAPPING = "/hyjf-api/aems/mergeauth";
    /**
     * 同步回调
     */
    public static final String RETURL_SYN_ACTION = "/return";
    /**
     * 异步回调
     */
    public static final String RETURL_ASY_ACTION = "/bgReturn";


    @ApiOperation(value = "AEMS系统:多合一授权", notes = "AEMS系统:多合一授权")
    @PostMapping(value = "/mergeAuth", produces = "application/json; charset=utf-8")
    public ModelAndView mergeAuthPage(@RequestBody @Valid AemsMergeAuthPagePlusRequestBean requestBean, HttpServletRequest request) {
        logger.info("AEMS系统请求 页面多合一授权[开始]---------------------------------,请求参数:{}", JSON.toJSONString(requestBean));

        ModelAndView modelAndView;
        // 入参校验
        Map<String, String> paramMap = authService.checkAemsParam(requestBean);

        // 数据校验不通过
        paramMap.put("callBackAction", requestBean.getRetUrl());
        if (!"1".equals(paramMap.get("status"))) {
            return callbackErrorView(paramMap);
        }

        AuthBean authBean = new AuthBean();
        // 根据用户ID生成订单ID
        String orderId = GetOrderIdUtils.getOrderId2(authBean.getUserId());

        // 打包参数
        authBean = paramPackage(request, requestBean, authBean, orderId);
        logger.info("AEMS系统请求 页面多合一授权,authBean:{}", JSON.toJSONString(authBean));
        // 校验用户角色和授权类型是否一致
        paramMap = authService.checkUserRoleAndAuthType(authBean, requestBean.getAuthType());
        if (!"1".equals(paramMap.get("status"))) {
            return callbackErrorView(paramMap);
        }
        logger.info("AEMS系统请求 页面多合一授权,paramMap:{}"+ JSON.toJSONString(paramMap));
        try {
            // 补充数据，请求银行
            modelAndView = authService.getApiCallbankMV(authBean);

            // 判断授权类型，保存至授权日志表
            authService.saveUserAuthLog(authBean, orderId);
        } catch (Exception e) {
            logger.info("AEMS多合一授权页面异常,异常信息:{}", e);
            return null;
        }
        logger.info("AEMS系统请求 页面多合一授权[结束]------------------------------");
        return modelAndView;
    }


    @ApiOperation(value = "AEMS多合一授权[同步回调]", notes = "AEMS多合一授权[同步回调]")
    @RequestMapping(RETURL_SYN_ACTION)
    public ModelAndView returnPage(HttpServletRequest request) {
        logger.info("AEMS多合一授权[同步回调]开始------------------------------------");

        String isSuccess = request.getParameter("isSuccess");
        logger.info("第三方端授权同步请求,isSuccess:{}"+ isSuccess);
        String url = request.getParameter("callback");

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        resultMap.put("callBackAction", url);
        if (isSuccess == null || !"1".equals(isSuccess)) {
            // 失败
            resultMap.put("status", ErrorCodeConstant.STATUS_CE999999);
            resultMap.put("chkValue", ApiSignUtil.encryptByRSA(ErrorCodeConstant.STATUS_CE999999));
            resultMap.put("statusDesc", "多合一授权失败,调用银行接口失败");
            resultMap.put("acqRes", request.getParameter("acqRes"));
            logger.info("AEMS多合一授权[同步回调],调用银行接口失败!");
            logger.info("AEMS多合一授权[同步回调]结束----------------------------------");
            return callbackErrorView(resultMap);
        }
        resultMap.put("status", ErrorCodeConstant.SUCCESS);
        resultMap.put("chkValue", ApiSignUtil.encryptByRSA(ErrorCodeConstant.SUCCESS));
        resultMap.put("statusDesc", "多合一授权成功");
        resultMap.put("acqRes", request.getParameter("acqRes"));
        logger.info("AEMS多合一授权成功,resultMap:{}", JSON.toJSONString(resultMap));
        logger.info("AEMS多合一授权[同步回调]结束----------------------------------");
        return callbackErrorView(resultMap);
    }


    @ApiOperation(value = "AEMS多合一授权[异步回调]", notes = "AEMS多合一授权[异步回调]")
    @RequestMapping(RETURL_ASY_ACTION)
    @ResponseBody
    public BankCallResult bgReturn(HttpServletRequest request, @RequestBody BankCallBean bean) {
        logger.info("AEMS多合一授权[异步回调]开始--------------------------------------------,请求参数:{}", JSON.toJSONString(bean.toString()));

        Map<String, String> params = new HashMap<>();
        BankCallResult result = new BankCallResult();
        BaseResultBean resultBean = new BaseResultBean();

        if (bean == null) {
            logger.warn("调用江西银行多合一授权接口,银行异步返回空");
            // 返回AEMS的参数
            params.put("acqRes", request.getParameter("acqRes"));
            params.put("status", ErrorCodeConstant.STATUS_CE999999);
            params.put("statusDesc", "多合一授权失败,调用银行接口失败");
            params.put("chkValue", ApiSignUtil.encryptByRSA(ErrorCodeConstant.STATUS_CE999999));
            CommonSoaUtils.noRetPostThree(request.getParameter("callback"), params);
            // 返回银行的参数
            result.setStatus(false);
            result.setMessage("多合一授权失败");
            return result;
        }

        bean.convert();

        int userId = Integer.parseInt(bean.getLogUserId());
        // 查询用户
        UserVO user = this.authService.getUsersById(userId);
        String authType = request.getParameter("authType");
        if(authService.checkDefaultConfig(bean, authType)){
            logger.info("[A-EMS用户合并授权完成后,回调结束]");
            result.setMessage("合并授权成功");
            result.setStatus(true);
            return result;
        }

        String status;
        String message;
        try {
            if (user != null
                && bean != null
                && (BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE)))
                ) {
                bean.setOrderId(bean.getLogOrderId());
                // 更新签约状态和日志表
                this.authService.updateUserAuth(userId, bean, authType);
                message = "多合一授权成功";
                status = ErrorCodeConstant.SUCCESS;
            }else{
                // 失败
                message = "多合一授权失败,失败原因：" + authService.getBankRetMsg(bean.getRetCode());
                status = ErrorCodeConstant.STATUS_CE999999;
            }
        } catch (Exception e) {
            logger.error("多合一授权异常,userId:{},异常报文:{}", userId, e);
            message = "多合一授权异常";
            status = ErrorCodeConstant.STATUS_CE999999;
        }
        // 返回值
        params.put("statusDesc", message);
        resultBean.setStatusForResponse(status);
        params.put("deadline", bean.getDeadline());
        params.put("accountId", bean.getAccountId());
        params.put("acqRes",request.getParameter("acqRes"));
        // 自动投标授权
        params.put("autoBidAuth", bean.getAutoBid());
        params.put("autoBidDeadline", bean.getAutoBidDeadline());
        params.put("autoBidMaxAmt", bean.getAutoBidMaxAmt());
        // 自动债转授权
        params.put("autoCreditAuth", bean.getAutoCredit());
        params.put("autoCreditDeadline", bean.getAutoCreditDeadline());
        params.put("autoCreditMaxAmt", bean.getAutoCreditMaxAmt());
        // 缴费授权
        params.put("paymentAuth", bean.getPaymentAuth());
        params.put("paymentDeadline", bean.getPaymentDeadline());
        params.put("paymentMaxAmt", bean.getPaymentMaxAmt());
        // 还款授权
        params.put("repayAuth", bean.getRepayAuth());
        params.put("repayDeadline", bean.getRepayDeadline());
        params.put("repayMaxAmt", bean.getRepayMaxAmt());
        logger.info("AEMS多合一授权[异步回调]，第三方返回参数:{}", JSON.toJSONString(params));

        CommonSoaUtils.noRetPostThree(request.getParameter("callback"), params);
        logger.info("AEMS多合一授权[异步回调]结束---------------------------------------------------------");
        result.setMessage("多合一授权权成功");
        result.setStatus(true);
        return result;
    }


    /**
     *  AEMS多合一授权-封装参数
     * @param request
     * @param requestBean
     * @param authBean
     * @param orderId
     * @return
     */
    private AuthBean paramPackage(HttpServletRequest request, AemsMergeAuthPagePlusRequestBean requestBean, AuthBean authBean, String orderId){
        // 拼装参数 调用江西银行
        // 成功同步调用路径
        String successUrl = systemConfig.getServerHost() + REQUEST_MAPPING + RETURL_SYN_ACTION + "?isSuccess=1&callback="
                + requestBean.getRetUrl();
        // 失败同步调用路径
        String retUrl = systemConfig.getServerHost() + REQUEST_MAPPING + RETURL_SYN_ACTION + "?callback="
                + requestBean.getRetUrl();
        // 异步调用路
        String bgRetUrl = "http://CS-USER" + REQUEST_MAPPING + RETURL_ASY_ACTION + "?authType="
                + requestBean.getAuthType() + "&callback="
                + requestBean.getNotifyUrl();

        // 查询开户信息通过电子账号
        BankOpenAccountVO bankOpenAccount = this.authService.getBankOpenAccountByAccount(requestBean.getAccountId());
        // 查询用户信息通过用户ID
        UserVO user = authService.getUsersById(bankOpenAccount.getUserId());//用户ID
        UserInfoVO usersInfo = authService.getUserInfo(user.getUserId());

        // 用户ID
        authBean.setUserId(user.getUserId());
        authBean.setUserType(user.getUserType());
        authBean.setIp(CustomUtil.getIpAddr(request));
        authBean.setAccountId(bankOpenAccount.getAccount());
        // 同步 异步
        authBean.setRetUrl(retUrl);
        authBean.setSuccessUrl(successUrl);
        authBean.setNotifyUrl(bgRetUrl);
        authBean.setIdNo(usersInfo.getIdcard());
        authBean.setName(usersInfo.getTruename());
        authBean.setChannel(requestBean.getChannel());
        // 0：PC 1：微官网 2：Android 3：iOS 4：其他
        authBean.setPlatform(requestBean.getPlatform());
        authBean.setAuthType(requestBean.getAuthType());
        authBean.setForgotPwdUrl(requestBean.getForgotPwdUrl());
        authBean.setIdentity(String.valueOf(usersInfo.getRoleId()));
        authBean.setOrderId(orderId);

        return authBean;
    }
}
