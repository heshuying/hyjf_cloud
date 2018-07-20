package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.customize.ContentArticleCustomize;
import com.hyjf.am.config.service.ContentArticleService;
import com.hyjf.am.response.config.ContentArticleCustomizeResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 公司动态
 *
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
     *
     * @author zhangyk
     * @date 2018/7/5 9:40
     */
    @PostMapping("/contentArticleList")
    public ContentArticleResponse getContentArticleList(@RequestBody  ContentArticleRequest request) {
        ContentArticleResponse response = new ContentArticleResponse();
        List<ContentArticle> list = contentArticleService.getContentArticleList(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<ContentArticleVO> result = CommonUtils.convertBeanList(list, ContentArticleVO.class);
            response.setResultList(result);
        }
        return response;
    }

    /**
     * 查询公告列表
     * @author zhangyk
     * @date 2018/7/5 9:40
     */
    @PostMapping("/noticeList")
    public ContentArticleResponse getNotictList(@RequestBody ContentArticleRequest request) {
        ContentArticleResponse response = new ContentArticleResponse();
        List<ContentArticle> list = contentArticleService.getContentArticleList(request);
        if (!CollectionUtils.isEmpty(list)){
            List<ContentArticleVO> result = CommonUtils.convertBeanList(list,ContentArticleVO.class);
            response.setResultList(result);
        }
        return response;
    }



    /**
     * 获取公司简介
     *
     * @return
     */
    @GetMapping("/getaboutus")
    public ContentArticleResponse getAboutUs() {
        ContentArticleResponse response = new ContentArticleResponse();
        ContentArticle contentArticle = contentArticleService.getAboutUs();
        if (contentArticle != null) {
            ContentArticleVO contentArticleVO = new ContentArticleVO();
            BeanUtils.copyProperties(contentArticle, contentArticleVO);
            response.setResult(contentArticleVO);
        }
        return response;
    }

    /**
     * 联系我们
     *
     * @return
     */
    @GetMapping("/getContentArticle")
    public ContentArticleResponse getContactUs() {
        ContentArticleResponse response = new ContentArticleResponse();
        ContentArticle contentArticle = contentArticleService.getContactUs();
        if (contentArticle != null) {
            ContentArticleVO contentArticleVO = new ContentArticleVO();
            BeanUtils.copyProperties(contentArticle, contentArticleVO);
            response.setResult(contentArticleVO);
        }
        return response;
    }

    /**
     * 根据id获取文章
     * @param id
     * @return
     */
    @RequestMapping("/getarticlebyid/{id}")
    public ContentArticleResponse getArticleById(@PathVariable Integer id) {
        ContentArticleResponse response = new ContentArticleResponse();
        ContentArticle contentArticle = contentArticleService.getArticleById(id);
        if (contentArticle != null) {
            ContentArticleVO vo = new ContentArticleVO();
            BeanUtils.copyProperties(contentArticle, vo);
            response.setResult(vo);
        }
        return response;
    }

    /**
     * 查询文章条数
     * @param params
     * @return
     */
    @RequestMapping("/countcontentarticlebytype")
    public ContentArticleCustomizeResponse countContentArticleByType(@RequestBody Map<String, Object> params) {
        ContentArticleCustomizeResponse response = new ContentArticleCustomizeResponse();
        Integer count = contentArticleService.countContentArticleByType(params);
        if (count != null) {
            response.setCount(count);
            return response;
        }
        return null;
    }

    /**
     * 查询文章列表
     * @param params
     * @return
     */
    @RequestMapping("/getcontentarticlelistbytype")
    public ContentArticleCustomizeResponse getContentArticleListByType(@RequestBody Map<String, Object> params) {
        ContentArticleCustomizeResponse response = new ContentArticleCustomizeResponse();
        List<ContentArticleCustomize> list = contentArticleService.getContentArticleListByType(params);
        if (!CollectionUtils.isEmpty(list)) {
            List<ContentArticleCustomizeVO> voList = CommonUtils.convertBeanList(list, ContentArticleCustomizeVO.class);
            response.setResultList(voList);
            return response;
        }
        return null;
    }

}
