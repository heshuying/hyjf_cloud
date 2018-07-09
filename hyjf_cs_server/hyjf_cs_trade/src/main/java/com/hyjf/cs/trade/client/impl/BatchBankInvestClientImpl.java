package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BatchBorrowTenderCustomizeResponse;
import com.hyjf.am.resquest.trade.BatchBorrowTenderCustomizeRequest;
import com.hyjf.am.vo.trade.borrow.BatchBorrowTenderCustomizeVO;
import com.hyjf.cs.trade.client.BatchBankInvestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 投资调单异常处理
 * @author jun
 * @date 20180623
 */
@Service
public class BatchBankInvestClientImpl implements BatchBankInvestClient {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<BatchBorrowTenderCustomizeVO> queryAuthCodeBorrowTenderList() {
		String url = "http://AM-TRADE/am-trade/bankException/queryAuthCodeBorrowTenderList";
		BatchBorrowTenderCustomizeResponse response =
				restTemplate.getForEntity(url,BatchBorrowTenderCustomizeResponse.class).getBody();
		if (response!=null){
			return response.getResultList();
		}
		return null;
	}

	@Override
	public void insertAuthCode(List<BatchBorrowTenderCustomizeVO> list) {
		String url = "http://AM-TRADE/am-trade/bankException/insertAuthCode";
		BatchBorrowTenderCustomizeRequest request = new BatchBorrowTenderCustomizeRequest();
		request.setBatchBorrowTenderCustomizeList(list);
		restTemplate.postForEntity(url,request,Boolean.class).getBody();

	}
}
