/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.admin.DirectionalTransferListRequest;
import com.hyjf.am.vo.admin.AccountDirectionalTransferVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: DirectionalTransferService, v0.1 2018/7/4 16:18
 */
public interface DirectionalTransferService {
    /**
     * 查询条数
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer getDirectionalTransferCount(DirectionalTransferListRequest request);

    List<AccountDirectionalTransferVO> searchDirectionalTransferList(DirectionalTransferListRequest request);
}
