package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.service.ContentArticleService;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 公司动态
 * @author zhangyk
 * @date 2018/7/5 9:46
 */
@Api(value = "公司动态")
@RestController
@RequestMapping("/am-config/article")
public class ContentArticleController {


    @Autowired
    private ContentArticleService contentArticleService;

    /**
     * 查询公司动态列表
     * @author zhangyk
     * @date 2018/7/5 9:40
     */
    @PostMapping("/contentArticleList")
    public ContentArticleResponse getContentArticleList(@RequestBody ContentArticleRequest request) {
        ContentArticleResponse response = new ContentArticleResponse();
        List<ContentArticle> list = contentArticleService.getContentArticleList(request);
        if (!CollectionUtils.isEmpty(list)){
            List<ContentArticleVO> result = CommonUtils.convertBeanList(list,ContentArticleVO.class);
            response.setResultList(result);
        }
        return response;
    }
}