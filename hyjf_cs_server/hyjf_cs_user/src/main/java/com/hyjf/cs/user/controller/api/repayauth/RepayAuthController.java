/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.repayauth;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.AutoPlusRequestBean;
import com.hyjf.cs.user.bean.AutoPlusRetBean;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.cs.user.service.paymentauthpage.PaymentAuthPageService;
import com.hyjf.cs.user.service.repayauth.RepayAuthPageService;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nxl
 * @version RepayAuthController, v0.1 2018/8/24 15:20
 */
@Api(tags = "api端-还款授权")
@RestController
@RequestMapping("/hyjf-api/server/repayAuth")
public class RepayAuthController extends BaseController {

    @Autowired
    private PaymentAuthPageService paymentAuthPageService;
    @Autowired
    private AutoPlusService autoPlusService;
    @Autowired
    private RepayAuthPageService pageService;
    @Autowired
    SystemConfig systemConfig;

    public static final String REQUEST_MAPPING = "hyjf-api/server/repayAuth";
    /**
     * 同步回调
     */
    public static final String RETURL_SYN_ACTION = "/repayAuthReturn";
    /**
     * 异步回调
     */
    public static final String RETURL_ASY_ACTION = "/repayAuthBgreturn";
    // 借款人受托支付申请 jsp
    public static final String CALL_BACK_TRUSTEEPAY_VIEW = "/callback/callback_trusteepay";

