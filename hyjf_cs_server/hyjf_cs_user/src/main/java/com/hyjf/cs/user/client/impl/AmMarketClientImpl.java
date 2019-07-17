package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.response.market.AdsResponse;
import com.hyjf.am.response.market.AppAdsCustomizeResponse;
import com.hyjf.am.response.market.DuiBaPointsDetailResponse;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.market.DuiBaPointsDetailRequest;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.market.DuiBaPointsDetailVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.user.client.AmMarketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author xiasq
 * @version AmMarketClientImpl, v0.1 2018/5/14 16:11
 */
@Cilent
public class AmMarketClientImpl implements AmMarketClient {
	private static Logger logger = LoggerFactory.getLogger(AmMarketClientImpl.class);

	@Value("${am.market.service.name}")
	private String marketService;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 根据活动id查询活动
	 * 
	 * @param
	 * @return
	 */
	@Override
	public AdsVO findAdsById(Integer activityId) {
		AdsResponse response = restTemplate
				.getForEntity(marketService+"/ads/findAdsById/" + activityId, AdsResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public ActivityListVO selectActivityList(int activityId){
		ActivityListResponse response = restTemplate.getForEntity(marketService+"/activity/selectActivity/"+activityId, ActivityListResponse.class).getBody();
		if(null!=response){
			return   response.getResult();
		}
		return  new ActivityListVO();
	}

	@Override
	public AppAdsCustomizeVO searchBanner(AdsRequest adsRequest){
		AppAdsCustomizeResponse response = restTemplate.postForEntity(marketService+"/ads/searchBanner",adsRequest, AppAdsCustomizeResponse.class).getBody();
		if(null!=response){
			return   response.getResult();
		}
		return new AppAdsCustomizeVO();
	}

	@Override
	public List<ActivityListVO> getActivityList() {
		return null;
	}

	/**
	 * 查询兑吧积分商城明细
	 * @param request
	 * @return
	 */
    @Override
    public DuiBaPointsDetailVO getPointsDetail(DuiBaPointsDetailRequest request) {
        String url = marketService.concat("/duiba/getpointsdetail");
		DuiBaPointsDetailResponse response = restTemplate.postForEntity(url, request,DuiBaPointsDetailResponse.class).getBody();
		if(Response.isSuccess(response)){
			return response.getResult();
		}
		return null;
    }

}
