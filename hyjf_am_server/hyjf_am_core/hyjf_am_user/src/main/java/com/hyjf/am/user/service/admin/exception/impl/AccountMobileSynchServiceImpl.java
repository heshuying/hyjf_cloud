/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.exception.impl;

import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.user.dao.model.auto.AccountMobileSynch;
import com.hyjf.am.user.service.admin.exception.AccountMobileSynchService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountMobileSynchServiceImpl, v0.1 2018/8/15 14:14
 */
@Service
public class AccountMobileSynchServiceImpl extends BaseServiceImpl implements AccountMobileSynchService {

    /**
     * 线下修改信息同步查询列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getModifyInfoCount(AccountMobileSynchRequest request) {
        return 0;
    }

    /**
     * 线下修改信息同步查询列表list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AccountMobileSynch> searchModifyInfoList(AccountMobileSynchRequest request) {
        return null;
    }
}
