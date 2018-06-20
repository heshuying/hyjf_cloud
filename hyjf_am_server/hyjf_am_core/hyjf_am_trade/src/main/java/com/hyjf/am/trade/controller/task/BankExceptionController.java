package com.hyjf.am.trade.controller.task;

import java.util.List;

import com.hyjf.am.response.trade.CreditTenderLogResponse;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLog;
import com.hyjf.am.trade.service.task.BankCreditTenderService;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    

}