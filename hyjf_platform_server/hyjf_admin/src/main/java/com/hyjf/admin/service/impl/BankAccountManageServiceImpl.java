/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.BankAccountManageClient;
import com.hyjf.admin.controller.LoginController;
import com.hyjf.admin.service.BankAccountManageService;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageServiceImpl, v0.1 2018/6/29 11:54
 */
@Service
public class BankAccountManageServiceImpl implements BankAccountManageService {

    @Value("${hyjf.bank.instcode}")
    private String bankInstCode;

    @Value("${hyjf.bank.bankcode}")
    private String bankBankCode;

    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    BankAccountManageClient bankAccountManageClient;

    @Override
    public Integer queryAccountCount(BankAccountManageRequest bankAccountManageRequest) {
        return this.bankAccountManageClient.queryAccountCount(bankAccountManageRequest);
    }

    @Override
    public List<BankAccountManageCustomizeVO> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest) {
        return this.bankAccountManageClient.queryAccountInfos(bankAccountManageRequest);
    }

    @Override
    public List<BankAccountManageCustomizeVO> queryAccountDetails(BankAccountManageRequest bankAccountManageRequest) {
        return this.bankAccountManageClient.queryAccountDetails(bankAccountManageRequest);
    }

    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        return this.bankAccountManageClient.getBankOpenAccount(userId);
    }

    @Override
    public Integer updateAccount(Integer userId, BigDecimal balance, BigDecimal frost) {
        AccountVO accountVO = new AccountVO();
        accountVO.setUserId(userId);
        accountVO.setBalance(balance);
        accountVO.setFrost(frost);
        return this.bankAccountManageClient.updateAccount(accountVO);
    }

    @Override
    public String updateAccountCheck(Integer userId, String startTime, String endTime) {
        return this.bankAccountManageClient.updateAccountCheck(userId,startTime,endTime);
    }

}
