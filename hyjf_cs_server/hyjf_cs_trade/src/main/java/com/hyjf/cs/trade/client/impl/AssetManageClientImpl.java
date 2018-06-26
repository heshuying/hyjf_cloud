package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.AssetManageResponse;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldPlanListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentPlanListCustomizeVO;
import com.hyjf.cs.trade.client.AssetManageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author pangchengchao
 * @version AssetManageClientImpl, v0.1 2018/6/24 11:14
 */
@Service
public class AssetManageClientImpl implements AssetManageClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<CurrentHoldObligatoryRightListCustomizeVO> selectCurrentHoldObligatoryRightList(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectCurrentHoldObligatoryRightList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();

        if (response != null) {
            return response.getCurrentHoldObligatoryRightList();
        }
        return null;
    }

    @Override
    public int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectCurrentHoldObligatoryRightListTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getCurrentHoldObligatoryRightCount();
        }
        return 0;
    }

    @Override
    public int selectRepaymentListTotal(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectRepaymentListTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getRepayMentCount();
        }
        return 0;
    }

    @Override
    public int countCreditRecordTotal(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/countCreditRecordTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getTenderCreditDetailCount();
        }
        return 0;
    }

    @Override
    public List<TenderAgreementVO> selectTenderAgreementByNid(String nid) {
        // TODO 待实现
        return null;
    }

    @Override
    public List<RepayMentListCustomizeVO> selectRepaymentList(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectRepaymentList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();

        if (response != null) {
            return response.getRepayMentList();
        }
        return null;
    }

    @Override
    public List<TenderCreditDetailCustomizeVO> selectCreditRecordList(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectCreditRecordList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();

        if (response != null) {
            return response.getCreditRecordList();
        }
        return null;
    }

    @Override
    public int countCurrentHoldPlanTotal(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/countCurrentHoldPlanTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getCurrentHoldPlanCount();
        }
        return 0;
    }

    @Override
    public List<CurrentHoldPlanListCustomizeVO> selectCurrentHoldPlanList(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectCurrentHoldPlanList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();

        if (response != null) {
            return response.getCurrentHoldPlanList();
        }
        return null;
    }

    @Override
    public Integer countRepayMentPlanTotal(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/countRepayMentPlanTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getRepayMentPlanCount();
        }
        return 0;
    }

    @Override
    public List<RepayMentPlanListCustomizeVO> selectRepayMentPlanList(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectRepayMentPlanList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();

        if (response != null) {
            return response.getRepayMentPlanList();
        }
        return null;
    }
}
