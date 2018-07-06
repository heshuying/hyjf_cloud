/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.DirectionalTransferService;
import com.hyjf.am.resquest.admin.DirectionalTransferListRequest;
import com.hyjf.am.vo.admin.AccountDirectionalTransferVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: DirectionalTransferServiceImpl, v0.1 2018/7/4 16:18
 */
@Service
public class DirectionalTransferServiceImpl extends BaseServiceImpl implements DirectionalTransferService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public Integer getDirectionalTransferCount(DirectionalTransferListRequest request) {
        return amTradeClient.getDirectionalTransferCount(request);
    }

    @Override
    public List<AccountDirectionalTransferVO> searchDirectionalTransferList(DirectionalTransferListRequest request) {
        return amTradeClient.searchDirectionalTransferList(request);
    }
}
