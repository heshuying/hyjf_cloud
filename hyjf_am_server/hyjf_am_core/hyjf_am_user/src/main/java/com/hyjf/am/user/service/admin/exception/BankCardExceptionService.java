/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.exception;

import com.hyjf.am.resquest.admin.BankCardExceptionRequest;
import com.hyjf.am.user.dao.model.customize.AdminBankCardExceptionCustomize;
import com.hyjf.am.user.service.BaseService;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BankCardExceptionService, v0.1 2018/8/14 15:02
 */
public interface BankCardExceptionService extends BaseService {

    /**
     * 银行卡异常count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getBankCardExceptionCount(BankCardExceptionRequest request);

    /**
     * 银行卡异常列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AdminBankCardExceptionCustomize> searchBankCardExceptionList(BankCardExceptionRequest request);
}
