/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.cs.trade.client.AutoRecordClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author fuqiang
 * @version AutoRecordClientImpl, v0.1 2018/6/14 11:03
 */
@Service
public class AutoRecordClientImpl implements AutoRecordClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BorrowVO selectBorrowByNid(String borrowNid) {
        BorrowResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/selectBorrowByNid/" + borrowNid, BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public boolean updateBorrowRegist(BorrowRegistRequest request) {
        Integer result = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrow/updateBorrowRegist", request, int.class).getBody();
        return result>0?true:false;
    }

    @Override
    public STZHWhiteListVO selectSTZHWhiteList(Integer entrustedUserId, String instCode) {
        STZHWhiteListResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/assetPush/selectStzfWhiteList/" + instCode + "/" + entrustedUserId, STZHWhiteListResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
