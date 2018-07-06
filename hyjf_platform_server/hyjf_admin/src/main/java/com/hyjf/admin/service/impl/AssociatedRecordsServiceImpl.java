/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.AssociatedRecordsService;
import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.vo.admin.AssociatedRecordListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AssociatedRecordsServiceImpl, v0.1 2018/7/5 14:25
 */
@Service
public class AssociatedRecordsServiceImpl extends BaseServiceImpl implements AssociatedRecordsService {

    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 根据筛选条件查询数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getAssociatedRecordsCount(AssociatedRecordListRequest request) {
        return amTradeClient.getAssociatedRecordsCount(request);
    }

    /**
     * 根据筛选条件查询关联记录list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<AssociatedRecordListVo> getAssociatedRecordList(AssociatedRecordListRequest request) {
        return amTradeClient.getAssociatedRecordList(request);
    }
}
