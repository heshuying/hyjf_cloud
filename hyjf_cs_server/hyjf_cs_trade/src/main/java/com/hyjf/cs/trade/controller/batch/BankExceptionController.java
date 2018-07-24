package com.hyjf.cs.trade.controller.batch;

import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyjf.cs.trade.service.BankCreditTenderService;
import com.hyjf.cs.trade.service.BankTenderCancelExceptionService;
import com.hyjf.cs.trade.service.BankWithdrawService;
import com.hyjf.cs.trade.service.BatchBankInvestAllService;
import com.hyjf.cs.trade.service.BatchBankInvestService;

import org.slf4j.Logger;
/**
 * 提现,债转,投资等异常定时任务controller
 * @author jijun
 * @date 20180625
 */
@Controller
@RequestMapping(value = "/cs-trade/bankException")
public class BankExceptionController {

	Logger logger = LoggerFactory.getLogger(BankExceptionController.class);

	@Autowired
	private BankTenderCancelExceptionService bankTenderCancelExceptionService;

	@Autowired
	private BankWithdrawService bankWithdrawService;

	@Autowired
	private BankCreditTenderService bankCreditTenderExceptionService;

	@Autowired
	private BatchBankInvestService batchBankInvestService;

	@Autowired
	private BatchBankInvestAllService batchBankInvestAllService;

	/**
	 * 处理债转投资异常
	 * @return
	 */
	@ApiOperation(value = "投资撤销掉单异常处理", notes = "投资撤销掉单异常处理")
	@RequestMapping(value = "/bankTenderCancelExceptionHandle")
	public void bankTenderCancelExceptionHandle() {
		logger.info("投资撤销掉单异常处理start...");
		bankTenderCancelExceptionService.handle();
		logger.info("投资撤销掉单异常处理end...");
	}


	 /**
     * 提现掉单异常处理
     * @return
     */
    @ApiOperation(value = "提现掉单异常处理", notes = "提现掉单异常处理")
    @GetMapping(value = "/bankWithdrawExceptionHandle")
    public Boolean bankWithdrawExceptionHandle(){
         return bankWithdrawService.batchWithdraw();
    }
	
    
    /**
     * 处理债转投资掉单异常
     * @return
     */
    @ApiOperation(value = "债转投资掉单异常处理", notes = "债转投资掉单异常处理")
    @GetMapping(value = "/creditTenderExceptionHandle")
    public void creditTenderExceptionHandle() {
        logger.info("债转投资掉单异常处理start...");
        bankCreditTenderExceptionService.handle();
        logger.info("债转投资掉单异常处理end...");
    }

    
    /**
	 * 处理投资异步掉单异常
	 * @return
	 */
	@ApiOperation(value = "投资异步掉单异常处理", notes = "投资异步掉单异常处理")
	@RequestMapping(value = "/investExceptionHandle")
	public void investExceptionHandle() {
		logger.info("投资异步掉单异常处理start...");
		batchBankInvestService.handle();
		logger.info("投资异步掉单异常处理end...");
	}
	
	
	/**
	 * 投资全部掉单异常处理
	 */
	@ApiOperation(value = "投资异常全部掉单异常处理", notes = "投资异常全部掉单异常处理")
	@RequestMapping(value = "/investAllExceptionHandle")
	public void investAllExceptionHandle() {
		logger.info("投资异常全部掉单跑批任务开始start...");
		batchBankInvestAllService.updateTender();
		logger.info("投资异常全部掉单跑批任务结束end...");
	}
	
	
	
}

