package com.hyjf.cs.market.service.impl;

import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.service.DataSearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 查詢千乐列表数据
     * @param dataSearchRequest
     * @return
     */
    @Override
    public DataSearchCustomizeResponse findDataList(DataSearchRequest dataSearchRequest) {
        DataSearchCustomizeResponse dataSearchCustomizeResponse = new DataSearchCustomizeResponse();
        List<Integer> qianleUser = amUserClient.getQianleUser();
        dataSearchRequest.setUserIds(qianleUser);
        String type = dataSearchRequest.getType();
        if (StringUtils.equals(type,"1")) {
            dataSearchCustomizeResponse= amTradeClient.queryQianleList(dataSearchRequest);
        }else  if (StringUtils.equals(type,"2")) {
            dataSearchCustomizeResponse = amTradeClient.queryPlanList(dataSearchRequest);
        }else  if (StringUtils.equals(type,"3")) {
             dataSearchCustomizeResponse = amTradeClient.querySanList(dataSearchRequest);
        }
        return dataSearchCustomizeResponse;
    }





}


