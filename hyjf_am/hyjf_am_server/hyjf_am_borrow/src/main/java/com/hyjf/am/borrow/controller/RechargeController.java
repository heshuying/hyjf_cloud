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

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping("/selectAccountByUserId")
    public AccountResponse selectAccountByUserId(@PathVariable Integer userId){
        AccountExample accountExample = new AccountExample();
		AccountExample.Criteria accountCriteria = accountExample.createCriteria();
		accountCriteria.andUserIdEqualTo(userId);
        AccountResponse response = new AccountResponse();
        Account account = accountService.selectByExample(accountExample);
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
    @GetMapping("/selectByOrderId")
    public AccountRechargeResponse selectByOrderId(@PathVariable String orderId){
        AccountRechargeExample example = new AccountRechargeExample();
        example.createCriteria().andNidEqualTo(orderId);
        AccountRechargeResponse response = new AccountRechargeResponse();
        AccountRecharge accountRecharge = rechargeService.selectByExample(example);
        if (accountRecharge != null){
            AccountRechargeVO accountRechargeVO = new AccountRechargeVO();
            BeanUtils.copyProperties(accountRecharge,accountRechargeVO);
            response.setResult(accountRechargeVO);
        }
        return response;
    }

    /**
     *
     * @param paramMap
     * @return
     */
    @PostMapping("/updateByExampleSelective")
    public int updateByExampleSelective(@RequestBody Map<String,Object> paramMap){
        AccountRechargeVO accountRecharge = (AccountRechargeVO) paramMap.get("accountRecharge");
        String orderId = (String) paramMap.get("orderId");
        AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
        accountRechargeExample.createCriteria().andNidEqualTo(orderId).andStatusEqualTo(accountRecharge.getStatus());
        int count = rechargeService.updateByExampleSelective(accountRecharge, accountRechargeExample);
        return count;
    }

    /**
     *
     * @param newAccount
     * @return
     */
    @PostMapping("/updateBankRechargeSuccess")
    public int updateBankRechargeSuccess(@RequestBody AccountVO newAccount){
        int isAccountUpdateFlag = rechargeService.updateBankRechargeSuccess(newAccount);
        return isAccountUpdateFlag;
    }

    /**
     * 插入交易明细
     * @param accountList
     * @return
     */
    @PostMapping("/insertSelective")
    public int insertSelective(@RequestBody AccountListVO accountList){
        int isAccountListUpdateFlag = rechargeService.insertSelective(accountList);
        return isAccountListUpdateFlag;
    }

    /**
     *
     * @param accountRecharge
     */
    @PutMapping("/updateByPrimaryKeySelective")
    public void updateByPrimaryKeySelective(@RequestBody AccountRechargeVO accountRecharge){
        this.rechargeService.updateByPrimaryKeySelective(accountRecharge);
    }

    /**
     *
     * @param retCode
     * @return
     */
    @GetMapping("/getBankReturnCodeConfig")
    public BankReturnCodeConfigResponse getBankReturnCodeConfig(@PathVariable String retCode){
        BankReturnCodeConfigExample example = new BankReturnCodeConfigExample();
        example.createCriteria().andRetCodeEqualTo(retCode);
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
