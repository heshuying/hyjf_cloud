package com.hyjf.cs.trade.service.aems.directrecharge;

import com.hyjf.cs.trade.bean.AemsUserDirectRechargeRequestBean;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @version AemsUserDirectRechargeService, v0.1 2018/12/6 18:14
 * @Author: Zha Daojian
 */
public interface AemsUserDirectRechargeService extends BaseTradeService {

    /**
     * Aems页面充值
    * @author Zha Daojian
    * @date 2018/12/12 14:29
    * @param userRechargeRequestBean, request
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> recharge(AemsUserDirectRechargeRequestBean userRechargeRequestBean, HttpServletRequest request);

    /***
     * Aems页面充值同步回调
    * @author Zha Daojian
    * @date 2018/12/12 14:29
    * @param request, bean
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> pageReturn(HttpServletRequest request, BankCallBean bean);

    /***
     * Aems页面充值异步回调
    * @author Zha Daojian
    * @date 2018/12/12 14:29
    * @param request, bean
    * @return com.hyjf.pay.lib.bank.bean.BankCallResult
    **/
    BankCallResult bgreturn(HttpServletRequest request, BankCallBean bean);
}
