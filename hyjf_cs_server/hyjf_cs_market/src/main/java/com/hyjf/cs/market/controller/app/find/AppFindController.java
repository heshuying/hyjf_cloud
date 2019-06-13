/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.app.find;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.resquest.app.AppBaseRequest;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.vo.app.find.*;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.bean.result.WebResult;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.common.enums.ContentArticleEnum;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.market.bean.AppContentArticleBean;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.AppFindService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fq
 * @version AppFindController, v0.1 2018/7/20 9:29
 */
@Api(tags = "app端-app发现页")
@RestController
@RequestMapping("/hyjf-app/find")
public class AppFindController extends BaseMarketController {

	private static final Logger logger = LoggerFactory.getLogger(AppFindController.class);

	@Value("${hyjf.app.host}")
	public String appHost;

	@Autowired
	private AppFindService	appFindService;

	private final static String DATE_FORMAT = "yyyy-MM-dd";
	/** REQUEST_MAPPING */
	public static final String REQUEST_MAPPING = "/find/contentArticle";
	/** 获取文章详情  */
	public static final String GET_CONTENT_ARTICLE_ID_ACTION = "/{type}/{contentArticleId}";

	@ResponseBody
	@GetMapping(value = "")
	@ApiOperation(value = "app发现页公司动态", notes = "app发现页公司动态")
	public JSONObject find() {
		JSONObject ret = new JSONObject();
		List<ContentArticleVO> newsList = appFindService.searchHomeNoticeList("20", 0, 3);
		JSONArray jsonArray = new JSONArray();
		for (ContentArticleVO article : newsList) {
			JSONObject detailsJson = new JSONObject();
			detailsJson.put("id", article.getId());
			detailsJson.put("img", article.getImgurl());
			detailsJson.put("title", article.getTitle());
			detailsJson.put("date", DateFormatUtils.format(article.getCreateTime(), DATE_FORMAT));
			detailsJson.put("shareTitle", article.getTitle());
			detailsJson.put("shareContent", article.getSummary());
			detailsJson.put("sharePicUrl", "https://www.hyjf.com/data/upfiles/image/20140617/1402991818340.png");
			detailsJson.put("shareUrl", appHost + REQUEST_MAPPING + GET_CONTENT_ARTICLE_ID_ACTION
					.replace("{contentArticleId}", article.getId() + "").replace("{type}", "20") + "?ignoreSign=true");
			jsonArray.add(detailsJson);
		}
		ret.put("status", "000");
		ret.put("statusDesc", "成功");
		ret.put("newsList", jsonArray);
		return ret;
	}

