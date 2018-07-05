package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.ContentArticleMapper;
import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.auto.ContentArticleExample;
import com.hyjf.am.config.service.ContentArticleService;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentArticleServiceImpl implements ContentArticleService {

    @Autowired
    private ContentArticleMapper contentArticleMapper;

    @Override
    public List<ContentArticle> getContentArticleList(ContentArticleRequest request) {
        ContentArticleExample example = new ContentArticleExample();
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        ContentArticleExample.Criteria crt = example.createCriteria();
        crt.andTypeEqualTo(request.getNoticeType());
        crt.andStatusEqualTo(1);
        example.setOrderByClause("create_time Desc");
        List<ContentArticle> list = contentArticleMapper.selectByExample(example);
        return list;
    }
}
