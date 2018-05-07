package com.hyjf.am.borrow.controller;

import com.hyjf.am.borrow.dao.model.auto.Account;
import com.hyjf.am.borrow.dao.model.auto.AccountRecharge;
import com.hyjf.am.borrow.dao.model.auto.AccountRechargeExample;
import com.hyjf.am.borrow.dao.model.auto.BankCard;
import com.hyjf.am.borrow.service.AccountService;
import com.hyjf.am.borrow.service.RechargeService;
import com.hyjf.am.response.borrow.AccountRechargeResponse;
import com.hyjf.am.response.borrow.AccountResponse;
import com.hyjf.am.vo.borrow.AccountListVO;
import com.hyjf.am.vo.borrow.AccountRechargeVO;
import com.hyjf.am.vo.borrow.AccountVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(value = "充值接口")
@RestController
@RequestMapping("/am-borrow/recharge")
public class RechargeController {
    private static Logger logger = LoggerFactory.getLogger(RechargeController.class);

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    AccountService accountService;

    /**
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = " Account", notes = " Account")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编码", required = true, dataType = "Integer")
    })
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
     * 查询充值记录
     * @param example
     * @return
     */
    @ApiOperation(value = " 查询充值记录", notes = " 查询充值记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "orderId", required = true, dataType = "String")
    })
    @GetMapping("/selectByOrderId/{orderId}")
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
    @ApiOperation(value = " updateByExampleSelective", notes = " updateByExampleSelective")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramMap", value = "paramMap", required = true, dataType = "Map<String,Object>")
    })
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
    @ApiOperation(value = " updateBankRechargeSuccess", notes = " updateBankRechargeSuccess")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newAccount", value = "newAccount", required = true, dataType = "AccountVO")
    })
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
    @ApiOperation(value = " 插入交易明细", notes = " 插入交易明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountList", value = "accountList", required = true, dataType = "AccountListVO")
    })
    @PostMapping("/insertSelective")
    public int insertSelective(@RequestBody AccountListVO accountList){
        int isAccountListUpdateFlag = rechargeService.insertSelective(accountList);
        return isAccountListUpdateFlag;
    }

    /**
     *
     * @param accountRecharge
     */
    @ApiOperation(value = " 更新", notes = " 更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountRecharge", value = "accountRecharge", required = true, dataType = "AccountRechargeVO")
    })
    @PutMapping("/updateByPrimaryKeySelective")
    public void updateByPrimaryKeySelective(@RequestBody AccountRechargeVO accountRecharge){
        this.rechargeService.updateByPrimaryKeySelective(accountRecharge);
    }

    @GetMapping("/selectByOrdId/{ordId}")
    public int selectByOrdId(@PathVariable String ordId){
        int ret = this.rechargeService.selectByOrdId(ordId);
        return ret;
    }

    @PostMapping("/insertSelectiveBank")
    public int insertSelectiveBank(@RequestBody Map<String,Object> paramMap){
        BankCallBean bean = (BankCallBean) paramMap.get("bankCallBean");
        BankCard bankCard = (BankCard) paramMap.get("bankCard");
        int ret = rechargeService.insertSelective(bean,bankCard);
        return ret;
    }

    @PostMapping("/updateBanks")
    public boolean updateBanks(@RequestBody Map<String,Object> paramMap) {
        AccountRechargeVO accountRecharge = (AccountRechargeVO) paramMap.get("accountRecharge");
         BankCallBean bean = (BankCallBean) paramMap.get("bean");
         String ip = (String) paramMap.get("ip");
        boolean flag = rechargeService.updateBanks(accountRecharge,bean,ip);
        return flag;
    }
}
