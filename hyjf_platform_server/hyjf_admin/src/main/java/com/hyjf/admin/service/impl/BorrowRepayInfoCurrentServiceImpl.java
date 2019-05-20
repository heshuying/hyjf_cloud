package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.BorrowRepayInfoCurrentService;
import com.hyjf.am.response.admin.BorrowRepayInfoCurrentExportResponse;
import com.hyjf.am.response.admin.BorrowRepayInfoCurrentResponse;
import com.hyjf.am.resquest.admin.BorrowRepayInfoCurrentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hesy
 */
@Service
public class BorrowRepayInfoCurrentServiceImpl implements BorrowRepayInfoCurrentService {
    @Autowired
    private AmAdminClient amAdminClient;

    /**
     * 获取当前债权还款明细数据
     */
    @Override
    public BorrowRepayInfoCurrentResponse getRepayInfoCurrentData(BorrowRepayInfoCurrentRequest requestBean) {
        return amAdminClient.getRepayInfoCurrentData(requestBean);
    }

    /**
     * 获取当前债权还款明细导出数据
     */
    @Override
    public BorrowRepayInfoCurrentExportResponse getRepayInfoCurrentExportData(BorrowRepayInfoCurrentRequest requestBean) {
        return amAdminClient.getRepayInfoCurrentExportData(requestBean);
    }

    /**
     * 获取当前债权还款明细导出总记录数
     */
    public Integer getRepayInfoCurrentExportCount(BorrowRepayInfoCurrentRequest requestBean) {
        return amAdminClient.getRepayInfoCurrentExportCount(requestBean);
    }
}
