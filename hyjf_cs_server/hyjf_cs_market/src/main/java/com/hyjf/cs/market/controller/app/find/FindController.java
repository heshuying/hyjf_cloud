package com.hyjf.cs.market.controller.app.find;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.cs.market.service.AppFindService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lisheng
 * @version FindController, v0.1 2018/7/26 9:39
 */
@Api(value = "app发现页", description = "app发现页")
@RestController
public class FindController {
    @Autowired
    private AppFindService appFindService;

    @Value("${hyjf.web.host}")
    public String webHost;
    private final static String DATE_FORMAT = "yyyy-MM-dd";
    /** REQUEST_MAPPING */
    public static final String REQUEST_MAPPING = "/find/contentArticle";
    /** 获取文章详情  */
    public static final String GET_CONTENT_ARTICLE_ID_ACTION = "/{type}/{contentArticleId}";

    @ResponseBody
    @RequestMapping(value = "/hyjf-app/find")
    public JSONObject find(@RequestHeader(value = "userId")Integer userId) {
        JSONObject ret = new JSONObject();
        List<ContentArticleVO> newsList = appFindService.searchHomeNoticeList("20", 0, 3);
        JSONArray jsonArray = new JSONArray();
        for(ContentArticleVO article:newsList){
            JSONObject detailsJson = new JSONObject();
            detailsJson.put("id", article.getId());
            detailsJson.put("img", article.getImgurl());
            detailsJson.put("title", article.getTitle());
            detailsJson.put("date", DateFormatUtils.format(article.getCreateTime(), DATE_FORMAT));
            detailsJson.put("shareTitle", article.getTitle());
            detailsJson.put("shareContent", article.getSummary());
            detailsJson.put("sharePicUrl", "https://www.hyjf.com/data/upfiles/image/20140617/1402991818340.png");
            detailsJson.put("shareUrl", webHost+ REQUEST_MAPPING+
                    GET_CONTENT_ARTICLE_ID_ACTION.replace("{contentArticleId}", article.getId()+"").replace("{type}", "20"));
            jsonArray.add(detailsJson);
        }
        ret.put("status", "000");
        ret.put("statusDesc", "成功");
        ret.put("newsList", jsonArray);
        ret.put("userid",userId);
        ret.put("status", "000");
        ret.put("statusDesc", "成功");
        return ret;
    }
}

