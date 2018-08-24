/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.admin.BankCardExceptionRequest;
import com.hyjf.am.user.dao.model.customize.AdminBankCardExceptionCustomize;

/**
 * @author: sunpeikai
 * @version: AdminBankCardExceptionCustomizeMapper, v0.1 2018/8/14 15:20
 */
public interface AdminBankCardExceptionCustomizeMapper {

    /**
     * 根据条件查询银行卡个数
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer queryAccountBankCount(BankCardExceptionRequest request);

    /**
     * 根据条件查询银行卡列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AdminBankCardExceptionCustomize> queryAccountBankList(BankCardExceptionRequest request);
}
