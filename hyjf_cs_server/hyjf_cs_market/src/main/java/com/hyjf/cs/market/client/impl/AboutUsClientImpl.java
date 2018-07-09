/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.config.TeamResponse;
import com.hyjf.am.response.trade.CalculateInvestInterestResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.config.EventVO;
import com.hyjf.am.vo.config.TeamVO;
import com.hyjf.cs.market.client.AboutUsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fuqiang
 * @version AboutUsClientImpl, v0.1 2018/7/9 10:09
 */
@Service
public class AboutUsClientImpl implements AboutUsClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ContentArticleVO getAboutUs() {
        ContentArticleResponse response = restTemplate.getForObject("http://AM-CONFIG/am-config/article/getaboutus", ContentArticleResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

	@Override
	public String getTotalInvestmentAmount() {
		CalculateInvestInterestResponse response = restTemplate.getForObject(
				"http://AM-DATA-COLLECT/am-statistics/search/gettotalinvestmentamount",
				CalculateInvestInterestResponse.class);
		if (response != null) {
			BigDecimal interestSum = response.getInterestSum();
			if (interestSum != null) {
				return String.valueOf(interestSum.divide(new BigDecimal("100000000")));
			}
		}
		return null;
	}

    @Override
	public TeamVO getFounder() {
		TeamResponse response = restTemplate.getForObject("http://am-config/team/getfounder", TeamResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

    @Override
    public List<EventVO> getEventsList() {
        return null;
    }

    @Override
    public List<ContentArticleVO> aboutUsClient() {
        return null;
    }

    @Override
    public ContentArticleVO getNoticeInfo(Integer id) {
        return null;
    }
}
