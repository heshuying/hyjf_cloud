/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.AccountBankResponse;
import com.hyjf.am.user.dao.model.auto.AccountBank;
import com.hyjf.am.user.service.AccountBankService;
import com.hyjf.am.vo.user.AccountBankVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountBankController, v0.1 2018/7/23 16:13
 */
@RestController
@RequestMapping("/am-user/accountbank")
public class AccountBankController extends BaseController{

    @Autowired
    private AccountBankService accountBankService;

    @GetMapping(value = "/getBankCardByUserId/{userId}")
    public AccountBankResponse getBankCardByUserId(@PathVariable Integer userId){
        AccountBankResponse response = new AccountBankResponse();
        List<AccountBank> accountBankList = accountBankService.getBankCardByUserId(userId);
        if(!CollectionUtils.isEmpty(accountBankList)){
            List<AccountBankVO> accountBankVOList = CommonUtils.convertBeanList(accountBankList,AccountBankVO.class);
            response.setResultList(accountBankVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
