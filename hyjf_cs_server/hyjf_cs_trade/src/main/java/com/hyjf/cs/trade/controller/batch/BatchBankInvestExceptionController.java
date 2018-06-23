package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.constants.BankWithdrawError;
import com.hyjf.cs.trade.service.BankCreditTenderService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 债转投资异常处理组合层controller
 * @author jun
 * @since 20180619
 */
@Controller
@RequestMapping(value = "/cs-trade/investexception")
public class BatchBankInvestExceptionController {

	Logger logger = LoggerFactory.getLogger(BatchBankInvestExceptionController.class);
	
	@Autowired
	private BankCreditTenderService bankCreditTenderExceptionService;
	/**
	 * 处理债转投资异常
	 * @return
	 */
	  @ApiOperation(value = "债转投资异常处理", notes = "债转投资异常处理")
	    @RequestMapping(value = "/handle")
	    public ApiResult<Object> handle(){
	        logger.info("债转投资异常处理start...");
	    	ApiResult<Object> result = new ApiResult<Object>();
	        result.setStatus(ApiResult.SUCCESS);
	        result.setStatusDesc(BankWithdrawError.BANK_WITHDRAW_EXCEPTION_HANDLE_SUCCESS.getMsg());
	        
	    	try {
	    		bankCreditTenderExceptionService.handle();
	        }catch (Exception e){
	            result.setStatus(ApiResult.FAIL);
	            result.setStatusDesc(BankWithdrawError.BANK_WITHDRAW_EXCEPTION_HANDLE_ERROR.getMsg());
	        }
	    	return result;
	    }
}
