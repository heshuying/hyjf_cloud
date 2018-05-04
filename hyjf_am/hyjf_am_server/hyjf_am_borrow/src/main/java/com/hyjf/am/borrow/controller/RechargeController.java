package com.hyjf.am.borrow.controller;

import com.hyjf.am.borrow.dao.model.auto.*;
import com.hyjf.am.borrow.service.AccountService;
import com.hyjf.am.borrow.service.RechargeService;
import com.hyjf.am.response.borrow.*;
import com.hyjf.am.vo.borrow.*;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @GetMapping("/selectByUserId/{userId}")
    public BankCardResponse selectByUserId (@PathVariable Integer userId){
        BankCardResponse response = new BankCardResponse();
        BankCard bankCard = rechargeService.selectBankCardByUserId(userId);
        if(null != bankCard){
            BankCardVO bankCardVO = new BankCardVO();
            BeanUtils.copyProperties(bankCard, bankCardVO);
            response.setResult(bankCardVO);
        }
        return response;
    }

    /**
     * 获取银行卡配置信息
     * @param bankId
     * @return
     */
    @GetMapping("/getBanksConfigByBankId/{bankId}")
    public BanksConfigResponse getBanksConfigByBankId(@PathVariable Integer bankId){
        BanksConfigResponse response = new BanksConfigResponse();
        BanksConfig bankConfig = rechargeService.getBanksConfigByBankId(bankId);
        if(null != bankConfig){
            BanksConfigVO banksConfigVO = new BanksConfigVO();
            BeanUtils.copyProperties(bankConfig,banksConfigVO);
            response.setResult(banksConfigVO);
        }
        return response;
    }

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping("/getAccount/{userId}")
    public AccountResponse getAccount(@PathVariable Integer userId){
        AccountResponse response = new AccountResponse();
        Account account = accountService.getAccount(userId);
        if(null != account){
            AccountVO accountVO = new AccountVO();
            BeanUtils.copyProperties(account,accountVO);
            response.setResult(accountVO);
        }
        return response;
    }

    @PostMapping("/selectAccountByExample")
    public AccountResponse selectByExample(@RequestBody AccountExample example){
        AccountResponse response = new AccountResponse();
        Account account = accountService.selectByExample(example);
        if(null != account){
            AccountVO accountVO = new AccountVO();
            BeanUtils.copyProperties(account,accountVO);
            response.setResult(accountVO);
        }
        return response;
    }

    /**
     * 根据用户ID查询企业用户信息
     * @param userId
     * @return
     */
    @GetMapping("/getCorpOpenAccountRecord/{userId}")
    public CorpOpenAccountRecordResponse getCorpOpenAccountRecord(@PathVariable Integer userId){
        CorpOpenAccountRecordResponse response = new CorpOpenAccountRecordResponse();
        CorpOpenAccountRecord corpOpenAccountRecord= rechargeService.getCorpOpenAccountRecord(userId);
        if(null != corpOpenAccountRecord){
            CorpOpenAccountRecordVO corpOpenAccountRecordVO = new CorpOpenAccountRecordVO();
            BeanUtils.copyProperties(corpOpenAccountRecord,corpOpenAccountRecordVO);
            response.setResult(corpOpenAccountRecordVO);
        }
        return response;
    }

    /**
     * 插入充值记录
     * @param bean
     * @return
     */
    @PostMapping("/insertRechargeInfo")
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
    @PostMapping("/selectByExample")
    public AccountRechargeResponse selectByExample(@RequestBody AccountRechargeExample example){
        AccountRechargeResponse response = new AccountRechargeResponse();
        AccountRecharge accountRecharge = rechargeService.selectByExample(example);
        if (accountRecharge != null){
            AccountRechargeVO accountRechargeVO = new AccountRechargeVO();
            BeanUtils.copyProperties(accountRecharge,accountRechargeVO);
            response.setResult(accountRechargeVO);
        }
        return response;
    }

    @PostMapping("/updateByExampleSelective")
    public int updateByExampleSelective(@RequestBody Map<String,Object> paramMap){

        AccountRecharge accountRecharge = (AccountRecharge) paramMap.get("accountRecharge");
        AccountRechargeExample accountRechargeExample = (AccountRechargeExample) paramMap.get("accountRechargeExample");
        int count = rechargeService.updateByExampleSelective(accountRecharge, accountRechargeExample);
        return count;
    }

    @PostMapping("/updateBankRechargeSuccess")
    public int updateBankRechargeSuccess(@RequestBody Account newAccount){
        int isAccountUpdateFlag = rechargeService.updateBankRechargeSuccess(newAccount);
        return isAccountUpdateFlag;
    }

    /**
     * 插入交易明细
     * @param accountList
     * @return
     */
    @PostMapping("/insertSelective")
    public int insertSelective(@RequestBody AccountList accountList){
        int isAccountListUpdateFlag = rechargeService.insertSelective(accountList);
        return isAccountListUpdateFlag;
    }


    @PutMapping("/updateByPrimaryKeySelective")
    public void updateByPrimaryKeySelective(@RequestBody AccountRecharge accountRecharge){
        this.rechargeService.updateByPrimaryKeySelective(accountRecharge);
    }

    @PostMapping("/getBankReturnCodeConfig")
    public BankReturnCodeConfigResponse getBankReturnCodeConfig(BankReturnCodeConfigExample example){
        BankReturnCodeConfigResponse response = new BankReturnCodeConfigResponse();
        BankReturnCodeConfig retCodes = this.rechargeService.selectByExample(example);
        if(null != retCodes){
            BankReturnCodeConfigVO bankReturnCodeConfigVO = new BankReturnCodeConfigVO();
            BeanUtils.copyProperties(retCodes,bankReturnCodeConfigVO);
            response.setResult(bankReturnCodeConfigVO);
        }
        return response;
    }
}
