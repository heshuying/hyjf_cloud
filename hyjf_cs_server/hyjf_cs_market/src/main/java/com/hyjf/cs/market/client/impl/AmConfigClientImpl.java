/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.config.EventResponse;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.response.config.RecruitResponse;
import com.hyjf.am.response.config.TeamResponse;
import com.hyjf.am.response.trade.CalculateInvestInterestResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.vo.config.*;
import com.hyjf.cs.market.client.AmConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fuqiang
 * @version AmConfigClientImpl, v0.1 2018/7/9 10:09
 */
@Service
public class AmConfigClientImpl implements AmConfigClient {

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
	public TeamVO getFounder() {
		TeamResponse response = restTemplate.getForObject("http://AM-CONFIG/am-config/team/getfounder", TeamResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

    @Override
	public List<EventVO> getEventsList() {
		EventResponse response = restTemplate.postForObject(
				"http://AM-CONFIG/am-config/content/contentevent/searchaction", null, EventResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<ContentArticleVO> aboutUsClient() {
		ContentArticleResponse response = restTemplate.postForObject(
				"http://AM-CONFIG/am-config/article/contentArticleList", null, ContentArticleResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public ContentArticleVO getNoticeInfo(Integer id) {
		ContentArticleResponse response = restTemplate
				.getForObject("http://AM-CONFIG/am-config/article/getarticlebyid/" + id, ContentArticleResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

    /**
     * 获取招贤纳士列表
     * @return
     */
    @Override
    public List<JobsVo> getJobsList() {
        RecruitResponse response = restTemplate.getForObject("http://AM-CONFIG/am-config/job/getJobs", RecruitResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public ContentArticleVO contactUs() {
        ContentArticleResponse response = restTemplate.getForObject("http://AM-CONFIG/am-config/article/getContentArticle", ContentArticleResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<ContentArticleVO> getknowsList() {
        ContentArticleResponse response = restTemplate.getForObject("http://AM-CONFIG/am-config/article/getKnowsList", ContentArticleResponse.class);
            if (response != null){
            return response.getResultList();
        }
        return null;

    }

	@Override
	public List<LinkVO> getPartnersList(Integer partnerType) {
		LinkResponse response = restTemplate.getForObject(
				"http://AM-CONFIG/am-config/content/contentpartner/getbypartnertype/" + partnerType,
				LinkResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
}
