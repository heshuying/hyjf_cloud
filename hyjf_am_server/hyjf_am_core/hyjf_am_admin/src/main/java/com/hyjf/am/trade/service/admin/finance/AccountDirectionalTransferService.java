/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import java.util.List;

import com.hyjf.am.resquest.admin.DirectionalTransferListRequest;
import com.hyjf.am.vo.admin.AccountDirectionalTransferVO;

/**
 * @author: sunpeikai
 * @version: AccountDirectionalTransferService, v0.1 2018/7/4 16:56
 */
public interface AccountDirectionalTransferService {
    /**
     * 按照筛选条件查询数据条数
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
