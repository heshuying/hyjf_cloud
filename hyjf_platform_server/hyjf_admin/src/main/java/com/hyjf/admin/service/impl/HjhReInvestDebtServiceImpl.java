package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.HjhReInvestDebtClient;
import com.hyjf.admin.service.HjhReInvestDebtService;
import com.hyjf.am.response.admin.HjhReInvestDebtResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author : huanghui
 */
@Service
public class HjhReInvestDebtServiceImpl implements HjhReInvestDebtService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public HjhReInvestDebtResponse hjhReInvestDebtList(HjhReInvestDebtRequest request) {
        HjhReInvestDebtResponse response = amTradeClient.hjhReInvestDebtList(request);
        return response;
    }
}
