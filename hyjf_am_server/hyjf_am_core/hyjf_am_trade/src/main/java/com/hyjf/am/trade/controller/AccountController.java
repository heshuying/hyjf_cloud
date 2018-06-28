/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.service.AccountService;
import com.hyjf.am.vo.trade.account.AccountVO;

/**
 * @author yaoy
 * @version AccountController, v0.1 2018/6/14 16:59
 * account表操作类
 */
@RestController
@RequestMapping("/am-trade/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/getAccountByUserId/{borrowUserId}")
    public AccountResponse getAccountByUserId(Integer borrowUserId) {
        AccountResponse response = new AccountResponse();
        Account account = accountService.getAccount(borrowUserId);
        if (account != null) {
            AccountVO accountVO = new AccountVO();
            BeanUtils.copyProperties(account,accountVO);
            response.setResult(accountVO);
        }
        return response;
    }

    @PostMapping("/updateOfRTBLoansTender")
    public int updateOfRTBLoansTender(@RequestBody AccountVO accountVO) {
        int result = 0;
        if (accountVO != null) {
            Account account = new Account();
            BeanUtils.copyProperties(accountVO,account);
            result = accountService.updateOfRTBLoansTender(account);
        }
        return result;
    }

    @PostMapping("/updateOfPlanRepayAccount")
    public Integer updateOfPlanRepayAccount(@RequestBody AccountVO accountVO){
        return this.accountService.updateOfPlanRepayAccount(accountVO);
    }
}
