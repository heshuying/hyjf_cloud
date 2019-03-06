/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.paymentauthpage;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.cs.user.service.paymentauthpage.PaymentAuthPageService;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nxl
 * @version PaymentAuthPageController, v0.1 2018/8/23 14:44
 */
@Api(tags = "api端-缴费授权（页面调用）")
@RestController
@RequestMapping("/hyjf-api/server/user/paymentAuthPage")
public class PaymentAuthPageController extends BaseController {

    @Autowired
    private PaymentAuthPageService paymentAuthPageService;
    @Autowired
    private AutoPlusService autoPlusService;
    @Autowired
    private SystemConfig systemConfig;

    /**
     * 错误页面
     */
    public static final String PATH_OPEN_ACCOUNT_PAGE_ERROR = "/bank/user/trusteePay/error";
    public static final String CALL_BACK_TRUSTEEPAY_VIEW = "/callback/callback_trusteepay";
    /**
     * 外部服务接口:缴费授权 @RequestMapping
     */
    public static final String REQUEST_MAPPING = "/server/user/paymentAuthPage";
    /**
     * 同步回调
     */
    public static final String RETURL_SYN_ACTION = "/paymentauthReturn";
    /**
     * 异步回调
     */
    public static final String RETURL_ASY_ACTION = "/paymentauthBgreturn";


