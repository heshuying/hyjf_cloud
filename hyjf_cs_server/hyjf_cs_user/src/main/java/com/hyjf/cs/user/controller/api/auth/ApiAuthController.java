package com.hyjf.cs.user.controller.api.auth;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.auth.AuthService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pcc
 */
@Api(value = "第三方用户多合一授权",tags = "api端-用户多合一授权")
@Controller
@RequestMapping("/hyjf-api/server/user/mergeAuthPagePlus")
public class ApiAuthController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(ApiAuthController.class);
    /**
     * 外部服务接口:缴费授权 @RequestMapping
     */
    public static final String REQUEST_MAPPING = "/hyjf-api/server/user/mergeAuthPagePlus";
    /**
     * 同步回调
     */
    public static final String RETURL_SYN_ACTION = "/return";
    /**
     * 异步回调
     */
    public static final String RETURL_ASY_ACTION = "/bgReturn";
    @Autowired
    private AuthService authService;

    @Autowired
    SystemConfig systemConfig;

    @ApiOperation(value = "多合一授权", notes = "多合一授权")
    @PostMapping(value = "/page.do", produces = "application/json; charset=utf-8")
    public ModelAndView mergeAuthPage(@RequestBody @Valid ApiAuthRequesBean requestBean , HttpServletRequest request) {
        logger.info("第三方请求页面多合一授权, ApiAuthRequesBean is :{}", JSONObject.toJSONString(requestBean));
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> paramMap = authService.checkApiParam(requestBean);
        paramMap.put("callBackAction",requestBean.getRetUrl());
        if(!"1".equals(paramMap.get("status"))){
            return callbackErrorView(paramMap);
        }
        // 根据电子账户号查询用户ID
        BankOpenAccountVO bankOpenAccount = this.authService.getBankOpenAccountByAccount(requestBean.getAccountId());
        // 检查用户是否存在
        UserVO user = authService.getUsersById(bankOpenAccount.getUserId());//用户ID
        // 拼装参数 调用江西银行
        // 同步调用路径
        String retUrl = systemConfig.getServerHost() + REQUEST_MAPPING + RETURL_SYN_ACTION + "?isSuccess=&authType="
                + requestBean.getAuthType()+ "&callback="
                + requestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl ="http://CS-USER" + REQUEST_MAPPING + RETURL_ASY_ACTION + "?authType="
                + requestBean.getAuthType()+ "&callback="
                + requestBean.getNotifyUrl().replace("#", "*-*-*");


        UserInfoVO usersInfo =authService.getUserInfo(user.getUserId());
        try {
            // 用户ID
            AuthBean authBean = new AuthBean();
            authBean.setUserId(user.getUserId());
            authBean.setIp(CustomUtil.getIpAddr(request));
            authBean.setAccountId(bankOpenAccount.getAccount());
            // 同步 异步
            authBean.setRetUrl(retUrl);
            authBean.setNotifyUrl(bgRetUrl);
            // 0：PC 1：微官网 2：Android 3：iOS 4：其他
            authBean.setPlatform(requestBean.getPlatform());
            authBean.setAuthType(requestBean.getAuthType());
            authBean.setChannel(requestBean.getChannel());
            authBean.setForgotPwdUrl(requestBean.getForgotPwdUrl());
            authBean.setName(usersInfo.getTruename());
            authBean.setIdNo(usersInfo.getIdcard());
            authBean.setIdentity(usersInfo.getRoleId()+"");
            authBean.setUserType(user.getUserType());
            String orderId = GetOrderIdUtils.getOrderId2(authBean.getUserId());
            authBean.setOrderId(orderId);
            modelAndView = authService.getApiCallbankMV(authBean);
            String type="0";
            if(AuthBean.AUTH_TYPE_AUTO_BID.equals(requestBean.getAuthType())){
                type="1";
            }else if(AuthBean.AUTH_TYPE_AUTO_CREDIT.equals(requestBean.getAuthType())){
                type="4";
            }else if(AuthBean.AUTH_TYPE_PAYMENT_AUTH.equals(requestBean.getAuthType())){
                type="5";
            }else if(AuthBean.AUTH_TYPE_REPAY_AUTH.equals(requestBean.getAuthType())){
                type="6";
            }
            authService.insertUserAuthLog(authBean.getUserId(), orderId,
                    Integer.parseInt(authBean.getPlatform()), type);
            logger.info("多合一授权页面end");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("多合一授权页面异常,异常信息:[" + e.toString() + "]");
            return null;
        }

		return modelAndView;
    }



    /**
     * 第三方多合一授权同步跳转地址
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "第三方端多合一授权同步回调", notes = "多合一授权")
    @RequestMapping(value = "/return")
    public ModelAndView returnPage(HttpServletRequest request,BankCallBean bean) {
        String isSuccess = request.getParameter("isSuccess");
        String url = request.getParameter("callback").replace("*-*-*", "#");
        String authType=request.getParameter("authType");
        logger.info("第三方端授权同步请求,isSuccess:{}", isSuccess);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        if (isSuccess == null || !"1".equals(isSuccess)) {
            // 失败
            resultMap.put("status", ErrorCodeConstant.STATUS_CE999999);
            resultMap.put("statusDesc", "多合一授权失败,调用银行接口失败");
            resultMap.put("acqRes", request.getParameter("acqRes"));
            resultMap.put("callBackAction", url);
            return callbackErrorView(resultMap);
        } else {
            if(authService.checkDefaultConfig(bean, authType)){
                authService.updateUserAuthLog(bean.getLogOrderId(),"QuotaError");
                resultMap.put("status", ErrorCodeConstant.STATUS_CE999999);
                resultMap.put("statusDesc", "多合一授权申请失败,失败原因：授权期限过短或额度过低，<br>请重新授权！");
                resultMap.put("acqRes", request.getParameter("acqRes"));
                resultMap.put("callBackAction", url);
                return callbackErrorView(resultMap);
            }
            resultMap.put("status", ErrorCodeConstant.SUCCESS);
            resultMap.put("status", "多合一授权成功");
            resultMap.put("deadline", bean.getDeadline());
            return callbackErrorView(resultMap);
        }
    }

    /**
     * 多合一授权异步处理
     *
     * @param bean
     * @return
     */
    @ApiOperation(value = "页面开户异步处理", notes = "页面开户异步处理")
    @RequestMapping("/bgReturn")
    @ResponseBody
    public BankCallResult bgReturn(HttpServletRequest request, @RequestBody BankCallBean bean) {
        logger.info("多合一授权异步回调start");
        BankCallResult result = new BankCallResult();
        BaseResultBean resultBean = new BaseResultBean();
        Map<String, String> params = new HashMap<String, String>();
        String message = "";
        String status = "";

        if (bean == null) {
            logger.info("调用江西银行多合一授权接口,银行异步返回空");
            params.put("status", BaseResultBean.STATUS_FAIL);
            resultBean.setStatusForResponse(BaseResultBean.STATUS_FAIL);
            params.put("statusDesc", "多合一授权失败,调用银行接口失败");
            result.setMessage("多合一授权失败");
            params.put("chkValue", resultBean.getChkValue());
            params.put("acqRes", request.getParameter("acqRes"));
            result.setStatus(false);
            CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*", "#"), params);
            return result;
        }

        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        // 查询用户
        UserVO user = this.authService.getUsersById(userId);
        String authType=request.getParameter("authType");
        if(authService.checkDefaultConfig(bean, authType)){
            logger.info("[用户合并授权完成后,回调结束]");
            result.setMessage("合并授权成功");
            result.setStatus(true);
            return result;
        }
        // 更新签约状态和日志表
        try {
            if (user != null&& bean != null
                    && (BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE)))) {
                bean.setOrderId(bean.getLogOrderId());
                this.authService.updateUserAuth(userId,bean,authType);
                message = "多合一授权成功";
                status = ErrorCodeConstant.SUCCESS;
            }else{
                // 失败
                message = "多合一授权失败,失败原因：" + authService.getBankRetMsg(bean.getRetCode());
                status = ErrorCodeConstant.STATUS_CE999999;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("多合一授权出错,userId:【"+userId+"】错误原因："+e.getMessage());
            message = "多合一授权失败";
            status = ErrorCodeConstant.STATUS_CE999999;
        }
        // 返回值
        params.put("accountId", bean.getAccountId());
        params.put("status", status);
        params.put("statusDesc",message);
        params.put("deadline", bean.getDeadline());
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        params.put("acqRes",request.getParameter("acqRes"));
        logger.info("多合一授权第三方返回参数："+JSONObject.toJSONString(params));
        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*","#"), params);
        logger.info("多合一授权异步回调end");
        result.setMessage("多合一授权权成功");
        result.setStatus(true);
        return result;
    }

}