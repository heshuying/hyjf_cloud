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



    @ApiOperation(value = "AEMS多合一授权", notes = "AEMS多合一授权")
    @PostMapping(value = "/mergeAuth", produces = "application/json; charset=utf-8")
    public ModelAndView mergeAuthPage(@RequestBody @Valid AemsMergeAuthPagePlusRequestBean requestBean, HttpServletRequest request) {
        logger.info("AEMS多合一授权[开始]----------------------------------");
        logger.info("AEMS多合一授权请求参数:{}", JSON.toJSONString(requestBean));

        ModelAndView modelAndView;
        // 入参校验
        Map<String, String> paramMap = authService.checkAemsParam(requestBean);

        // 数据校验不通过
        paramMap.put("callBackAction", requestBean.getRetUrl());
        if (!"1".equals(paramMap.get("status"))) {
            logger.info("AEMS多合一授权[数据校验不通过!]----------------------------------");
            return callbackErrorView(paramMap);
        }

        AuthBean authBean = new AuthBean();
        // 根据用户ID生成订单ID
        String orderId = GetOrderIdUtils.getOrderId2(authBean.getUserId());

        // 打包参数
        authBean = paramPackage(request, requestBean, authBean, orderId);
        logger.info("AEMS多合一授权请求银行参数打印,authBean:{}", JSON.toJSONString(authBean));
        // 校验用户角色和授权类型是否一致
        paramMap = authService.checkUserRoleAndAuthType(authBean, requestBean.getAuthType());
        if (!"1".equals(paramMap.get("status"))) {
            logger.info("AEMS多合一授权返回AEMS的参数, paramMap:{}"+ JSON.toJSONString(paramMap));
            return callbackErrorView(paramMap);
        }
        try {
            // 补充数据，请求银行
            modelAndView = authService.getApiCallbankMV(authBean);

            // 判断授权类型，保存至授权日志表
            authService.saveUserAuthLog(authBean, orderId);
        } catch (Exception e) {
            logger.info("AEMS多合一授权异常,异常信息:{}", e);
            return null;
        }

        logger.info("AEMS多合一授权[结束]----------------------------------");
        return modelAndView;
    }



    @ApiOperation(value = "AEMS多合一授权[同步回调]", notes = "AEMS多合一授权[同步回调]")
    @RequestMapping(RETURL_SYN_ACTION)
    public ModelAndView returnPage(HttpServletRequest request) {
        logger.info("AEMS多合一授权[同步回调]开始----------------------------------");

        String isSuccess = request.getParameter("isSuccess");
        logger.info("银行同步请求参数isSuccess:{}", isSuccess);
        String url = request.getParameter("callback");

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        resultMap.put("callBackAction", url);

        if (isSuccess == null || !"1".equals(isSuccess)) {
            logger.warn("AEMS多合一授权[同步回调],银行返回数据异常!");
            // 失败
            resultMap.put("status", ErrorCodeConstant.STATUS_CE999999);
            resultMap.put("statusDesc", "资金端多合一授权失败,调用银行接口失败!");
            resultMap.put("chkValue", ApiSignUtil.encryptByRSA(ErrorCodeConstant.STATUS_CE999999));
            resultMap.put("acqRes", request.getParameter("acqRes"));

            logger.info("AEMS多合一授权失败!");
            logger.info("AEMS多合一授权[同步回调]结束----------------------------------");
            return callbackErrorView(resultMap);
        }

        resultMap.put("status", ErrorCodeConstant.SUCCESS);
        resultMap.put("statusDesc", "资金端多合一授权成功");
        resultMap.put("chkValue", ApiSignUtil.encryptByRSA(ErrorCodeConstant.SUCCESS));
        resultMap.put("acqRes", request.getParameter("acqRes"));

        logger.info("AEMS多合一授权[同步回调],返回给AEMS的参数:{}", JSON.toJSONString(resultMap));
        logger.info("AEMS多合一授权[同步回调]结束----------------------------------");
        return callbackErrorView(resultMap);
    }



    @ApiOperation(value = "AEMS多合一授权[异步回调]", notes = "AEMS多合一授权[异步回调]")
    @RequestMapping(RETURL_ASY_ACTION)
    @ResponseBody
    public BankCallResult bgReturn(HttpServletRequest request, @RequestBody BankCallBean bean) {
        logger.info("AEMS多合一授权[异步回调]开始------------------------------");
        logger.info("银行异步请求参数:{}", JSON.toJSONString(bean));

        Map<String, String> params = new HashMap<>();
        BankCallResult result = new BankCallResult();

        if (bean == null) {
            logger.warn("调用江西银行多合一授权接口,银行异步返回空");
            // 返回AEMS的参数
            params.put("acqRes", request.getParameter("acqRes"));
            params.put("status", ErrorCodeConstant.STATUS_CE999999);
            params.put("statusDesc", "资金端多合一授权失败,调用银行接口失败");
            params.put("chkValue", ApiSignUtil.encryptByRSA(ErrorCodeConstant.STATUS_CE999999));
            CommonSoaUtils.noRetPostThree(request.getParameter("callback"), params);
            // 返回银行的参数
            result.setStatus(false);
            result.setMessage("获得参数为null");
            return result;
        }

        bean.convert();

        int userId = Integer.parseInt(bean.getLogUserId());
        // 查询用户
        UserVO user = this.authService.getUsersById(userId);
        String authType = request.getParameter("authType");
        if(authService.checkDefaultConfig(bean, authType)){
            logger.warn("AEMS多合一授权[异步回调],用户修改了默认授权数据,授权失败!");
            result.setStatus(true);
            return result;
        }

        String status;
        String statusDesc;
        try {
            if (user != null
                && bean != null
                && (BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE)))
                ) {
                status = ErrorCodeConstant.SUCCESS;
                statusDesc = "资金端多合一授权成功";
                // 更新签约状态和日志表
                bean.setOrderId(bean.getLogOrderId());
                this.authService.updateUserAuth(userId, bean, authType);
            }else{
                // 失败
                status = ErrorCodeConstant.STATUS_CE999999;
                statusDesc = "资金端多合一授权失败,失败原因:"+ authService.getBankRetMsg(bean.getRetCode());
            }
        } catch (Exception e) {
            logger.error("AEMS多合一授权[异步回调]异常,userId:{},异常报文:{}", userId, e);
            status = ErrorCodeConstant.STATUS_CE999999;
            statusDesc = "资金端多合一授权异常";
        }
        // 返回值
        params.put("status", status);
        params.put("statusDesc", statusDesc);
        params.put("deadline", bean.getDeadline());
        params.put("accountId", bean.getAccountId());
        params.put("acqRes",request.getParameter("acqRes"));
        params.put("chkValue", ApiSignUtil.encryptByRSA(status));
        // 自动投标授权
        params.put("autoBidAuth", bean.getAutoBid());
        params.put("autoBidMaxAmt", bean.getAutoBidMaxAmt());
        params.put("autoBidDeadline", bean.getAutoBidDeadline());
        // 自动债转授权
        params.put("autoCreditAuth", bean.getAutoCredit());
        params.put("autoCreditMaxAmt", bean.getAutoCreditMaxAmt());
        params.put("autoCreditDeadline", bean.getAutoCreditDeadline());
        // 缴费授权
        params.put("paymentAuth", bean.getPaymentAuth());
        params.put("paymentMaxAmt", bean.getPaymentMaxAmt());
        params.put("paymentDeadline", bean.getPaymentDeadline());
        // 还款授权
        params.put("repayAuth", bean.getRepayAuth());
        params.put("repayMaxAmt", bean.getRepayMaxAmt());
        params.put("repayDeadline", bean.getRepayDeadline());

        logger.info("AEMS多合一授权[异步回调],返回给AEMS的参数:{}", JSON.toJSONString(params));
        CommonSoaUtils.noRetPostThree(request.getParameter("callback"), params);

        result.setStatus(true);
        logger.info("AEMS多合一授权[异步回调]结束------------------------------");
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
        authBean.setNotifyUrl(bgRetUrl);
        authBean.setSuccessUrl(successUrl);
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
