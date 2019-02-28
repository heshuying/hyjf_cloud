package com.hyjf.am.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.customize.ContentArticleCustomize;
import com.hyjf.am.config.dao.model.customize.HelpCategoryCustomize;
import com.hyjf.am.config.dao.model.customize.HelpContentCustomize;
import com.hyjf.am.config.service.ContentArticleService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.ContentArticleCustomizeResponse;
import com.hyjf.am.response.config.WechatContentArticleResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.resquest.config.ContentArticleRequest;
import com.hyjf.am.resquest.config.WechatContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.config.WechatContentArticleResultVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RestController
@RequestMapping("/am-config/article")
public class ContentArticleController {
    private final Logger logger = LoggerFactory.getLogger(ContentArticleController.class);

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

    @PostMapping("/getCompanyDynamicsListPage")
    public ContentArticleResponse getCompanyDynamicsListPage(@RequestBody ContentArticleRequest request) {
        ContentArticleResponse response = new ContentArticleResponse();
        int totalPage = contentArticleService.getNoticeListCount(request.getNoticeType());
        if (totalPage > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), totalPage, request.getPageSize());
            List<ContentArticle> list = contentArticleService.searchNoticeList(request.getNoticeType(),paginator.getOffset(),paginator.getLimit());
            if (!CollectionUtils.isEmpty(list)) {
                List<ContentArticleVO> result = CommonUtils.convertBeanList(list, ContentArticleVO.class);
                response.setResultList(result);
                response.setRecordTotal(totalPage);
            }
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
     * @param type
     * @return
     */
    @RequestMapping("/countcontentarticlebytype/{type}")
    public ContentArticleCustomizeResponse countContentArticleByType(@PathVariable String type) {
        ContentArticleCustomizeResponse response = new ContentArticleCustomizeResponse();
        Integer count = contentArticleService.countContentArticleByType(type);
        if (count != null) {
            response.setCount(count);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 查询文章列表
     * @param params
     * @return
     */
    @RequestMapping("/getcontentarticlelistbytype")
    public ContentArticleCustomizeResponse getContentArticleListByType(@RequestBody Map<String, Object> params) {
        logger.info("查询文章列表, params is : {}", JSONObject.toJSONString(params));
        ContentArticleCustomizeResponse response = new ContentArticleCustomizeResponse();
        List<ContentArticleCustomize> list = contentArticleService.getContentArticleListByType(params);
        if (!CollectionUtils.isEmpty(list)) {
            List<ContentArticleCustomizeVO> voList = CommonUtils.convertBeanList(list, ContentArticleCustomizeVO.class);
            response.setResultList(voList);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 上下翻页
     * @param params
     * @return
     */
    @RequestMapping("/getContentArticleFlip")
    public ContentArticleCustomizeResponse getContentArticleFlip(@RequestBody Map<String, Object> params) {
        String offset = (String) params.get("offset");
        ContentArticleCustomizeResponse response = new ContentArticleCustomizeResponse();
        ContentArticleCustomize customize = contentArticleService.getContentArticleFlip(params, offset);
        if (customize != null) {
            logger.info("网贷知识customize is: {}", customize);
            ContentArticleCustomizeVO voList = CommonUtils.convertBean(customize, ContentArticleCustomizeVO.class);
            response.setResult(voList);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 网贷知识,风险教育
     * @author lisheng
     * @return
     */
    @PostMapping("/getKnowsList")
    public ContentArticleResponse getKnowReportList(@RequestBody ContentArticleRequest request) {
        ContentArticleResponse response = new ContentArticleResponse();
        int totalPage = contentArticleService.countHomeNoticeList(request.getNoticeType());
        if (totalPage>0) {
            Paginator paginator = new Paginator(request.getCurrPage(), totalPage, request.getPageSize());
            List<ContentArticle> recordList = contentArticleService.searchHomeNoticeList(request.getNoticeType(), paginator.getOffset(), paginator.getLimit());
            if (recordList != null && recordList.size() != 0) {
                for (int i = 0; i < recordList.size(); i++) {
                    recordList.get(i).setContent((recordList.get(i).getContent().replaceAll("src=\"//", "src=\"" + webHost + "//")));
                }
            }
            List<ContentArticleVO> contentArticleVOS = CommonUtils.convertBeanList(recordList, ContentArticleVO.class);
            response.setResultList(contentArticleVOS);
            response.setRecordTotal(totalPage);
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
    private ContentArticleResponse help_index(@RequestBody ContentArticleRequest request) {
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
        response.setResponseList(AllList);
        return response;
    }


    /**
     * 用于展示发布的信息
     * @param noticeType
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @GetMapping("/find/{noticeType}/{limitStart}/{limitEnd}")
    private ContentArticleResponse appFind(	@PathVariable String noticeType,@PathVariable int limitStart,@PathVariable int limitEnd){
        ContentArticleResponse response = new ContentArticleResponse();
        List<ContentArticle> newsList =
                contentArticleService.searchHomeNoticeList(noticeType, limitStart, limitEnd);
        if (!CollectionUtils.isEmpty(newsList)){
            List<ContentArticleVO> result = CommonUtils.convertBeanList(newsList,ContentArticleVO.class);
            response.setResultList(result);
        }
        return  response;
    }

    /**
     * 微信获取查询文章列表
     * @return
     */
    @PostMapping("/getWechatContentArticleListByType")
    public WechatContentArticleResponse getWechatContentArticleListByType(@RequestBody  WechatContentArticleRequest form) {
        WechatContentArticleResponse result = new WechatContentArticleResponse();
        //result.setEnum(ResultEnum.SUCCESS);
        //返回当前页
        result.setCurrentPage(form.getCurrentPage());
        try {
            // 检查参数正确性
            if (form.getPageSize()<0||form.getCurrentPage()<0){
                return result;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", form.getType());
            params.put("limitStart", -1);
            params.put("limitEnd", -1);
            // 查询总数
            Integer count = contentArticleService.countContentArticleByType(form.getType());
            if (count != null && count > 0) {
                // 构造分页
                if(form.getCurrentPage()<=0){
                    form.setCurrentPage(1);
                }
                if(form.getPageSize()<=0){
                    form.setPageSize(10);
                }
                params.put("limitStart", form.getPageSize() * (form.getCurrentPage() - 1));
                params.put("limitEnd", form.getPageSize()+1);params.put("limitEnd", form.getPageSize()+1);
               // List<WechatContentArticleResultVO> list=
                List<ContentArticleCustomize> contentArticleListByType = contentArticleService.getContentArticleListByType(params);

                List<WechatContentArticleResultVO> list = CommonUtils.convertBeanList(contentArticleListByType, WechatContentArticleResultVO.class);

                if(!CollectionUtils.isEmpty(list)){
                    if(list.size()==(form.getPageSize()+1)) {
                        //不是最后一页
                        result.setEndPage(0);
                    }else {
                        //是最后一页
                        result.setEndPage(1);
                    }
                    list.remove(list.size()-1);
                }
                if (!CollectionUtils.isEmpty(list)) {
                    result.setMessageCount(count);
                    result.setResultList(list);
                } else {
                    result.setMessageCount(0);
                    result.setResultList(new ArrayList<WechatContentArticleResultVO>());
                }
            } else {
                result.setMessageCount(0);
                result.setResultList(new ArrayList<WechatContentArticleResultVO>());
            }
        } catch (Exception e) {
            result.setMessageCount(0);
            result.setResultList(new ArrayList<WechatContentArticleResultVO>());
            return result;
        }
        return result;
    }



}
