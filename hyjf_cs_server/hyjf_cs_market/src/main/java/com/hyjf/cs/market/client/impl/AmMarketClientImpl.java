package com.hyjf.cs.market.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.response.market.SellDailyDistributionResponse;
import com.hyjf.am.response.market.SellDailyResponse;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.admin.SellDailyDistributionVO;
import com.hyjf.am.vo.market.ActivityListBeanVO;
import com.hyjf.am.vo.market.SellDailyVO;
import com.hyjf.cs.market.client.AmMarketClient;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 11:18
 * @Description: AmMarketClientImpl
 */
@Service
public class AmMarketClientImpl implements AmMarketClient {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Integer queryActivityCount(ActivityListRequest activityListRequest) {
        ActivityListResponse response = restTemplate.postForObject(
                "http://AM-MARKET/am-market/activity/queryactivitycount",activityListRequest,
                ActivityListResponse.class);
        if (response != null) {
            return response.getCount();
        }
        return null;
    }

    @Override
    public List<ActivityListBeanVO> queryActivityList(ActivityListRequest activityListRequest) {
        ActivityListResponse response = restTemplate.postForObject(
                "http://AM-MARKET/am-market/activity/queryactivitylist",activityListRequest,
                ActivityListResponse.class);
        if (response != null) {
            return response.getActivityList();
        }
        return null;
    }

    @Override
    public List<SellDailyDistributionVO> selectSellDailyDistribution() {
        SellDailyDistributionResponse response = restTemplate.getForEntity(
                "http://AM-MARKET/am-market/daily_distribution/selectdistribution",
                SellDailyDistributionResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<SellDailyVO> selectSellDailyByDateStr(String dateStr) {
        SellDailyResponse response = restTemplate.getForObject(
                "http://AM-MARKET/am-market/sell_daily/selectbydatestr/" + dateStr,
                SellDailyResponse.class);
        return response.getResultList();
    }

    @Override
    public SellDailyVO selectOCSum(String formatDateStr) {
        SellDailyResponse response = restTemplate.getForObject(
                "http://AM-MARKET/am-market/sell_daily/selectocsum/" + formatDateStr,
                SellDailyResponse.class);
        return response.getResult();
    }

    @Override
    public SellDailyVO selectPrimaryDivisionSum(String formatDateStr, int drawOrder) {
        SellDailyResponse response = restTemplate.getForObject(
                "http://AM-MARKET/am-market/sell_daily/select_primary_divisionsum/" + formatDateStr + "/" + drawOrder,
                SellDailyResponse.class);
        return response.getResult();
    }

    @Override
    public SellDailyVO selectAllSum(String formatDateStr) {
        SellDailyResponse response = restTemplate.getForObject(
                "http://AM-MARKET/am-market/sell_daily/select_allsum/" + formatDateStr,
                SellDailyResponse.class);
        return response.getResult();
    }

	@Override
	public boolean hasGeneratorDataToday() {
		BooleanResponse response = restTemplate
				.getForObject("http://AM-MARKET/am-market/sell_daily/has_generator_data_today", BooleanResponse.class);
		if (response != null) {
			return response.getResultBoolean();
		}
		return false;
	}

    @Override
    public void batchInsertSellDaily(List<SellDailyVO> list) {
		restTemplate.postForObject("http://AM-MARKET/am-market/sell_daily/batch_insert_sell_daily", list,
				Object.class);
    }

    @Override
    public void calculateRate() {
        restTemplate.getForObject("http://AM-MARKET/am-market/sell_daily/calculate_rate",
                Object.class);
    }

    @Override
    public void callActivityEnd() {
        restTemplate.getForObject("http://AM-MARKET/am-market/ads/batch/update",
                Object.class);
    }

}
