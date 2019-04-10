package com.hyjf.admin.service.screendata.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.screendata.BorrowRepayDataService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.RepayResponse;
import com.hyjf.am.vo.trade.RepaymentPlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisheng
 * @version BorrowRepayDataServiceImpl, v0.1 2019/3/20 14:48
 */
@Service
public class BorrowRepayDataServiceImpl implements BorrowRepayDataService {
    @Autowired
    AmAdminClient adminClient;
    @Override
    public RepayResponse findRepayUser(Integer startTime, Integer endTime, Integer currPage, Integer pageSize) {
        return adminClient.findRepayUser(startTime,endTime,currPage,pageSize);
    }

    @Override
    public void addRepayUserList(List<RepaymentPlanVO> resultList) {
        adminClient.addRepayUserList(resultList);
    }

    @Override
    public IntegerResponse countRepayUserList() {
        return adminClient.countRepayUserList();
    }

}
