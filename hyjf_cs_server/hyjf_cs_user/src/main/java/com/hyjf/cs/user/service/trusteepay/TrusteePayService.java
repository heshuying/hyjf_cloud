package com.hyjf.cs.user.service.trusteepay;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.user.bean.TrusteePayRequestBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public interface TrusteePayService extends BaseUserService {
    /**
     * 借款人受托支付申请
     *
     * @param request
     * @param payRequestBean
     * @return
     */
    ModelAndView trusteePayApply(HttpServletRequest request, TrusteePayRequestBean payRequestBean);

    /**
     * 借款人受托支付申请同步回调
     *
     * @param request
     * @param bean
     * @return
     */
    ModelAndView trusteePayReturn(HttpServletRequest request, BankCallBean bean);

    /**
     * 借款人受托支付申请异步回调
     *
     * @param request
     * @param bean
     * @return
     */
    BankCallResult trusteePayBgreturn(HttpServletRequest request, BankCallBean bean);

    /**
     * 受托支付申请状态查询
     *
     * @param payRequestBean
     * @return
     */
    JSONObject trusteePayQuery(TrusteePayRequestBean payRequestBean);
}
