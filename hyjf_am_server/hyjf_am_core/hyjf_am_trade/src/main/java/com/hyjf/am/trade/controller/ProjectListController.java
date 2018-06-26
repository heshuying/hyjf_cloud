/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.ProjectDetailResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectListCustomize;
import com.hyjf.am.trade.service.ProjectListService;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 项目列表
 * @author liuyang
 * @version ProjectListController, v0.1 2018/6/13 11:15
 */
@RestController
@RequestMapping("/am-trade/projectlist")
public class ProjectListController {

    @Autowired
   private ProjectListService projectListService;

    /**
     * Web网站首页获取散标推荐
     * @param request
     * @return
     */
    @RequestMapping("/web/searchProjectList")
    public ProjectListResponse searchProjectList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        List<WebProjectListCustomize> list = projectListService.searchProjectList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<WebProjectListCustomizeVO> webProjectListCustomizeVO = CommonUtils.convertBeanList(list,WebProjectListCustomizeVO.class);
            projectListResponse.setResultList(webProjectListCustomizeVO);
        }
        return projectListResponse;
    }

    /**
     * 网站首页获取散标推荐数目
     * @param request
     * @return
     */
    @RequestMapping("/web/countProjectList")
    public ProjectListResponse countProjectList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        int count = projectListService.countProjectList(request);
        projectListResponse.setCount(count);
        return projectListResponse;
    }

    /**
     * Web端散标和新手标的详情
     * @author zhangyk
     * @date 2018/6/23 14:23
     */
    @RequestMapping("/web/searchProjectDetail")
    public ProjectDetailResponse getProjectDetail(@RequestBody @Valid Map map){
        ProjectDetailResponse response = new ProjectDetailResponse();
        ProjectCustomeDetailVO vo = projectListService.getProjectDetail(map);
        response.setResult(vo);
        return response;
    }

    /**
     * @desc  查询web端债转列表count
     * @author zhangyk
     * @date 2018/6/19 15:12
     */
    @RequestMapping("/web/searchWebCreditListCount")
    public CreditListResponse searchCreditListCount(@RequestBody @Valid CreditListRequest request){
        CreditListResponse CreditListResponse = new CreditListResponse();
        int count = projectListService.countCreditList(request);
        CreditListResponse.setCount(count);
        return CreditListResponse;
    }


    /**
     * @desc 查询web端债转列表数据
     * @author zhangyk
     * @date 2018/6/19 15:09
     */
    @RequestMapping("/web/searchWebCreditList")
    public CreditListResponse searchCreditList(@RequestBody @Valid CreditListRequest request){
        CreditListResponse res = new CreditListResponse();
        List<TenderCreditDetailCustomizeVO> list = projectListService.searchCreditList(request);
        res.setResultList(list);
        return res;
    }


    /**
     * 查询web端计划专区上部统计数据
     * @author zhangyk
     * @date 2018/6/21 15:40
     */
    @RequestMapping("/web/planData")
    public ProjectListResponse searchPlanData(@RequestBody ProjectListRequest request){
        ProjectListResponse res = new ProjectListResponse();
        Map<String,Object> map = projectListService.searchPlanData();
        res.setTotalData(map);
        return res;
    }

    /**
     * 查询web端计划专区计划列表count
     * @author zhangyk
     * @date 2018/6/21 15:49
     */
    @RequestMapping("/web/countPlanList")
    public ProjectListResponse countPlanList(@RequestBody @Valid  ProjectListRequest request){
        ProjectListResponse res = new ProjectListResponse();
        int count = projectListService.countWebPlanList(request);
        res.setCount(count);
        return res;
    }


    /**
     * 查询web端计划专区计划列表count
     * @author zhangyk
     * @date 2018/6/21 15:49
     */
    @RequestMapping("/web/searchPlanList")
    public ProjectListResponse searchPlanList(@RequestBody @Valid  ProjectListRequest request){
        ProjectListResponse res = new ProjectListResponse();
        List<WebProjectListCustomizeVO> list= projectListService.searchWebPlanList(request);
        res.setResultList(list);
        return res;
    }




    // --------------------------------------web end------------------------------------------
    //---------------------------------------app start------------------------------------------
    /**
     * App端获取散标投资
     * @param request
     * @return
     */
    @RequestMapping("/app/searchProjectList")
    public ProjectListResponse searchAppProjectList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        List<WebProjectListCustomize> list = projectListService.searchAppProjectList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<WebProjectListCustomizeVO> webProjectListCustomizeVO = CommonUtils.convertBeanList(list,WebProjectListCustomizeVO.class);
            projectListResponse.setResultList(webProjectListCustomizeVO);
        }
        return projectListResponse;
    }

    /**
     * app端散标投资count
     * @param request
     * @return
     */
    @RequestMapping("/app/countProjectList")
    public ProjectListResponse countAppProjectList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        int count = projectListService.countAppProjectList(request);
        projectListResponse.setCount(count);
        return projectListResponse;
    }

    /**
     * App端获取债转投资list
     * @param request
     * @return
     */
    @RequestMapping("/app/searchCreditList")
    public ProjectListResponse searchAppCreditList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        List<WebProjectListCustomize> list = projectListService.searchAppCreditList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<WebProjectListCustomizeVO> webProjectListCustomizeVO = CommonUtils.convertBeanList(list,WebProjectListCustomizeVO.class);
            projectListResponse.setResultList(webProjectListCustomizeVO);
        }
        return projectListResponse;
    }

    /**
     * app端获取债转投资count
     * @param request
     * @return
     */
    @RequestMapping("/app/countCreditList")
    public ProjectListResponse countAppCreditList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        int count = projectListService.countAppCreditList(request);
        projectListResponse.setCount(count);
        return projectListResponse;
    }

    /**
     * app端获取计划投资count
     * @author zhangyk
     * @date 2018/6/22 10:22
     */
    @RequestMapping("/app/countPlanList")
    public ProjectListResponse countAppPlanList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        int count = projectListService.countAppPlanList(request);
        projectListResponse.setCount(count);
        return projectListResponse;
    }

    /**
     * app端获取计划投资count
     * @author zhangyk
     * @date 2018/6/22 10:22
     */
    @RequestMapping("/app/searchPlanList")
    public ProjectListResponse searchAppPlanList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        List<WebProjectListCustomize> list = projectListService.searchAppPlanList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<WebProjectListCustomizeVO> webProjectListCustomizeVO = CommonUtils.convertBeanList(list,WebProjectListCustomizeVO.class);
            projectListResponse.setResultList(webProjectListCustomizeVO);
        }
        return projectListResponse;
    }


    // --------------------------------------app end-------------------------------------------------


}
