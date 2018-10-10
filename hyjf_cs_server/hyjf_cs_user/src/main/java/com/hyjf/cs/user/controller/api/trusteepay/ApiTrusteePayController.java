package com.hyjf.cs.user.controller.api.trusteepay;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.TrusteePayRequestBean;
import com.hyjf.cs.user.service.trusteepay.TrusteePayService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Api(value = "api端-受托支付申请接口", tags = "api端-受托支付申请接口")
@Controller
@RequestMapping(value = "/hyjf-api/server/trusteePay")
public class ApiTrusteePayController extends BaseController {
    @Autowired
    private TrusteePayService trusteePayService;

    /**
     * 借款人受托支付申请
     *
     * @param request
     * @param payRequestBean
     * @return
     */
    @ApiOperation(value = "借款人受托支付申请", notes = "借款人受托支付申请")
    @RequestMapping(value = "/page.do")
    public ModelAndView trusteePay(HttpServletRequest request, @RequestBody TrusteePayRequestBean payRequestBean) {
        return trusteePayService.trusteePayApply(request, payRequestBean);
    }

    /**
     * 同步回调
     *
     * @param request
     * @param bean
     * @return
     */
    @RequestMapping("/trusteePayReturn")
    public ModelAndView trusteePayReturn(HttpServletRequest request, BankCallBean bean) {
        return trusteePayService.trusteePayReturn(request, bean);
    }

    /**
     * 异步回调
     *
     * @param request
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping("/trusteePayBgreturn")
    public BankCallResult trusteePayBgreturn(HttpServletRequest request, @RequestBody BankCallBean bean) {
        return trusteePayService.trusteePayBgreturn(request, bean);
    }

    /**
     * 受托支付申请状态查询
     *
     * @param payRequestBean
     * @return
     */
    @ApiOperation(value = "受托支付申请状态查询", notes = "受托支付申请状态查询")
    @ResponseBody
    @RequestMapping(value = "/trusteePayQuery.do", method = RequestMethod.POST)
    public JSONObject trusteePayQuery(@RequestBody TrusteePayRequestBean payRequestBean) {
        return trusteePayService.trusteePayQuery(payRequestBean);
    }
}
