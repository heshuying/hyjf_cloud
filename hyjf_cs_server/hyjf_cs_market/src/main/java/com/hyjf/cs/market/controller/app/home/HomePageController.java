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
import io.swagger.annotations.ApiParam;
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
@Api(tags = "app端-app起始页信息获取")
@RestController
@RequestMapping("/hyjf-app/homepage")
public class HomePageController extends BaseMarketController {
    private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);
    @Autowired
    private HomePageService homePageService;

    @Value("file.domain.url")
    private String FILE_DOMAIN_URL;

    /**
     * 获取起始页banner
     * @param
     * @param
     * @return
     */
	@ApiOperation(value = "获取起始页广告信息", httpMethod = "POST", notes = "获取起始页广告信息")
	@ApiParam(required = true, name = "request", value = "查询条件")
	@PostMapping("/getStartPage")
	public JSONObject getStartPage(@RequestParam(value = "platform", required = false) String platform,
			@RequestParam(value = "realPlatform", required = false) String realPlatform) {
		logger.info(this.getClass().getName(), "获取起始页广告信息 start",
				"platform：{}" + platform + ",realPlatform：{}" + realPlatform, "/hyjf-app/homepage/getStartPage");
		JSONObject result = new JSONObject();
		String platformT = realPlatform;
		if (StringUtils.isBlank(platformT)) {
			platformT = platform;
		}
		result.put(CustomConstants.APP_REQUEST,
				CustomConstants.REQUEST_HOME + CustomConstants.REQUEST_MAPPING + CustomConstants.START_PAGE_ACTION);
		try {
			Map<String, Object> ads = new HashMap<String, Object>();
			ads.put("limitStart", 0);
			ads.put("limitEnd", 1);
			ads.put("host", FILE_DOMAIN_URL);
			ads.put("code", "startpage");
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
			logger.info("platform======" + platform);
			logger.info("picUrl======" + picList.get(0).getImage());
			logger.info("actionUrl======" + picList.get(0).getUrl());
			result.put("picUrl", picList.get(0).getImage());
			if (StringUtils.isNotEmpty(picList.get(0).getUrl())) {
				result.put("actionUrl", picList.get(0).getUrl());
			} else {
				result.put("actionUrl", "");
			}
			result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
			result.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_SUCCESS);
		} catch (Exception e) {
			logger.info(this.getClass().getName(), "获取起始页广告信息 异常", "/hyjf-app/homepage/getStartPage", e);
			result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_FAIL);
			result.put(CustomConstants.APP_STATUS_DESC, "获取banner出现异常");
			return result;
		}
		logger.info(this.getClass().getName(), "获取起始页广告信息 end", "/hyjf-app/homepage/getStartPage");
		return result;
	}

	/**
	 * 获取JumpCommend
	 * 
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
	 *
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
