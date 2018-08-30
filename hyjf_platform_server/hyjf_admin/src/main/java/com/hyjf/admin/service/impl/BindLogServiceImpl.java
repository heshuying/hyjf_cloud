/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.BindLogService;
import com.hyjf.am.resquest.admin.BindLogListRequest;
import com.hyjf.am.vo.admin.BindLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BindLogServiceImpl, v0.1 2018/7/5 15:47
 */
@Service
public class BindLogServiceImpl extends BaseServiceImpl implements BindLogService {

    @Autowired
    private CsMessageClient csMessageClient;

    /**
     * 根据筛选条件查询绑定日志count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getBindLogCount(BindLogListRequest request) {
        return csMessageClient.getDirectionalTransferLogCount(request);
    }

    /**
     * 根据筛选条件查询绑定日志list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<BindLogVO> searchBindLogList(BindLogListRequest request) {
        return csMessageClient.searchDirectionalTransferLogList(request);
    }
}
