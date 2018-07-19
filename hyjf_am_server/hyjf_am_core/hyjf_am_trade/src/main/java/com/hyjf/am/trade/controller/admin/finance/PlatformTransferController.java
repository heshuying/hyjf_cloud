/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.response.admin.PlatformTransferResponse;
import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccount;
import com.hyjf.am.trade.service.admin.finance.PlatformTransferService;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PlatformTransferController, v0.1 2018/7/9 11:07
 */
@Api(value = "资金中心-转账管理-平台转账",description = "资金中心-转账管理-平台转账")
@RestController
@RequestMapping(value = "/am-trade/platformtransfer")
public class PlatformTransferController extends BaseController {

    @Autowired
    private PlatformTransferService platformTransferService;

    @ApiOperation(value = "平台转账-查询count",notes = "平台转账-查询count")
    @PostMapping(value = "/getplatformtransfercount")
    public Integer getPlatformTransferCount(@RequestBody PlatformTransferListRequest request){
        return platformTransferService.getPlatformTransferCount(request);
    }

    @ApiOperation(value = "平台转账-查询转账列表",notes = "平台转账-查询转账列表")
    @PostMapping(value = "/searchplatformtransferlist")
    public PlatformTransferResponse searchPlatformTransferList(@RequestBody PlatformTransferListRequest request){
        PlatformTransferResponse response = new PlatformTransferResponse();
        Integer count = platformTransferService.getPlatformTransferCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchPlatformTransferList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<AccountRecharge> accountRechargeList = platformTransferService.searchPlatformTransferList(request);
        if(!CollectionUtils.isEmpty(accountRechargeList)){
            List<AccountRechargeVO> accountRechargeVOList = CommonUtils.convertBeanList(accountRechargeList,AccountRechargeVO.class);
            response.setResultList(accountRechargeVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @ApiOperation(value = "平台转账-更新账户信息",notes = "平台转账-更新账户信息")
    @PostMapping(value = "/updateaccount")
    public Integer updateAccount(@RequestBody AccountVO accountVO){
        return platformTransferService.updateAccount(accountVO);
    }

    @ApiOperation(value = "平台转账-插入充值表",notes = "平台转账-插入充值表")
    @PostMapping(value = "/insertaccountrecharge")
    public Integer insertAccountRecharge(@RequestBody AccountRechargeVO accountRechargeVO){
        return platformTransferService.insertAccountRecharge(accountRechargeVO);
    }

    @ApiOperation(value = "平台转账-插入收支明细表",notes = "平台转账-插入收支明细表")
    @PostMapping(value = "/insertaccountlist")
    public Integer insertAccountList(@RequestBody AccountListVO accountListVO){
        return platformTransferService.insertAccountList(accountListVO);
    }


    @ApiOperation(value = "平台转账-插入网站收支",notes = "平台转账-插入网站收支")
    @PostMapping(value = "/insertaccountweblist")
    public Integer insertAccountWebList(@RequestBody AccountWebListVO accountWebListVO){
        return platformTransferService.insertAccountWebList(accountWebListVO);
    }

    @ApiOperation(value = "平台转账-根据账户id查询BankMerchantAccount",notes = "平台转账-根据账户id查询BankMerchantAccount")
    @GetMapping(value = "/searchbankmerchantaccount/{accountId}")
    public BankMerchantAccountResponse searchBankMerchantAccountByAccountId(@PathVariable Integer accountId){
        BankMerchantAccountResponse response = new BankMerchantAccountResponse();
        BankMerchantAccount bankMerchantAccount = platformTransferService.searchBankMerchantAccountByAccountId(accountId);
        if(null != bankMerchantAccount){
            BankMerchantAccountVO bankMerchantAccountVO = CommonUtils.convertBean(bankMerchantAccount,BankMerchantAccountVO.class);
            response.setResult(bankMerchantAccountVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }


    @ApiOperation(value = "平台转账-更新红包账户信息",notes = "平台转账-更新红包账户信息")
    @PostMapping(value = "/updatebankmerchantaccount")
    public Integer updateBankMerchantAccount(@RequestBody BankMerchantAccountVO bankMerchantAccountVO){
        return platformTransferService.updateBankMerchantAccount(bankMerchantAccountVO);
    }


    @ApiOperation(value = "平台转账-插入红包明细",notes = "平台转账-插入红包明细")
    @PostMapping(value = "/insertbankmerchantaccountlist")
    public Integer insertBankMerchantAccountList(@RequestBody BankMerchantAccountListVO bankMerchantAccountListVO){
        return platformTransferService.insertBankMerchantAccountList(bankMerchantAccountListVO);
    }
}
