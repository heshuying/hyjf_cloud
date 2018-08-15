/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.exception;

import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.user.dao.model.auto.AccountMobileSynch;
import com.hyjf.am.user.service.BaseService;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountMobileSynchService, v0.1 2018/8/15 14:13
 */
public interface AccountMobileSynchService extends BaseService {

    /**
     * 线下修改信息同步查询列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getModifyInfoCount(AccountMobileSynchRequest request);

    /**
     * 线下修改信息同步查询列表list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AccountMobileSynch> searchModifyInfoList(AccountMobileSynchRequest request);

    /**
     * 添加信息
     * @auth sunpeikai
     * @param
     * @return
     */
    int insertAccountMobileSynch(AccountMobileSynchRequest request);

    /**
     * 根据主键id删除一条信息
     * @auth sunpeikai
     * @param
     * @return
     */
    int deleteAccountMobileSynch(AccountMobileSynchRequest request);
}
