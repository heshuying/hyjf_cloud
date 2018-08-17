package com.hyjf.am.config.controller.batch;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.service.ContentArticleService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 读取最新的 5 条公司动态,大屏幕使用(上海)
 * @Author : huanghui
 */
@RestController
@RequestMapping("/am-config/contentArticle")
public class ContentArticleController extends BaseConfigController {

    @Autowired
    private ContentArticleService contentArticleService;

    @GetMapping("/getContentList")
    public void getContentList(){
        //查询指定栏目的文章
        String type = "20";

        List<ContentArticle> contentArticleList = this.contentArticleService.getContentArticListByType(type);

        JSONObject jsonObject= new JSONObject();
        jsonObject.put("data", contentArticleList);

        //写入Redis
        RedisUtils.set(RedisConstants.SH_OPERATIONAL_DATA + RedisConstants.ARTICLE_LIST , jsonObject.toString(), 3600);
    }

}
