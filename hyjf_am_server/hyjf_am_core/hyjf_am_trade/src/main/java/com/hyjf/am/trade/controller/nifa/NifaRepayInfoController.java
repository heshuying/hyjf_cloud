/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.nifa;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.nifa.NifaRepayInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PC-LIUSHOUYI
 * @version NifaRepayInfoController, v0.1 2018/9/11 17:15
 */
@Api(value = "互金生成合同要素")
@RestController
@RequestMapping("/am-trade/nifa_repay_info")
public class NifaRepayInfoController extends BaseController {

    @Autowired
    NifaRepayInfoService nifaRepayInfoService;

    /**
     * 借款人还款表
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    @GetMapping(value = "/insert_nifa_repay_info/{borrowNid}/{repayPeriod}")
    public BooleanResponse insertNifaRepayInfo(@PathVariable String borrowNid ,@PathVariable Integer repayPeriod) {
        boolean delFlg =nifaRepayInfoService.insertNifaRepayInfo(borrowNid,repayPeriod);
        return new BooleanResponse(delFlg);
    }

    /**
     * 合同状态变更数据生成
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    @GetMapping(value = "/insert_nifa_contract_status/{borrowNid}/{repayPeriod}")
    public BooleanResponse insertNifaContractStatus(@PathVariable String borrowNid ,@PathVariable Integer repayPeriod) {
        boolean delFlg =nifaRepayInfoService.insertNifaContractStatus(borrowNid,repayPeriod);
        return new BooleanResponse(delFlg);
    }

    /**
     * 出借人回款记录生成
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    @GetMapping(value = "/insert_nifa_received_payments/{borrowNid}/{repayPeriod}")
    public BooleanResponse insertNifaReceivedPayments(@PathVariable String borrowNid ,@PathVariable Integer repayPeriod) {
        boolean delFlg =nifaRepayInfoService.insertNifaReceivedPayments(borrowNid,repayPeriod);
        return new BooleanResponse(delFlg);
    }


}
