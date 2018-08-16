/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.app.find;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.market.bean.AppContentArticleBean;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.AppFindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version AppFindController, v0.1 2018/7/20 9:29
 */
@Api(tags = "app端-app发现页")
@RestController
@RequestMapping("/hyjf-app/find")
public class AppFindController extends BaseMarketController {
    private static final Logger logger = LoggerFactory.getLogger(AppFindController.class);
    @Autowired
    private AppFindService appFindService;

    @ApiOperation(value = "知识列表", notes = "知识列表")
    @PostMapping(value = "/contentArticle/getContentArticleListByType")
    @ApiParam(required = true, name = "form", value = "查询条件")
    @ResponseBody
    public JSONObject getContentArticleListByType(@ModelAttribute AppContentArticleBean form) {
        logger.info(AppFindController.class.toString(), "startLog -- /hyjf-app/find/contentArticle/getContentArticleListByType");
        JSONObject ret = new JSONObject();
        ret.put("status", "0");
        ret.put("statusDesc", "请求成功");
        ret.put("request", "/hyjf-app/find/contentArticle/getContentArticleListByType");
        try {
            // 检查参数正确性
            if (Validator.isNull(form.getVersion()) || Validator.isNull(form.getPlatform())){
                ret.put("status", "1");
                ret.put("statusDesc", "请求参数非法");
                return ret;
            }
            // 检查参数正确性
            if (form.getSize() < 0 || form.getPage() < 0){
                ret.put("status", "1");
                ret.put("statusDesc", "分页参数非法");
                return ret;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", form.getType());
            params.put("limitStart", -1);
            params.put("limitEnd", -1);
            // 查询总数
            Integer count = appFindService.countContentArticleByType(params);

            if (count != null && count > 0) {
                // 构造分页
                params.put("limitStart", form.getSize() * (form.getPage() - 1));
                params.put("limitEnd", form.getSize());
                List<ContentArticleCustomizeVO> list=appFindService.getContentArticleListByType(params);

                if (!CollectionUtils.isEmpty(list)) {
                    ret.put("messageCount", count);
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
            logger.info(AppFindController.class.toString(), "endLog -- /hyjf-app/find/contentArticle/getContentArticleListByType");
            return ret;
        }
        logger.info(AppFindController.class.toString(), "endLog -- /hyjf-app/find/contentArticle/getContentArticleListByType");
        return ret;

    }

    @ApiOperation(value = "上下翻页", notes = "上下翻页")
    @PostMapping(value = "/contentArticle/getContentArticleFlip")
    @ApiParam(required = true, name = "form", value = "查询条件")
    @ResponseBody
    public JSONObject getContentArticleFlip(@ModelAttribute AppContentArticleBean form) {
        logger.info(AppFindController.class.toString(), "startLog -- /hyjf-app/find/contentArticle/getContentArticleFlip");
        JSONObject ret = new JSONObject();
        ret.put("status", "0");
        ret.put("statusDesc", "请求成功");
        ret.put("request", "/hyjf-app/find/contentArticle/getContentArticleFlip");
        try {

            // 检查参数正确性
            if (Validator.isNull(form.getOffset()) || Validator.isNull(form.getMessageId())){
                ret.put("status", "1");
                ret.put("statusDesc", "请求参数非法");
                return ret;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("messageId", form.getMessageId());
            params.put("type", form.getType());
            // 查询总数
            ContentArticleCustomizeVO contentArticleCustomize = appFindService.getContentArticleFlip(params,form.getOffset());

            if(contentArticleCustomize!=null){
                ret.put("messageId", contentArticleCustomize.getMessageId());
                ret.put("messageUrl", contentArticleCustomize.getMessageUrl());
                ret.put("shareTitle", contentArticleCustomize.getTitle());
                ret.put("shareContent", contentArticleCustomize.getShareContent());
                ret.put("sharePicUrl", contentArticleCustomize.getSharePicUrl());
                ret.put("shareUrl", contentArticleCustomize.getShareUrl());
            }else{
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
            return ret;
        }
        logger.info(AppFindController.class.toString(), "endLog -- /hyjf-app/find/contentArticle/getContentArticleFlip");
        return ret;
    }

    @ApiOperation(value = "根据文章编号查询对应文章", notes = "根据文章编号查询对应文章")
    @GetMapping("/contentArticle/{type}/{contentArticleId}")
    public JSONObject contentArticle (@PathVariable Integer type,
                                      @PathVariable Integer contentArticleId) {
        JSONObject ret = new JSONObject();
        ret.put("status", "0");
        ret.put("statusDesc", "成功");
        ret.put("topTitle", getTopTitle(type));
        try {
            ContentArticleVO contentArticle = appFindService.getContentArticleById(contentArticleId);
            if(contentArticle!=null){
                JSONObject details = new JSONObject();
                details.put("title",contentArticle.getTitle());
                details.put("content",contentArticle.getContent());
                details.put("date",new SimpleDateFormat("yyyy-MM-dd").format(contentArticle.getCreateTime()));
                ret.put("details", details);
            }else{
                ret.put("status", 99);
                ret.put("statusDesc", "失败");
            }
        } catch (Exception e) {
            ret.put("status", 99);
            ret.put("statusDesc", "失败");
        }
        return ret;
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
