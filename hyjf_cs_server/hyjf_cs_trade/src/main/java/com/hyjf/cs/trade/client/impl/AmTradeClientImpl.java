package com.hyjf.cs.trade.client.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hyjf.am.response.trade.CouponRecoverCustomizeResponse;
import com.hyjf.am.response.trade.CouponTenderCustomizeResponse;
import com.hyjf.am.response.trade.MyCouponListResponse;
import com.hyjf.am.response.trade.MyRewardListResponse;
import com.hyjf.am.response.user.MyInviteListResponse;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.trade.RepayDataRecoverRequest;
import com.hyjf.am.resquest.trade.RtbIncreaseRepayRequest;
import com.hyjf.am.vo.trade.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.CouponTenderCustomizeVO;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.cs.trade.client.AmTradeClient;

/**
 * @author xiasq
 * @version AmTradeClientImpl, v0.1 2018/6/19 15:44
 */
@Service
public class AmTradeClientImpl implements AmTradeClient {
    private static Logger logger = LoggerFactory.getLogger(AmTradeClientImpl.class);

    public static final String urlBase = "http://AM-TRADE/am-trade/";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 统计加息券每日待收收益
     *
     * @param
     * @return
     */
    @Override
    public List<CouponRecoverCustomizeVO> selectCouponInterestWaitToday(long timeStart, long timeEnd) {
        String url = urlBase + "batch/selectCouponInterestWaitToday/" + timeStart + "/" + timeEnd;
        CouponRecoverCustomizeResponse response = restTemplate.getForEntity(url, CouponRecoverCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 统计加息券每日已收收益
     *
     * @param
     * @return
     */
    @Override
    public BigDecimal selectCouponInterestReceivedToday(long timeStart, long timeEnd) {
        String url = urlBase + "batch/selectCouponInterestReceivedToday/" + timeStart + "/" + timeEnd;
        BigDecimal interest = restTemplate.getForEntity(url, BigDecimal.class).getBody();
        if (interest != null) {
            return interest;
        }
        return null;
    }

    /**
     * 我的优惠券列表
     *
     * @auther: hesy
     * @date: 2018/6/23
     */
    @Override
    public List<MyCouponListCustomizeVO> selectMyCouponList(MyCouponListRequest requestBean) {
        String url = urlBase + "coupon/myCouponList";
        MyCouponListResponse response = restTemplate.postForEntity(url, requestBean, MyCouponListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<CouponTenderCustomizeVO> selectCouponRecoverAll(String borrowNid, int repayTimeConfig) {
        String url = urlBase + "batch/selectCouponRecover/" + borrowNid + "/" + repayTimeConfig;
        CouponTenderCustomizeResponse response = restTemplate.getForEntity(url,CouponTenderCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public CouponRecoverCustomizeVO getCurrentCouponRecover(String couponTenderNid, int periodNow) {
        String url = urlBase + "batch/getCurrentCouponRecover/" + couponTenderNid + "/" +periodNow;
        CouponRecoverCustomizeResponse response = restTemplate.getForEntity(url,CouponRecoverCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 收支明细不存在时掉单处理
     * @param currentRecover
     * @param bankOpenAccountInfo
     * @param userId
     * @param couponUserCode
     * @param ip
     * @param count
     */
    @Override
    public void repayDataRecover(CouponRecoverCustomizeVO currentRecover, BankOpenAccountVO bankOpenAccountInfo, String userId, String couponUserCode, String ip, int count) {
        String url = urlBase + "batch/repayDataRecover";
        RepayDataRecoverRequest request = new RepayDataRecoverRequest();
        request.setCouponRecoverCustomizeVO(currentRecover);
        request.setBankOpenAccountVO(bankOpenAccountInfo);
        request.setUserId(userId);
        request.setCouponUserCode(couponUserCode);
        request.setIp(ip);
        request.setCount(count);

        restTemplate.postForEntity(url, request, Object.class);
    }
	/**
	 * 统计总的优惠券数
	 * @param requestBean
	 * @return
	 */
	@Override
	public Integer selectMyCouponCount(MyCouponListRequest requestBean){
		String url = urlBase + "coupon/myCouponCount";
		Integer result = restTemplate.postForEntity(url,requestBean,Integer.class).getBody();
		return result;
	}

	/**
	 * 我的奖励列表
	 * @param requestBean
	 * @return
	 */
	@Override
	public List<MyRewardRecordCustomizeVO> selectMyRewardList(MyInviteListRequest requestBean){
		String url = urlBase + "reward/myRewardList";
		MyRewardListResponse response = restTemplate.postForEntity(url,requestBean,MyRewardListResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

    /**
     * 我的奖励列表总记录数
     */
    @Override
    public int selectMyRewardCount(MyInviteListRequest requestBean) {
        int count = restTemplate
                .postForEntity(urlBase + "reward/myRewardTotal", requestBean, Integer.class).getBody();
        return count;
    }

	/**
	 * 统计总的奖励金额
	 * @param requestBean
	 * @return
	 */
	@Override
	public BigDecimal selectMyRewardTotal(MyInviteListRequest requestBean){
		String url = urlBase + "reward/myRewardTotal";
		BigDecimal result = restTemplate.postForEntity(url,requestBean,BigDecimal.class).getBody();
		return result;
	}

    /**
     * 收支明细存在时掉单处理
     * @param borrowTenderCpn
     * @param borrowNid
     * @param couponUserCode
     */
    @Override
    public void updateRepayDataRecover(CouponRecoverCustomizeVO currentRecover,BorrowTenderCpnVO borrowTenderCpn, String borrowNid, String couponUserCode,String userId,String borrowStyle,int periodNow) {
        String url = urlBase + "batch/updateRepayDataRecover";
        RepayDataRecoverRequest request = new RepayDataRecoverRequest();
        request.setCouponRecoverCustomizeVO(currentRecover);
        request.setBorrowTenderCpnVO(borrowTenderCpn);
        request.setCouponUserCode(couponUserCode);
        request.setBorrowNid(borrowNid);
        request.setUserId(userId);
        request.setBorrowStyle(borrowStyle);
        request.setPeriodNow(periodNow);

        restTemplate.postForEntity(url,request,Object.class);
    }
}
