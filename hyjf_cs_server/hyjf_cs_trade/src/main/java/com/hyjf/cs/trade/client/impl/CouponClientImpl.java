package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.CoupUserResponse;
import com.hyjf.am.vo.trade.CouponTenderVO;
import com.hyjf.am.vo.trade.CouponUserVO;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.CouponClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date  
 */

@Service
public class CouponClientImpl implements CouponClient {
	private static Logger logger = LoggerFactory.getLogger(AmUserClient.class);
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * @param couponGrantId
	 * @param userId
	 * @Description 根据优惠券ID和用户ID查询优惠券
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/19 16:20
	 */
	@Override
	public CouponUserVO getCouponUser(Integer couponGrantId, Integer userId) {
		String url = "http://AM-TRADE/am-trade/coupon/getCouponUser/" + couponGrantId + "/" + userId;
		CoupUserResponse response = restTemplate.getForEntity(url, CoupUserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 优惠券投资
	 *
	 * @param couponTender
	 * @return
	 */
	@Override
	public boolean updateCouponTender(CouponTenderVO couponTender) {
		Integer result = restTemplate
				.postForEntity("http://AM-TRADE/am-trade/coupon/updateCouponTender", couponTender, Integer.class).getBody();
		if (result != null) {
			return result == 0 ? false : true;
		}
		return false;
	}
}
