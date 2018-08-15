package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ActivityListCustomizeResponse;
import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.admin.ActivityListCustomizeVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmMarketClientImpl, v0.1 2018/5/14 16:11
 */
@Service
public class AmMarketClientImpl implements AmMarketClient {
	private static Logger logger = LoggerFactory.getLogger(AmMarketClientImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<ActivityListVO> getActivityList() {
		ActivityListResponse response = restTemplate.getForEntity(
				"http://AM-MARKET/am-market/activity/getActivityList",
				ActivityListResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public String getActivityTitle(Integer activityId) {
		CouponTenderResponse response = restTemplate
				.getForEntity("http://AM-MARKET/am-market/activity/hztgetactivitytitle/" + activityId,
						CouponTenderResponse.class)
				.getBody();
		if (response != null) {
			return response.getAttrbute();
		}
		return null;
	}

	@Override
	public ActivityListResponse getRecordList(ActivityListRequest activityListRequest) {
		String url = "http://AM-MARKET/am-market/activity/getRecordList";
		ActivityListResponse response = restTemplate.postForEntity(url, activityListRequest, ActivityListResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	@Override
	public ActivityListResponse insertRecord(ActivityListRequest request) {
		String url = "http://AM-MARKET/am-market/activity/insertRecord";
		ActivityListResponse response = restTemplate.postForEntity(url, request, ActivityListResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

	@Override
	public ActivityListResponse selectActivityById(ActivityListRequest activityListRequest) {
		String url = "http://AM-MARKET/am-market/activity/selectActivityList";
		ActivityListResponse response = restTemplate.postForEntity(url, activityListRequest, ActivityListResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	@Override
	public ActivityListResponse updateActivity(ActivityListRequest request) {
		String url = "http://AM-MARKET/am-market/activity/updateActivity";
		ActivityListResponse response = restTemplate.postForEntity(url, request, ActivityListResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	@Override
	public ActivityListResponse deleteActivity(ActivityListRequest request) {
		String url = "http://AM-MARKET/am-market/activity/deleteActivity";
		ActivityListResponse response = restTemplate.postForEntity(url, request, ActivityListResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

	@Override
	public AppBannerResponse findAppBannerList(AppBannerRequest request) {
		AppBannerResponse response = restTemplate
				.postForEntity("http://AM-MARKET/am-market/appconfig/getRecordList" ,request,
						AppBannerResponse.class)
				.getBody();
		return response;
	}

	@Override
	public AppBannerResponse insertAppBannerList(AdsWithBLOBsVO ads) {
		AppBannerResponse response = restTemplate
				.postForEntity("http://AM-MARKET/am-market/appconfig/insertRecord" ,ads,
						AppBannerResponse.class)
				.getBody();
		return response;
	}

	@Override
	public AppBannerResponse updateAppBannerList(AdsWithBLOBsVO ads) {
		AppBannerResponse response = restTemplate
				.postForEntity("http://AM-MARKET/am-market/appconfig/updateRecord" ,ads,
						AppBannerResponse.class)
				.getBody();
		return response;
	}

	@Override
	public AppBannerResponse updateAppBannerStatus(AdsWithBLOBsVO ads) {
		AppBannerResponse response = restTemplate
				.postForEntity("http://AM-MARKET/am-market/appconfig/updateStatus" ,ads,
						AppBannerResponse.class)
				.getBody();
		return response;
	}

	@Override
	public AppBannerResponse deleteAppBanner(AdsWithBLOBsVO ads) {
		AppBannerResponse response = restTemplate
				.postForEntity("http://AM-MARKET/am-market/appconfig/deleteAppBanner" ,ads,
						AppBannerResponse.class)
				.getBody();
		return response;
	}

	@Override
	public ContentAdsResponse searchAction(ContentAdsRequest request) {
		return restTemplate.postForEntity("http://AM-MARKET/am-market/content/contentads/searchaction",
				request, ContentAdsResponse.class).getBody();
	}

	@Override
	public ContentAdsResponse inserAction(ContentAdsRequest request) {
		return restTemplate.postForObject("http://AM-MARKET/am-market/content/contentads/inseraction",
				request, ContentAdsResponse.class);
	}

	@Override
	public ContentAdsResponse updateAction(ContentAdsRequest request) {
		return restTemplate.postForObject("http://AM-MARKET/am-market/content/contentads/updateaction",
				request, ContentAdsResponse.class);
	}

	@Override
	public ContentAdsResponse deleteById(Integer id) {
		return restTemplate.getForObject("http://AM-MARKET/am-market/content/contentads/delete/" + id, ContentAdsResponse.class);
	}

	@Override
	public ContentAdsResponse getAdsTypeList() {
		return restTemplate.postForObject("http://AM-MARKET/am-market/content/contentads/getadstypelist",
				null, ContentAdsResponse.class);
	}

	@Override
	public CouponTenderResponse getActivityById(Integer activityId) {

		String url = "http://AM-MARKET/am-market/activity/hztgetactivitytitle/" + activityId;
		CouponTenderResponse response = restTemplate.getForEntity(url, CouponTenderResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

	/**
	 * 获取有效活动列表
	 *
	 * @param request
	 * @return
	 */
	@Override
	public List<ActivityListCustomizeVO> getActivityList(ActivityListCustomizeVO request) {
		String url = "http://AM-MARKET/am-market/activity/selectRecordListValid";
		ActivityListCustomizeResponse response = restTemplate.postForEntity(url, request, ActivityListCustomizeResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
}
