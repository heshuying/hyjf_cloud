/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.exception.impl;

import com.hyjf.am.resquest.admin.BankCardExceptionRequest;
import com.hyjf.am.user.dao.mapper.customize.AdminBankCardExceptionCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.AdminBankCardExceptionCustomize;
import com.hyjf.am.user.service.admin.exception.BankCardExceptionService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BankCardExceptionServiceImpl, v0.1 2018/8/14 15:03
 */
@Service
public class BankCardExceptionServiceImpl extends BaseServiceImpl implements BankCardExceptionService {

    @Autowired
    private AdminBankCardExceptionCustomizeMapper adminBankCardExceptionCustomizeMapper;

    /**
     * 银行卡异常count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getBankCardExceptionCount(BankCardExceptionRequest request) {
        return adminBankCardExceptionCustomizeMapper.queryAccountBankCount(request);
    }

    /**
     * 银行卡异常list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AdminBankCardExceptionCustomize> searchBankCardExceptionList(BankCardExceptionRequest request) {
        return adminBankCardExceptionCustomizeMapper.queryAccountBankList(request);
    }
}
