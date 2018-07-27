/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.customize.ContentArticleCustomize;
import com.hyjf.am.config.service.AppContentArticleService;
import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.response.config.ContentArticleCustomizeResponse;
import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version AppContentArticleController, v0.1 2018/7/26 16:09
 */
@RestController
@RequestMapping("/am-config/find/contentArticle")
public class AppContentArticleController {

    @Autowired
    AppContentArticleService appContentArticleService;

    /**
     * 获取网贷知识列表总条数
     * @author pcc
     * @param params
     * @return
     */
    @PostMapping("/getContentArticleListByType")
    public ContentArticleResponse countContentArticleByType(@RequestBody Map<String, Object> params) {
        ContentArticleResponse response = new ContentArticleResponse();
        Integer ContentArticleSize = appContentArticleService.countContentArticleByType(params);
        if (ContentArticleSize != null) {
            response.setCount(ContentArticleSize);
            return response;
        }
        return null;
    }

    /**
     * 获取网贷知识列表
     * @author pcc
     * @param params
     * @return
     */
    @PostMapping("/getContentArticleFlip")
    public ContentArticleCustomizeResponse getContentArticleFlip(@RequestBody Map<String, Object> params) {
        ContentArticleCustomizeResponse response = new ContentArticleCustomizeResponse();
        List<ContentArticleCustomize> list = appContentArticleService.getContentArticleFlip(params);
        if (!CollectionUtils.isEmpty(list)) {
            List<ContentArticleCustomizeVO> voList = CommonUtils.convertBeanList(list, ContentArticleCustomizeVO.class);
            response.setResultList(voList);
            return response;
        }
        return null;
    }

}
