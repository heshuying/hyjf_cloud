package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.BankCreditTenderService;
import com.hyjf.cs.trade.service.batch.BankTenderCancelExceptionService;
import com.hyjf.cs.trade.service.batch.BatchBankInvestAllService;
import com.hyjf.cs.trade.service.batch.BatchBankInvestService;
import com.hyjf.cs.trade.service.withdraw.BankWithdrawService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 提现,债转,出借等异常定时任务controller
 * @author jijun
 * @date 20180625
 */
@ApiIgnore
@RestController
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
	 * 处理债转出借异常
	 * @return
	 */
	@ApiOperation(value = "出借撤销掉单异常处理", notes = "出借撤销掉单异常处理")
	@GetMapping(value = "/bankTenderCancelExceptionHandle")
	public void bankTenderCancelExceptionHandle() {
		logger.info("出借撤销掉单异常处理start...");
		bankTenderCancelExceptionService.handle();
		logger.info("出借撤销掉单异常处理end...");
	}


	 /**
     * 提现掉单异常处理
     * @return
     */
    @ApiOperation(value = "提现掉单异常处理", notes = "提现掉单异常处理")
    @GetMapping(value = "/bankWithdrawExceptionHandle")
    public void bankWithdrawExceptionHandle(){
         bankWithdrawService.batchWithdraw();
    }
	
    
    /**
     * 处理债转出借掉单异常
     * @return
     */
    @ApiOperation(value = "债转出借掉单异常处理", notes = "债转出借掉单异常处理")
    @GetMapping(value = "/creditTenderExceptionHandle")
    public String creditTenderExceptionHandle() {
        logger.info("债转出借掉单异常处理start...");
        bankCreditTenderExceptionService.handle();
        logger.info("债转出借掉单异常处理end...");
        return "SUCCESS";
    }

    
    /**
	 * 处理出借异步掉单异常
	 * @return
	 */
	@ApiOperation(value = "出借异步掉单异常处理", notes = "出借异步掉单异常处理")
	@GetMapping(value = "/investExceptionHandle")
	public void investExceptionHandle() {
		logger.info("出借异步掉单异常处理start...");
		batchBankInvestService.insertAuthCode();
		logger.info("出借异步掉单异常处理end...");
	}
	
	
	/**
	 * 出借全部掉单异常处理
	 */
	@ApiOperation(value = "出借异常全部掉单异常处理", notes = "出借异常全部掉单异常处理")
	@GetMapping(value = "/investAllExceptionHandle")
	public void investAllExceptionHandle() {
		logger.info("出借异常全部掉单跑批任务开始start...");
		batchBankInvestAllService.updateTender();
		logger.info("出借异常全部掉单跑批任务结束end...");
	}

	/**
	 * 投资全部掉单异常处理
	 */
	@ApiOperation(value = "江西银行充值掉单异常处理定时任务", notes = "江西银行充值掉单异常处理定时任务")
	@GetMapping(value = "/recharge")
	public void recharge() {
		logger.info("江西银行充值掉单异常处理定时任务start...");
		batchBankInvestAllService.recharge();
		logger.info("江西银行充值掉单异常处理定时任务end...");
	}
	
}

