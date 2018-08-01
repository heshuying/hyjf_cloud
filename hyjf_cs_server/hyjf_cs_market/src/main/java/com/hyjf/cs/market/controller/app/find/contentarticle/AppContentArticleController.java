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
import static com.hyjf.am.response.Response.FAIL;

/**
 * @author dangzw
 * @version AppContentArticleController, v0.1 2018/7/30 23:13
 */
@Api(tags = "APP端协议接口")
@RestController
@RequestMapping(value = "/find/contentArticle")

public class AppContentArticleController extends BaseMarketController {

    private static final Logger logger = LoggerFactory.getLogger(AppContentArticleController.class);

    /** 类名 */
    public static final String THIS_CLASS = AppContentArticleController.class.getName();
    /** 获取文章详情  */
    public static final String GET_CONTENT_ARTICLE_ID_ACTION = "/{articleType}/{articleId}";

    @Autowired
    private AppContentArticleService appContentArticleService;

    @ApiOperation(value = "APP端协议接口", notes = "协议列表内容的获取")
    @ResponseBody
    @PostMapping(value = GET_CONTENT_ARTICLE_ID_ACTION)
    public AppResult getContentArticleById(@PathVariable("contentArticleId") Integer contentArticleId, @PathVariable("type") Integer type) {
        logger.info("*******************************协议列表内容的获取************************************");
        AppContentArticleResponse response = new AppContentArticleResponse();
        response.setRtn(response.SUCCESS);
        response.setMessage(response.SUCCESS_MSG);
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
            }
        } catch (Exception e) {
            response.setRtn(FAIL);
            response.setMessage(response.FAIL_MSG);
        }
        if(response == null) {
            return new AppResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AppResult<>(FAIL, response.getMessage());
        }
        logger.info(THIS_CLASS, GET_CONTENT_ARTICLE_ID_ACTION);
        return new AppResult<>(response) ;
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
