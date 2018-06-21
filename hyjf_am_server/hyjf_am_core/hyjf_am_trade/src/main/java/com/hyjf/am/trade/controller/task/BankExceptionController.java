package com.hyjf.am.trade.controller.task;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.BankCallBeanResponse;
import com.hyjf.am.response.trade.CreditTenderLogResponse;
import com.hyjf.am.response.trade.CreditTenderResponse;
import com.hyjf.am.resquest.trade.TenderCreditRequest;
import com.hyjf.am.trade.dao.model.auto.CreditTender;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLog;
import com.hyjf.am.trade.service.task.BankCreditTenderService;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.response.trade.AccountwithdrawResponse;
import com.hyjf.am.trade.dao.model.auto.Accountwithdraw;
import com.hyjf.am.trade.service.BankRechargeService;
import com.hyjf.am.trade.service.BankWithdrawService;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.common.util.CommonUtils;

import io.swagger.annotations.Api;

@Api(value = "江西银行充值掉单异常处理定时任务")
@RestController
@RequestMapping("/am-trade/bankException")
public class BankExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(BankExceptionController.class);

    @Autowired
    private BankRechargeService bankRechargeService;
    @Autowired
    private BankWithdrawService bankWithdrawService;
    @Autowired
    private BankCreditTenderService bankCreditTenderService;

    @RequestMapping("/recharge")
    public void recharge(){
        logger.info("recharge...");
        bankRechargeService.recharge();
    }


    @GetMapping("/selectCreditTender/{assignNid}")
    public CreditTenderResponse selectCreditTender(@PathVariable String assignNid){
        CreditTenderResponse response = new CreditTenderResponse();
        List<CreditTender> creditTenderList=bankCreditTenderService.selectCreditTender(assignNid);
        if (CollectionUtils.isNotEmpty(creditTenderList)){
            List<CreditTenderVO> voList = CommonUtils.convertBeanList(creditTenderList, CreditTenderVO.class);
            response.setResultList(voList);
        }

        return response;
    }


    /**
     * 查询债转承接掉单的数据
     */
    @GetMapping("/selectCreditTenderLogs")
    public CreditTenderLogResponse selectCreditTenderLogs(){
        logger.info("selectCreditTenderLogs...");
        CreditTenderLogResponse response = new CreditTenderLogResponse();
        List<CreditTenderLog> creditTenderLogList=bankCreditTenderService.selectCreditTenderLogs();
        if (CollectionUtils.isNotEmpty(creditTenderLogList)){
            List<CreditTenderLogVO> voList = CommonUtils.convertBeanList(creditTenderLogList, CreditTenderLogVO.class);
            response.setResultList(voList);
        }
        return response;
    }


    /**
     * 获取银行充值记录
     * @return
     */
    @RequestMapping(value = "/selectBankWithdrawList")
    public AccountwithdrawResponse selectBankWithdrawList(){
        logger.info("selectBankWithdrawList...");
        AccountwithdrawResponse response = new AccountwithdrawResponse();
        List<Accountwithdraw> withdrawList=bankWithdrawService.selectBankWithdrawList();
        if (CollectionUtils.isNotEmpty(withdrawList)){
            List<AccountWithdrawVO> voList = CommonUtils.convertBeanList(withdrawList, AccountWithdrawVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value = "/updateBankWithdraw")
    public boolean updateBankWithdraw(@RequestBody AccountVO accountVO){
    	logger.info("updateBankWithdraw...");
    	int count = bankWithdrawService.updateBankWithdraw(accountVO);
    	if(count>0) {
    		return true;
    	}else {
    		return false;
    	}
    }


    @PostMapping(value = "/updateCreditTenderLog")
    public Boolean updateCreditTenderLog(@RequestBody CreditTenderLogVO creditTenderLog){
        logger.info("updateCreditTenderLog...");
        int count = bankCreditTenderService.updateCreditTenderLog(creditTenderLog);
        if(count>0) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 同步回调收到后,根据logOrderId检索投资记录表
     * @param logOrderId
     * @return
     */
    @GetMapping(value = "/selectCreditTenderLogByOrderId")
    public CreditTenderLogResponse selectCreditTenderLogByOrderId(@PathVariable String logOrderId){
        logger.info("selectCreditTenderLogByOrderId...");
        CreditTenderLogResponse response = new CreditTenderLogResponse();
        CreditTenderLog creditTenderLog =bankCreditTenderService.selectCreditTenderLogByOrderId(logOrderId);
        if(null != creditTenderLog){
            response.setResult(CommonUtils.convertBean(creditTenderLog,CreditTenderLogVO.class));
        }
        return response;
    }


    @GetMapping("/selectByOrderIdAndUserId/{assignOrderId}/{userId}")
    public CreditTenderLogResponse selectByOrderIdAndUserId(@PathVariable String assignOrderId,@PathVariable Integer userId){
        CreditTenderLogResponse response = new CreditTenderLogResponse();
        List<CreditTenderLog> creditTenderLogs=bankCreditTenderService.selectByOrderIdAndUserId(assignOrderId,userId);
        if(CollectionUtils.isNotEmpty(creditTenderLogs)){
            List<CreditTenderLogVO> voList = CommonUtils.convertBeanList(creditTenderLogs, CreditTenderLogVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 刪除
     * @param assignOrderId
     * @param userId
     * @return
     */
    @GetMapping("/deleteByOrderIdAndUserId/{assignOrderId}/{userId}")
    public Boolean deleteByOrderIdAndUserId(@PathVariable String assignOrderId,@PathVariable Integer userId){
        int count=bankCreditTenderService.deleteByOrderIdAndUserId(assignOrderId,userId);
        if(count>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 用户提现回调方法
     * @param para
     * @return
     */
    @PostMapping("/handlerAfterCash")
    public Boolean handlerAfterCash(@RequestBody JSONObject para){
        Boolean ret = true;
        try {
            ret = bankWithdrawService.updateHandlerAfterCash(para);
        }catch (Exception e){
            ret = false;
        }
        return ret;

    }

}