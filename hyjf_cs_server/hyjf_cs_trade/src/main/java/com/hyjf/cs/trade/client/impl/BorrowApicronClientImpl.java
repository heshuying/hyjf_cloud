package com.hyjf.cs.trade.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.cs.trade.client.BorrowApicronClient;

/**
 * @author xiasq
 * @version BorrowApicronClientImpl, v0.1 2018/6/19 14:27
 */
@Service
public class BorrowApicronClientImpl implements BorrowApicronClient {
	@Autowired
	private RestTemplate restTemplate;

	/**
	 *
	 * @param extraYieldRepayStatus
	 *            融通宝加息利息还款状态0未完成1已完成
	 * @param apiType
	 *            0放款1还款
	 * @return
	 */
	@Override
	public List<BorrowApicronVO> getBorrowApicronList(int extraYieldRepayStatus, int apiType) {
		BorrowApicronResponse response = restTemplate.getForEntity(
				"http://AM-TRADE/am-trade/borrowApicron/getBorrowApicronList/" + extraYieldRepayStatus + "/" + apiType,
				BorrowApicronResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 计划退出查询判断标的是否还款
	 * BorrowNidEqualTo(borrowNid)
	 * ApiTypeEqualTo(1)
	 * StatusNotEqualTo(6);
	 * @param borrowNid
	 * @return
	 */
	@Override
	public List<BorrowApicronVO> selectBorrowApicronListByBorrowNid(String borrowNid) {
		BorrowApicronResponse response = restTemplate.getForEntity(
				"http://AM-TRADE/am-trade/borrowApicron/selectBorrowApicronListByBorrowNid/" + borrowNid,
				BorrowApicronResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
}
