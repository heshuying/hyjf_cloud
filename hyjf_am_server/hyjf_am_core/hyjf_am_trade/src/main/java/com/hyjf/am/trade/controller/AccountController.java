/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.response.trade.BankMerchantAccountListResponse;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.service.AccountService;
import com.hyjf.am.vo.trade.account.AccountVO;

/**
 * @author ${yaoy}
 * @version AccountController, v0.1 2018/6/14 16:59
 */
@RestController
@RequestMapping("/am-trade/account")
public class AccountController extends BaseController {

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
    public AccountResponse updateOfRTBLoansTender(@RequestBody AccountVO accountVO) {
        AccountResponse response = new AccountResponse();
        if (accountVO != null) {
            Account account = new Account();
            BeanUtils.copyProperties(accountVO,account);
            int updateFlag = accountService.updateOfRTBLoansTender(account);
            response.setUpdateFlag(updateFlag);
        }
        return response;
    }

    @PostMapping("/updateOfPlanRepayAccount")
    public Integer updateOfPlanRepayAccount(@RequestBody AccountVO accountVO){
        return this.accountService.updateOfPlanRepayAccount(accountVO);
    }

    /**
     * @Author walter.limeng
     * @Description  更新用户计划账户
     * @Date 10:37 2018/7/18
     * @Param accountVO
     * @return
     */
    @PostMapping("/updateofrepaycouponhjh")
    public AccountResponse updateOfRepayCouponHjh(@RequestBody AccountVO accountVO) {
        AccountResponse response = new AccountResponse();
        if (accountVO != null) {
            int updateFlag = accountService.updateOfRepayCouponHjh(accountVO);
            response.setUpdateFlag(updateFlag);
        }
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  根据nid和trade查询
     * @Date 11:33 2018/7/18
     * @Param nid
     * @Param trade
     * @return
     */
    @GetMapping("/countaccountweblist/{nid}/{trade}")
    public AccountWebListResponse countAccountWebList(@PathVariable String nid,@PathVariable String trade) {
        AccountWebListResponse response = new AccountWebListResponse();
        Integer count = accountService.countAccountWebList(nid,trade);
        response.setRecordTotal(count);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  新增accountWebList
     * @Date 14:08 2018/7/18
     * @Param
     * @return
     */
    @PostMapping("/insertaccountweblist")
    public AccountWebListResponse insertAccountWebList(@RequestBody AccountWebListVO accountWebList) {
        AccountWebListResponse response = new AccountWebListResponse();
        Integer count = accountService.insertAccountWebList(accountWebList);
        response.setRecordTotal(count);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  根据accountCode查询红包账户
     * @Date 14:16 2018/7/18
     * @Param accountCode
     * @return
     */
    @GetMapping("/getbankmerchantaccount/{accountCode}")
    public BankMerchantAccountResponse getBankMerchantAccount(@PathVariable String accountCode) {
        BankMerchantAccountResponse response = new BankMerchantAccountResponse();
        BankMerchantAccountVO bankMerchantAccount = accountService.getBankMerchantAccount(accountCode);
        response.setResult(bankMerchantAccount);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  更新BankMerchatAccount
     * @Date 14:21 2018/7/18
     * @Param bankMerchantAccountVO
     * @return
     */
    @PostMapping("/getbankmerchantaccount")
    public BankMerchantAccountResponse updateBankMerchatAccount(@RequestBody BankMerchantAccountVO bankMerchantAccountVO) {
        BankMerchantAccountResponse response = new BankMerchantAccountResponse();
        Integer flag = accountService.updateBankMerchatAccount(bankMerchantAccountVO);
        response.setRecordTotal(flag);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  新增BankMerchantAccountList对象
     * @Date 14:27 2018/7/18
     * @Param 新增BankMerchantAccountList对象
     * @return
     */
    @PostMapping("/insertbankmerchantaccountlist")
    public BankMerchantAccountListResponse insertBankMerchantAccountList(@RequestBody BankMerchantAccountListVO bankMerchantAccountList) {
        BankMerchantAccountListResponse response = new BankMerchantAccountListResponse();
        Integer flag = accountService.insertBankMerchantAccountList(bankMerchantAccountList);
        response.setFlag(flag);
        return response;
    }
}
