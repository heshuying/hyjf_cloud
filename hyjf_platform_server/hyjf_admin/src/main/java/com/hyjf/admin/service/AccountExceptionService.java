/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.admin.AccountExceptionRequest;
import com.hyjf.am.vo.admin.AccountExceptionVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountExceptionService, v0.1 2018/7/11 15:13
 */
public interface AccountExceptionService {

    /**
     * 根据筛选条件查询汇付对账count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getAccountExceptionCount(AccountExceptionRequest request);

    /**
     * 根据筛选条件查询汇付对账列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<AccountExceptionVO> searchAccountExceptionList(AccountExceptionRequest request);

    /**
     * 根据id更新账户信息
     * @auth sunpeikai
     * @param id 主键
     * @return
     */
    void syncAccount(Integer id);
}
