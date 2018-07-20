package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.customize.ContentArticleCustomize;
import com.hyjf.am.config.dao.model.customize.HelpCategoryCustomize;
import com.hyjf.am.config.dao.model.customize.HelpContentCustomize;
import com.hyjf.am.config.service.ContentArticleService;
import com.hyjf.am.response.config.ContentArticleCustomizeResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.resquest.config.ContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Value("${hyjf.web.host}")
    public String webHost;


    /**
     * 查询公司动态列表
     *
     * @author zhangyk
     * @date 2018/7/5 9:40
     */
    @PostMapping("/contentArticleList")
    public ContentArticleResponse getContentArticleList(@RequestBody ContentArticleRequest request) {
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
     *@author lisheng
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
     *@author lisheng
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

    /**
     * 网贷知识,风险教育
     * @author lisheng
     * @return
     */
    @PostMapping("/getKnowsList")
    public ContentArticleResponse getKnowReportList(ContentArticleRequest request) {
        ContentArticleResponse response = new ContentArticleResponse();

        request.setNoticeType("3");
        int totalPage = contentArticleService.countHomeNoticeList(request.getNoticeType());
        if (totalPage>0) {
            List<ContentArticle> recordList = contentArticleService.searchHomeNoticeList(request.getNoticeType(), request.getLimitStart(), request.getLimitEnd());
            if (recordList != null && recordList.size() != 0) {
                for (int i = 0; i < recordList.size(); i++) {
                    recordList.get(i).setContent((recordList.get(i).getContent().replaceAll("src=\"//", "src=\"" + webHost + "//")));
                }
            }
            List<ContentArticleVO> contentArticleVOS = CommonUtils.convertBeanList(recordList, ContentArticleVO.class);
            response.setResultList(contentArticleVOS);
        }
        return response;
    }

    /**
     * 帮助中心索引页面
     *@author lisheng
     * @param request
     * @return
     */
    @PostMapping("/index")
    private ContentArticleResponse help_index(ContentArticleRequest request) {
        ContentArticleResponse response = new ContentArticleResponse();
        // 查出帮助中心分类
        List<HelpCategoryCustomize> list = contentArticleService.selectCategory("help");
        List<Map<String, Object>> AllList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> tmpmap = new HashMap<String, Object>();
            tmpmap.put("HelpCategoryCustomize", list.get(i));
            // 查出帮助中心子分类
            List<HelpCategoryCustomize> listsun = contentArticleService.selectSunCategory(list.get(i).getId() + "");
            if (listsun != null) {
                for (int j = 0; j < listsun.size(); j++) {
                    List<HelpContentCustomize> listsunContent = contentArticleService.selectSunContentCategory(
                            String.valueOf(listsun.get(j).getId()), String.valueOf(list.get(i).getPid()));
                    listsun.get(j).setListsunContent(listsunContent);
                    // tmpmap.put("listsunContent", listsunContent);
                }
                tmpmap.put("listsun", listsun);
            }
            AllList.add(tmpmap);
        }

        return response;
    }






}
