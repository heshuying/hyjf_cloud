/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.ContentArticleService;
import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.resquest.config.ContentArticleRequest;
import com.hyjf.am.vo.admin.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yinhui
 * @version ContentArticleServiceImpl, v0.1 2018/7/18 10:11
 */
@Service
public class ContentArticleServiceImpl implements ContentArticleService {

    @Autowired
    private AmConfigClient amConfigClient;

    @Override
    public ContentArticleResponse searchAction(ContentArticleRequest contentArticleRequestBean) {

        return amConfigClient.searchAction(contentArticleRequestBean);
    }

    @Override
    public ContentArticleResponse inserAction(ContentArticleRequest contentArticleRequestBean) {
        return amConfigClient.inserAction(contentArticleRequestBean);
    }

    @Override
    public ContentArticleResponse infoAction(Integer id) {
        return amConfigClient.findById(id);
    }

    @Override
    public ContentArticleResponse updateAction(ContentArticleRequest contentArticleRequestBean) {
        return amConfigClient.updateAction(contentArticleRequestBean);
    }

    @Override
    public ContentArticleResponse deleteById(Integer id) {
        return amConfigClient.deleteContentArticleById(id);
    }

    @Override
    public List<CategoryVO> putCategory() {
        List<CategoryVO> categoryList = new ArrayList<CategoryVO>();
        {
            CategoryVO category = new CategoryVO();
            category.setId(2);
            category.setTitle("网站公告");
            categoryList.add(category);
        }
        {
            CategoryVO category = new CategoryVO();
            category.setId(3);
            category.setTitle("网贷知识");
            categoryList.add(category);
        }
        {
            CategoryVO category = new CategoryVO();
            category.setId(5);
            category.setTitle("关于我们");
            categoryList.add(category);
        }
        {
            CategoryVO category = new CategoryVO();
            category.setId(101);
            category.setTitle("风险教育");
            categoryList.add(category);
        }
        {
            CategoryVO category = new CategoryVO();
            category.setId(8);
            category.setTitle("联系我们");
            categoryList.add(category);
        }
        {
            CategoryVO category = new CategoryVO();
            category.setId(20);
            category.setTitle("公司动态");
            categoryList.add(category);
        }
        return categoryList;
    }

}
