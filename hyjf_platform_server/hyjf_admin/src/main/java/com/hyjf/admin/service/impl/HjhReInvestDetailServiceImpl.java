package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.HjhReInvestDetailService;
import com.hyjf.am.response.admin.HjhReInvestDetailResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资金计划 -> 复投原始标的 实现
 * @Author : huanghui
 */
@Service
public class HjhReInvestDetailServiceImpl implements HjhReInvestDetailService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public HjhReInvestDetailResponse getHjhReInvestDetailList(HjhReInvestDetailRequest request) {
        HjhReInvestDetailResponse recoList = this.amTradeClient.getHjhReInvestDetailList(request);
        return recoList;
    }
}
