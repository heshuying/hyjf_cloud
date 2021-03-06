package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.TimingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yinhui
 * @Date: 2018/12/18 17:28
 * @Version 1.0
 */
@Service
public class TimingServiceImpl implements TimingService {

    @Autowired
    private AmTradeClient amClient;

    @Autowired
    private AmConfigClient amConfigClient;

    @Override
    public void noneSplitBorrow() {
        amClient.noneSplitBorrow();
    }

    @Override
    public void hjhBorrow() {
        amClient.hjhBorrow();
    }

    @Override
    public void splitBorrow() {
        amClient.splitBorrow();
    }

    @Override
    public void couponExpired() {
        amClient.couponExpired();
    }

    @Override
    public void dataInfo() {
        amClient.dataInfo();
    }

    @Override
    public void downloadRedFile() {
        amClient.downloadRedFile();
    }

    @Override
    public void holidays() {
        amConfigClient.holidays();
    }
}
