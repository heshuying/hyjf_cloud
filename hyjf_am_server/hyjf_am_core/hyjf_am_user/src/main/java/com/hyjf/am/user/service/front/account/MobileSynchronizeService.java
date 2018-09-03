/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.account;

import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.user.dao.model.auto.AccountMobileSynch;
import com.hyjf.am.user.service.BaseService;

import java.util.List;

/**
 * @author wangjun
 * @version MobileSynchronizeService, v0.1 2018/6/22 11:39
 */
public interface MobileSynchronizeService extends BaseService {

    /**
     * 查询未同步数据
     *
     * @return
     */
    List<AccountMobileSynch> searchAccountMobileSynch(String flag);

    /**
     * 更新银行卡号手机号同步表
     *
     * @param accountMobileSynchRequest
     * @return
     */
    boolean updateAccountMobileSynch(AccountMobileSynchRequest accountMobileSynchRequest);

    /**
     * 同步手机号
     *
     * @param accountMobileSynchRequest
     * @return
     */
    boolean updateMobileSynch(AccountMobileSynchRequest accountMobileSynchRequest);

}