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

    Map<String,Object> recharge(AemsUserDirectRechargeRequestBean userRechargeRequestBean, HttpServletRequest request);
    Map<String,Object> pageReturn(HttpServletRequest request, BankCallBean bean);
    BankCallResult bgreturn(HttpServletRequest request, BankCallBean bean);
}
