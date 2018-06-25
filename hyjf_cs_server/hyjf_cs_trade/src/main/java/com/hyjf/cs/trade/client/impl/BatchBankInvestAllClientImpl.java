package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowTenderTmpResponse;
import com.hyjf.am.resquest.trade.BorrowTenderTmpRequest;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.cs.trade.client.BatchBankInvestAllClient;

import java.util.List;

/**
 * 投资全部掉单异常处理
 * @author jun
 * @date 20180623
 */
@Service
public class BatchBankInvestAllClientImpl implements BatchBankInvestAllClient {

	@Autowired
	private RestTemplate restTemplate;


	@Override
	public List<BorrowTenderTmpVO> getBorrowTenderTmpList() {
		String url = "http://AM-TRADE/am-trade/bankexception/getBorrowTenderTmpList";
		BorrowTenderTmpResponse response =restTemplate.getForEntity(url,BorrowTenderTmpResponse.class).getBody();
		if (response!=null){
			response.getResultList();
		}
		return null;
	}

	@Override
	public Boolean updateTenderStart(BorrowTenderTmpRequest request) {
		String url = "http://AM-TRADE/am-trade/bankexception/updateTenderStart";
		return restTemplate.postForEntity(url,request,Boolean.class).getBody();
	}

}
