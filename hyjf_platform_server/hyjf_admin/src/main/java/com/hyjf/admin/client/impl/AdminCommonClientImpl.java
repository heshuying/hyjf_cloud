/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AdminCommonClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author wangjun
 * @version AdminCommonClientImpl, v0.1 2018/7/3 10:30
 */
@Service
public class AdminCommonClientImpl implements AdminCommonClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BorrowStyleVO> selectBorrowStyleList() {
        String url = "http://AM-TRADE/am-trade/borrow_regist/select_borrow_style";
        BorrowStyleResponse response = restTemplate.getForEntity(url, BorrowStyleResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigList() {
        String url = "http://AM-TRADE/am-trade/hjhInstConfig/selectInstConfigAll";
        HjhInstConfigResponse response = restTemplate.getForEntity(url, HjhInstConfigResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }
}
