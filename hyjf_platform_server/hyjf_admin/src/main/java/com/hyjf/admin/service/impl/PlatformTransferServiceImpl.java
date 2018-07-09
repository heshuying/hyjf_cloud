/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.PlatformTransferService;
import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PlatformTransferServiceImpl, v0.1 2018/7/9 10:29
 */
@Service
public class PlatformTransferServiceImpl extends BaseServiceImpl implements PlatformTransferService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;

    /**
     * 根据筛选条件查询数据count
     * @auth sunpeikai
     * @param request
     * @return
     */
    @Override
    public Integer getPlatformTransferCount(PlatformTransferListRequest request) {
        return amTradeClient.getPlatformTransferCount(request);
    }

    /**
     * 根据筛选条件查询平台转账list
     * @auth sunpeikai
     * @param request
     * @return
     */
    @Override
    public List<AccountRechargeVO> searchPlatformTransferList(PlatformTransferListRequest request) {
        List<AccountRechargeVO> accountRechargeVOList = amTradeClient.searchPlatformTransferList(request);
        String userIds = "";
        for(AccountRechargeVO accountRechargeVO:accountRechargeVOList){
            userIds += String.valueOf(accountRechargeVO.getUserId()) + ",";
        }
        userIds = userIds.substring(0,userIds.lastIndexOf(","));
        logger.info("userIds=====[{}]",userIds);
        return accountRechargeVOList;
    }
}
