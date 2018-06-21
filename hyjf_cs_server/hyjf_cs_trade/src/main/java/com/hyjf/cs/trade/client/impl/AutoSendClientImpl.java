/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhLabelVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.trade.client.AutoSendClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author fuqiang
 * @version AutoSendClientImpl, v0.1 2018/6/12 16:13
 */
@Service
public class AutoSendClientImpl implements AutoSendClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HjhPlanAssetVO selectPlanAsset(String assetId, String instCode) {
        HjhPlanAssetResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/assetPush/selectPlanAsset/" + assetId + "/" + instCode, HjhPlanAssetResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode) {
        HjhInstConfigResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/hjhPlan/selectHjhInstConfigByInstCode/" + instCode, HjhInstConfigResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public void updatePlanAsset(HjhPlanAssetVO planAssetVO) {
        restTemplate.postForEntity("http://AM-TRADE/am-trade/assetPush/updatePlanAsset", planAssetVO, int.class).getBody();
    }

    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectByBorrowCd(String borrowCd) {
        BorrowProjectTypeResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/assetPush/selectBorrowProjectByBorrowCd/" + borrowCd, BorrowProjectTypeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BorrowFinmanNewChargeVO selectBorrowApr(BorrowFinmanNewChargeRequest request) {
        BorrowFinmanNewChargeResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/trade/selectBorrowApr", request, BorrowFinmanNewChargeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<UserVO> selectUserByUsername(String repayOrgName) {
        UserResponse response = restTemplate.getForEntity("http://AM-USER/am-user/user/findByRepayOrgName/" + repayOrgName, UserResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BorrowConfigVO getBorrowConfig(String configCd) {
        BorrowConfigResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/trade/getBorrowConfig/" + configCd, BorrowConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public void insertSelective(BorrowWithBLOBsVO borrow) {
        restTemplate.postForEntity("http://AM-TRADE/am-trade/trade/insertBorrow", borrow, int.class).getBody();
    }

    @Override
    public void insertBorrowManinfo(BorrowManinfoVO borrowManinfoVO) {
        restTemplate.postForEntity("http://AM-TRADE/am-trade/trade/insertBorrowManinfo", borrowManinfoVO, int.class).getBody();
    }

    @Override
    public int updateHjhPlanAssetnew(HjhPlanAssetVO hjhPlanAssetnewVO) {
        int result = restTemplate.postForEntity("http://AM-TRADE/am-trade/assetPush/updateHjhPlanAssetnew", hjhPlanAssetnewVO, int.class).getBody();
        return result;
    }

    @Override
    public List<HjhLabelVO> seleHjhLabel(String borrowStyle) {
        HjhLabelResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhPlan/seleHjhLabel/" + borrowStyle, HjhLabelResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
