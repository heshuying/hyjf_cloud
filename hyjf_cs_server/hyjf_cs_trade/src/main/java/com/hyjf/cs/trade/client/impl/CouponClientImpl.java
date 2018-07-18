package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.CoupUserResponse;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.CouponClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

	@Override
	public List<BorrowTenderCpnVO> getBorrowTenderCpnHjhList(String orderId) {
		CouponResponse response = new CouponResponse();
		response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/coupon/getborrowtendercpnhjhlist/"+orderId, CouponResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<BorrowTenderCpnVO> getBorrowTenderCpnHjhCouponOnlyList(String couponOrderId) {
		CouponResponse response = new CouponResponse();
		response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/coupon/getborrowtendercpnhjhcoupononlylist/"+couponOrderId, CouponResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public Integer updateBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn) {
		CouponResponse response = new CouponResponse();
		response = restTemplate
				.postForEntity("http://AM-TRADE/am-trade/coupon/updateborrowtendercpn",borrowTenderCpn, CouponResponse.class).getBody();
		if (response != null) {
			return response.getTotalRecord();
		}
		return 0;
	}

    @Override
    public int countByExample(String tenderNid) {
		CouponResponse response = new CouponResponse();
		response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/couponConfig/countbytendernid/"+tenderNid, CouponResponse.class).getBody();
		if (response != null) {
			return response.getTotalRecord();
		}
        return 0;
    }

    @Override
    public Integer crRecoverPeriod(String tenderNid, int currentRecoverFlg, int period) {
		CouponResponse response = new CouponResponse();
		response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/couponConfig/crrecoverperiod/"+tenderNid+"/"+currentRecoverFlg+"/"+period, CouponResponse.class).getBody();
		if (response != null) {
			return response.getTotalRecord();
		}
		return 0;
    }
}
