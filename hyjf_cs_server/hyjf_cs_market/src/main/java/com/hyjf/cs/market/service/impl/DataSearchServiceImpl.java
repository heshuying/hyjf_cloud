package com.hyjf.cs.market.service.impl;

import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.service.DataSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisheng
 * @version DataSearchServiceImpl, v0.1 2018/8/21 9:39
 */
@Service
public class DataSearchServiceImpl implements DataSearchService {
    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    @Override
    public DataSearchCustomizeResponse findDataList(DataSearchRequest dataSearchRequest) {
        List<Integer> qianleUser = amUserClient.getQianleUser();
        dataSearchRequest.setUserIds(qianleUser);
        DataSearchCustomizeResponse dataSearchCustomizeResponse = amTradeClient.querySanList(dataSearchRequest);
        return dataSearchCustomizeResponse;
    }
}


