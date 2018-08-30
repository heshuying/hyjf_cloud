/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.account.impl;

import com.hyjf.am.trade.dao.mapper.customize.AdminMerchantAccountCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.AdminMerchantAccountSumCustomize;
import com.hyjf.am.trade.service.admin.account.AdminMerchantAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version MerchantAccountServiceImpl, v0.1 2018/8/29 21:17
 */
@Service
public class AdminMerchantAccountServiceImpl implements AdminMerchantAccountService {

    @Autowired
    protected AdminMerchantAccountCustomizeMapper adminMerchantAccountCustomizeMapper;

    @Override
    public AdminMerchantAccountSumCustomize searchAccountSum() {
        return adminMerchantAccountCustomizeMapper.searchMerchantAccountSum();
    }
}
