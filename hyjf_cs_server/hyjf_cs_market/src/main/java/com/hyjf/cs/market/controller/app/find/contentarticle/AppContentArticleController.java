/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.app.find.contentarticle;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.app.AppContentArticleResponse;
import com.hyjf.am.vo.app.AppContentArticleVO;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.AppContentArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

import static com.hyjf.am.bean.result.BaseResult.FAIL_DESC;

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
    public AppResult getContentArticleById(@PathVariable("articleType") Integer type, @PathVariable("articleId") Integer contentArticleId) {
        logger.info(AppContentArticleController.class.toString(), "startLog -- /hyjf-app/find/contentArticle/{articleType}/{articleId}");
        AppContentArticleResponse response = new AppContentArticleResponse();
        response.setRtn("000");
        response.setTopTitle(getTopTitle(type));
        try {
            // 根据id返回文章详情
            AppContentArticleVO contentArticle = appContentArticleService.getContentArticleById(contentArticleId);
            if(contentArticle != null){
                JSONObject details = new JSONObject();
                details.put("title",contentArticle.getTitle());
                details.put("content",contentArticle.getContent());
                details.put("date",new SimpleDateFormat("yyyy-MM-dd").format(contentArticle.getCreateTime()));
                response.setDetails(details);
            }else{
                return new AppResult<>(Response.ERROR, Response.FAIL_MSG);
            }
        } catch (Exception e) {
            return new AppResult<>(Response.ERROR, Response.FAIL_MSG);
        }
        AppResult appResult = new AppResult<>(response);
        appResult.setStatus("000");
        appResult.setStatusDesc("成功！");
        logger.info(AppContentArticleController.class.toString(), "endLog -- /hyjf-app/find/contentArticle/{articleType}/{articleId}");
        return appResult;
    }

    private String getTopTitle(Integer type) {
        switch (type) {
            case 2:
                return "网站公告";
            case 3:
                return "网贷知识";
            case 5:
                return "关于我们";
            case 101:
                return "风险教育";
            case 8:
                return "联系我们";
            case 20:
                return "公司动态";
            default:
                return "";
        }
    }

}
