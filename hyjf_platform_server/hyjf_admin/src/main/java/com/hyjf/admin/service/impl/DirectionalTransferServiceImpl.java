/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.DirectionalTransferService;
import com.hyjf.am.resquest.admin.DirectionalTransferListRequest;
import com.hyjf.am.vo.admin.AccountDirectionalTransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定向转账service
 * @author: sunpeikai
 * @version: DirectionalTransferServiceImpl, v0.1 2018/7/4 16:18
 */
@Service
public class DirectionalTransferServiceImpl extends BaseServiceImpl implements DirectionalTransferService {

    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 查询条数
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getDirectionalTransferCount(DirectionalTransferListRequest request) {
        return amTradeClient.getDirectionalTransferCount(request);
    }

    /**
     * 查询定向转账列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<AccountDirectionalTransferVO> searchDirectionalTransferList(DirectionalTransferListRequest request) {
        return amTradeClient.searchDirectionalTransferList(request);
    }
}
