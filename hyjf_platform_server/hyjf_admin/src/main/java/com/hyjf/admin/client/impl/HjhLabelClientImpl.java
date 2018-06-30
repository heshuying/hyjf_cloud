/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.HjhLabelClient;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

/**
 * @author libin
 * @version HjhLabelClientImpl.java, v0.1 2018年6月30日 上午9:48:45
 */
@Service
public class HjhLabelClientImpl implements HjhLabelClient{
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public List<BorrowProjectTypeVO> findBorrowProjectTypeList() {
		// 复用
        BorrowProjectTypeResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/hjhLabel/selectBorrowProjectByBorrowCd", BorrowProjectTypeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
		return null;
	}
	@Override
	public List<BorrowStyleVO> findBorrowStyleList() {
		// 复用
		BorrowStyleResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/hjhLabel/selectBorrowStyleList", BorrowStyleResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
		return null;
	}
}
