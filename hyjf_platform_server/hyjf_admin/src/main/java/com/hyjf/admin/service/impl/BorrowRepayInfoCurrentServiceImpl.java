package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.BorrowRepayInfoCurrentService;
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
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowRepayInfoCurrentResponse getRepayInfoCurrentData(String borrowNid) {
        BorrowRepayInfoCurrentRequest requestBean = new BorrowRepayInfoCurrentRequest();
        requestBean.setBorrowNid(borrowNid);
        return amAdminClient.getRepayInfoCurrentData(requestBean);
    }
}
