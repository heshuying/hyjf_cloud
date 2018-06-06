/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.service.CallCenterBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangjun
 * @version CallCenterBankController, v0.1 2018/6/6 14:14
 */
@RestController
@RequestMapping("/am-user/callcenter")
public class CallCenterBankController {
    @Autowired
    CallCenterBankService callCenterBankService;
    private static final Logger logger = LoggerFactory.getLogger(CallCenterBankController.class);
    @RequestMapping("/getTiedCardForBank")
    public List<BankCard> getTiedCardOfAccountBank(@PathVariable Integer userId){
        return callCenterBankService.getTiedCardOfAccountBank(userId);
    }
}
