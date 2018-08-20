/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.response.config.*;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.resquest.config.WechatContentArticleRequest;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.market.ShareNewsBeanVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.cs.market.client.AmConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

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
    public ContentArticleResponse getknowsList(ContentArticleRequest request) {
        ContentArticleResponse response = restTemplate.postForObject("http://AM-CONFIG/am-config/article/getKnowsList",request ,ContentArticleResponse.class);
        return response;

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

	@Override
	public List<Map<String, Object>> getIndexList(ContentArticleRequest request) {
		ContentArticleResponse response = restTemplate.postForObject("http://AM-CONFIG/am-config/article/index",request ,ContentArticleResponse.class);
		if (response != null){
			return response.getResponseList();
		}
		return null;

	}

    @Override
    public TotalInvestAndInterestResponse searchData() {
		TotalInvestAndInterestResponse response = restTemplate.getForObject("http://AM-CONFIG/am-config/article/index",TotalInvestAndInterestResponse.class);
		return response;

    }

	/**
	 * 查询文章条数
	 * @return
	 */
	@Override
	public Integer countContentArticleByType() {
		ContentArticleCustomizeResponse response = restTemplate.getForObject(
				"http://AM-CONFIG/am-config/article/countcontentarticlebytype",
				ContentArticleCustomizeResponse.class);
		if (response != null) {
			return response.getCount();
		}
		return null;
	}

	/**
	 * 查询文章列表
	 * @return
	 */
	@Override
	public List<ContentArticleCustomizeVO> getContentArticleListByType(Map<String, Object> params) {
		ContentArticleCustomizeResponse response = restTemplate.postForObject(
				"http://AM-CONFIG/am-config/article/getcontentarticlelistbytype", params,
				ContentArticleCustomizeResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public ContentArticleVO getContentArticleById(Integer contentArticleId) {
		ContentArticleResponse response = restTemplate
				.getForObject("http://AM-CONFIG/am-config/article/getarticlebyid/" + contentArticleId, ContentArticleResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
	@Override
	public List<ContentArticleVO> searchHomeNoticeList(String noticeType, int limitStart, int limitEnd) {
		ContentArticleResponse response=restTemplate.getForObject("http://AM-CONFIG/am-config/article/find/"+noticeType+"/"+limitStart+"/"+limitEnd, ContentArticleResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

    @Override
    public WechatContentArticleResponse searchContentArticleList(WechatContentArticleRequest form) {
		WechatContentArticleResponse response=restTemplate.postForObject(
				"http://AM-CONFIG/am-config/article/getWechatContentArticleListByType", form,
				WechatContentArticleResponse.class);
        return response;
    }

	/**
	 * 上下翻页
	 * @param params
	 * @param offset
	 * @return
	 */
    @Override
	public ContentArticleCustomizeVO getContentArticleFlip(Map<String, Object> params, String offset) {
		params.put("offset", offset);
		ContentArticleCustomizeResponse response = restTemplate
				.postForObject("http://AM-CONFIG/am-config/article/getContentArticleFlip/", params, ContentArticleCustomizeResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

    @Override
    public ShareNewsBeanVO queryShareNews() {
		ShareNewsResponse response = restTemplate
				.getForObject("http://AM-CONFIG/am-config/article/querysharenews" , ShareNewsResponse.class);
		if (response != null) {
			return response.getResult();
		}
        return null;
    }
	/**
	 * 添加反馈信息
	 * @param submissionsVO
	 * @return
	 */
	@Override
	public int addSubmission(SubmissionsVO submissionsVO){
		Integer response = restTemplate.postForObject("http://AM-CONFIG/am-config/submission/addSubmission",submissionsVO ,Integer.class);
		return response;
	}

    @Override
    public List<JxBankConfigVO> getBankRecordList() {
		JxBankConfigResponse response=restTemplate
				.getForObject("http://AM-CONFIG/am-config/config/getbanklist" , JxBankConfigResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
    }
}
