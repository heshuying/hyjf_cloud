package com.hyjf.am.borrow.controller;

import com.hyjf.am.borrow.dao.model.auto.*;
import com.hyjf.am.borrow.service.AccountService;
import com.hyjf.am.borrow.service.RechargeService;
import com.hyjf.am.user.dao.model.auto.Users;
import com.hyjf.am.user.dao.model.auto.UsersInfo;
import com.hyjf.am.user.dao.model.auto.UsersInfoExample;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/am-borrow/recharge")
public class RechargeController {
    private static Logger logger = LoggerFactory.getLogger(RechargeController.class);

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    AccountService accountService;

    /**
     * 根据用户Id检索用户银行卡信息
     * @param userId
     * @return
     */
    @RequestMapping("/selectByUserId/{userId}")
    public BankCard selectByUserId (@PathVariable Integer userId){
        BankCard bankCard = rechargeService.selectBankCardByUserId(userId);
        if(null == bankCard){
            bankCard = new BankCard();
        }
        return bankCard;
    }

    /**
     * 获取银行卡配置信息
     * @param bankId
     * @return
     */
    @RequestMapping("/getBanksConfigByBankId/{bankId}")
    public BanksConfig getBanksConfigByBankId(@PathVariable Integer bankId){
        BanksConfig bankConfig = rechargeService.getBanksConfigByBankId(bankId);
        if(null == bankConfig){
            bankConfig = new BanksConfig();
        }
        return bankConfig;
    }

    /**
     *
     * @param userId
     * @return
     */
    @RequestMapping("/getAccount/{userId}")
    public Account getAccount(@PathVariable Integer userId){
        Account account = accountService.getAccount(userId);
        if(null == account){
            account = new Account();
        }
        return account;
    }
    @RequestMapping("/selectByExample")
    public Account selectByExample(@RequestBody AccountExample example){
        Account account = accountService.selectByExample(example);
        if(null == account){
            account = new Account();
        }
        return account;
    }


    /**
     * 根据用户ID查询企业用户信息
     * @param userId
     * @return
     */
    @RequestMapping("/getCorpOpenAccountRecord/{userId}")
    public CorpOpenAccountRecord getCorpOpenAccountRecord(@PathVariable Integer userId){
        CorpOpenAccountRecord corpOpenAccountRecord= rechargeService.getCorpOpenAccountRecord(userId);
        if(null == corpOpenAccountRecord){
            corpOpenAccountRecord = new CorpOpenAccountRecord();
        }
        return corpOpenAccountRecord;
    }

    /**
     * 插入充值记录
     * @param bean
     * @return
     */
    @RequestMapping("/insertRechargeInfo")
    public int insertRechargeInfo(@RequestBody @Valid BankCallBean bean){
       Integer response = rechargeService.insertRechargeInfo(bean);
       if (response != null){
           return response;
       }
       return 0;
    }

    /**
     * 查询充值记录
     * @param example
     * @return
     */
    @RequestMapping("/selectByExample")
    public AccountRecharge selectByExample(@RequestBody AccountRechargeExample example){
        AccountRecharge accountRecharge = rechargeService.selectByExample(example);
        return accountRecharge;
    }

    @RequestMapping("/updateByExampleSelective")
    public int updateByExampleSelective(@RequestBody Map<String,Object> paramMap){

        AccountRecharge accountRecharge = (AccountRecharge) paramMap.get("accountRecharge");
        AccountRechargeExample accountRechargeExample = (AccountRechargeExample) paramMap.get("accountRechargeExample");
        int count = rechargeService.updateByExampleSelective(accountRecharge, accountRechargeExample);
        return count;
    }

    @RequestMapping("/updateBankRechargeSuccess")
    public int updateBankRechargeSuccess(@RequestBody Account newAccount){
        int isAccountUpdateFlag = rechargeService.updateBankRechargeSuccess(newAccount);
        return isAccountUpdateFlag;
    }

    /**
     * 插入交易明细
     * @param accountList
     * @return
     */
    @RequestMapping("/insertSelective")
    public int insertSelective(@RequestBody AccountList accountList){
        int isAccountListUpdateFlag = rechargeService.insertSelective(accountList);
        return isAccountListUpdateFlag;
    }

    @RequestMapping("/selectByPrimaryKey")
    public Users selectByPrimaryKey(@RequestBody Integer userId){
        Users users = rechargeService.selectByPrimaryKey(userId);
        return users;
    }

    @RequestMapping("/updateByPrimaryKeySelective")
    public void updateByPrimaryKeySelective(@RequestBody AccountRecharge accountRecharge){
        this.rechargeService.updateByPrimaryKeySelective(accountRecharge);
    }

    @RequestMapping("/getBankReturnCodeConfig")
    public BankReturnCodeConfig getBankReturnCodeConfig(BankReturnCodeConfigExample example){
        BankReturnCodeConfig retCodes = this.rechargeService.selectByExample(example);
        return retCodes;
    }

    @RequestMapping("/selectByExample")
    public UsersInfo selectByExample(UsersInfoExample example) {
        UsersInfo usersInfo = this.rechargeService.selectByExample(example);
        return usersInfo;

    }
}
