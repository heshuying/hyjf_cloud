/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.exception;


import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.BankCreditEndResponse;
import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.resquest.trade.BankCreditEndRequest;
import com.hyjf.am.resquest.trade.InsertBankCreditEndForCreditEndRequest;
import com.hyjf.am.resquest.trade.UpdateBankCreditEndForStatusRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BankCreditEnd;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.service.admin.exception.BankCreditEndService;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
public class BankCreditEndController extends BaseController {

    @Autowired
    private BankCreditEndService bankCreditEndService;

    /**
     * 结束债权列表
     * @auther: hesy
     * @date: 2018/7/12
     */
    @RequestMapping("/getlist")
    public BankCreditEndResponse getCreditEndList(@RequestBody BankCreditEndListRequest requestBean){
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
    public Integer getCreditEndCount(@RequestBody BankCreditEndListRequest requestBean){
        return bankCreditEndService.getCreditEndCount(requestBean);
    }

    /**
     * 根据orderId获取
     * @auther: hesy
     * @date: 2018/7/12
     */
    @RequestMapping("/getby_orderid/{orderId}")
    public BankCreditEndResponse getCreditEndByOrderId(@PathVariable String orderId){
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
        logger.info("结束债权更新为初始状态，requestBean: " + JSON.toJSONString(requestBean));
        BankCreditEnd creditEnd = new BankCreditEnd();
        BeanUtils.copyProperties(requestBean,creditEnd);
        return bankCreditEndService.updateCreditEndForInitial(creditEnd);
    }

    @RequestMapping("/insertBankCreditEndForCreditEnd")
    public IntegerResponse insertBankCreditEndForCreditEnd(@RequestBody InsertBankCreditEndForCreditEndRequest request){
        HjhDebtCredit hjhDebtCredit = new HjhDebtCredit();
        BeanUtils.copyProperties(request.getHjhDebtCreditVO(), hjhDebtCredit);
        String tenderAccountId =  request.getTenderAccountId();
        String tenderAuthCode = request.getTenderAuthCode();
        return new IntegerResponse(this.bankCreditEndService.insertBankCreditEndForCreditEnd(hjhDebtCredit, tenderAccountId, tenderAuthCode));
    }

    /**
     * 批次结束债权用更新 结束债权任务表
     * @param request
     * @return
     */
    @RequestMapping("/updateBankCreditEndForBatch")
    public IntegerResponse updateBankCreditEndForBatch(@RequestBody BankCreditEndRequest request){
        BankCreditEnd bankCreditEnd = new BankCreditEnd();
        BeanUtils.copyProperties(request.getBankCreditEndVO(), bankCreditEnd);
        return new IntegerResponse(this.bankCreditEndService.updateBankCreditEndForBatch(bankCreditEnd));
    }

    /**
     * 据批次号和日期，取得结束债权任务列表
     * @param batchNo
     * @param txDate
     * @return
     */
    @RequestMapping("/getBankCreditEndListByBatchnoTxdate/{batchNo}/{txDate}")
    public BankCreditEndResponse getBankCreditEndListByBatchnoTxdate(@PathVariable String batchNo, @PathVariable String txDate) {
        BankCreditEndResponse response = new BankCreditEndResponse();
        List<BankCreditEnd> list = this.bankCreditEndService.getBankCreditEndListByBatchnoTxdate(batchNo, txDate);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(CommonUtils.convertBeanList(list, BankCreditEndVO.class));
        }
        return response;
    }


    /**
     * 根据条件(批次号和日期)，更新结束债权任务状态
     * @param request
     * @return
     */
    @RequestMapping("/updateBankCreditEndForStatus")
    public IntegerResponse updateBankCreditEndForStatus(@RequestBody UpdateBankCreditEndForStatusRequest request){
        String batchNo = request.getBankCreditEndVO().getBatchNo();
        String txDate = request.getBankCreditEndVO().getTxDate();
        Integer txCounts = request.getBankCreditEndVO().getTxCounts();
        logger.info("am-trade:txCounts:{}", txCounts);
        int status = request.getStatus();
        return new IntegerResponse(this.bankCreditEndService.updateBankCreditEndForStatus(batchNo, txDate, txCounts, status));
    }


    /**
     * 合法性检查后，更新批次结束债权任务
     * @param request
     * @return
     */
    @RequestMapping("/updateBatchCreditEndCheck")
    public IntegerResponse updateBatchCreditEndCheck(@RequestBody BankCallBeanVO request){
        return new IntegerResponse(this.bankCreditEndService.updateBatchCreditEndCheck(request));
    }

    /**
     * 银行完成后，更新批次结束债权任务
     * @param request
     * @return
     */
    @RequestMapping("/updateBatchCreditEndFinish")
    public IntegerResponse updateBatchCreditEndFinish(@RequestBody BankCallBeanVO request){
        return new IntegerResponse(this.bankCreditEndService.updateBatchCreditEndFinish(request));
    }
}
