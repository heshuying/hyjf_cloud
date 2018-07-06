/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.DirectionalTransferListRequest;
import com.hyjf.am.vo.admin.AccountDirectionalTransferVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountDirectionalTransferClient, v0.1 2018/7/4 16:45
 */
public interface AccountDirectionalTransferClient {
    /**
     * 查询定向转账列表count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getDirectionalTransferCount(DirectionalTransferListRequest request);
    /**
     * 根据筛选条件查询list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<AccountDirectionalTransferVO> searchDirectionalTransferList(DirectionalTransferListRequest request);
}
