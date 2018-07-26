/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.app.find.contentarticle;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.market.bean.AppContentArticleBean;
import com.hyjf.cs.market.service.AppContentArticleService;
import com.hyjf.cs.market.service.AppFindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version AppContentArticleController, v0.1 2018/7/26 14:46
 */
@Api(value = "app", description = "app")
@RestController
@RequestMapping("/hyjf-app/find")
public class AppContentArticleController {

    @Autowired
    private AppContentArticleService appContentArticleService;

    /**
     * 根据文章类型获取文章列表
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "知识列表", notes = "知识列表")
    @RequestMapping(value = "/getContentArticleListByType", method = RequestMethod.POST ,produces = "application/json; charset=utf-8")
    public JSONObject getContentArticleListByType(HttpServletRequest request, HttpServletResponse response, @ModelAttribute() AppContentArticleBean form) {
        //LogUtil.startLog(THIS_CLASS, AppContentArticleDefine.GET_CONTENT_ARTICLE_LIST_BY_TYPE_ACTION);
        JSONObject ret = new JSONObject();
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
            if (form.getSize()<0||form.getPage()<0){
                ret.put("status", "1");
                ret.put("statusDesc", "分页参数非法");
                return ret;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", form.getType());
            params.put("limitStart", -1);
            params.put("limitEnd", -1);
            // 查询总数
            Integer count = appContentArticleService.countContentArticleByType(params);

            if (count != null && count > 0) {
                // 构造分页
                params.put("limitStart", form.getSize() * (form.getPage() - 1));
                params.put("limitEnd", form.getSize());
                List<ContentArticleCustomizeVO> list=appContentArticleService.getContentArticleListByType(params,request);

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
            return ret;
        }
        //LogUtil.endLog(THIS_CLASS, AppContentArticleDefine.GET_CONTENT_ARTICLE_LIST_BY_TYPE_ACTION);
        return ret;

    }

}
