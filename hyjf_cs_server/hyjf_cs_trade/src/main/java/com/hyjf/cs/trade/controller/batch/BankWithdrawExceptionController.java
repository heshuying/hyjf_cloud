package com.hyjf.cs.trade.controller.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.constants.BankWithdrawError;
import com.hyjf.cs.trade.service.BankWithdrawService;

import io.swagger.annotations.ApiOperation;

/**
 * 江西银行提现掉单异常处理controller
 * create by jijun 20180614
 */
@Controller
@RequestMapping(value = "/bankWithdrawException")
public class BankWithdrawExceptionController {

    Logger logger = LoggerFactory.getLogger(BankWithdrawExceptionController.class);

    @Autowired
    private BankWithdrawService bankWithdrawService;

    /**
     * 提现掉单异常处理
     * @return
     */
    @ApiOperation(value = "江西银行提现掉单异常处理", notes = "江西银行提现掉单异常处理")
    @RequestMapping(value = "/withdraw")
    public ApiResult<Object> withdraw(){
        logger.info("江西银行提现掉单异常处理");
    	ApiResult<Object> result = new ApiResult<Object>(); 
        result.setStatus(ApiResult.SUCCESS);
        result.setStatusDesc(BankWithdrawError.BANK_WITHDRAW_EXCEPTION_HANDLE_SUCCESS.getMsg());

    	try {
            bankWithdrawService.withdraw();
        }catch (Exception e){
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(BankWithdrawError.BANK_WITHDRAW_EXCEPTION_HANDLE_ERROR.getMsg());
        }

    	return result;
    }

}
