/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.web.aboutus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.datacollect.TotalMessageVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.market.service.AboutUsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version AboutUsController, v0.1 2018/7/9 9:40
 */
@Api(tags = "web端-信息披露")
@RestController
@RequestMapping("/hyjf-web/aboutus")
public class AboutUsController extends BaseController {

	private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");

	@Autowired
	private AboutUsService aboutUsService;


	@ApiOperation(value = "信息披露", notes = "关于我们")
	@GetMapping("/about")
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
	@GetMapping("/partners")
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
	@GetMapping("/events")
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
	@GetMapping("/getNoticeListPage")
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
	@GetMapping("/events/{id}")
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
	@GetMapping("/recurit")
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
	@GetMapping("/contactus")
	public WebResult<List<ContentArticleVO>> contactus(){
		ContentArticleVO contactUs = aboutUsService.getContactUs();
		WebResult webResult = new WebResult(contactUs);
		return webResult;
	}

	/**
	 * 查询网贷知识信息
	 * @return
	 */
	@ApiOperation(value = "网贷知识", notes = "查询网贷知识信息")
	@GetMapping("/getKnowReportList")
	public WebResult<List<ContentArticleVO>> getKnowReportList(ContentArticleRequest request ){
		request.setNoticeType("3");
		List<ContentArticleVO> homeNoticeList = aboutUsService.getHomeNoticeList(request);
		WebResult webResult = new WebResult(homeNoticeList);
		return webResult;
	}


	/**
	 * 风险教育信息
	 * @return
	 */
	@ApiOperation(value = "风险教育", notes = "查询风险教育信息")
	@GetMapping("/getFXReportList")
	public WebResult<List<ContentArticleVO>> getFXReportList(ContentArticleRequest request ){
		request.setNoticeType("101");
		List<ContentArticleVO> homeNoticeList = aboutUsService.getHomeNoticeList(request);
		WebResult webResult = new WebResult(homeNoticeList);
		return webResult;
	}



	/**
	 * 帮助中心：注册登录
	 * @return
	 */
	@ApiOperation(value = "帮助中心：注册登录", notes = "帮助中心：注册登录")
	@GetMapping("/index")
	public WebResult<List<ContentArticleVO>> help_index(ContentArticleRequest request ){
		List<Map<String, Object>> homeNoticeList = aboutUsService.getIndex(request);
		WebResult webResult = new WebResult(homeNoticeList);
		return webResult;
	}


	/**
	 * 新手指引(新手进阶)请求

	 * @return
	 */
	@ApiOperation(value = "新手指引(新手进阶)请求", notes = "新手指引(新手进阶)请求")
	@GetMapping("/fresher")
	public WebResult<TotalMessageVO> noviceGuide() {
		WebResult webResult = new WebResult();

		TotalMessageVO totalMessageVO = new TotalMessageVO();
		TotalInvestAndInterestVO totalInvestAndInterestVO = aboutUsService.searchData();
		//投资总额(亿元) tenderSum
		String tenderSum = totalInvestAndInterestVO.getTotalInvestAmount().divide(new BigDecimal("100000000")).setScale(0, BigDecimal.ROUND_DOWN).toString();
		//收益总额(亿元) interestSum
		String interestSum = totalInvestAndInterestVO.getTotalInterestAmount().divide(new BigDecimal("100000000")).setScale(0, BigDecimal.ROUND_DOWN).toString();
		//累计投资人数(万人) totalTenderSum
		int totalTenderSum = totalInvestAndInterestVO.getTotalInvestNum() / 10000;
		//当前时间 date
		String date = GetDate.getDataString(GetDate.date_sdf_wz);
		// TODO 查询用户是否登录
		/*WebViewUser wuser = WebUtils.getUser(request);
		if (wuser == null) {
			modelAndView.addObject("isLogin", "0");//未登陆
		}else{
			modelAndView.addObject("isLogin", "1");//已登陆
		}*/
		totalMessageVO.setDate(date);
		totalMessageVO.setTenderSum(tenderSum);
		totalMessageVO.setInterestSum(interestSum);
		totalMessageVO.setTotalTenderSum(totalTenderSum);
		return webResult;
	}


	/**
	 * 服务中心
	 * @return
	 */
	@ApiOperation(value = "服务中心", notes = "服务中心")
	@GetMapping("/getSecurityPage")
	public WebResult<BanksConfigVO>  getSecurityPage(@RequestParam String pageType) {
		WebResult webResult=null;
		if(StringUtils.isBlank(pageType)){
			// TODO 参数为空转跳页面
			//modelAndView = new ModelAndView("/contentarticle/bank-page");
		}else{
			List<JxBankConfigVO> list = aboutUsService.getBanksList();
			for (JxBankConfigVO banksConfig : list) {
				BigDecimal monthCardQuota = banksConfig.getMonthCardQuota();
				BigDecimal singleQuota = banksConfig.getSingleQuota();
				BigDecimal singleCardQuota = banksConfig.getSingleCardQuota();

				banksConfig.setSingleCardQuota(new BigDecimal(CommonUtils.formatBigDecimal(singleCardQuota.divide(new BigDecimal(10000)))));
				banksConfig.setMonthCardQuota(new BigDecimal(CommonUtils.formatBigDecimal(monthCardQuota.divide(new BigDecimal(10000)))));
			}
			webResult = new WebResult(list);
		}
		return webResult;
	}
}
