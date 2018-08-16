/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.admin.BankCardExceptionRequest;
import com.hyjf.am.vo.admin.AdminBankCardExceptionCustomizeVO;
import com.hyjf.am.vo.trade.BankConfigVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BankCardExceptionService, v0.1 2018/8/14 14:01
 */
public interface BankCardExceptionService extends BaseAdminService {
    /**
     * 银行卡配置下拉框
     * @auth sunpeikai
     * @param
     * @return
     */
    List<BankConfigVO> searchBankConfig();

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
    List<AdminBankCardExceptionCustomizeVO> searchBankCardExceptionList(BankCardExceptionRequest request);

    /**
     * 更新银行卡
     * @auth sunpeikai
     * @param
     * @return
     */
    String updateAccountBankByUserId(Integer userId);
}
