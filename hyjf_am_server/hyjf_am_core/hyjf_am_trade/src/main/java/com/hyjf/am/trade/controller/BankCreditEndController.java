/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;


import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BankCreditEndResponse;
import com.hyjf.am.resquest.trade.BankCreditEndRequest;
import com.hyjf.am.resquest.trade.InsertBankCreditEndForCreditEndRequest;
import com.hyjf.am.trade.dao.model.auto.BankCreditEnd;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.service.BankCreditEndService;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * 结束债权列表
     * @auther: hesy
     * @date: 2018/7/12
     */
    @RequestMapping("/getlist")
    public BankCreditEndResponse getCreditEndList(@RequestBody BankCreditEndRequest requestBean){
        BankCreditEndResponse response = new BankCreditEndResponse();
        List<BankCreditEnd> recordList = bankCreditEndService.getCreditEndList(requestBean);
        if (Validator.isNotNull(recordList)){
            response.setResultList(CommonUtils.convertBeanList(recordList,BankCreditEndVO.class));
        }
        return response;
    }

    /**
     * 结束债权总记录数
     * @auther: hesy
     * @date: 2018/7/12
     */
    @RequestMapping("/getcount")
    public Integer getCreditEndCount(@RequestBody BankCreditEndRequest requestBean){
        return bankCreditEndService.getCreditEndCount(requestBean);
    }

    /**
     * 根据orderId获取
     * @auther: hesy
     * @date: 2018/7/12
     */
    @RequestMapping("/getby_orderid/{orderId}")
    public BankCreditEndResponse getCreditEndList(@PathVariable String orderId){
        BankCreditEndResponse response = new BankCreditEndResponse();
        BankCreditEnd record = bankCreditEndService.selectByOrderId(orderId);
        if (Validator.isNotNull(record)){
            response.setResult(CommonUtils.convertBean(record,BankCreditEndVO.class));
        }
        return response;
    }

    /**
     * 更新
     * @auther: hesy
     * @date: 2018/7/12
     */
    @RequestMapping("/update")
    public Integer updateBankCreditEnd(@RequestBody BankCreditEndVO requestBean){
        BankCreditEnd creditEnd = new BankCreditEnd();
        BeanUtils.copyProperties(requestBean,creditEnd);
        return bankCreditEndService.updateBankCreditEnd(creditEnd);
    }

    /**
     * 批次恢复为初始状态
     * @auther: hesy
     * @date: 2018/7/12
     */
    @RequestMapping("/update_initial")
    public Integer updateCreditEndForInitial(@RequestBody BankCreditEndVO requestBean){
        BankCreditEnd creditEnd = new BankCreditEnd();
        BeanUtils.copyProperties(requestBean,creditEnd);
        return bankCreditEndService.updateBankCreditEnd(creditEnd);
    }




}
