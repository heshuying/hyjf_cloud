package com.hyjf.cs.trade.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.cs.trade.client.HjhSmsNoticeServiceClient;

/**
 * 还款逾期短息提醒
 * 
 * @author jun 20180626
 */
@Service
public class HjhSmsNoticeServiceClientImpl implements HjhSmsNoticeServiceClient {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<BorrowVO> selectOverdueBorrowList() {
		String url = "http://AM-TRADE/am-trade/borrow/selectOverdueBorrowList";
		BorrowResponse response = restTemplate.getForEntity(url, BorrowResponse.class).getBody();
		if (response != null) {
            return response.getResultList();
        }
        return null;
	}

}
