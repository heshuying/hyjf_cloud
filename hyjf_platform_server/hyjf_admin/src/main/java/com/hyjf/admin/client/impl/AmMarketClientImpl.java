package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.admin.ActivityListCustomizeVO;
import com.hyjf.am.vo.market.ActivityListVO;
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
				"http://AM-admin/am-market/activity/getActivityList",
				ActivityListResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public ActivityListResponse getRecordList(ActivityListRequest activityListRequest) {
		String url = "http://AM-ADMIN/am-market/activity/getRecordList";
		ActivityListResponse response = restTemplate.postForEntity(url, activityListRequest, ActivityListResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	@Override
	public ActivityListResponse insertRecord(ActivityListRequest request) {
		String url = "http://AM-ADMIN/am-market/activity/insertRecord";
		ActivityListResponse response = restTemplate.postForEntity(url, request, ActivityListResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

	@Override
	public ActivityListResponse selectActivityById(ActivityListRequest activityListRequest) {
		String url = "http://AM-ADMIN/am-market/activity/selectActivityList";
		ActivityListResponse response = restTemplate.postForEntity(url, activityListRequest, ActivityListResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	@Override
	public ActivityListResponse updateActivity(ActivityListRequest request) {
		String url = "http://AM-ADMIN/am-market/activity/updateActivity";
		ActivityListResponse response = restTemplate.postForEntity(url, request, ActivityListResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	@Override
	public ActivityListResponse deleteActivity(ActivityListRequest request) {
		String url = "http://AM-ADMIN/am-market/activity/deleteActivity";
		ActivityListResponse response = restTemplate.postForEntity(url, request, ActivityListResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}


	@Override
	public ContentAdsResponse searchAction(ContentAdsRequest request) {
		return restTemplate.postForEntity("http://AM-ADMIN/am-market/content/contentads/searchaction",
				request, ContentAdsResponse.class).getBody();
	}

	@Override
	public ContentAdsResponse inserAction(ContentAdsRequest request) {
		return restTemplate.postForObject("http://AM-ADMIN/am-market/content/contentads/inseraction",
				request, ContentAdsResponse.class);
	}

	@Override
	public ContentAdsResponse infoaction(Integer id) {
		return restTemplate.getForObject("http://AM-ADMIN/am-market/content/contentads/infoaction/"+
				id, ContentAdsResponse.class);
	}

	@Override
	public ContentAdsResponse updateAction(ContentAdsRequest request) {
		return restTemplate.postForObject("http://AM-ADMIN/am-market/content/contentads/updateaction",
				request, ContentAdsResponse.class);
	}

	@Override
	public ContentAdsResponse deleteById(Integer id) {
		return restTemplate.getForObject("http://AM-ADMIN/am-market/content/contentads/delete/" + id, ContentAdsResponse.class);
	}

	@Override
	public ContentAdsResponse getAdsTypeList() {
		return restTemplate.postForObject("http://AM-ADMIN/am-market/content/contentads/getadstypelist",
				null, ContentAdsResponse.class);
	}

	@Override
	public CouponTenderResponse getActivityById(Integer activityId) {

		String url = "http://AM-ADMIN/am-market/activity/hztgetactivitytitle/" + activityId;
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
		String url = "http://AM-ADMIN/am-market/activity/selectRecordListValid";
		ActivityListCustomizeResponse response = restTemplate.postForEntity(url, request, ActivityListCustomizeResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 获取发送消息表
 	 * @param bean
	 * @return
	 */
	@Override
	public MessagePushNoticesResponse getRecordList(MessagePushNoticesRequest bean) {
		MessagePushNoticesResponse response = restTemplate
				.postForEntity("http://CS-MESSAGE/cs-message/adminMessage/messagePushList",bean,
						MessagePushNoticesResponse.class)
				.getBody();
		return response;
	}

	/**
	 * 添加发送消息列表
	 * @param bean
	 * @return
	 */
	@Override
	public MessagePushNoticesResponse insertRecord(MessagePushNoticesRequest bean) {
		MessagePushNoticesResponse response = restTemplate
				.postForEntity("http://CS-MESSAGE/cs-message/adminMessage/insertPushList",bean,
						MessagePushNoticesResponse.class)
				.getBody();
		return response;
	}

	/**
	 * 删除
	 * @param bean
	 * @return
	 */
    @Override
    public MessagePushNoticesResponse deleteRecord(MessagePushNoticesRequest bean) {
		MessagePushNoticesResponse response = restTemplate
				.postForEntity("http://CS-MESSAGE/cs-message/adminMessage/deletePushList",bean,
						MessagePushNoticesResponse.class)
				.getBody();
		return response;

	}

	/**
	 * 修改
	 * @param bean
	 * @return
	 */
	@Override
	public MessagePushNoticesResponse updateRecord(MessagePushNoticesRequest bean) {
		MessagePushNoticesResponse response = restTemplate
				.postForEntity("http://CS-MESSAGE/cs-message/adminMessage/updatePushList",bean,
						MessagePushNoticesResponse.class)
				.getBody();
		return response;

	}

	/**
	 * 获取历史消息列表
	 * @param request
	 * @return
	 */
    @Override
    public MessagePushHistoryResponse getRecordList(MessagePushHistoryRequest request) {
		MessagePushHistoryResponse response = restTemplate
				.postForEntity("http://CS-MESSAGE/cs-message/historyMessage/getMessageList",request,
						MessagePushHistoryResponse.class)
				.getBody();
		return response;

    }



    @Override
    public MessagePushNoticesResponse getRecord(MessagePushNoticesRequest bean) {
		MessagePushNoticesResponse response = restTemplate
				.postForEntity("http://CS-MESSAGE/cs-message/adminMessage/getPushRecord",bean,
						MessagePushNoticesResponse.class)
				.getBody();
		return response;
    }

    @Override
    public MessagePushTagResponse getTagList() {
		MessagePushTagResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/messagePushTag/getTagList",
						MessagePushTagResponse.class)
				.getBody();
		return response;
    }

	@Override
    public ActivityListResponse getInfoById(Integer id) {
		ActivityListResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-market/activity/getInfoById/" + id, ActivityListResponse.class).getBody();
        return response;
    }

    @Override
    public NewYearActivityRewardResponse selectAwardList(NewYearNineteenRequestBean requestBean) {
    	NewYearActivityRewardResponse response = restTemplate.postForObject("http://AM-ADMIN/am-admin/newYearNineteen/getAwardList", requestBean, NewYearActivityRewardResponse.class);
        return response;
    }

    @Override
    public NewYearActivityRewardResponse selectAwardInfo(NewYearNineteenRequestBean request) {
    	NewYearActivityRewardResponse response = restTemplate.postForObject("http://AM-ADMIN/am-admin/newYearNineteen/getAwardInfo", request, NewYearActivityRewardResponse.class);
        return response;
    }

    @Override
    public BooleanResponse updateStatus(NewYearNineteenRequestBean request) {
    	BooleanResponse response = restTemplate.postForObject("http://AM-ADMIN/am-admin/newYearNineteen/updateStatus", request, BooleanResponse.class);
        return response;
    }

	/**
	 * 查询兑吧积分明细
	 *
	 * @param requestBean
	 * @return
	 */
	@Override
	public DuibaPointsResponse selectDuibaPointsList(DuibaPointsRequest requestBean) {
		DuibaPointsResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-admin/duibapoints/selectDuibaPointsList",requestBean,DuibaPointsResponse.class).getBody();
		return response;
	}

	/**
	 * 兑吧积分账户修改明细
	 *
	 * @param requestBean
	 * @return
	 */
	@Override
	public DuibaPointsModifyResponse selectDuibaPointsModifyList(DuibaPointsRequest requestBean) {
		DuibaPointsModifyResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-admin/duibapointsmodify/selectDuibaPointsModifyList",requestBean,DuibaPointsModifyResponse.class).getBody();
		return response;
	}

}