    @PostMapping("/page.do")
    @ResponseBody
    @ApiOperation(value = "用户缴费授权", notes = "用户缴费授权")
    public ModelAndView openAccont(@RequestBody PaymentAuthPageRequestBean requestBean, HttpServletRequest request, HttpServletResponse response) {
        logger.info("======PaymentAuthPageController======page方法=====");
        ModelAndView modelAndView = new ModelAndView(PATH_OPEN_ACCOUNT_PAGE_ERROR);
        logger.info("缴费授权第三方请求参数：" + JSONObject.toJSONString(requestBean));
        try {
            // 验证请求参数
            // 机构编号
            if (Validator.isNull(requestBean.getInstCode())) {
                getErrorMV(requestBean, modelAndView, ErrorCodeConstant.STATUS_CE000001, "请求参数异常");
                logger.info("请求参数异常[" + JSONObject.toJSONString(requestBean, true) + "]");
                return modelAndView;
            }
            if (Validator.isNull(requestBean.getRetUrl())) {
                getErrorMV(requestBean, modelAndView, ErrorCodeConstant.STATUS_CE000001, "同步地址不能为空");
                logger.info("同步地址不能为空[" + JSONObject.toJSONString(requestBean, true) + "]");
                return modelAndView;
            }
            if (Validator.isNull(requestBean.getNotifyUrl())) {
                getErrorMV(requestBean, modelAndView, ErrorCodeConstant.STATUS_CE000001, "异步地址不能为空");
                logger.info("异步地址不能为空[" + JSONObject.toJSONString(requestBean, true) + "]");
                return modelAndView;
            }
            // 渠道
            if (Validator.isNull(requestBean.getChannel())) {
                getErrorMV(requestBean, modelAndView, ErrorCodeConstant.STATUS_ZC000006, "渠道号不能为空");
                logger.info("渠道号不能为空[" + JSONObject.toJSONString(requestBean, true) + "]");
                return modelAndView;
            }

            // 验签
            // 机构编号  accountID  时间戳
            if (!SignUtil.verifyRequestSign(requestBean, "/server/user/paymentAuthPage/page")) {
                logger.info("----验签失败----");
                getErrorMV(requestBean, modelAndView, ErrorCodeConstant.STATUS_CE000002, "验签失败");
                logger.info("验签失败[" + JSONObject.toJSONString(requestBean, true) + "]");
                return modelAndView;
            }

            // 根据电子账户号查询用户ID
            BankOpenAccountVO bankOpenAccount = paymentAuthPageService.seletBankOpenAccountByAccountId(requestBean.getAccountId());
            if (bankOpenAccount == null) {
                getErrorMV(requestBean, modelAndView, ErrorCodeConstant.STATUS_CE000004, "没有根据电子银行卡找到用户");
                logger.info("没有根据电子银行卡找到用户[" + JSONObject.toJSONString(requestBean, true) + "]");
                return modelAndView;
            }

            // 检查用户是否存在
            UserVO user = paymentAuthPageService.getUsersById(bankOpenAccount.getUserId());//用户ID
            if (user == null) {
                getErrorMV(requestBean, modelAndView, ErrorCodeConstant.STATUS_CE000007, "用户不存在汇盈金服账户");
                logger.info("用户不存在汇盈金服账户[" + JSONObject.toJSONString(requestBean, true) + "]");
                return modelAndView;
            }

            Integer userId = user.getUserId();
            if (user.getBankOpenAccount().intValue() != 1) {// 未开户
                getErrorMV(requestBean, modelAndView, ErrorCodeConstant.STATUS_CE000006, "用户未开户！");
                logger.info("用户未开户！[" + JSONObject.toJSONString(requestBean, true) + "]");
                return modelAndView;
            }

            // 检查是否设置交易密码
            Integer passwordFlag = user.getIsSetPassword();
            if (passwordFlag != 1) {// 未设置交易密码
                getErrorMV(requestBean, modelAndView, ErrorCodeConstant.STATUS_TP000002, "未设置交易密码！");
                return modelAndView;
            }

            // 查询是否已经授权过
            boolean isAuth = paymentAuthPageService.checkIsAuth(user.getUserId(), BankCallConstant.TXCODE_PAYMENT_AUTH_PAGE);
            if (isAuth) {
                getErrorMV(requestBean, modelAndView, ErrorCodeConstant.STATUS_CE000009, "已授权,请勿重复授权！");
                return modelAndView;
            }
            // 同步调用路径
            String retUrl = systemConfig.getFrontHost() + request.getContextPath() + REQUEST_MAPPING + RETURL_SYN_ACTION + ".do?acqRes="
                    + requestBean.getAcqRes() + StringPool.AMPERSAND + "callback=" + requestBean.getRetUrl().replace("#", "*-*-*");
            // 异步调用路
            String bgRetUrl = systemConfig.getFrontHost() + request.getContextPath() + REQUEST_MAPPING + RETURL_ASY_ACTION + ".do?acqRes="
                    + requestBean.getAcqRes() + "&callback=" + requestBean.getNotifyUrl().replace("#", "*-*-*");
            // 拼装参数 调用江西银行
            // 同步调用路径
            /*String retUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath()
                    + PaymentAuthPageDefine.REQUEST_MAPPING + PaymentAuthPageDefine.RETURL_SYN_ACTION + ".do?acqRes="
                    + requestBean.getAcqRes() + StringPool.AMPERSAND + "callback="
                    + requestBean.getRetUrl().replace("#", "*-*-*");
            // 异步调用路
            String bgRetUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath()
                    + PaymentAuthPageDefine.REQUEST_MAPPING + PaymentAuthPageDefine.RETURL_ASY_ACTION + ".do?acqRes="
                    + requestBean.getAcqRes() + "&callback=" + requestBean.getNotifyUrl().replace("#", "*-*-*");*/
            // 用户ID
            PaymentAuthPageBean openBean = new PaymentAuthPageBean();
            PropertyUtils.copyProperties(openBean, requestBean);
            openBean.setUserId(userId);
            openBean.setIp(CustomUtil.getIpAddr(request));
            // 同步 异步
            openBean.setRetUrl(retUrl);
            openBean.setNotifyUrl(bgRetUrl);
            String orderId = GetOrderIdUtils.getOrderId2(openBean.getUserId());
            openBean.setOrderId(orderId);
            modelAndView = paymentAuthPageService.getCallbankMV(openBean);
            paymentAuthPageService.insertUserAuthLog(openBean.getUserId(), orderId, Integer.parseInt(openBean.getPlatform()), "5");
            logger.info("缴费授权页面end");
            return modelAndView;
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("缴费授权页面异常,异常信息:[" + e.toString() + "]");
            return null;
        }
    }

    private ModelAndView getErrorMV(PaymentAuthPageRequestBean userOpenAccountRequestBean, ModelAndView modelAndView, String status, String des) {
        PaymentAuthPageResultBean repwdResult = new PaymentAuthPageResultBean();
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        repwdResult.setCallBackAction(userOpenAccountRequestBean.getRetUrl());
        repwdResult.set("chkValue", resultBean.getChkValue());
        repwdResult.set("status", resultBean.getStatus());
        repwdResult.setAcqRes(userOpenAccountRequestBean.getAcqRes());
        modelAndView.addObject("callBackForm", repwdResult);
        return modelAndView;
    }

