/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.BankAccountManageClient;
import com.hyjf.admin.service.BankAccountManageService;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageServiceImpl, v0.1 2018/6/29 11:54
 */
@Service
public class BankAccountManageServiceImpl implements BankAccountManageService {

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
}
