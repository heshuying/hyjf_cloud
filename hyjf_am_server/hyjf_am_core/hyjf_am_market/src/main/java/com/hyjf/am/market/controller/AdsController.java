package com.hyjf.am.market.controller;

import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.customize.app.AppAdsCustomize;
import com.hyjf.am.market.service.AdsService;
import com.hyjf.am.response.market.AdsResponse;
import com.hyjf.am.response.market.AppAdsCustomizeResponse;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
		AppAdsCustomize appAdsCustomize = adsService.searchBanner(ads);
		if (null != appAdsCustomize) {
			AppAdsCustomizeVO appAdsCustomizeVO = new AppAdsCustomizeVO();
			BeanUtils.copyProperties(appAdsCustomize, appAdsCustomizeVO);
			response.setResult(appAdsCustomizeVO);
		}
		return response;
	}
}
