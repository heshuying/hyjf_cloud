/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.app.find.contentarticle;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.app.AppContentArticleResponse;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.market.contants.ArticleTypeEnum;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.AppContentArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

/**
 * @author dangzw
 * @version AppContentArticleController, v0.1 2018/7/30 23:13
 */
@Api(tags = "app端-APP端文章详情")
@RestController
@RequestMapping(value = "/hyjf-app/find/contentArticleTrue")

public class AppContentArticleController extends BaseMarketController {

    private static final Logger logger = LoggerFactory.getLogger(AppContentArticleController.class);

    /** 获取文章详情  */
    public static final String GET_CONTENT_ARTICLE_ID_ACTION = "/{articleType}/{articleId}";

    @Autowired
    private AppContentArticleService appContentArticleService;

	@ApiOperation(value = "文章详情页", httpMethod = "POST", notes = "文章详情页")
	@PostMapping(value = GET_CONTENT_ARTICLE_ID_ACTION)
	@ResponseBody
	public AppResult getContentArticleById(@PathVariable("articleType") Integer type,
			@PathVariable("articleId") Integer contentArticleId) {
		logger.info("文章详情页: /hyjf-app/find/contentArticle/{articleType}/{articleId}", type, contentArticleId);
		AppContentArticleResponse response = new AppContentArticleResponse();
		response.setRtn("000");
		response.setTopTitle(ArticleTypeEnum.getTitle(type));
		try {
			// 根据id返回文章详情
			ContentArticleVO contentArticle = appContentArticleService.getContentArticleById(contentArticleId);
			if (contentArticle != null) {
				JSONObject details = new JSONObject();
				details.put("title", contentArticle.getTitle());
				details.put("content", contentArticle.getContent());
				details.put("date", new SimpleDateFormat("yyyy-MM-dd").format(contentArticle.getCreateTime()));
				response.setDetails(details);
			} else {
				return new AppResult<>(Response.ERROR, Response.FAIL_MSG);
			}
		} catch (Exception e) {
			return new AppResult<>(Response.ERROR, Response.FAIL_MSG);
		}
		AppResult appResult = new AppResult<>(response);
		appResult.setStatus("000");
		appResult.setStatusDesc("成功！");
		return appResult;
	}
}
