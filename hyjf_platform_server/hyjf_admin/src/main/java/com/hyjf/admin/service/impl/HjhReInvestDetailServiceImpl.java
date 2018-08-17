package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.HjhReInvestDetailService;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资金计划 -> 复投原始标的 实现
 * @Author : huanghui
 */
@Service
public class HjhReInvestDetailServiceImpl implements HjhReInvestDetailService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public Integer countHjhReInvestDetailTotal(String data, String planNid) {
        return this.amTradeClient.getHjhReInvestDetailListCount(data, planNid);
    }

    @Override
    public List<HjhReInvestDetailVO> hjhReInvestDetailList(String data, String planNid) {
        List<HjhReInvestDetailVO> recoList = this.amTradeClient.getHjhReInvestDetailList(data, planNid);
        return recoList;
    }
}
