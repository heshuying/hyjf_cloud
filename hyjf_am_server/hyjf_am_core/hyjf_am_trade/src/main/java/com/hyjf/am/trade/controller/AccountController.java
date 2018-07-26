/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.response.trade.BankMerchantAccountListResponse;
import com.hyjf.am.response.trade.account.AccountResponse;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.service.AccountService;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.common.util.CommonUtils;

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
     * userIds范围查询
     * @param
     * @return
     */
    @PostMapping("/getAccountByUserIds")
    public AccountResponse getAccountByUserIds(@RequestBody List<Integer> userIds) {
        AccountResponse response = new AccountResponse();
        List<Account> accounts = accountService.getAccountByUserIds(userIds);
        if (CollectionUtils.isNotEmpty(accounts)) {
            response.setResultList(CommonUtils.convertBeanList(accounts,AccountVO.class));
        }
        return response;
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

    /**
     * @Author walter.limeng
     * @Description  更新account用户散标信息
     * @Date 17:17 2018/7/18
     * @Param AccountVO
     * @return
     */
    @PostMapping("/updateofrepaytender")
    public AccountResponse updateOfRepayTender(@RequestBody AccountVO accountVO) {
        AccountResponse response = new AccountResponse();
        if (accountVO != null) {
            int updateFlag = accountService.updateOfRepayTender(accountVO);
            response.setUpdateFlag(updateFlag);
        }
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  更新用户散标账户
     * @Date 18:33 2018/7/18
     * @Param AccountVO
     * @return
     */
    @PostMapping("/updateofloanstender")
    public AccountResponse updateOfLoansTender(@RequestBody AccountVO accountVO) {
        AccountResponse response = new AccountResponse();
        if (accountVO != null) {
            int updateFlag = accountService.updateOfLoansTender(accountVO);
            response.setUpdateFlag(updateFlag);
        }
        return response;
    }
}
