/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowTenderCpnResponse;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.cs.trade.client.BorrowTenderCpnClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version BorrowTenderCpnClientImpl, v0.1 2018/6/25 15:45
 */
@Service
public class BorrowTenderCpnClientImpl implements BorrowTenderCpnClient {

    @Autowired
    RestTemplate restTemplate;
    @Override
    public int updateBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn) {
        String url = "http://AM-TRADE/am-trade/borrowTender/updateborrowtendercn";
        BorrowTenderCpnResponse response = restTemplate.postForEntity(url,borrowTenderCpn,BorrowTenderCpnResponse.class).getBody();
        if (response != null) {
            return response.getFlag();
        }
        return 0;
    }

    @Override
    public List<BorrowTenderCpnVO> getBorrowTenderCpnList(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/coupon/getborrowtendercpnlist/"+borrowNid;
        BorrowTenderCpnResponse response = restTemplate.getForEntity(url,BorrowTenderCpnResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

}
