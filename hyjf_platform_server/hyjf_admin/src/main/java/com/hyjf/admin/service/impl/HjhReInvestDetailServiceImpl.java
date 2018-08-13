package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.HjhReInvestDetailClient;
import com.hyjf.admin.service.HjhReInvestDetailService;
import com.hyjf.am.response.admin.HjhReInvestDetailResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HjhReInvestDetailServiceImpl implements HjhReInvestDetailService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public Integer countHjhReInvestDetailTotal(HjhReInvestDetailRequest request) {
        return null;
    }

    @Override
    public HjhReInvestDetailResponse hjhReInvestDetailList(HjhReInvestDetailRequest request) {
        HjhReInvestDetailResponse recoList = amTradeClient.getHjhReInvestDetailList(request);
        return recoList;
    }
}
