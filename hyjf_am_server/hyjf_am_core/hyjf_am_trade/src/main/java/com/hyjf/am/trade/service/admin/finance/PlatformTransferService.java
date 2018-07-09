/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PlatformTransferService, v0.1 2018/7/9 11:11
 */
public interface PlatformTransferService {
    /**
     * 根据筛选条件查询数据count
     * @auth sunpeikai
     * @param request
     * @return
     */
    Integer getPlatformTransferCount(PlatformTransferListRequest request);
    /**
     * 根据筛选条件查询平台转账list
     * @auth sunpeikai
     * @param request
     * @return
     */
    List<AccountRecharge> searchPlatformTransferList(PlatformTransferListRequest request);
}