    /**
     * 异步回调
     */
    @PostMapping("/paymentauthBgreturn")
    @ResponseBody
    @ApiOperation(value = "异步回调", notes = "异步回调")
    public BankCallResult paymentAuthBgreturn(HttpServletRequest request, HttpServletResponse response,@ModelAttribute BankCallBean bean) {
        logger.info("缴费授权异步回调start");
        BankCallResult result = new BankCallResult();
        BaseResultBean resultBean = new BaseResultBean();
        Map<String, String> params = new HashMap<String, String>();
        String message = "";
        String status = "";
        if (bean == null) {
            logger.info("调用江西银行缴费授权接口,银行异步返回空");
            params.put("status", BaseResultBean.STATUS_FAIL);
            resultBean.setStatusForResponse(BaseResultBean.STATUS_FAIL);
            params.put("statusDesc", "缴费授权失败,调用银行接口失败");
            result.setMessage("缴费授权失败");
            params.put("chkValue", resultBean.getChkValue());
            params.put("acqRes", request.getParameter("acqRes"));
            result.setStatus(false);
            CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*", "#"), params);
            return result;
        }
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        // 更新签约状态和日志表
        try {
            if (bean != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))
                    && "1".equals(bean.getTxState())) {
                bean.setOrderId(bean.getLogOrderId());
                paymentAuthPageService.updateUserAuth(userId, bean);
                message = "缴费授权成功";
                status = ErrorCodeConstant.SUCCESS;
            } else {
                // 失败
                message = "缴费授权失败,失败原因：" + autoPlusService.getBankRetMsg(bean.getRetCode());
                status = ErrorCodeConstant.STATUS_CE999999;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("缴费授权出错,userId:【" + userId + "】错误原因：" + e.getMessage());
            message = "缴费授权失败";
            status = ErrorCodeConstant.STATUS_CE999999;
        }
        // 返回值
        params.put("accountId", bean.getAccountId());
        params.put("status", status);
        params.put("statusDesc", message);
        params.put("deadline", bean.getDeadline());
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        params.put("acqRes", request.getParameter("acqRes"));
        logger.info("缴费授权第三方返回参数：" + JSONObject.toJSONString(params));
        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*", "#"), params);
        logger.info("缴费授权异步回调end");
        result.setMessage("缴费授权权成功");
        result.setStatus(true);
        return result;
    }

    /**
     * 同步回调
     *
     * @param request
     * @param response
     * @param bean
     * @return
     * @author sunss
     */
    @PostMapping("/paymentauthReturn")
    @ApiOperation(value = "同步回调", notes = "同步回调")
    public ModelAndView paymentAuthReturn(HttpServletRequest request, HttpServletResponse response,BankCallBean bean) {
        logger.info("缴费授权同步回调start,请求参数为：【" + JSONObject.toJSONString(bean, true) + "】");
        ModelAndView modelAndView = new ModelAndView(CALL_BACK_TRUSTEEPAY_VIEW);
        AutoPlusRetBean repwdResult = new AutoPlusRetBean();
        repwdResult.setCallBackAction(request.getParameter("callback").replace("*-*-*", "#"));
        bean.convert();
        repwdResult.set("accountId", bean.getAccountId());
        //出借人签约状态查询
        logger.info("缴费授权授权同步回调调用查询接口查询状态");
        BankCallBean retBean = autoPlusService.getTermsAuthQuery(Integer.parseInt(bean.getLogUserId()), "000002");
        logger.info("缴费授权授权同步回调调用查询接口查询状态结束  结果为:" + (retBean == null ? "空" : retBean.getPaymentAuth()));
        bean = retBean;
        if (retBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())
                && "1".equals(retBean.getPaymentAuth())) {
            modelAndView.addObject("statusDesc", "缴费授权申请成功！");
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("deadline", bean.getDeadline());
        } else {
            // 失败
            modelAndView.addObject("statusDesc", "缴费授权申请失败,失败原因：" + autoPlusService.getBankRetMsg(bean.getRetCode()));
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        }
        //------------------------------------------------
        repwdResult.setAcqRes(request.getParameter("acqRes"));
        logger.info("缴费授权同步第三方返回参数：" + JSONObject.toJSONString(repwdResult));
        modelAndView.addObject("callBackForm", repwdResult);
        logger.info("缴费授权申请同步回调end");
        return modelAndView;
    }

}
