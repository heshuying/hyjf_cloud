/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.service.AccountListService;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author ${yaoy}
 * @version AccountController, v0.1 2018/6/14 16:59
 */
@RestController
@RequestMapping("/am-trade/accountList")
public class AccountListController {

    @Autowired
    private AccountListService accountListService;

   
    /**
     * 保存accountList
     * @param accountListVO
     * @return
     */
    @PostMapping("/addAccountList")
    public boolean addAccountList(@RequestBody AccountListVO accountListVO){
    	int count=accountListService.addAccountList(CommonUtils.convertBean(accountListVO, AccountList.class));
    	if(count>0) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
}
