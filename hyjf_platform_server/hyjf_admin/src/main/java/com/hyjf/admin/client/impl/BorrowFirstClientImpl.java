/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowFirstClient;
import com.hyjf.am.response.admin.BorrowFirstCustomizeResponse;
import com.hyjf.am.response.trade.BorrowConfigResponse;
import com.hyjf.am.response.trade.BorrowInfoResponse;
import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.admin.BorrowFireRequest;
import com.hyjf.am.resquest.admin.BorrowFirstRequest;
import com.hyjf.am.vo.admin.BorrowFirstCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowConfigVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowFirstClientImpl, v0.1 2018/7/3 15:13
 */
@Service
public class BorrowFirstClientImpl implements BorrowFirstClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer countBorrowFirst(BorrowFirstRequest borrowFirstRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_first/count_borrow_first";
        return restTemplate.postForEntity(url, borrowFirstRequest, Integer.class).getBody();
    }

    @Override
    public List<BorrowFirstCustomizeVO> selectBorrowFirstList(BorrowFirstRequest borrowFirstRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_first/select_borrow_first_list";
        BorrowFirstCustomizeResponse response = restTemplate.postForEntity(url, borrowFirstRequest, BorrowFirstCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public String sumBorrowFirstAccount(BorrowFirstRequest borrowFirstRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_first/sum_borrow_first_account";
        return restTemplate.postForEntity(url, borrowFirstRequest, String.class).getBody();
    }

    @Override
    public BorrowConfigVO getBorrowConfig(String configCd) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowConfig/" + configCd;
        BorrowConfigResponse response = restTemplate.getForEntity(url, BorrowConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BorrowVO selectBorrowByNid(String borrowNid) {
        BorrowResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrow/getBorrow/" + borrowNid, BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BorrowInfoVO selectBorrowInfoByNid(String borrowNid) {
        BorrowInfoResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrow/getBorrowInfoByNid/" + borrowNid, BorrowInfoResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public String getUserName(Integer userId) {
        String url = "http://AM-USER/am-user/user/findById/" + userId;
        UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
        if (response != null) {
            if (response.getResult() != null) {
                return response.getResult().getUsername();
            }
        }
        return null;
    }

    /**
     * 交保证金
     *
     * @param borrowNid
     * @return
     */
    @Override
    public boolean insertBorrowBail(String borrowNid, String currUserId) {
        String url = "http://AM-TRADE/am-trade/borrow_first/insert_borrow_bail/" + borrowNid + "/" + currUserId;
        return restTemplate.getForEntity(url, boolean.class).getBody();
    }

    /**
     * 更新-发标
     *
     * @param borrowFireRequest
     */
    @Override
    public void updateOntimeRecord(BorrowFireRequest borrowFireRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_first/update_ontime_record";
        restTemplate.postForEntity(url, borrowFireRequest, String.class);
    }

    /**
     * 加入计划
     *
     * @param borrowFireRequest
     */
    @Override
    public void sendToMQ(BorrowFireRequest borrowFireRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_first/send_to_mq";
        restTemplate.postForEntity(url, borrowFireRequest, String.class);
    }
}
