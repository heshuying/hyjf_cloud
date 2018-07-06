/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.exception;

import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.user.service.admin.exception.BorrowRegistExceptionService;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionController, v0.1 2018/7/3 17:58
 */
@RestController
@RequestMapping("/am-user/borrow_regist_exception")
@Api(value = "异常中心-标的备案掉单")
public class BorrowRegistExceptionController {

    @Autowired
    private BorrowRegistExceptionService borrowRegistExceptionService;

    /**
     * 根据userId获取BankOpenAccount
     * @auth sunpeikai
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取BankOpenAccount", notes = "根据userId获取BankOpenAccount")
    @GetMapping(value = "/searchbankopenaccount/{userId}")
    public BankOpenAccountResponse searchBankOpenAccount(@PathVariable Integer userId){
        BankOpenAccountResponse response = new BankOpenAccountResponse();
        BankOpenAccountVO bankOpenAccountVO = borrowRegistExceptionService.searchBankOpenAccount(userId);
        if(null != bankOpenAccountVO){
            response.setResult(bankOpenAccountVO);
        }
        return response;
    }
}
