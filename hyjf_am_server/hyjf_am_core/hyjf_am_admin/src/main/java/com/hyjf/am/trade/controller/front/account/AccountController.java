/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.account;

import com.hyjf.am.response.admin.BankMerchantAccountInfoResponse;
import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.response.trade.BankMerchantAccountListResponse;
import com.hyjf.am.response.trade.account.AccountResponse;
import com.hyjf.am.response.trade.account.AccountWithdrawResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccount;
import com.hyjf.am.trade.service.front.account.AccountService;
import com.hyjf.am.vo.admin.BankMerchantAccountInfoVO;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author ${yaoy}
 * @version AccountController, v0.1 2018/6/14 16:59
 */
@RestController
@RequestMapping("/am-trade/account")
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    /**
     * 根据用户id查询账户信息
     * @param borrowUserId
     * @return
     */
    @GetMapping("/getAccountByUserId/{borrowUserId}")
    public AccountResponse getAccountByUserId(@PathVariable Integer borrowUserId) {
        AccountResponse response = new AccountResponse();
        Account account = accountService.getAccount(borrowUserId);
        if (account != null) {
            response.setResult(CommonUtils.convertBean(account,AccountVO.class));
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
     * 根据accountCode查询子账户信息
     * @param accountCode
     * @return
     */
    @GetMapping("/getBankMerchantAccountInfo/{accountCode}")
    public BankMerchantAccountInfoResponse getBankMerchantAccountInfo(@PathVariable String accountCode) {
        BankMerchantAccountInfoResponse response = new BankMerchantAccountInfoResponse();
        BankMerchantAccountInfoVO bankMerchantAccount = accountService.getBankMerchantAccountInfo(accountCode);
        response.setResult(bankMerchantAccount);
        return response;
    }

    /**
     * 更新子账户信息-已设置交易密码
     * @param accountId
     * @param flag
     * @return
     */
    @GetMapping("/updateBankMerchantAccountIsSetPassword/{accountId}/{flag}")
    public Integer updateBankMerchantAccountIsSetPassword(@PathVariable String accountId,@PathVariable Integer flag) {
        int count = accountService.updateBankMerchantAccountIsSetPassword(accountId,flag);
        return count;
    }

    @PostMapping(value = "/updateBankMerchantAccountByCode")
    public int updateBankMerchantAccount(@RequestBody BankMerchantAccount bankMerchantAccount) {
        int count = accountService.updateBankMerchantAccount(bankMerchantAccount);
        return count;
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
            try{
                logger.info("updateOfLoansTender更新用户账户开始，accountVO："+ accountVO.toString());
                int updateFlag = accountService.updateOfLoansTender(accountVO);
                response.setUpdateFlag(updateFlag);
            }catch (Exception e){
                logger.error("updateOfLoansTender更新用户账户异常",e);
            }
        }
        return response;
    }

    /**
     *根据订单号查询提现订单
     */
    @GetMapping("/queryAccountwithdrawByNid/{nid}/{userId}")
    public AccountWithdrawResponse queryAccountwithdrawByNid(@PathVariable String nid, @PathVariable Integer userId){
        AccountWithdrawResponse response=new AccountWithdrawResponse();
        AccountWithdraw result=accountService.queryAccountwithdrawByNid(nid,userId);
        if (result!=null){
            response.setResult(CommonUtils.convertBean(result,AccountWithdrawVO.class));
        }
        return response;
    }


    /**
     * 提现成功后,更新用户账户信息,提现记录
     * @param param
     * @return
     */
    @PostMapping("/updateAccountAfterWithdraw")
    public boolean updateAccountAfterWithdraw(@RequestBody Map<String, String> param){
       return accountService.updateAccountAfterWithdraw(param);
    }


    /**
     *充值失败,更新充值订单
     */
    @GetMapping("/updateAccountAfterWithdrawFail/{userId}/{nid}")
    public boolean updateAccountAfterWithdrawFail(@PathVariable Integer userId,@PathVariable String nid){
        boolean ret = false;
        try {
            ret = accountService.updateAccountAfterWithdrawFail(userId,nid);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ret;
    }


    @GetMapping("/testUpdate")
    public void testUpdate(){
        Account account = new Account();
        account.setUserId(88881234);
        account.setId(88881234);
        account.setBankTotal(new BigDecimal("0"));
        account.setBankAwait(new BigDecimal("0"));
        account.setBankWaitInterest(new BigDecimal("0"));
        accountService.updateOfRTBLoansTender(account);
    }



}
