/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.vo.user.AccountMobileSynchVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: ModifyInfoService, v0.1 2018/8/15 13:44
 */
public interface ModifyInfoService extends BaseAdminService {
    /**
     * 查询列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getModifyInfoCount(AccountMobileSynchRequest request);

    /**
     * 查询list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AccountMobileSynchVO> searchModifyInfoList(AccountMobileSynchRequest request);
}
