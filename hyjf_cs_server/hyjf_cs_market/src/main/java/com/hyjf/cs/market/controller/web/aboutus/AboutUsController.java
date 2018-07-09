/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.web.aboutus;

import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.config.TeamVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.market.service.AboutUsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fuqiang
 * @version AboutUsController, v0.1 2018/7/9 9:40
 */
@Api(value = "信息披露")
@RestController
@RequestMapping("/cs-market/aboutus")
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
}