	@ApiOperation(value = "知识列表", notes = "知识列表")
	@PostMapping(value = "/contentArticle/getContentArticleListByType")
	@ApiParam(required = true, name = "form", value = "查询条件")
	@ResponseBody
	public JSONObject getContentArticleListByType(@ModelAttribute AppContentArticleBean form) {
		logger.info("app端-app发现页 知识列表 start /hyjf-app/find/contentArticle/getContentArticleListByType....");
		JSONObject ret = new JSONObject();
		ret.put("status", "0");
		ret.put("statusDesc", "请求成功");
		ret.put("request", "/hyjf-app/find/contentArticle/getContentArticleListByType");
		try {
			// 检查参数正确性
			if (Validator.isNull(form.getVersion()) || Validator.isNull(form.getPlatform())) {
				ret.put("status", "1");
				ret.put("statusDesc", "请求参数非法");
				return ret;
			}
			// 检查参数正确性
			if (form.getSize() < 0 || form.getPage() < 0) {
				ret.put("status", "1");
				ret.put("statusDesc", "分页参数非法");
				return ret;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", form.getType());
			params.put("limitStart", -1);
			params.put("limitEnd", -1);
			// 查询总数
			Integer count = appFindService.countContentArticleByType(form.getType());

			if (count != null && count > 0) {
				// 构造分页
				params.put("limitStart", form.getSize() * (form.getPage() - 1));
				params.put("limitEnd", form.getSize());
				List<ContentArticleCustomizeVO> list = appFindService.getContentArticleListByType(params);

				if (!CollectionUtils.isEmpty(list)) {
					ret.put("messageCount", count);
					list.stream().forEach(article -> {
						if(StringUtils.isNotBlank(article.getImgurl())) {
							article.setImgurl(appHost + article.getImgurl());
						}
					});
					ret.put("messageList", list);
				} else {
					ret.put("messageCount", "0");
					ret.put("messageList", new ArrayList<ContentArticleCustomizeVO>());
				}
			} else {
				ret.put("messageCount", "0");
				ret.put("messageList", new ArrayList<ContentArticleCustomizeVO>());
			}
		} catch (Exception e) {
			ret.put("status", "1");
			ret.put("statusDesc", "系统异常请稍后再试");
			ret.put("messageCount", "0");
			ret.put("messageList", new ArrayList<ContentArticleCustomizeVO>());
			logger.warn("app端-app发现页 知识列表 异常", e);
			return ret;
		}
		logger.info("app端-app发现页 知识列表 end ");
		return ret;
	}

	@ApiOperation(value = "上下翻页", notes = "上下翻页")
	@PostMapping(value = "/contentArticle/getContentArticleFlip")
	@ApiParam(required = true, name = "form", value = "查询条件")
	@ResponseBody
	public JSONObject getContentArticleFlip(@ModelAttribute AppContentArticleBean form) {
		logger.info("app端-app发现页 上下翻页 start  /hyjf-app/find/contentArticle/getContentArticleFlip");
		JSONObject ret = new JSONObject();
		ret.put("status", "0");
		ret.put("statusDesc", "请求成功");
		ret.put("request", "/hyjf-app/find/contentArticle/getContentArticleFlip");
		try {

			// 检查参数正确性
			if (Validator.isNull(form.getOffset()) || Validator.isNull(form.getMessageId())) {
				ret.put("status", "1");
				ret.put("statusDesc", "请求参数非法");
				return ret;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("messageId", form.getMessageId());
			params.put("type", form.getType());
			// 查询总数
			ContentArticleCustomizeVO contentArticleCustomize = appFindService.getContentArticleFlip(params,
					form.getOffset());

			if (contentArticleCustomize != null) {
				logger.info("contentArticleCustomize: {}", contentArticleCustomize);
				ret.put("messageId", contentArticleCustomize.getMessageId());
				ret.put("messageUrl", contentArticleCustomize.getMessageUrl());
				ret.put("shareTitle", contentArticleCustomize.getTitle());
				ret.put("shareContent", contentArticleCustomize.getShareContent());
				ret.put("sharePicUrl", contentArticleCustomize.getSharePicUrl());
				ret.put("shareUrl", contentArticleCustomize.getShareUrl());
			} else {
				ret.put("messageId", "");
				ret.put("messageUrl", "");
				ret.put("shareTitle", "");
				ret.put("shareContent", "");
				ret.put("sharePicUrl", "");
				ret.put("shareUrl", "");
			}
		} catch (Exception e) {
			ret.put("status", "1");
			ret.put("statusDesc", "系统异常请稍后再试");
			ret.put("messageCount", "0");
			ret.put("messageList", new ArrayList<ContentArticleCustomizeVO>());
			logger.info("app端-app发现页 上下翻页 异常", e);
			return ret;
		}
		logger.info("app端-app发现页 上下翻页 end ");
		return ret;
	}

	@ApiOperation(value = "根据文章编号查询对应文章", notes = "根据文章编号查询对应文章")
	@GetMapping("/contentArticle/{type}/{contentArticleId}")
	public JSONObject contentArticle(@PathVariable Integer type, @PathVariable Integer contentArticleId) {
		logger.info("app端-app发现页 根据文章编号查询对应文章 start type：" + type + ",contentArticleId：" + contentArticleId +
				"/hyjf-app/find/contentArticle/{type}/{contentArticleId}");
		JSONObject ret = new JSONObject();
		ret.put("status", "000");
		ret.put("statusDesc", "成功");
		ret.put("topTitle", ContentArticleEnum.getName(String.valueOf(type)));
		try {
			if (type != null && 4 == type) {// 3.0.9 新增推送公告详情
				AppPushManageVO manageInfo = appFindService.getAppPushManagerContentByID(contentArticleId);
				if (manageInfo != null) {
					ret.put("topTitle", manageInfo.getTitle());
					JSONObject details = new JSONObject();
					details.put("title", manageInfo.getTitle());
					details.put("content", manageInfo.getContent());
					details.put("date", new SimpleDateFormat("yyyy-MM-dd").format(manageInfo.getCreateTime()));
					ret.put("details", details);
				}
			} else {
				ContentArticleVO contentArticle = appFindService.getContentArticleById(contentArticleId);
				if (contentArticle != null) {
					JSONObject details = new JSONObject();
					details.put("title", contentArticle.getTitle());
					details.put("content", contentArticle.getContent());
					details.put("date", new SimpleDateFormat("yyyy-MM-dd").format(contentArticle.getCreateTime()));
					ret.put("details", details);
				} else {
					ret.put("status", 99);
					ret.put("statusDesc", "失败");
				}
			}

		} catch (Exception e) {
			ret.put("status", 99);
			ret.put("statusDesc", "失败");
			logger.warn("app端-app发现页 根据文章编号查询对应文章 异常", e);
		}
		logger.info("app端-app发现页 根据文章编号查询对应文章 end");
		return ret;
	}

	@ApiOperation(value = "app发现页面", notes = "app发现页面")
	@ApiImplicitParam(name = "platform", value = "平台类型 2：Android，3：IOS", required = true, dataType = "String")
	@PostMapping("/init")
	@ResponseBody
	public WebResult<AppFindVO> initFind(HttpServletRequest request, @ModelAttribute AppBaseRequest appBaseRequest) {
		WebResult webResult = new WebResult("0","成功");
		AdsRequest adsRequest = new AdsRequest();
		adsRequest.setPlatformType(appBaseRequest.getPlatform());
		adsRequest.setHost(appHost);
		adsRequest.setLimitEnd(1);
		List<AppFindAdCustomizeVO> adVOList = appFindService.getFindModules(adsRequest);
		AppFindAdCustomizeVO adVO = appFindService.getFindBanner(adsRequest);
		List<ContentArticleVO> articleList = appFindService.searchHomeNoticeList("20", 0, 3);
		List<AppFindNewsVO> newList = new ArrayList<>();
		for (ContentArticleVO article : articleList) {
			AppFindNewsVO newsVO = new AppFindNewsVO();
			newsVO.setId(article.getId());
			if(StringUtils.isNotBlank(article.getImgurl())){
				newsVO.setImg(appHost + article.getImgurl());
			}
			newsVO.setTitle(article.getTitle());
			newsVO.setDate(DateFormatUtils.format(article.getCreateTime(), DATE_FORMAT));
			newsVO.setShareContent(article.getSummary());
			newsVO.setSharePicUrl("https://www.hyjf.com/data/upfiles/image/20140617/1402991818340.png");
			newsVO.setShareUrl(appHost + REQUEST_MAPPING + GET_CONTENT_ARTICLE_ID_ACTION
					.replace("{contentArticleId}", article.getId() + "").replace("{type}", "20") + "?ignoreSign=true");
			newList.add(newsVO);
		}
		List reportList = appFindService.getReportList(1);
		List<AppFindReportVO> appFindReportList = new ArrayList<>();
		reportList.stream().forEach(p -> {
			AppFindReportVO reportVO = new AppFindReportVO();
			HashMap report = (HashMap) p;
			reportVO.setId(report.get("id").toString());
			reportVO.setTitle(report.get("typeRealName") + "运营报告");
			reportVO.setDate(report.get("year") + "年" + report.get("sortMonth") + "月" + report.get("sortDay") + "日");
			reportVO.setUrl(CommonUtils.concatReturnUrl(request, appHost + ClientConstants.FIND_REPORT + ClientConstants.FIND_REPORT_DETAIL
					.replace("{year}", report.get("year") + "").replace("{month}", report.get("sortMonth") + "")
			+ "?id=" +report.get("id")));
			appFindReportList.add(reportVO);
		});
		AppFindVO appFindVO = new AppFindVO();
		appFindVO.setModules(adVOList);
		appFindVO.setBanner(adVO);
		appFindVO.setNewsList(newList);
		AppFindMoreNewsVO moreNewsVO = new AppFindMoreNewsVO();
		moreNewsVO.setTitle("公司动态");
		moreNewsVO.setType("20");
		appFindVO.setMoreNewsType(moreNewsVO);
		appFindVO.setReportList(appFindReportList);
		appFindVO.setMoreReportsUrl(CommonUtils.concatReturnUrl(request, appHost + ClientConstants.FIND_REPORT));
		appFindVO.setContact("联系我们 400-900-7878");
		webResult.setData(appFindVO);
		return webResult;
	}
}
