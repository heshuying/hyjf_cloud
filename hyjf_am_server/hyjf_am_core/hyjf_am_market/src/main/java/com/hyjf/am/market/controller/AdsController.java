package com.hyjf.am.market.controller;

import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.customize.app.AppAdsCustomize;
import com.hyjf.am.market.service.AdsService;
import com.hyjf.am.response.market.AdsResponse;
import com.hyjf.am.response.market.AppAdsCustomizeResponse;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version ActivityController, v0.1 2018/4/19 15:38
 */

@RestController
@RequestMapping("/am-market/ads")
public class AdsController {
	private static final Logger logger = LoggerFactory.getLogger(AdsController.class);

	@Autowired
	private AdsService adsService;

	@RequestMapping("/findAdsById/{activityId}")
	public AdsResponse findActivityById(@PathVariable Integer activityId) {
		AdsResponse response = new AdsResponse();
		Ads ads = adsService.findActivityById(activityId);
		if (null != ads) {
			AdsVO adsVO = new AdsVO();
			BeanUtils.copyProperties(ads, adsVO);
			response.setResult(adsVO);
		}
		return response;
	}

	/**
	 * batch 批量检测到期活动
	 */
	@RequestMapping("/batch/update")
	public void updateActivityEndStatus() {
		logger.info("批量更新到期活动...");
		adsService.updateActivityEndStatus();
	}

	@RequestMapping("/searchBanner")
	public AppAdsCustomizeResponse searchBanner(@RequestBody AdsRequest adsRequest) {
		Map<String, Object> ads = new HashMap<>();
		ads.put("limitStart", adsRequest.getLimitStart());
		ads.put("limitEnd", adsRequest.getLimitEnd());
		ads.put("host", adsRequest.getHost());
		ads.put("code", adsRequest.getCode());
		AppAdsCustomizeResponse response = new AppAdsCustomizeResponse();
		List<AppAdsCustomize> appAdsCustomizeList = adsService.searchBanner(ads);
		if (!CollectionUtils.isEmpty(appAdsCustomizeList)) {
			AppAdsCustomizeVO appAdsCustomizeVO = new AppAdsCustomizeVO();
			BeanUtils.copyProperties(appAdsCustomizeList.get(0), appAdsCustomizeVO);
			response.setResult(appAdsCustomizeVO);
		}
		return response;
	}

	/**
	 * 查询首页banner
	 * @author zhangyk
	 * @date 2018/10/12 10:46
	 */
	@RequestMapping("/searchHomeBanner")
	public AppAdsCustomizeResponse searchHomeBanner(@RequestBody AdsRequest adsRequest) {
		Map<String, Object> ads = new HashMap<>();
		ads.put("limitStart", adsRequest.getLimitStart());
		ads.put("limitEnd", adsRequest.getLimitEnd());
		ads.put("host", adsRequest.getHost());
		ads.put("code", adsRequest.getCode());
		ads.put("platformType",adsRequest.getPlatformType());
		AppAdsCustomizeResponse response = new AppAdsCustomizeResponse();
		List<AppAdsCustomize> appAdsCustomizeList = adsService.searchBanner(ads);
		if (!CollectionUtils.isEmpty(appAdsCustomizeList)) {
			response.setResultList(CommonUtils.convertBeanList(appAdsCustomizeList,AppAdsCustomizeVO.class));
		}
		return response;
	}

	@PostMapping("/getBannerList")
	public AppAdsCustomizeResponse getBannerList(@RequestBody AdsRequest adsRequest) {
		AppAdsCustomizeResponse response = new AppAdsCustomizeResponse();
		List<Ads> list = adsService.getBannerList(adsRequest);
		if (!CollectionUtils.isEmpty(list)) {
			List<AppAdsCustomizeVO> adsList = CommonUtils.convertBeanList(list,AppAdsCustomizeVO.class);
			response.setResultList(adsList);
		}
		return response;
	}
}
