/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.web.aboutus;

import com.hyjf.am.vo.config.*;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.market.service.AboutUsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version AboutUsController, v0.1 2018/7/9 9:40
 */
@Api(value = "信息披露", description = "信息披露")
@RestController
@RequestMapping("/web/cs-market/aboutus")
public class AboutUsController extends BaseController {

	private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");

	@Autowired
	private AboutUsService aboutUsService;


	@ApiOperation(value = "信息披露", notes = "关于我们")
	@RequestMapping("/about")
	public WebResult<Map<String, Object>> aboutus() {
		logger.info("web端获取关于我们数据开始...");
		WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			ContentArticleVO aboutus = aboutUsService.getAboutUs();
			String contents = aboutus.getContent();
			// 获取累计投资金额
			String totalInvestmentAmount = aboutUsService.getTotalInvestmentAmount();
			totalInvestmentAmount = StringUtils.isBlank(totalInvestmentAmount) ? "0.00"
					: DF_FOR_VIEW.format(new BigDecimal(totalInvestmentAmount));
			contents = contents.replaceAll("money",
					totalInvestmentAmount.substring(0, totalInvestmentAmount.indexOf(".")));
			aboutus.setContent(contents);
			// 2.获取创始人信息
			TeamVO team = aboutUsService.getFounder();
			resultMap.put("aboutus", aboutus);
			resultMap.put("founder", team);
			result.setData(resultMap);
			return result;
		} catch (Exception e) {
			logger.error("web端获取关于我们数据失败...", e);
			result.setStatus(WebResult.ERROR);
			result.setStatusDesc(WebResult.ERROR_DESC);
			return result;
		}
	}

	@ApiOperation(value = "信息披露", notes = "合作伙伴")
	@RequestMapping("/partners")
	public WebResult<Map<String, Object>> partners() {
		logger.info("web端获取合作伙伴数据开始...");
		WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			List<LinkVO> serviceSupportList = aboutUsService.getPartnersList(10);// 服务支持
			List<LinkVO> lawSupportList = aboutUsService.getPartnersList(11);// 法律支持
			List<LinkVO> financeOrgList = aboutUsService.getPartnersList(7);// 金融机构
			List<LinkVO> otherOrgList = aboutUsService.getPartnersList(12);// 其他机构
			resultMap.put("serviceSupportList", serviceSupportList);
			resultMap.put("lawSupportList", lawSupportList);
			resultMap.put("financeOrgList", financeOrgList);
			resultMap.put("otherOrgList", otherOrgList);
			result.setData(resultMap);
			return result;
		} catch (Exception e) {
			logger.error("web端获取合作伙伴数据失败...", e);
			result.setStatus(WebResult.ERROR);
			result.setStatusDesc(WebResult.ERROR_DESC);
			return result;
		}
	}

	@ApiOperation(value = "信息披露", notes = "公司历程")
	@RequestMapping("/events")
	public WebResult<Map<String, Object>> events() {
		logger.info("web端获取公司历程数据开始...");
		WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			List<EventVO> eventVOList = aboutUsService.getEventsList();
			resultMap.put("eventsList", eventVOList);
			result.setData(resultMap);
			return result;
		} catch (Exception e) {
			logger.error("web端获取公司历程数据失败...", e);
			result.setStatus(WebResult.ERROR);
			result.setStatusDesc(WebResult.ERROR_DESC);
			return result;
		}
	}

	@ApiOperation(value = "信息披露", notes = "网站公告列表")
	@RequestMapping("/getNoticeListPage")
	public WebResult<Map<String, Object>> getNoticeListPage() {
		logger.info("web端获取网站公告列表数据开始...");
		WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			List<ContentArticleVO> recordList = aboutUsService.getNoticeListCount();
			resultMap.put("contentArticleList", recordList);
			result.setData(resultMap);
			return result;
		} catch (Exception e) {
			logger.error("web端获取网站公告列表失败...", e);
			result.setStatus(WebResult.ERROR);
			result.setStatusDesc(WebResult.ERROR_DESC);
			return result;
		}
	}

	@ApiOperation(value = "信息披露", notes = "根据id获取网站公告")
	@RequestMapping("/events/{id}")
	public WebResult<Map<String, Object>> getCompanyNoticeDetail(@PathVariable Integer id) {
		logger.info("web端根据id获取网站公告数据开始...");
		WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			ContentArticleVO contentArticleVO = aboutUsService.getNoticeInfo(id);
			resultMap.put("companyNotice", contentArticleVO);
			result.setData(resultMap);
			return result;
		} catch (Exception e) {
			logger.error("web端获取网站公告列表失败...", e);
			result.setStatus(WebResult.ERROR);
			result.setStatusDesc(WebResult.ERROR_DESC);
			return result;
		}
	}


	/**
	 * 查询招贤纳士信息
	 * @return
	 */
	@ApiOperation(value = "招贤纳士", notes = "招贤纳士列表查询")
	@RequestMapping("/recurit")
	public WebResult<List<JobsVo>> getRecurit(){
		List<JobsVo> jobsList = aboutUsService.getJobsList();
		WebResult webResult = new WebResult(jobsList);
		return webResult;
	}


	/**
	 * 联系我们
	 * @return
	 */

	@ApiOperation(value = "联系我们", notes = "联系我们查询")
	@RequestMapping("/contactus")
	public WebResult<List<JobsVo>> contactus(){
		ContentArticleVO contactUs = aboutUsService.getContactUs();
		WebResult webResult = new WebResult(contactUs);
		return webResult;
	}

	/**
	 * 查询网贷知识信息
	 * @return
	 */
	@ApiOperation(value = "网贷知识", notes = "查询网贷知识信息")
	@RequestMapping("/getKnowReportList")
	public WebResult<List<JobsVo>> get(){
		List<ContentArticleVO> homeNoticeList = aboutUsService.getHomeNoticeList();
		WebResult webResult = new WebResult(homeNoticeList);
		return webResult;
	}
}
