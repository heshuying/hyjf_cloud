package com.hyjf.cs.user.service.bankopen.impl;

import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.bankopen.CrmRepairService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangjun
 */

@Service
public class CrmRepairServiceImpl extends BaseUserServiceImpl implements CrmRepairService {

    @Autowired
    private AmUserClient amUserClient;

    /**
     * 修复CRM开户数据
     */
    @Override
    public void getBankOpenAccountForCrmRepair() {
        amUserClient.getBankOpenAccountForCrmRepair();
    }
}
