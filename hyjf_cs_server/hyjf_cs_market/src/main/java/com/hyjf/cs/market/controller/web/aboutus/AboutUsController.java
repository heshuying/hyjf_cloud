/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.web.aboutus;

import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.datacollect.TotalMessageVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.market.bean.ContentArticleBean;
import com.hyjf.cs.market.bean.RechargeDescResultBean;
import com.hyjf.cs.market.service.AboutUsService;
import com.hyjf.cs.market.service.AppFindService;
import com.hyjf.cs.market.util.CdnUrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
	@Autowired
    private AppFindService appFindService;


	@ApiOperation(value = "关于我们", notes = "关于我们")
	@GetMapping("/about")
	public WebResult<Map<String, Object>> aboutus() {
		logger.info("web端获取关于我们数据开始...");
		WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			ContentArticleVO aboutus = aboutUsService.getAboutUs();
			String contents = aboutus.getContent();
			// 获取累计出借金额
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

	@ApiOperation(value = "合作伙伴", notes = "合作伙伴")
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

	@ApiOperation(value = "公司历程", notes = "公司历程")
	@GetMapping("/getCompanyProgress")
	public WebResult<Map<String, Object>> events() {
		logger.info("web端获取公司历程数据开始...");
		WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			List<EventVO> eventVOList = aboutUsService.getEventsList();
			if (!CollectionUtils.isEmpty(eventVOList)) {
				for (EventVO vo : eventVOList) {
					vo.setActTime(GetDate.dateString2Timestamp(vo.getEventTime()));
				}
			}
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

	@ApiOperation(value = "网站公告列表", notes = "网站公告列表")
	@PostMapping("/getNoticeListPage")
	public WebResult<Map<String, Object>> getNoticeListPage(@RequestBody BasePage request) {
		logger.info("web端获取网站公告列表数据开始...");
		WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			List<ContentArticleVO> recordList = aboutUsService.getNoticeListCount(request);
			resultMap.put("contentArticleList", recordList);
			result.setData(resultMap);
            Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
            String type = "2";
            int count = appFindService.countContentArticleByType(type);
            page.setTotal(count);
            result.setPage(page);
			return result;
		} catch (Exception e) {
			logger.error("web端获取网站公告列表失败...", e);
			result.setStatus(WebResult.ERROR);
			result.setStatusDesc(WebResult.ERROR_DESC);
			return result;
		}
	}

	/**
	 * 根据ID获取公司历程详情
	 * @param id
	 * @return
	 * @Author : huanghui
	 */
	@ApiOperation(value = "根据ID获取公司历程详情", notes = "信息披露 - 公司历程")
	@GetMapping(value = "getEventDetailById/{id}")
	public WebResult<Map<String, Object>> getEventDetailById(@PathVariable Integer id){
		WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<>();

		// ID不 能为空
		if (id <= 0 || id == null){
			result.setStatus("404");
			result.setStatusDesc("未找到数据");
			return result;
		}

		try {
			EventVO eventVO = this.aboutUsService.getEventDetailById(id);
			if(eventVO == null){
				result.setStatus("404");
				result.setStatusDesc("未找到数据");
				return result;
			}
			resultMap.put("eventNotice", eventVO);
			result.setData(resultMap);
		}catch (Exception e){
			result.setStatus("404");
			result.setStatusDesc("未找到数据");
		}
		return result;
	}

	@ApiOperation(value = "根据id获取网站公告", notes = "根据id获取网站公告")
	@GetMapping("/getCompanyNoticeDetail/{id}")
	public WebResult<Map<String, Object>> getCompanyNoticeDetail(@PathVariable Integer id) {
		logger.info("web端根据id获取网站公告数据开始...");
		WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			ContentArticleVO contentArticleVO = aboutUsService.getNoticeInfo(id);

			if(contentArticleVO == null){
				result.setStatus("404");
				result.setStatusDesc("未找到数据");
			}else{
				resultMap.put("companyNotice", contentArticleVO);
				result.setData(resultMap);
			}
			return result;
		} catch (Exception e) {
			logger.error("web端获取网站公告列表失败...", e);
			result.setStatus("404");
			result.setStatusDesc("未找到数据");
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
		if(CollectionUtils.isEmpty(jobsList)){
			jobsList = new ArrayList<JobsVo>();
		}
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
		if(contactUs==null){
			contactUs = new ContentArticleVO();
		}
		WebResult webResult = new WebResult(contactUs);
		return webResult;
	}

	/**
	 * 查询网贷知识信息
	 * @return
	 */
	@ApiOperation(value = "网贷知识", notes = "查询网贷知识信息")
	@PostMapping("/getKnowReportList")
	public WebResult<List<ContentArticleVO>> getKnowReportList(@RequestBody ContentArticleBean request ){
        ContentArticleRequest contentArticleRequest = CommonUtils.convertBean(request, ContentArticleRequest.class);
        contentArticleRequest.setNoticeType("3");
		ContentArticleResponse response = aboutUsService.getHomeNoticeList(contentArticleRequest);
		WebResult webResult = new WebResult(response.getResultList());
		Page page = new Page();
		page.setTotal(response.getRecordTotal());
		page.setCurrPage(request.getCurrPage());
        page.setPageSize(request.getPageSize());
		webResult.setPage(page);
		return webResult;
	}


	/**
	 * 风险教育信息
	 * @return
	 */
	@ApiOperation(value = "风险教育", notes = "查询风险教育信息")
	@PostMapping("/getFXReportList")
	public WebResult<List<ContentArticleVO>> getFXReportList(@RequestBody ContentArticleBean request ){
		ContentArticleRequest contentArticleRequest = CommonUtils.convertBean(request, ContentArticleRequest.class);
		contentArticleRequest.setNoticeType("101");
		ContentArticleResponse homeNoticeList = aboutUsService.getHomeNoticeList(contentArticleRequest);
		WebResult webResult = new WebResult(homeNoticeList.getResultList());
		Page page = new Page();
		page.setTotal(homeNoticeList.getRecordTotal());
		page.setCurrPage(request.getCurrPage());
		page.setPageSize(request.getPageSize());
		webResult.setPage(page);
		return webResult;
	}



	/**
	 * 帮助中心：注册登录
	 * @return
	 */
	@ApiOperation(value = "帮助中心：注册登录", notes = "帮助中心：注册登录")
	@PostMapping("/index")
	public WebResult<List<ContentArticleVO>> help_index(@RequestBody ContentArticleRequest request ){
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
	public WebResult<TotalMessageVO> noviceGuide(@RequestHeader(value = "userId" ,required = false) Integer userId ) {

		TotalMessageVO totalMessageVO = new TotalMessageVO();
		//出借总额(亿元) tenderSum
		String tenderSum = aboutUsService.selectTenderSum().divide(new BigDecimal("100000000")).setScale(0, BigDecimal.ROUND_DOWN).toString();
		//收益总额(亿元) interestSum
		String interestSum = aboutUsService.selectInterestSum().divide(new BigDecimal("100000000")).setScale(0, BigDecimal.ROUND_DOWN).toString();
		//累计出借人数(万人) totalTenderSum
		int totalTenderSum = aboutUsService.selectTotalTenderSum() / 10000;
		//当前时间 date
		String date = GetDate.getDataString(GetDate.date_sdf_wz);
		if (userId == null) {
			totalMessageVO.setIsLogin("0");//未登陆
		}else{
			totalMessageVO.setIsLogin("1");//已登陆
		}
		totalMessageVO.setDate(date);
		totalMessageVO.setTenderSum(tenderSum);
		totalMessageVO.setInterestSum(interestSum);
		totalMessageVO.setTotalTenderSum(totalTenderSum);
		WebResult webResult = new WebResult(totalMessageVO);
		return webResult;
	}


	/**
	 * 服务中心
	 * @return
	 */
	@ApiOperation(value = "服务中心", notes = "服务中心")
	@PostMapping("/getSecurityPage")
	public WebResult<JxBankConfigVO>  getSecurityPage(@RequestParam String pageType) {
		WebResult webResult=null;
		if(StringUtils.isBlank(pageType)){
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
	@ApiOperation(value = "充值限额", notes = "充值限额")
	@ResponseBody
	@GetMapping("/rechargeRule")
	public WebResult<RechargeDescResultBean> rechargeRule() {
		WebResult webResult=null;
		List<JxBankConfigVO> list = aboutUsService.getBanksList();
		List<RechargeDescResultBean> result = new ArrayList<RechargeDescResultBean>();
		if (list != null) {
			result = this.conventBanksConfigToResult(list);
		}
		webResult = new WebResult(result);
		return webResult;
	}

	/**
	 * 获取媒体报道（风险教育 +网贷知识）详情
	 * @return
	 */
	@ApiOperation(value = "获取媒体报道（风险教育 +网贷知识）详情", notes = "获取媒体报道（风险教育 +网贷知识）详情")
	@PostMapping("/getMediaReportInfo")
	public WebResult<ContentArticleVO>  getMediaReportInfo(@RequestParam Integer id) {
		WebResult webResult=null;
		// 根据type查询 风险教育 或 媒体报道 或 网贷知识
		ContentArticleVO mediaReport = aboutUsService.getNoticeInfo(id);
		if(mediaReport == null){
			webResult.setStatus("404");
			webResult.setStatusDesc("未找到数据");
			return webResult;
		}
		if(!"".equals(mediaReport.getType()) && mediaReport.getType().equals("101")){
			// 风险教育
			//modelAndView = new ModelAndView(AboutUsDefine.FX_REPORT_INFO_PATH);
		} else if(!"".equals(mediaReport.getType()) && mediaReport.getType().equals("3")){
			// 网贷知识
			//modelAndView = new ModelAndView(AboutUsDefine.MEDIA_REPORT_INFO_PATH);
		}
		if (mediaReport != null) {
			if (mediaReport.getContent().contains("../../../..")) {
				mediaReport.setContent(mediaReport.getContent().replaceAll("../../../..", CdnUrlUtil.getCdnUrl()));
			} else if (mediaReport.getContent().contains("src=\"/")) {
				mediaReport.setContent(mediaReport.getContent().replaceAll("src=\"/", "src=\"" + CdnUrlUtil.getCdnUrl()));
			}
		}
		webResult = new WebResult(mediaReport);
		return webResult;
	}

	/**
	 * 获取公司动态详情
	 * @return
	 */
	@ApiOperation(value = "获取公司动态详情", notes = "获取公司动态详情")
	@GetMapping("/getCompanyDynamicsDetail")
	public WebResult<ContentArticleVO>  getCompanyDynamicsDetail(@RequestParam Integer id) {
		WebResult webResult=null;
		// 根据type查询 风险教育 或 媒体报道 或 网贷知识
		ContentArticleVO mediaReport = aboutUsService.getNoticeInfo(id);
		if (mediaReport != null) {
			if (mediaReport.getContent().contains("../../../..")) {
				mediaReport.setContent(mediaReport.getContent().replaceAll("../../../..", CdnUrlUtil.getCdnUrl()));
			} else if (mediaReport.getContent().contains("src=\"/")) {
				mediaReport.setContent(mediaReport.getContent().replaceAll("src=\"/", "src=\"" + CdnUrlUtil.getCdnUrl()) );
			}
		}

		webResult = new WebResult(mediaReport);
		if(mediaReport == null){
			webResult.setStatus("404");
			webResult.setStatusDesc("未找到数据");
		}
		return webResult;
	}

	/**
	 * 查询公司动态列表
	 * @return
	 */
	@ApiOperation(value = "查询公司动态列表", notes = "查询公司动态列表")
	@PostMapping("/getCompanyDynamicsListPage")
	public WebResult<ContentArticleVO>  getCompanyDynamicsListPage(@RequestBody ContentArticleBean request) {
		ContentArticleRequest contentArticleRequest = CommonUtils.convertBean(request, ContentArticleRequest.class);
		WebResult webResult=null;
		// 根据type查询 风险教育 或 媒体报道 或 网贷知识
		contentArticleRequest.setNoticeType("20");
		ContentArticleResponse response = aboutUsService.getCompanyDynamicsListPage(contentArticleRequest);
		List<ContentArticleVO> companyDynamicsList = response.getResultList();
		for (ContentArticleVO companyDynamics : companyDynamicsList) {
			if (companyDynamics.getContent().contains("../../../..")) {
				companyDynamics.setContent(companyDynamics.getContent().replaceAll("../../../..", CdnUrlUtil.getCdnUrl()));
			} else if (companyDynamics.getContent().contains("src=\"/")) {
				companyDynamics.setContent(companyDynamics.getContent().replaceAll("src=\"/",
						"src=\"" + CdnUrlUtil.getCdnUrl())
						);
			}
		}
		webResult = new WebResult(companyDynamicsList);
		Page page = new Page();
		page.setTotal(response.getRecordTotal());
		page.setCurrPage(request.getCurrPage());
		page.setPageSize(request.getPageSize());
		webResult.setPage(page);


		return webResult;
	}


	private List<RechargeDescResultBean> conventBanksConfigToResult( List<JxBankConfigVO> configs) {
		List<RechargeDescResultBean> list = new ArrayList<RechargeDescResultBean>();
		if (!CollectionUtils.isEmpty(configs)) {
			//RechargeDescResultBean bean = null;
			for (JxBankConfigVO config : configs) {
				RechargeDescResultBean bean = new RechargeDescResultBean();
				bean.setBankName(config.getBankName());
				//单卡单日限额
				if(config.getSingleCardQuota().compareTo(BigDecimal.ZERO)==0){
					bean.setDay("不限");
				}else{
					bean.setDay(String.valueOf(config.getSingleCardQuota().divide(new BigDecimal(10000)))+" 万元");
				}
				//单笔限额
				if(config.getSingleQuota().compareTo(BigDecimal.ZERO)==0){
					bean.setOnce("不限");
				}else{
					bean.setOnce(String.valueOf(config.getSingleQuota().divide(new BigDecimal(10000)))+" 万元");
				}
				//单月单卡限额
				if(config.getMonthCardQuota().compareTo(BigDecimal.ZERO)==0){
					bean.setMonth("不限");
				}else{
					bean.setMonth(String.valueOf(config.getMonthCardQuota().divide(new BigDecimal(10000)))+" 万元");
				}
				list.add(bean);
			}
		}
		return list;
	}
}
