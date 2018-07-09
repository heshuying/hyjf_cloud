/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.vo.admin.AccountRechargeVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PlatformTransferService, v0.1 2018/7/9 10:29
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
    List<AccountRechargeVO> searchPlatformTransferList(PlatformTransferListRequest request);
}
