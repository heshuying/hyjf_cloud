/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;


import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.trade.InsertBankCreditEndForCreditEndRequest;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.service.BankCreditEndService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 结束债权（原子层）
 * @author liubin
 * @version BankCreditEndController, v0.1 2018/7/6 18:02
 */
@RestController
@RequestMapping("/am-trade/bankCreditEndController")
public class BankCreditEndController {

    @Autowired
    private BankCreditEndService bankCreditEndService;

    @RequestMapping("/insertBankCreditEndForCreditEnd")
    public Response<Integer> insertBankCreditEndForCreditEnd(@RequestBody InsertBankCreditEndForCreditEndRequest request){
        HjhDebtCredit hjhDebtCredit = new HjhDebtCredit();
        BeanUtils.copyProperties(request.getHjhDebtCreditVO(), hjhDebtCredit);
        String tenderAccountId =  request.getTenderAccountId();
        String tenderAuthCode = request.getTenderAuthCode();
        return new Response(this.bankCreditEndService.insertBankCreditEndForCreditEnd(hjhDebtCredit, tenderAccountId, tenderAuthCode));
    }
}