    /**
     * 还款授权
     *
     * @param request
     * @param response
     * @param payRequestBean
     * @return
     */
    @PostMapping("/page.do")
    @ResponseBody
    @ApiOperation(value = "还款授权", notes = "还款授权")
    public ModelAndView repayAuth(HttpServletRequest request, HttpServletResponse response, @RequestBody AutoPlusRequestBean payRequestBean) {
        ModelAndView modelAndView = new ModelAndView();
        logger.info("还款授权请求参数" + JSONObject.toJSONString(payRequestBean, true) + "]");
        // 检查参数是否为空
        if (payRequestBean.checkParmIsNull()) {
            autoPlusService.getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000001);
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000001, "请求参数异常"));
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return modelAndView;
        }

        // 验签  暂时去掉验签
        if (!SignUtil.verifyRequestSign(payRequestBean, "/server/repayAuth/repayAuth")) {
            autoPlusService.getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000002);
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000002, "验签失败"));
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return modelAndView;
        }

        // 根据电子账户号查询用户ID
        BankOpenAccountVO bankOpenAccount = paymentAuthPageService.seletBankOpenAccountByAccountId(payRequestBean.getAccountId());
        if (bankOpenAccount == null) {
            logger.info("-------------------没有根据电子银行卡找到用户" + payRequestBean.getAccountId() + "！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000004, "没有根据电子银行卡找到用户");
            payRequestBean.doNotify(params);
            autoPlusService.getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000004);
            return modelAndView;
        }

        // 检查用户是否存在
        UserVO user = paymentAuthPageService.getUsersById(bankOpenAccount.getUserId());//用户ID
        if (user == null) {
            logger.info("-------------------用户不存在汇盈金服账户！" + payRequestBean.getAccountId() + "！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000007, "用户不存在汇盈金服账户！");
            payRequestBean.doNotify(params);
            autoPlusService.getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000007);
            return modelAndView;
        }

        Integer userId = user.getUserId();
        if (user.getBankOpenAccount().intValue() != 1) {// 未开户
            logger.info("-------------------用户未开户！" + payRequestBean.getAccountId() + "！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000006, "用户未开户！");
            payRequestBean.doNotify(params);
            autoPlusService.getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000006);
            return modelAndView;
        }

        UserInfoVO usersInfo = paymentAuthPageService.getUserInfo(userId);
        if (usersInfo.getRoleId() == 1) {// 未开户
            logger.info("-------------------用户为出借人角色无法授权！" + payRequestBean.getAccountId() + "！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000010, "用户为出借账户！");
            payRequestBean.doNotify(params);
            autoPlusService.getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000010);
            return modelAndView;
        }

        // 检查是否设置交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag != 1) {// 未设置交易密码
            logger.info("-------------------未设置交易密码！" + payRequestBean.getAccountId() + "！--------------------status" + user.getIsSetPassword());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_TP000002, "未设置交易密码！");
            payRequestBean.doNotify(params);
            autoPlusService.getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_TP000002);
            return modelAndView;
        }

        // 查询是否已经授权过
        boolean isAuth = paymentAuthPageService.checkIsAuth(user.getUserId(), BankCallConstant.TXCODE_REPAY_AUTH_PAGE);

        if (isAuth) {
            logger.info("-------------------已经授权过！" + payRequestBean.getAccountId());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000009, "已授权,请勿重复授权！");
            payRequestBean.doNotify(params);
            autoPlusService.getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000009);
            return modelAndView;
        }
        // 同步调用路径
        String retUrl = systemConfig.getFrontHost() + request.getContextPath() + REQUEST_MAPPING + RETURL_SYN_ACTION + ".do?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl = systemConfig.getFrontHost() + request.getContextPath() + REQUEST_MAPPING + RETURL_ASY_ACTION + ".do?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getNotifyUrl().replace("#", "*-*-*");
        /*// 同步调用路径
        String retUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath()
                + RepayAuthDefine.REQUEST_MAPPING + RepayAuthDefine.RETURL_SYN_ACTION + ".do?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath()
                + RepayAuthDefine.REQUEST_MAPPING + RepayAuthDefine.RETURL_ASY_ACTION + ".do?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getNotifyUrl().replace("#", "*-*-*");*/
        // 组装发往江西银行参数
        BankCallBean bean = getCommonBankCallBean(payRequestBean.getAccountId(), userId, 1, payRequestBean.getChannel());
        bean.setRetUrl(retUrl);//同步通知地址
        bean.setNotifyUrl(bgRetUrl);//异步通知地址
        bean.setForgotPwdUrl(payRequestBean.getForgotPwdUrl());
        // 插入日志
        this.pageService.insertUserAuthLog(user.getUserId(), bean,Integer.parseInt(payRequestBean.getPlatform()));
        // 跳转到汇付天下画面
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("调用银行接口失败！" + e.getMessage());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE999999, "系统异常！");
            payRequestBean.doNotify(params);
            autoPlusService.getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE999999);
            return modelAndView;

        }
        logger.info("还款授权申请end");
        return modelAndView;
    }

    // 组装发往江西银行参数
    private BankCallBean getCommonBankCallBean(String accountId, Integer userid, int type, String channel) {
        // 构造函数已经设置
        // 版本号  交易代码  机构代码  银行代码  交易日期  交易时间  交易流水号   交易渠道
        BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10, BankCallConstant.TXCODE_REPAY_AUTH_PAGE, userid, channel);
        bean.setTxCode(BankCallConstant.TXCODE_REPAY_AUTH_PAGE);
        bean.setAccountId(accountId);// 电子账号
        bean.setMaxAmt("1350000");
        bean.setDeadline(GetDate.date2Str(GetDate.countDate(1, 4), new SimpleDateFormat("yyyyMMdd")));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_REPAY_AUTH_PAGE);
        bean.setLogRemark("还款授权");

        return bean;
    }

    // 还款授权同步回调
    @PostMapping("/repayAuthReturn")
    @ApiOperation(value = "还款授权同步回调", notes = "还款授权同步回调")
    public ModelAndView repayAuthReturn(HttpServletRequest request, HttpServletResponse response, BankCallBean bean) {
        logger.info("还款授权同步回调start,请求参数为：【" + JSONObject.toJSONString(bean, true) + "】");
        ModelAndView modelAndView = new ModelAndView(CALL_BACK_TRUSTEEPAY_VIEW);
        AutoPlusRetBean repwdResult = new AutoPlusRetBean();
        repwdResult.setCallBackAction(request.getParameter("callback").replace("*-*-*", "#"));
        bean.convert();
        repwdResult.set("accountId", bean.getAccountId());
        //出借人签约状态查询
        logger.info("还款授权同步回调调用查询接口查询状态");
        BankCallBean retBean = autoPlusService.getTermsAuthQuery(Integer.parseInt(bean.getLogUserId()), BankCallConstant.CHANNEL_PC);
        logger.info("还款授权同步回调调用查询接口查询状态结束  结果为:" + (retBean == null ? "空" : retBean.getRetCode()));
        if (retBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode()) && "1".equals(retBean.getRepayAuth())) {
            try {
                modelAndView.addObject("statusDesc", "还款授权申请成功！");
                this.paymentAuthPageService.updateUserAuth(Integer.parseInt(bean.getLogUserId()), retBean);
                // 成功
                modelAndView.addObject("statusDesc", "还款授权申请成功！");
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
                repwdResult.set("chkValue", resultBean.getChkValue());
                repwdResult.set("status", resultBean.getStatus());
                repwdResult.set("deadline", bean.getDeadline());

            } catch (Exception e) {
                logger.info("还款授权申请同步插入数据库失败，错误原因:" + e.getMessage());
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                repwdResult.set("chkValue", resultBean.getChkValue());
                repwdResult.set("status", resultBean.getStatus());
            }
        } else {
            // 失败
            modelAndView.addObject("statusDesc", "还款授权申请失败,失败原因：" + autoPlusService.getBankRetMsg(bean.getRetCode()));

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        }

        //------------------------------------------------
        repwdResult.setAcqRes(request.getParameter("acqRes"));
        modelAndView.addObject("callBackForm", repwdResult);
        logger.info("还款授权申请同步回调end");
        return modelAndView;
    }

    // 异步回调
    @ResponseBody
    @PostMapping("/repayAuthBgreturn")
    @ApiOperation(value = "还款授权异步回调", notes = "还款授权异步回调")
    public BankCallResult repayAuthBgreturn(HttpServletRequest request, HttpServletResponse response, @ModelAttribute BankCallBean bean) {
        logger.info("还款授权申请异步回调start");
        BankCallResult result = new BankCallResult();
        BaseResultBean resultBean = new BaseResultBean();
        String message = "";
        String status = "";
        Map<String, String> params = new HashMap<String, String>();

        if (bean == null) {
            logger.info("调用江西银行还款授权接口,银行异步返回空");
            params.put("status", BaseResultBean.STATUS_FAIL);
            resultBean.setStatusForResponse(BaseResultBean.STATUS_FAIL);
            params.put("statusDesc", "还款授权失败,调用银行接口失败");
            result.setMessage("还款授权失败");
            params.put("chkValue", resultBean.getChkValue());
            params.put("acqRes", request.getParameter("acqRes"));
            result.setStatus(false);
            CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*", "#"), params);
            return result;
        }
        // 返回值修改 end
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        UserVO user = paymentAuthPageService.getUsersById(userId);

        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                bean.setOrderId(bean.getLogOrderId());
                // 更新签约状态和日志表
                this.paymentAuthPageService.updateUserAuth(Integer.parseInt(bean.getLogUserId()), bean);
                message = "还款授权成功";
                status = ErrorCodeConstant.SUCCESS;
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.info("还款授权出错,userId:【" + userId + "】错误原因：" + e.getMessage());
                message = "还款授权失败";
                status = ErrorCodeConstant.STATUS_CE999999;
            }

        } else {
            // 失败
            message = "还款授权失败,失败原因：" + autoPlusService.getBankRetMsg(bean.getRetCode());
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
        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*", "#"), params);
        logger.info("还款授权异步回调end");
        result.setMessage("还款授权成功");
        result.setStatus(true);
        return result;
    }

}
