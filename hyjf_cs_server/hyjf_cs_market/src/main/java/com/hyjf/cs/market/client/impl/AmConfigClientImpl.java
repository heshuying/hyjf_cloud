/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.response.config.*;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.response.trade.HolidaysConfigResponse;
import com.hyjf.am.resquest.admin.ContentPartnerRequest;
import com.hyjf.am.resquest.admin.EventsRequest;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import com.hyjf.am.resquest.config.WechatContentArticleRequest;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.market.ShareNewsBeanVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.cs.market.client.AmConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
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
				"http://AM-CONFIG/am-config/content/contentevent/searchaction", new EventsRequest(), EventResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<ContentArticleVO> aboutUsClient(BasePage request) {
		ContentArticleRequest articleRequest = new ContentArticleRequest();
		// 网站公告
		articleRequest.setNoticeType("2");
		articleRequest.setCurrPage(request.getCurrPage());
		articleRequest.setPageSize(request.getPageSize());
		ContentArticleResponse response = restTemplate.postForObject(
				"http://AM-CONFIG/am-config/article/contentArticleList", articleRequest, ContentArticleResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据ID获取公司历程详情
	 * @param id
	 * @return
	 * @Author : huanghui
	 */
	@Override
	public EventVO getEventDetailById(Integer id) {
		EventResponse response = restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentevent/getEventDetail/" + id, EventResponse.class);
		if (response != null){
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
		ContentPartnerRequest request = new ContentPartnerRequest();
		request.setPartnerType(partnerType);
		LinkResponse response = restTemplate.postForObject(
				"http://AM-CONFIG/am-config/content/contentpartner/searchaction", request,
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
	public Integer countContentArticleByType(String type) {
		ContentArticleCustomizeResponse response = restTemplate.getForObject(
				"http://AM-CONFIG/am-config/article/countcontentarticlebytype/" + type,
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
	public IntegerResponse addSubmission(SubmissionsVO submissionsVO){
		IntegerResponse response = restTemplate.postForObject("http://AM-CONFIG/am-config/submission/addSubmission",submissionsVO ,IntegerResponse.class);
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

	/**
	 *获取公司公告列表
	 * @param request
	 * @return
	 */
	@Override
	public ContentArticleResponse getCompanyDynamicsListPage(ContentArticleRequest request) {
		ContentArticleResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/article/getCompanyDynamicsListPage",request,ContentArticleResponse.class).getBody();
		return response;

	}

	@Override
	public boolean isWorkdateOnSomeDay() {
		BooleanResponse response = restTemplate.getForObject(
				"http://AM-CONFIG/am-config/holidays/is_workdate",
				BooleanResponse.class);
		if (response != null) {
			return response.getResultBoolean();
		}
		return false;
	}

	@Override
	public boolean isWorkdateOnSomeDay(Date date) {
		BooleanResponse response = restTemplate.postForObject(
				"http://AM-CONFIG/am-config/holidays/is_workdateOn", date,
				BooleanResponse.class);
		if (response != null) {
			return response.getResultBoolean();
		}
		return false;
	}

	@Override
	public Date getFirstWorkdateBeforeSomeDate(Date date) {
		HolidaysConfigResponse response = restTemplate.postForObject(
				"http://AM-CONFIG/am-config/holidays/get_first_workdate_before_some_date", date,
				HolidaysConfigResponse.class);
		if (response != null) {
			return response.getSomedate();
		}
		return null;
	}

	@Override
	public Date getFirstWorkdateAfterSomeDate(Date torrowDate) {
		HolidaysConfigResponse response = restTemplate.postForObject(
				"http://AM-CONFIG/am-config/holidaysConfig/getFirstWorkdateAfterSomeDate", torrowDate,
				HolidaysConfigResponse.class);
		if (response != null) {
			return response.getSomedate();
		}
		return null;
	}

	/**
	 * 判断某天是否是工作日
	 *
	 * @return
	 */
	@Override
	public boolean queryWorkdateOnSomeday() {
		BooleanResponse response = restTemplate.postForObject(
				"http://AM-CONFIG//am-config/holidays/is_workdate", null,
				BooleanResponse.class);
		return response.getResultBoolean();
	}

	/**
	 * 判断当天是不是当月第一个工作日
	 * @return
	 */
    @Override
    public boolean selectFirstWorkdayOnMonth() {
    	BooleanResponse response = restTemplate.postForObject(
    			"http://AM-CONFIG/am-config/holidays/is_firstworkdate",null,
				BooleanResponse.class);
        return response.getResultBoolean();
    }

	/**
	 * 查询短信加固数据
	 *
	 * @param request
	 * @return
	 * @author xiehuili
	 */
	@Override
	public SmsConfigResponse initSmsConfig(SmsConfigRequest request) {
		return restTemplate.postForEntity("http://AM-CONFIG/am-config/smsConfig/initSmsConfig", request, SmsConfigResponse.class).getBody();
	}
}
