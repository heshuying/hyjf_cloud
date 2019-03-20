/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.app.home;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.user.EvalationCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.HomePageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version HomePageController, v0.1 2018/7/26 10:15
 */
@Api(tags = "App-起始页信息获取")
@RestController
@RequestMapping(CustomConstants.REQUEST_HOME + CustomConstants.REQUEST_MAPPING)
public class HomePageController extends BaseMarketController {
    private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);

	@Value("${hyjf.app.host}")
	private String APP_PRE_URL;

	/**
	 * 获取起始页banner
	 * @param platform
	 * @param realPlatform
	 * @return
	 */
	@PostMapping(CustomConstants.START_PAGE_ACTION)
	@ApiOperation(value = "App-获取起始页广告信息", httpMethod = "POST", notes = "App-获取起始页广告信息")
	public JSONObject getStartPage(@RequestParam(value = "platform", required = false) String platform,
								   @RequestParam(value = "realPlatform", required = false) String realPlatform) {

		logger.info("App-获取起始页广告信息:[开始]");
		logger.info("请求路径:{},请求参数:platform:{},realPlatform:{}", CustomConstants.REQUEST_HOME + CustomConstants.REQUEST_MAPPING + CustomConstants.START_PAGE_ACTION, platform, realPlatform);

		JSONObject result = new JSONObject();
		result.put(CustomConstants.APP_REQUEST, CustomConstants.REQUEST_HOME + CustomConstants.REQUEST_MAPPING + CustomConstants.START_PAGE_ACTION);

		String platformT = realPlatform;
		if (StringUtils.isBlank(platformT)) {
			platformT = platform;
		}

		try {
			Map<String, Object> ads = new HashMap<String, Object>();
			ads.put("limitStart", 0);
			ads.put("limitEnd", 1);
			ads.put("code", "startpage");
			ads.put("host", APP_PRE_URL);

			if (platformT.equals("2")) {
				ads.put("platformType", "1");
			} else if (platformT.equals("3")) {
				ads.put("platformType", "2");
			}

			List<AppAdsCustomizeVO> picList = homePageService.searchBannerList(ads);

			if (CollectionUtils.isEmpty(picList)) {
				result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_FAIL);
				result.put(CustomConstants.APP_STATUS_DESC, "获取banner失败,暂无数据");
				return result;
			}
			logger.info("picUrl:{},actionUrl:{}", picList.get(0).getImage(), picList.get(0).getUrl());

			result.put("picUrl", picList.get(0).getImage());
			if (StringUtils.isNotEmpty(picList.get(0).getUrl())) {
				result.put("actionUrl", picList.get(0).getUrl());
			} else {
				result.put("actionUrl", "");
			}
			result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
			result.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_SUCCESS);
		} catch (Exception e) {
			logger.info("App-获取起始页广告信息:[异常],异常详情如下:{}", e);
			result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_FAIL);
			result.put(CustomConstants.APP_STATUS_DESC, "获取banner出现异常");
			return result;
		}

		logger.info("App-获取起始页广告信息:[结束]");
		return result;
	}

    @Autowired
    private HomePageService homePageService;

	/**
	 * 获取JumpCommend
	 * @return
	 */
	@ResponseBody
	@ApiOperation(value = "获取JumpCommend", httpMethod = "GET", notes = "获取JumpCommend")
	@GetMapping(value = "/getJumpCommend")
	public BaseResultBeanFrontEnd getJumpCommend() {
		logger.info(this.getClass().getName(), "获取JumpCommend start", "/hyjf-app/homepage/getJumpCommend");
		BaseResultBeanFrontEnd baseResultBeanFrontEnd = new BaseResultBeanFrontEnd();
		baseResultBeanFrontEnd.setStatus(BaseResultBeanFrontEnd.SUCCESS);
		baseResultBeanFrontEnd.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
		logger.info(this.getClass().getName(), "获取JumpCommend end", "/hyjf-app/homepage/getJumpCommend");
		return baseResultBeanFrontEnd;
	}

	/**
	 * 评分标准接口
	 * @return
	 */
	@ApiOperation(value = "评分标准", httpMethod = "POST", notes = "评分标准")
	@PostMapping(value = "/gradingStandardResult")
	public JSONObject gradingStandardResult() {

		JSONObject map = new JSONObject();
		List<EvalationCustomizeVO> evalationCustomizeList = homePageService.getEvalationRecord();
		map.put("status", "000");
		map.put("statusDesc", "请求成功");
		map.put("evalationList", evalationCustomizeList);
		return map;
	}
}
