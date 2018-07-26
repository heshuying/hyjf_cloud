/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.customize.ContentArticleCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.customize.ContentArticleCustomize;
import com.hyjf.am.config.service.AppContentArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version AppContentArticleServiceImpl, v0.1 2018/7/26 16:10
 */
@Service
public class AppContentArticleServiceImpl implements AppContentArticleService {

    @Autowired
    ContentArticleCustomizeMapper contentArticleCustomizeMapper;

    @Override
    public Integer countContentArticleByType(Map<String, Object> params) {
        return contentArticleCustomizeMapper.countContentArticleByType(params);
    }

    @Override
    public List<ContentArticleCustomize> getContentArticleFlip(Map<String, Object> params) {
        List<ContentArticle> list = contentArticleCustomizeMapper.getContentArticleListByType(params);
        List<ContentArticleCustomize> knowledgeCustomizes=new ArrayList<ContentArticleCustomize>();
        for (ContentArticle contentArticle : list) {
            ContentArticleCustomize customize=new ContentArticleCustomize();
            customize.setTitle(contentArticle.getTitle());
            customize.setTime(new SimpleDateFormat("yyyy-MM-dd").format(contentArticle.getCreateTime()));
            customize.setMessageId(contentArticle.getId()+"");
            //customize.setMessageUrl(PropUtils.getSystem("hyjf.web.host")+AppContentArticleDefine.REQUEST_MAPPING+
            //        AppContentArticleDefine.GET_CONTENT_ARTICLE_ID_ACTION.replace("{contentArticleId}", contentArticle.getId()+"").replace("{type}", (String)params.get("type")));
            customize.setShareTitle(contentArticle.getTitle());
            customize.setShareContent(contentArticle.getSummary());
            customize.setSharePicUrl("https://www.hyjf.com/data/upfiles/image/20140617/1402991818340.png");
            //customize.setShareUrl(PropUtils.getSystem("hyjf.web.host")+AppContentArticleDefine.REQUEST_MAPPING+
            //       AppContentArticleDefine.GET_CONTENT_ARTICLE_ID_ACTION.replace("{contentArticleId}", contentArticle.getId()+"").replace("{type}", (String)params.get("type")));

            knowledgeCustomizes.add(customize);
        }
        return knowledgeCustomizes;
    }
}
