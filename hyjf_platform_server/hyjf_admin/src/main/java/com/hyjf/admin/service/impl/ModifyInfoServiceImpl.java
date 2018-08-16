/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.service.ModifyInfoService;
import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.vo.user.AccountMobileSynchVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: ModifyInfoServiceImpl, v0.1 2018/8/15 13:45
 */
@Service
public class ModifyInfoServiceImpl extends BaseAdminServiceImpl implements ModifyInfoService {

    /**
     * 查询列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getModifyInfoCount(AccountMobileSynchRequest request) {
        return amUserClient.getModifyInfoCount(request);
    }

    /**
     * 查询列表list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AccountMobileSynchVO> searchModifyInfoList(AccountMobileSynchRequest request) {
        return amUserClient.searchModifyInfoList(request);
    }

    /**
     * 添加信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public Integer insertAccountMobileSynch(AccountMobileSynchRequest request) {
        return amUserClient.insertAccountMobileSynch(request);
    }

    /**
     * 根据主键id删除一条信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public Integer deleteAccountMobileSynch(AccountMobileSynchRequest request) {
        return amUserClient.deleteAccountMobileSynch(request);
    }
}
