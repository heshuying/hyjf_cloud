/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.exception;

import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.admin.exception.BorrowRegistRepairService;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunpeikai
 * @version: BorrowRegistRepairController, v0.1 2018/7/3 17:58
 */
@RestController(value = "userBorrowRegistRepairController")
@RequestMapping("/am-user/borrow_regist_repair")
@Api(value = "异常中心-标的备案掉单",tags ="异常中心-标的备案掉单")
public class BorrowRegistRepairController extends BaseController {

    @Autowired
    private BorrowRegistRepairService borrowRegistRepairService;

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
        BankOpenAccountVO bankOpenAccountVO = borrowRegistRepairService.searchBankOpenAccount(userId);
        if(null != bankOpenAccountVO){
            response.setResult(bankOpenAccountVO);
        }
        return response;
    }
}
