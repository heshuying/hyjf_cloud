package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.AccountTradeResponse;
import com.hyjf.am.response.trade.AssetManageResponse;
import com.hyjf.am.response.trade.TenderAgreementResponse;
import com.hyjf.am.response.trade.TenderDetailResponse;
import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserRechargeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserWithdrawListCustomizeVO;
import com.hyjf.cs.trade.client.TradeDetailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author pangchengchao
 * @version TradeDetailClientImpl, v0.1 2018/6/27 10:44
 */
@Service
public class TradeDetailClientImpl implements TradeDetailClient {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<AccountTradeVO> selectTradeTypes() {
        String url = "http://AM-TRADE/am-trade/accounttrade/selectTradeTypes";
        AccountTradeResponse response = restTemplate.getForEntity(url,AccountTradeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int countUserTradeRecordTotal(TradeDetailBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/tradedetail/countUserTradeRecordTotal";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();
        if (response != null) {
            return response.getUserTradesCount();
        }
        return 0;
    }

    @Override
    public List<WebUserTradeListCustomizeVO> searchUserTradeList(TradeDetailBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/tradedetail/searchUserTradeList";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();

        if (response != null) {
            return response.getUserTrades();
        }
        return null;
    }

    @Override
    public int countUserRechargeRecordTotal(TradeDetailBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/tradedetail/countUserRechargeRecordTotal";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();
        if (response != null) {
            return response.getRechargeListCount();
        }
        return 0;
    }

    @Override
    public List<WebUserRechargeListCustomizeVO> searchUserRechargeList(TradeDetailBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/tradedetail/searchUserRechargeList";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();

        if (response != null) {
            return response.getRechargeList();
        }
        return null;
    }

    @Override
    public int countUserWithdrawRecordTotal(TradeDetailBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/tradedetail/countUserWithdrawRecordTotal";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();
        if (response != null) {
            return response.getWithdrawListCount();
        }
        return 0;
    }

    @Override
    public List<WebUserWithdrawListCustomizeVO> searchUserWithdrawList(TradeDetailBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/tradedetail/searchUserWithdrawList";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();

        if (response != null) {
            return response.getWithdrawList();
        }
        return null;
    }
}
