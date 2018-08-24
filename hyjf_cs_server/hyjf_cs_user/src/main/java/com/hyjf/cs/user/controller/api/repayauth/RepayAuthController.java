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
import com.hyjf.cs.user.bean.AutoPlusRetBean;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.bean.RepayAuthRequestBean;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.service.paymentauthpage.PaymentAuthPageService;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author nxl
 * @version RepayAuthController, v0.1 2018/8/24 15:20
 */
@Api(tags = "api端-还款授权")
@RestController
@RequestMapping("/hyjf-api/server/repayAuth")
public class RepayAuthController extends BaseController{

    @Autowired
    private PaymentAuthPageService paymentAuthPageService;

    /**
     *
     * 还款授权
     * @param request
     * @param response
     * @param payRequestBean
     * @return
     */
    @PostMapping("/page")
    @ResponseBody
    @ApiOperation(value = "还款授权",notes = "还款授权")
    public ModelAndView repayAuth(HttpServletRequest request, HttpServletResponse response, @RequestBody RepayAuthRequestBean payRequestBean){
        ModelAndView modelAndView = new ModelAndView();
        logger.info("还款授权请求参数" + JSONObject.toJSONString(payRequestBean, true) + "]");
        // 检查参数是否为空
        if(payRequestBean.checkParmIsNull(modelAndView)){
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000001);
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000001, "请求参数异常"));
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return modelAndView;
        }

        // 验签  暂时去掉验签
        if (!SignUtil.verifyRequestSign(payRequestBean, "/server/repayAuth/repayAuth")) {
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000002);
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000002, "验签失败"));
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return modelAndView;
        }

        // 根据电子账户号查询用户ID
        BankOpenAccountVO bankOpenAccount = paymentAuthPageService.seletBankOpenAccountByAccountId(payRequestBean.getAccountId());
        if(bankOpenAccount == null){
            logger.info("-------------------没有根据电子银行卡找到用户"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000004,"没有根据电子银行卡找到用户");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000004);
            return modelAndView;
        }

        // 检查用户是否存在
        UserVO user = paymentAuthPageService.getUsersById(bankOpenAccount.getUserId());//用户ID
        if (user == null) {
            logger.info("-------------------用户不存在汇盈金服账户！"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000007,"用户不存在汇盈金服账户！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000007);
            return modelAndView;
        }

        Integer userId = user.getUserId();
        if (user.getBankOpenAccount().intValue() != 1) {// 未开户
            logger.info("-------------------用户未开户！"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000006,"用户未开户！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000006);
            return modelAndView;
        }

        UserInfoVO usersInfo=paymentAuthPageService.getUserInfo(userId);
        if (usersInfo.getRoleId() == 1) {// 未开户
            logger.info("-------------------用户为投资人角色无法授权！"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000010,"用户为投资账户！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000010);
            return modelAndView;
        }

        // 检查是否设置交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag != 1) {// 未设置交易密码
            logger.info("-------------------未设置交易密码！"+payRequestBean.getAccountId()+"！--------------------status"+user.getIsSetPassword());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_TP000002,"未设置交易密码！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_TP000002);
            return modelAndView;
        }

        // 查询是否已经授权过
        boolean isAuth = paymentAuthPageService.checkIsAuth(user.getUserId(), BankCallConstant.TXCODE_REPAY_AUTH_PAGE);

        if(isAuth){
            logger.info("-------------------已经授权过！"+payRequestBean.getAccountId());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000009,"已授权,请勿重复授权！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000009);
            return modelAndView;
        }

        /*// 同步调用路径
        String retUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath()
                + RepayAuthDefine.REQUEST_MAPPING + RepayAuthDefine.RETURL_SYN_ACTION + ".do?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath()
                + RepayAuthDefine.REQUEST_MAPPING + RepayAuthDefine.RETURL_ASY_ACTION + ".do?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getNotifyUrl().replace("#", "*-*-*");*/
        String retUrl ="";
        String bgRetUrl ="";
        // 组装发往江西银行参数
        BankCallBean bean = getCommonBankCallBean(payRequestBean.getAccountId(),userId,1,payRequestBean.getChannel());
        bean.setRetUrl(retUrl);//同步通知地址
        bean.setNotifyUrl(bgRetUrl);//异步通知地址
        bean.setForgotPwdUrl(payRequestBean.getForgotPwdUrl());
        // 插入日志
//        this.repayAuthService.insertUserAuthLog(user.getUserId(), bean,Integer.parseInt(payRequestBean.getPlatform()));
        // 跳转到汇付天下画面
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用银行接口失败！"+e.getMessage());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE999999,"系统异常！");
            payRequestBean.doNotify(params);
            getErrorMV(payRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE999999);
            return modelAndView;

        }
        logger.info("还款授权申请end");
        return modelAndView;
    }
    private ModelAndView getErrorMV(RepayAuthRequestBean payRequestBean, ModelAndView modelAndView, String status) {
        AutoPlusRetBean repwdResult = new AutoPlusRetBean();
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        repwdResult.setCallBackAction(payRequestBean.getRetUrl());
        repwdResult.set("chkValue", resultBean.getChkValue());
        repwdResult.set("status", resultBean.getStatus());
        repwdResult.setAcqRes(payRequestBean.getAcqRes());
        modelAndView.addObject("callBackForm", repwdResult);
        return modelAndView;
    }
    // 组装发往江西银行参数
    private BankCallBean getCommonBankCallBean(String accountId, Integer userid, int type, String channel) {
        // 构造函数已经设置
        // 版本号  交易代码  机构代码  银行代码  交易日期  交易时间  交易流水号   交易渠道
        BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10,BankCallConstant.TXCODE_REPAY_AUTH_PAGE,userid,channel);
        bean.setTxCode(BankCallConstant.TXCODE_REPAY_AUTH_PAGE);
        bean.setAccountId(accountId);// 电子账号
        bean.setMaxAmt("1350000");
        bean.setDeadline(GetDate.date2Str(GetDate.countDate(1,4),new SimpleDateFormat("yyyyMMdd")));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_REPAY_AUTH_PAGE);
        bean.setLogRemark("还款授权");

        return bean;
    }
}
