/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.BorrowDeleteService;
import com.hyjf.am.vo.admin.BorrowDeleteConfirmCustomizeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 标的删除
 * @author hesy
 */
@Service
public class BorrowDeleteServiceImpl implements BorrowDeleteService {

    Logger logger = LoggerFactory.getLogger(BorrowDeleteServiceImpl.class);

    @Autowired
    AmTradeClient amTradeClient;

    /**
     * 标的删除确认页面数据
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowDeleteConfirmCustomizeVO selectDeleteConfirm(String borrowNid) {
        return amTradeClient.selectDeleteConfirm(borrowNid);
    }
}
