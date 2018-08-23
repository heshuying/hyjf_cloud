/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.client.BankAccountManageClient;
import com.hyjf.admin.controller.LoginController;
import com.hyjf.admin.service.BankAccountManageService;
import com.hyjf.am.response.admin.OADepartmentResponse;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
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
    AmUserClient amUserClient;
    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public Integer queryAccountCount(BankAccountManageRequest bankAccountManageRequest) {
        return this.amTradeClient.queryAccountCount(bankAccountManageRequest);
    }

    @Override
    public List<BankAccountManageCustomizeVO> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest) {
        return this.amTradeClient.queryAccountInfos(bankAccountManageRequest);
    }

    @Override
    public List<BankAccountManageCustomizeVO> queryAccountDetails(BankAccountManageRequest bankAccountManageRequest) {
        return this.amTradeClient.queryAccountDetails(bankAccountManageRequest);
    }

    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        return this.amUserClient.getBankOpenAccount(userId);
    }

    @Override
    public Integer updateAccount(Integer userId, BigDecimal balance, BigDecimal frost) {
        AccountVO accountVO = new AccountVO();
        accountVO.setUserId(userId);
        accountVO.setBalance(balance);
        accountVO.setFrost(frost);
        return this.amTradeClient.updateAccountManage(accountVO);
    }

    @Override
    public String updateAccountCheck(Integer userId, String startTime, String endTime) {
        return this.amTradeClient.updateAccountCheck(userId,startTime,endTime);
    }

    @Override
    public Integer selectAccountInfoCount(BankAccountManageRequest request) {
        return this.amTradeClient.queryAccountCount(request);
    }

    /**
     * 获取部门列表
     *
     * @return
     */
    @Override
    public List<OADepartmentCustomizeVO> queryDepartmentInfo() {
        // 实际未传任何参数
        HjhCommissionRequest form = new HjhCommissionRequest();
        OADepartmentResponse response = amTradeClient.getCrmDepartmentList(form);
        if(response != null){
            return response.getResultList();
        }
        return null;
    }
}
