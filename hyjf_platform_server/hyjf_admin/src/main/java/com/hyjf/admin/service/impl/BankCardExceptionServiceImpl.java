/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.service.BankCardExceptionService;
import com.hyjf.am.resquest.admin.BankCardExceptionRequest;
import com.hyjf.am.vo.admin.AdminBankCardExceptionCustomizeVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BankCardExceptionServiceImpl, v0.1 2018/8/14 14:02
 */
@Service
public class BankCardExceptionServiceImpl extends BaseAdminServiceImpl implements BankCardExceptionService {
    /**
     * 银行卡配置下拉框
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<BankConfigVO> searchBankConfig() {
        return amConfigClient.selectBankConfigList();
    }

    /**
     * 银行卡异常count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getBankCardExceptionCount(BankCardExceptionRequest request) {
        return amUserClient.getBankCardExceptionCount(request);
    }

    /**
     * 银行卡异常列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AdminBankCardExceptionCustomizeVO> searchBankCardExceptionList(BankCardExceptionRequest request) {
        return amUserClient.searchBankCardExceptionList(request);
    }
}
